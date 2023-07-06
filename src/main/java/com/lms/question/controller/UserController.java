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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.lms.question.constants.UserConstant.ENABLE;

@RestController
@RequestMapping("/user")
@EnableResponseAdvice
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
    public LoginUserVo userLogin(@Validated @RequestBody LoginDto loginDto, HttpServletRequest request){
        LoginUserVo loginUserVo = userService.userLogin(loginDto, request);
        return loginUserVo;
    }


    public Boolean  userRegister(@Validated @RequestBody RegisterUserDto registerUserDto){
        return userService.registerUser(registerUserDto);
    }

    /**
     * 注销
     * @param request
     * @return
     */
   @PostMapping("/logout")
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
    public LoginUserVo getCurrentUser(HttpServletRequest request){
        LoginUserVo loginUser = userService.getLoginUser(request);
        return loginUser;
    }




    /**
     * 注册
     *
     * @param userDto
     * @return
     */
    @PostMapping("/add")
    public Integer add(@Validated @RequestBody(required = true) AddUserDto userDto) {
        return userService.addUser(userDto);
    }

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    @PostMapping("/delete")
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
    public Boolean updateUser(@Validated @RequestBody UpdateUserDto userDto){
        return userService.updateUser(userDto);
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
