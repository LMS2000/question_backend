package com.lms.question.service.impl;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lms.contants.HttpCode;
import com.lms.question.client.OssClient;
import com.lms.question.config.OssProperties;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.*;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.LoginUserVo;
import com.lms.question.entity.vo.UserVo;
import com.lms.question.exception.BusinessException;
import com.lms.question.mapper.BankMapper;
import com.lms.question.mapper.UserMapper;
import com.lms.question.service.IUserService;
import com.lms.question.utis.MybatisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.lms.question.constants.BankConstant.PUBLISHED;
import static com.lms.question.constants.BankConstant.UNPUBLISHED;
import static com.lms.question.constants.FileConstants.STATIC_REQUEST_PREFIX;
import static com.lms.question.constants.UserConstant.*;
import static com.lms.question.entity.factory.factory.UserFactory.USER_CONVERTER;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;



//    @Resource
//    private BankMapper bankMapper;


    @Resource
    private OssClient ossClient;



    @Resource
    private OssProperties ossProperties;


    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "luomosan";


    @Override
    public Integer addUser(AddUserDto addUserDto) {

        String username = addUserDto.getUsername();
        //校验重复的用户名
        BusinessException.throwIf(this.count(new QueryWrapper<User>().eq("username",username))>0,HttpCode.PARAMS_ERROR,"用户名重复");

        String password = addUserDto.getPassword();

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        User user=new User();
        BeanUtils.copyProperties(addUserDto,user);
        user.setPassword(encryptPassword);
        this.save(user);

        return user.getUid();
    }

    @Override
    public Boolean registerUser(RegisterUserDto registerUserDto) {

        String username = registerUserDto.getUsername();
        //校验重复的用户名
        BusinessException.throwIf(this.count(new QueryWrapper<User>().eq("username",username))>0,HttpCode.PARAMS_ERROR,"用户名重复");

        String password = registerUserDto.getPassword();

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        User user=new User();
        BeanUtils.copyProperties(registerUserDto,user);
        user.setUserRole(DEFAULT_ROLE);
        user.setNickname("user");
        user.setPassword(encryptPassword);

        return  this.save(user);
    }

    @Override
    public LoginUserVo userLogin(LoginDto loginDto, HttpServletRequest request) {
        // 1. 校验
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username",username)
                .eq("password",encryptPassword));
        // 用户不存在
        if (user == null) {
//            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(HttpCode.PARAMS_ERROR, "用户不存在或密码错误");
        }
        // 3. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        LoginUserVo loginUserVo=new LoginUserVo();
        BeanUtils.copyProperties(user,loginUserVo);
        return loginUserVo;

    }

    @Override
    public Boolean updateUser(UpdateUserDto userDto) {
        Integer userId = userDto.getUid();
        //不可以修改超级管理员
        BusinessException.throwIfOperationAdmin(userId);
        BusinessException.throwIfNot(MybatisUtils.existCheck(this,Map.of("uid",userId,
                "delete_flag",NOT_DELETED)));
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        //判断使用配额是否高于修改后用户总容量
        User byId = this.getById(userId);

       return this.updateById(user);
    }

    @Override
    public LoginUserVo getLoginUser(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) attribute;
        if (currentUser == null || currentUser.getUid() == null) {
            throw new BusinessException(HttpCode.NOT_LOGIN_ERROR, "未登录");
        }
        Integer id = currentUser.getUid();
//        User byId = this.getById(id);  根据用户id查找用户
        User byId = userMapper.selectById(id);
        if (byId == null) {
            throw new BusinessException(HttpCode.NOT_LOGIN_ERROR, "未登录");
        }
        LoginUserVo loginUserVo=new LoginUserVo();

        BeanUtils.copyProperties(byId,loginUserVo);
        return loginUserVo;
    }

    @Override
    public boolean isAdmin(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);

        User user = (User) attribute;

        return user != null && ADMIN_ROLE.equals(user.getUserRole());
    }

    @Override
    public boolean userLogout(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(USER_LOGIN_STATE);

        if (attribute == null) {
            throw new BusinessException(HttpCode.NOT_LOGIN_ERROR, "未登录");
        }

        request.getSession().removeAttribute(USER_LOGIN_STATE);

        return true;
    }

    @Override
    public Page<UserVo> pageUser(QueryUserPageDto userPageDto) {
        Integer enable = userPageDto.getEnable();
        String username = userPageDto.getUsername();
        Integer pageNum = userPageDto.getPageNum();
        Integer pageSize = userPageDto.getPageSize();
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.like(StringUtils.isNotBlank(username), "username", userPageDto.getUsername())
                .eq(validEnable(enable), "enable", userPageDto.getEnable())
                .eq("delete_flag", NOT_DELETED);
        Page<User> page = this.page(new Page<>(pageNum, pageSize), userQueryWrapper);
        List<User> records = page.getRecords();

        List<UserVo> userVos = USER_CONVERTER.toListUserVo(records);
        Page<UserVo> result = new Page<>(pageNum, pageSize, page.getTotal());
        result.setRecords(userVos);
        return result;
    }
    public boolean validEnable(Integer enable) {
        return ObjectUtils.isNotEmpty(enable) && (ENABLE.equals(enable) || DISABLE.equals(enable));
    }






    @Override
    public Boolean enableUser(Integer id) {
        BusinessException.throwIf(id==1);
        return updateById(User.builder().uid(id).enable(ENABLE).build());
    }

    @Override
    public Boolean disableUser(Integer id) {
        BusinessException.throwIf(id==1);
        return updateById(User.builder().uid(id).enable(UserConstant.DISABLE).build());
    }




    /**
     * 删除用户,使用户不可用，逻辑删除
     *
     * @param uids
     */
    @Override
    public Boolean deleteUser(List<Integer> uids) {
        //不可以删除超级管理员
        BusinessException.throwIf(uids.contains(1));
        //集合包括不存在的用户
        List<User> userIdList = this.list(new QueryWrapper<User>().in("uid", uids));
        BusinessException.throwIf(userIdList.size() != uids.size());
        //还得删除用户角色表信息？
        return this.update(new UpdateWrapper<User>().set("delete_flag", DELETED).in("uid", uids));
    }

    @Override
    public Boolean resetPassword(ResetPasswordDto resetPasswordDto, Integer uid) {
        String oldPassword = resetPasswordDto.getOldPassword();
        String newPassword = resetPasswordDto.getNewPassword();

        String encodeOldPassword =  DigestUtils.md5DigestAsHex((SALT + oldPassword).getBytes());
        //密码不对就报错
        BusinessException
                .throwIfNot(MybatisUtils.existCheck(this, Map.of("uid", uid,
                        "password", encodeOldPassword)), HttpCode.PARAMS_ERROR);

        //如果新旧密码一致就直接返回
        if (oldPassword.equals(newPassword)) {
            return false;
        }
        String encodeNewPassword =  DigestUtils.md5DigestAsHex((SALT + newPassword).getBytes());
        return updateById(User.builder().uid(uid).password(encodeNewPassword).build());
    }

    @Override
    public String uploadAvatar(MultipartFile file, Integer uid) {
        //校验文件
        validFile(file);

        User user = this.getById(uid);
        String bucketName = "bucket_user_" + uid;


        if (!user.getAvatar().equals("#")) {
            String[] split = user.getAvatar().split(bucketName);
            ossClient.deleteObject(bucketName, split[1]);
        }

        //上传文件

        String filePath;
        try {
            String randomPath =
                    com.lms.question.utis.FileUtil.generatorFileName(file.getOriginalFilename() == null ? file.getName() : file.getOriginalFilename());
            filePath = "avatar/" + randomPath;
            ossClient.putObject(bucketName, filePath, file.getInputStream());

        } catch (IOException e) {
            throw new BusinessException(HttpCode.OPERATION_ERROR, "上传头像失败");
        }

        String fileUrl = com.lms.question.utis.FileUtil.getFileUrl(ossProperties.getEndpoint(), STATIC_REQUEST_PREFIX, bucketName, filePath);
        this.updateById(User.builder().uid(uid).avatar(fileUrl).build());

        return fileUrl;
    }

    /**
     * 修改当前用户信息
     * @param userDto
     * @param request
     * @return
     */
    @Override
    public Boolean updateCurrentUser(UpdateCurrentUserDto userDto, HttpServletRequest request) {

        Integer uid = this.getLoginUser(request).getUid();
        return this.updateById(User.builder().uid(uid)
                .nickname(userDto.getNickname()).email(userDto.getEmail()).build());
    }

    private void validFile(MultipartFile multipartFile) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024 * 10L;
        if (fileSize > ONE_M) {
            throw new BusinessException(HttpCode.PARAMS_ERROR, "文件大小不能超过 10M");
        }
        if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
            throw new BusinessException(HttpCode.PARAMS_ERROR, "文件类型错误");
        }
    }


}
