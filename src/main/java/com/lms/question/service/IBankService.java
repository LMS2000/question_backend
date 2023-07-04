package com.lms.question.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.AddBankDto;
import com.lms.question.entity.dto.QueryBankPageDto;
import com.lms.question.entity.dto.UpdateBankDto;
import com.lms.question.entity.vo.BankVo;

import java.util.List;

//题库管理
public interface IBankService extends IService<Bank> {

    //curd


    Boolean  addBank(AddBankDto addBankDto);

    Boolean  removeBank(List<Integer> bids);

    Page<BankVo> pageBankList(QueryBankPageDto queryBankPageDto);


    Boolean updateBank(UpdateBankDto updateBankDto);

    BankVo getBankById(Integer bid);
}
