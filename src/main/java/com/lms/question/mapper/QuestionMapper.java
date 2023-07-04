package com.lms.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lms.question.entity.dao.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
