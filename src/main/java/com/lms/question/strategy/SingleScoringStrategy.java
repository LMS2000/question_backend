package com.lms.question.strategy;

import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;

//单选判题
public class SingleScoringStrategy implements ScoringStrategy{

    @Override
    public float scoring(RecordVo recordVo, Map<Integer,Question> questionMap) {

        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String answer = question.getAnswer().trim();
        String userAnswer = recordVo.getUserAnswer().trim();

        //对比用户的答案
        if(StringUtils.isNotBlank(userAnswer)&&userAnswer.equals(answer)){
            recordVo.setScore(questionScore);
            recordVo.setCorrect(CORRECT);
            return questionScore;
        }
        recordVo.setCorrect(UNCORRECT);
        recordVo.setScore(0f);
        return 0;
    }
}
