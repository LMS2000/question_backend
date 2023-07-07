package com.lms.question.strategy;

import com.lms.question.constants.BankConstant;
import com.lms.question.constants.QuestionConstant;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;


//填空题判题

public class GapFillingScoringStrategy implements ScoringStrategy{


    @Override
    public float scoring(RecordVo recordVo, Map<Integer, Question> questionMap) {
        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String questionAnswer = question.getAnswer().trim();
        String userAnswer = recordVo.getUserAnswer().trim();

        //todo 修改每个空给多少分？

        //对比用户的答案和正确答案，忽略大小写
        if(StringUtils.isNotBlank(userAnswer)&&questionAnswer.equalsIgnoreCase(userAnswer)){
            recordVo.setScore(questionScore);
            recordVo.setCorrect(CORRECT);
            return questionScore;
        }
        recordVo.setCorrect(UNCORRECT);
        recordVo.setScore(0f);
        return 0;
    }
}
