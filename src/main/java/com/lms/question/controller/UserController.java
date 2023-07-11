package com.lms.question.controller;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.contants.HttpCode;
import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.*;
import com.lms.question.entity.vo.LoginUserVo;
import com.lms.question.entity.vo.UserVo;
import com.lms.question.exception.BusinessException;
import com.lms.question.service.IUserService;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.lms.question.constants.UserConstant.ENABLE;

@RestController
@RequestMapping("/user")
@EnableResponseAdvice
@Api(description = "用户管理")
public class UserController {

    @Resource
    private IUserService userService;


    /**
     * 登录
     * @param loginDto
     * @param request
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public LoginUserVo userLogin(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request){
        LoginUserVo loginUserVo = userService.userLogin(loginDto, request);
        return loginUserVo;
    }


    @PostMapping("/register")
    @ApiOperation("用户注册")
    public Boolean  userRegister(@Validated @RequestBody RegisterUserDto registerUserDto){
        return userService.registerUser(registerUserDto);
    }

    /**
     * 注销
     * @param request
     * @return
     */
   @PostMapping("/logout")
   @ApiOperation("用户登出")
   public Boolean logout(HttpServletRequest request){
        if (request == null) {
            throw new BusinessException(HttpCode.PARAMS_ERROR);
        }
        return userService.userLogout(request);
    }


    /**
     * 获取当前用户
     * @param request
     * @return
     */
    @GetMapping(value = "/get/login")
    @ApiOperation("获取当前用户的登录信息")
    public LoginUserVo getCurrentUser(HttpServletRequest request){
        LoginUserVo loginUser = userService.getLoginUser(request);
        return loginUser;
    }




    /**
     * 添加用户
     *
     * @param userDto
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Integer add(@Validated @RequestBody(required = true) AddUserDto userDto) {
        return userService.addUser(userDto);
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @PostMapping("/delete")
    @ApiOperation("批量删除用户")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean removeUser( @RequestParam("userIds") List<Integer> userIds){
        return userService.deleteUser(userIds);
    }





    /**
     * 修改当前用户的密码
     * @param resetPasswordDto
     * @return
     */
    @PostMapping("/resetPassword")
    public Boolean resetPassword(@RequestBody ResetPasswordDto resetPasswordDto,HttpServletRequest request) {
        Integer uid = userService.getLoginUser(request).getUid();
        return  userService.resetPassword(resetPasswordDto, uid);
    }

    /**
     * 上传头像
     *
     * @param file
     * @return 返回头像图片地址0
     */
    @PostMapping("/uploadAvatar")
    public String uploadAvatar(@RequestBody MultipartFile file,HttpServletRequest request) {
        Integer uid = userService.getLoginUser(request).getUid();
        return userService.uploadAvatar(file, uid);
    }


    /**
     * 分页条件获取用户列表
     * @param userPageDto
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/page")
    public Page<UserVo> getUserPage( @RequestBody QueryUserPageDto userPageDto) {
        Page<UserVo> userVoPage = userService.pageUser(userPageDto);
        return userVoPage;
    }


    /**
     * 启用或者禁用用户
     * @param changeUserEnableDto
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/change/enable")
    public Boolean changeUserEnable(@Validated @RequestBody ChangeUserEnableDto changeUserEnableDto) {
        Integer enable = changeUserEnableDto.getEnable();
        if (enable.equals(ENABLE)) {
            return userService.enableUser(changeUserEnableDto.getUid());
        } else {
            return userService.disableUser(changeUserEnableDto.getUid());
        }
    }

    /**
     * 修改用户
     * @param userDto
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean updateUser(@Validated @RequestBody UpdateUserDto userDto){
        return userService.updateUser(userDto);
    }

    @PostMapping("/update/current")
    public Boolean updateCurrentUser(@Validated @RequestBody  UpdateCurrentUserDto userDto,HttpServletRequest request){
        return userService.updateCurrentUser(userDto,request);
    }

//    /**
//     * 修改用户密码
//     * @param password
//     * @param userId
//     * @return
//     */
//    @PostMapping("/reset/{password}/{userId}")
//    public Boolean resetPwd(@PathVariable("password") String password,
//                            @PathVariable("userId") Integer userId){
//        return   userService.resetPassword(password,userId);
//    }

}
