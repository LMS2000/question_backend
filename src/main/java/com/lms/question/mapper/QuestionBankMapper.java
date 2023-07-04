package com.lms.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lms.question.entity.dao.QuestionBank;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {
}
