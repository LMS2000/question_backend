package com.lms.question.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;

import java.util.List;
import java.util.Map;

//判分策略
public interface ScoringStrategy {

    float scoring(RecordVo recordVo, Map<Integer,Question> questionMap) throws JsonProcessingException;
}
