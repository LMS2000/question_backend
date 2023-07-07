package com.lms.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.QuestionBank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.QueryBankAndQuestionDto;
import com.lms.question.entity.vo.BankAndQuestionVo;
import com.lms.question.entity.vo.GetQuestionsAndRecordVo;
import com.lms.question.entity.vo.QuestionVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IQuestionBankService  extends IService<QuestionBank> {


    //获取题库的所有题目

    BankAndQuestionVo getBankAndQuestion(Integer bid, QueryBankAndQuestionDto queryBankAndQuestionDto);

    // 将题目赋予题库下

    Boolean addQuestionBank(Integer bid, List<Integer> qids);



}
