package com.lms.question.strategy;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.question.constants.QuestionConstant;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.RecordVo;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;

//多选题判题   如何用户的答案有达到正确答案的一般算半对
public class MultipleScoringStrategy implements ScoringStrategy {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public float scoring(RecordVo recordVo, Map<Integer, Question> questionMap) throws JsonProcessingException {

        Question question = questionMap.get(recordVo.getQuestionId());
        Float questionScore = question.getQuestionScore();
        String questionAnswer = question.getAnswer();

        String userAnswer = recordVo.getUserAnswer();

        //转换json对象

        List<String> userAnswerList = OBJECT_MAPPER.readValue(userAnswer
                , new TypeReference<List<String>>() {});
        List<String> questionAnswerList = OBJECT_MAPPER.readValue(questionAnswer
                , new TypeReference<List<String>>() {});

        //对比答案 //包括一部分分数就是半对
        if (compareStringListsIgnoreWhitespace(questionAnswerList,userAnswerList)) {
            recordVo.setScore(questionScore);
            recordVo.setCorrect(CORRECT);
            return questionScore;
        }
        recordVo.setCorrect(UNCORRECT);
        recordVo.setScore(0f);
        return 0;
    }
    private static boolean compareStringsIgnoreWhitespace(String str1, String str2) {
        String s1 = StringUtils.deleteWhitespace(str1); // 删除字符串1中的所有空格
        String s2 = StringUtils.deleteWhitespace(str2); // 删除字符串2中的所有空格
        return StringUtils.equals(s1, s2); // 比较删除空格后的字符串是否相等
    }
    private static boolean compareStringListsIgnoreWhitespace(List<String> list1, List<String> list2) {
        if (list1.size() != list2.size()) {
            return false; // 如果两个列表长度不相等，则直接返回 false
        }

        for (int i = 0; i < list1.size(); i++) {
            String str1 = list1.get(i); // 获取列表1中的元素
            String str2 = list2.get(i); // 获取列表2中的元素
            if (!compareStringsIgnoreWhitespace(str1, str2)) {
                return false; // 如果比较结果不相等，则直接返回 false
            }
        }

        return true; // 所有元素比较结果都相等，返回 true
    }

}
