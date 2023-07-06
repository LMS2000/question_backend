package com.lms.question.entity.factory.factory;

import com.lms.question.entity.dao.User;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.dto.UserDto;
import com.lms.question.entity.vo.UserBankVo;
import com.lms.question.entity.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class UserBankFactory {

    public static final UserBankConverter USER_BANK_CONVERTER= Mappers.getMapper(UserBankConverter.class);
    @Mapper
    public interface UserBankConverter {
        @Mappings({

        }
        )
        UserBankVo toUserBankVo(UserBank userBank);
        List<UserBankVo> toListUserBankVo(List<UserBank> userBanks);
    }
}
