package com.lms.question.controller;

import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.vo.BankAndQuestionVo;
import com.lms.question.service.IQuestionBankService;
import com.lms.question.service.IQuestionService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/questionBank")
@EnableResponseAdvice
public class QuestionBankController {



    @Resource
    private IQuestionBankService questionBankService;




    @GetMapping("/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BankAndQuestionVo getBankAndQuestionVo(@PathVariable("id") Integer id){
        return questionBankService.getBankAndQuestion(id);
    }


    @PostMapping("/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean  setBankQuestion(@Positive(message = "id不合法") @PathVariable("id") Integer bid, @RequestParam("qids") List<Integer> qids){
        return questionBankService.addQuestionBank(bid,qids);
    }
}
