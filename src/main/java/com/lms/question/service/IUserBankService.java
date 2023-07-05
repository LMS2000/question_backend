package com.lms.question.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.dto.QueryUserBankDto;

public interface IUserBankService extends IService<UserBank> {


    //查看用户的练习记录和考试记录

    Page<UserBank> pageUserBank(QueryUserBankDto queryUserBankDto);


}
