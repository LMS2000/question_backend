package com.lms.question.entity.factory.factory;

import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.QuestionVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class QuestionFactory {

    public static final QuestionConverter QUESTION_CONVERTER= Mappers.getMapper(QuestionConverter.class);
    @Mapper
    public interface QuestionConverter {
        @Mappings({

        }
        )
//        User toBank(UserDto userDto);
        QuestionVo toQuestionVo(Question question);

        List<QuestionVo> toListQuestionVo(List<Question> questions);
    }
}
