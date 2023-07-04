package com.lms.question.entity.factory.factory;

import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.UserDto;
import com.lms.question.entity.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
public class UserFactory {
    public static final UserConverter USER_CONVERTER= Mappers.getMapper(UserConverter.class);
    @Mapper
   public interface UserConverter {
        @Mappings({
                @Mapping(target = "uid",ignore = true),
        }
        )
        User toUser(UserDto userDto);
        UserVo toUserVo(User user);
        List<UserVo> toListUserVo(List<User> user);
    }
}
