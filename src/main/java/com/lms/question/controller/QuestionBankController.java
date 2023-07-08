package com.lms.question.controller;

import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.QueryBankAndQuestionDto;
import com.lms.question.entity.vo.BankAndQuestionVo;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.service.IQuestionBankService;
import com.lms.question.service.IQuestionService;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/questionBank")
@EnableResponseAdvice
@Api( description = "题目题库管理")
public class QuestionBankController {



    @Resource
    private IQuestionBankService questionBankService;


    /**
     * 获取赋予题库所需要的题目列表
     * @param id
     * @param queryBankAndQuestionDto
     * @return
     */
    @PostMapping("/get/{id}")
    @ApiOperation("获取赋予题库所需要的题目列表")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BankAndQuestionVo getBankAndQuestionVo(@PathVariable("id") Integer id, @RequestBody QueryBankAndQuestionDto queryBankAndQuestionDto){
        return questionBankService.getBankAndQuestion(id,queryBankAndQuestionDto);
    }


    /**
     * 设置题库题目
     * @param bid
     * @param qids
     * @return
     */
    @PostMapping("/{id}")
    @ApiOperation("设置题库题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean  setBankQuestion(@Positive(message = "id不合法") @PathVariable("id") Integer bid, @RequestParam("qids") List<Integer> qids){
        return questionBankService.addQuestionBank(bid,qids);
    }










}
