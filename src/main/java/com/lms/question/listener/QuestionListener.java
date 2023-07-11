package com.lms.question.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.contants.HttpCode;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.exception.BusinessException;
import com.lms.question.service.IQuestionService;
import org.apache.commons.math3.analysis.solvers.BaseUnivariateSolver;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lms.question.constants.QuestionConstant.*;


@Component
@Scope("prototype")//标记此处的监听器为多例的，防止并发读操作时出现错误
public class QuestionListener extends AnalysisEventListener<QuestionVo> {

    @Resource
    private IQuestionService questionService;

    private boolean skipRow = false;
    @Override
    public void invoke(QuestionVo questionVo, AnalysisContext analysisContext) {


            Question question = new Question();
            BeanUtils.copyProperties(questionVo, question);
            //将选项的划分为json对象
            String options = question.getOptions().trim();
            String answer = question.getAnswer().trim();
            List<Integer> list = List.of(SINGLE, MULTIPLE);
            //如果是单选，多选，判断题
            if(SINGLE.equals(question.getType())){

                String answerJson="";
                String optionsJson = "";
                try{
                    optionsJson=toJSON(options);
                    answerJson=toJSON(answer);
                }catch (Exception e){
                    throw new BusinessException(HttpCode.OPERATION_ERROR,"题目选项转换json失败");
                }
                question.setOptions(optionsJson);
                question.setAnswer(answerJson);
            }else if(question.getType().equals(TRUE_OR_FALSE)){
                String trueOrFalseAnswer="";
                String answerJson="";
                try{
                    answerJson=toJSONTureOrFalse(answer);
                    trueOrFalseAnswer=toJSONTureOrFalse(options);
                }catch (Exception e){
                    throw new BusinessException(HttpCode.OPERATION_ERROR,"题目选项转换json失败");
                }
                question.setOptions(trueOrFalseAnswer);
                question.setAnswer(answerJson);
            }else if(question.getType().equals(MULTIPLE)){
                String multipleOptions="";
                String answerJson="";
                try{
                    multipleOptions=toJSONTureOrFalse(options);
                    answerJson=toJSONTureOrFalse(answer);
                }catch (Exception e){
                    if (skipRow) {
                        skipRow = false;
                        throw new BusinessException(HttpCode.OPERATION_ERROR, "题目选项转换json失败");
                    }
                }
                question.setOptions(multipleOptions);
                question.setAnswer(answerJson);
            }

            questionService.save(question);


//                  questionList.add(question);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 解析完毕后的操作，例如入库操作
        // userList 可以用于执行入库操作

    }

    private String toJSON(String options) throws JsonProcessingException {


            // 使用分号 (;) 分割成一个选项数组
            String[] optionArray = options.split(";");

            // 创建一个Map，将选项数组转换成键值对
            Map<String, String> optionMap = new HashMap<>();
            for (String option : optionArray) {
                String[] parts = option.split("\\.");
                optionMap.put(parts[0], parts[1]);
            }
            // 创建一个ObjectMapper对象
            ObjectMapper objectMapper = new ObjectMapper();

            // 将Map转换成JSON字符串
            String jsonStr = objectMapper.writeValueAsString(optionMap);
            return jsonStr;

    }
    private String toJSONTureOrFalse(String options) throws JsonProcessingException {


        // 使用分号 (;) 分割成一个选项数组
        String[] optionArray = options.split(";");

        // 创建一个Map，将选项数组转换成键值对

        // 创建一个ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 将Map转换成JSON字符串
        String jsonStr = objectMapper.writeValueAsString(optionArray);
        return jsonStr;
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // 解析失败时的逻辑
        // 处理解析失败的操作
        // 这里可以选择忽略失败的条目并继续解析下一条数据
        //                   20           20           10           30      20
        // 继续解析下一条数据  single 20   multiple 10   trueOrFalse   gap 6   subjective  1
        skipRow=true;
    }
}
