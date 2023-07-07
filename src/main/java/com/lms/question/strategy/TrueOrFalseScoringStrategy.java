package com.lms.question.strategy;

import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;

//判断题的判题
public class TrueOrFalseScoringStrategy implements ScoringStrategy{


    @Override
    public float scoring(RecordVo recordVo, Map<Integer, Question> questionMap) {
        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String answer = question.getAnswer().trim();
        String userAnswer = recordVo.getUserAnswer().trim();

        if(StringUtils.isNotBlank(userAnswer)&&answer.equalsIgnoreCase(userAnswer)){
            recordVo.setScore(questionScore);
            recordVo.setCorrect(CORRECT);
            return questionScore;
        }
        recordVo.setCorrect(UNCORRECT);
        recordVo.setScore(0f);
        return 0;
    }
}
