package com.lms.question.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.*;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.PublishBankVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//题库管理
public interface IBankService extends IService<Bank> {

    //curd


    Boolean  addBank(AddBankDto addBankDto);

    Boolean  removeBank(List<Integer> bids);

    Page<BankVo> pageBankList(QueryBankPageDto queryBankPageDto);


    Boolean updateBank(UpdateBankDto updateBankDto);

    BankVo getBankById(Integer bid);

    //发布或者下架题库
    Boolean changePublishBank(ChangePublishStatusDto changePublishStatusDto);
    //获取发布后的题库

    PublishBankVo pagePublishBankList(QueryPublishDto queryPublishDto, HttpServletRequest request);

}
