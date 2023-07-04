package com.lms.question.entity.factory.factory;

import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.UserDto;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class BankFactory {
    public static final BankConverter BANK_CONVERTER= Mappers.getMapper(BankConverter.class);
    @Mapper
    public interface BankConverter {
        @Mappings({

        }
        )
//        User toBank(UserDto userDto);
        BankVo toBankVo(Bank bank);
        List<BankVo> toListBankVo(List<Bank> banks);
    }
}
