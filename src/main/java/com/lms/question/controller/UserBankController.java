package com.lms.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.QueryUserBankDto;
import com.lms.question.entity.vo.UserBankVo;
import com.lms.question.service.IUserBankService;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userBank")
@EnableResponseAdvice
@Api( description = "用户题库管理")
public class UserBankController {

    @Resource
    private IUserBankService userBankService;


    /**
     * 获取用户练习记录 可以按照用户名和题库名模糊查询，以及按照练习模式和是否已经提交
     * @param queryUserBankDto
     * @return
     */
    @PostMapping("/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation("根据ubid用户练习记录id获取题目列表和临时答题记录")
    public Page<UserBankVo> userBankVoPage(@RequestBody QueryUserBankDto queryUserBankDto){
        return userBankService.pageUserBank(queryUserBankDto);
    }

    /**
     * 获取当前用户的条件分页练习记录
     * @param queryUserBankDto
     * @param request
     * @return
     */
    @PostMapping("/page/user")
    @ApiOperation("获取当前用户的条件分页练习记录")
    public Page<UserBankVo> getCurrentUserBanks(@RequestBody QueryUserBankDto queryUserBankDto, HttpServletRequest request){
        return userBankService.getCurrentUserBanks(queryUserBankDto,request);
    }







}
