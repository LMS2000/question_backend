package com.lms.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.*;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.UserVo;
import com.lms.question.exception.BusinessException;
import com.lms.question.mapper.BankMapper;
import com.lms.question.mapper.UserMapper;
import com.lms.question.service.IBankService;
import com.lms.question.service.IUserService;
import com.lms.question.utis.MybatisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.lms.question.constants.BankConstant.PUBLISHED;
import static com.lms.question.constants.BankConstant.UNPUBLISHED;
import static com.lms.question.constants.UserConstant.DELETED;
import static com.lms.question.constants.UserConstant.NOT_DELETED;
import static com.lms.question.entity.factory.factory.BankFactory.BANK_CONVERTER;


@Service
public class BankServiceImpl extends ServiceImpl<BankMapper, Bank> implements IBankService {

    @Override
    public Boolean addBank(AddBankDto addBankDto) {
        String name = addBankDto.getName();
        BusinessException.throwIf(this.count(new QueryWrapper<Bank>().eq("name",name))>0);
        Bank bank=new Bank();
        BeanUtils.copyProperties(addBankDto,bank);
        return this.save(bank);
    }

    @Override
    public Boolean removeBank(List<Integer> bids) {
        //校验bids的合法性
        BusinessException.throwIfNot(MybatisUtils.checkBids(bids));
         //逻辑删除
        return this.update(new UpdateWrapper<Bank>().set("delete_flag",DELETED).in("id",bids));
    }

    @Override
    public Page<BankVo> pageBankList(QueryBankPageDto queryBankPageDto) {

        String name = queryBankPageDto.getName();
        Integer pageNum = queryBankPageDto.getPageNum();
        Integer pageSize = queryBankPageDto.getPageSize();
        QueryWrapper<Bank> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .eq("delete_flag", NOT_DELETED);

        Page<Bank> pageBank = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Bank> records = pageBank.getRecords();
        List<BankVo> bankVos = BANK_CONVERTER.toListBankVo(records);
        Page<BankVo> result = new Page<>(pageNum, pageSize, pageBank.getTotal());
        result.setRecords(bankVos);
        return result;
    }

    @Override
    public Boolean updateBank(UpdateBankDto updateBankDto) {
        Integer id = updateBankDto.getId();
        BusinessException.throwIfNot(MybatisUtils.existCheck(this, Map.of("id",id)));

        Bank bank=new Bank();
        BeanUtils.copyProperties(updateBankDto,bank);
        return this.updateById(bank);

    }

    @Override
    public BankVo getBankById(Integer bid) {
        Bank byId = this.getById(bid);
        return BANK_CONVERTER.toBankVo(byId);
    }

    @Override
    public Boolean changePublishBank(ChangePublishStatusDto changePublishStatusDto) {
        Integer bid = changePublishStatusDto.getBid();
        Integer status = changePublishStatusDto.getStatus();
        return this.updateById(Bank.builder().id(bid).publish(status).build());
    }

    @Override
    public Page<BankVo> pagePublishBankList(QueryPublishDto queryPublishDto) {
        String name = queryPublishDto.getName();
        Integer pageNum = queryPublishDto.getPageNum();
        Integer pageSize = queryPublishDto.getPageSize();
        QueryWrapper<Bank> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .eq("delete_flag", NOT_DELETED).eq("publish",PUBLISHED);
        Page<Bank> pageBank = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Bank> records = pageBank.getRecords();
        List<BankVo> bankVos = BANK_CONVERTER.toListBankVo(records);
        Page<BankVo> result = new Page<>(pageNum, pageSize, pageBank.getTotal());
        result.setRecords(bankVos);
        return result;
    }


}
