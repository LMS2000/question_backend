package com.lms.question.strategy;

import com.lms.question.constants.QuestionConstant;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;

//多选题判题   如何用户的答案有达到正确答案的一般算半对
public class MultipleScoringStrategy implements ScoringStrategy{
    @Override
    public float scoring(RecordVo recordVo, Map<Integer, Question> questionMap) {

        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String questionAnswer = question.getAnswer().trim();
        List<String> questionAnswers = Arrays.asList(questionAnswer.split(QuestionConstant.OPTIONS_SUFFIX));
        String userAnswer = recordVo.getUserAnswer().trim();
        List<String> userAnswers = Arrays.asList(userAnswer.split(QuestionConstant.OPTIONS_SUFFIX));
        Integer halfNum=questionAnswer.length()/2;

        //对比答案 //包括一部分分数就是半对
        int trueNum = getTrueNum(userAnswers, questionAnswers);
        if(trueNum>=halfNum){
            //全对
            if(trueNum==halfNum){
                recordVo.setScore(questionScore);
                recordVo.setCorrect(CORRECT);
                return questionScore;
            }
            //对一半
            float userScore=questionScore/2f;
            recordVo.setScore(userScore);
            recordVo.setCorrect(UNCORRECT);
            return userScore;
        }
        //0分
        recordVo.setCorrect(UNCORRECT);
        recordVo.setScore(0f);
        return 0;
    }

    private int getTrueNum(List<String> userAnswers,List<String> questionAnswers){
        AtomicInteger count= new AtomicInteger();
         userAnswers.forEach(userAnswer->{
             if(questionAnswers.contains(userAnswer)) count.getAndIncrement();
         });
         return count.get();
    }


}
