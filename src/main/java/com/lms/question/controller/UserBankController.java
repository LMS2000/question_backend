package com.lms.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.entity.dto.QueryUserBankDto;
import com.lms.question.entity.vo.UserBankVo;
import com.lms.question.service.IUserBankService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userBank")
@EnableResponseAdvice
public class UserBankController {

    @Resource
    private IUserBankService userBankService;



    @PostMapping("/page")
    public Page<UserBankVo> userBankVoPage(@RequestBody QueryUserBankDto queryUserBankDto){
        return userBankService.pageUserBank(queryUserBankDto);
    }
}
