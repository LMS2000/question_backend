package com.lms.question.strategy;

import com.lms.question.entity.enums.QuestionTypeEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// * 单例+工厂模式
public class ScoringStrategyFactory {
    private static final Map<QuestionTypeEnum,ScoringStrategy> DATA_GENERATOR_POOL=new HashMap<QuestionTypeEnum,ScoringStrategy>(){{
        put(QuestionTypeEnum.SINGLE,new SingleScoringStrategy());
        put(QuestionTypeEnum.MULTIPLE,new MultipleScoringStrategy());
        put(QuestionTypeEnum.GAP_FILLING,new GapFillingScoringStrategy());
        put(QuestionTypeEnum.TRUE_OR_FALSE,new TrueOrFalseScoringStrategy());
        put(QuestionTypeEnum.SUBJECTIVE,new SubjectiveScoringStrategy());
    }};


    public static ScoringStrategy getScoringStrategy(QuestionTypeEnum questionTypeEnum){
        questionTypeEnum = Optional.ofNullable(questionTypeEnum).orElse(questionTypeEnum.SINGLE);
        return DATA_GENERATOR_POOL.get(questionTypeEnum);
    }
}
