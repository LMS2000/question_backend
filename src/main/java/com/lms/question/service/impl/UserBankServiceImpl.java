package com.lms.question.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.dto.QueryUserBankDto;
import com.lms.question.mapper.BankMapper;
import com.lms.question.mapper.UserBankMapper;
import com.lms.question.service.IBankService;
import com.lms.question.service.IUserBankService;
import com.lms.question.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements IUserBankService {


    @Resource
    private IUserService userService;


    @Resource
    private  IBankService bankService;
    @Override
    public Page<UserBank> pageUserBank(QueryUserBankDto queryUserBankDto) {


        return null;
    }
}
