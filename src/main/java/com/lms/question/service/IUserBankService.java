package com.lms.question.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.dto.QueryUserBankDto;
import com.lms.question.entity.vo.UserBankVo;

public interface IUserBankService extends IService<UserBank> {


    //查看用户的练习记录和考试记录

    Page<UserBankVo> pageUserBank(QueryUserBankDto queryUserBankDto);


}
