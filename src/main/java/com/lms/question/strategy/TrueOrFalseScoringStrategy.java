package com.lms.question.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;

//判断题的判题
public class TrueOrFalseScoringStrategy implements ScoringStrategy{


    private static final ObjectMapper OBJECT_MAPPER=new ObjectMapper();

    @Override
    public float scoring(RecordVo recordVo, Map<Integer, Question> questionMap) throws JsonProcessingException {
        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String answer = question.getAnswer().trim();
        String userAnswer = recordVo.getUserAnswer().trim();

        List<String> answerList = OBJECT_MAPPER.readValue(answer,
                new TypeReference<List<String>>() {});
        if(StringUtils.isNotBlank(userAnswer)&&answerList.get(0).trim().equalsIgnoreCase(userAnswer)){
            //
            recordVo.setScore(questionScore);
            recordVo.setCorrect(CORRECT);
            return questionScore;
        }
        recordVo.setCorrect(UNCORRECT);
        recordVo.setScore(0f);
        return 0;
    }
}
