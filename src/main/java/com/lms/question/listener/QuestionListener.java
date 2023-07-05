package com.lms.question.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.service.IQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
@Scope("prototype")//标记此处的监听器为多例的，防止并发读操作时出现错误
public class QuestionListener extends AnalysisEventListener<QuestionVo> {

    @Resource
    private IQuestionService questionService;

    @Override
    public void invoke(QuestionVo questionVo, AnalysisContext analysisContext) {
//                  questionList.add(question);
        Question question = new Question();
        BeanUtils.copyProperties(questionVo, question);
        questionService.save(question);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 解析完毕后的操作，例如入库操作
        // userList 可以用于执行入库操作


    }
}
