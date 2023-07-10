package com.lms.question.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;

//单选判题
public class SingleScoringStrategy implements ScoringStrategy{


    private static  final ObjectMapper OBJECT_MAPPER=new ObjectMapper();
    @Override
    public float scoring(RecordVo recordVo, Map<Integer,Question> questionMap) throws JsonProcessingException {

        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String answer = question.getAnswer().trim();

        String userAnswer = recordVo.getUserAnswer().trim();
        //将json选项转换成map
        Map<String, String> answerMap = OBJECT_MAPPER.readValue(answer, Map.class);
        AtomicReference<String> rightOption= new AtomicReference<>("");
          answerMap.forEach((key,value)->{
              rightOption.set(key);
          });
        String trim = rightOption.get().trim();
        //对比用户的答案
        if(StringUtils.isNotBlank(userAnswer)&&userAnswer.equals(trim)){
            recordVo.setScore(questionScore);
            recordVo.setCorrect(CORRECT);
            return questionScore;
        }
        recordVo.setCorrect(UNCORRECT);
        recordVo.setScore(0f);
        return 0;
    }
}
