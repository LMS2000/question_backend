package com.lms.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.dto.QueryUserBankDto;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.UserBankVo;
import com.lms.question.mapper.BankMapper;
import com.lms.question.mapper.UserBankMapper;
import com.lms.question.service.IBankService;
import com.lms.question.service.IUserBankService;
import com.lms.question.service.IUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lms.question.constants.BankConstant.*;
import static com.lms.question.constants.UserConstant.NOT_DELETED;
import static com.lms.question.entity.factory.factory.BankFactory.BANK_CONVERTER;
import static com.lms.question.entity.factory.factory.UserBankFactory.USER_BANK_CONVERTER;
import static com.lms.question.entity.factory.factory.UserFactory.USER_CONVERTER;

@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank> implements IUserBankService {


    @Resource
    private IUserService userService;


    @Resource
    private  IBankService bankService;
    @Override
    public Page<UserBankVo> pageUserBank(QueryUserBankDto queryUserBankDto) {
        String bankName = queryUserBankDto.getBankName();
        Integer type = queryUserBankDto.getType();
        String username = queryUserBankDto.getUsername();
        Integer pageSize = queryUserBankDto.getPageSize();
        Integer pageNum = queryUserBankDto.getPageNum();
        Integer submit = queryUserBankDto.getSubmit();

        //先获取两个列表map


        //
        Map<Integer, User> userMap = userService.list(null).stream()
                .collect(Collectors.toMap(User::getUid, Function.identity()));

        Map<Integer, Bank> bankMap = bankService.list(null).stream()
                .collect(Collectors.toMap(Bank::getId, Function.identity()));


        List<Integer> bids=null;
        if(StringUtils.isNotBlank(bankName)){
            // 查找bid
            bids = bankService.list(new QueryWrapper<Bank>()
                    .like("name", bankName)).stream().map(Bank::getId).collect(Collectors.toList());

        }
        List<Integer> uids =null;
        if(StringUtils.isNotBlank(username)){
            uids = userService.list(new QueryWrapper<User>()
                    .like("username", username)).stream().map(User::getUid).collect(Collectors.toList());
        }
//
        Page<UserBank> page = this.page(new Page<>(pageNum, pageSize), new QueryWrapper<UserBank>()
                .in(ObjectUtils.isNotEmpty(uids), "user_id", uids)
                .in(ObjectUtils.isNotEmpty(bids), "bank_id", bids)
                .eq(validType(type), "type", type)
                .eq(validType(submit),"submit",submit));

        List<UserBank> records = page.getRecords();
        Page<UserBankVo> userBankVoPage=new Page<>(pageNum,pageSize,page.getTotal());
        List<UserBankVo> userBankVos = USER_BANK_CONVERTER.toListUserBankVo(records);
        userBankVos.forEach(userBankVo -> {
            Integer userId = userBankVo.getUserId();
            Integer bankId = userBankVo.getBankId();
             userBankVo.setUser(USER_CONVERTER.toUserVo(userMap.getOrDefault(userId, null)));
             userBankVo.setBank(BANK_CONVERTER.toBankVo(bankMap.getOrDefault(bankId,null)));
        });
        userBankVoPage.setRecords(userBankVos );
        return userBankVoPage;
    }


    private boolean validType(Integer type){
        return ObjectUtils.isNotEmpty(type)&&(type.equals(SUBMITTED)||type.equals(NOT_SUBMMITTED));
    }
}
