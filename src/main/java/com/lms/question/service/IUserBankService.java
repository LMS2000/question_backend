package com.lms.question.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.dto.QueryCurrentUserBanksDto;
import com.lms.question.entity.dto.QueryUserBankDto;
import com.lms.question.entity.vo.UserBankVo;

import javax.servlet.http.HttpServletRequest;

public interface IUserBankService extends IService<UserBank> {


    //查看用户的练习记录和考试记录

    Page<UserBankVo> pageUserBank(QueryUserBankDto queryUserBankDto);


    //获取用户最近未提交的练习记录
    UserBankVo getUserNotRecentlySubmitted(Integer bid,Integer type, HttpServletRequest request);

    //查看用户的练习记录
    Page<UserBankVo> getCurrentUserBanks(QueryCurrentUserBanksDto queryCurrentUserBanksDto, HttpServletRequest request);
}
