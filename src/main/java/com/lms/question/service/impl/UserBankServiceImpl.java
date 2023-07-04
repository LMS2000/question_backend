package com.lms.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.mapper.BankMapper;
import com.lms.question.mapper.UserBankMapper;
import com.lms.question.service.IBankService;
import com.lms.question.service.IUserBankService;
import org.springframework.stereotype.Service;

@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements IUserBankService {


}
