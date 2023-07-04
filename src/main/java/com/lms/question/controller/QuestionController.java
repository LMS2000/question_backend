package com.lms.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.entity.dto.AddQuestionDto;
import com.lms.question.entity.dto.QueryQuestionDto;
import com.lms.question.entity.dto.UpdateQuestionDto;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.service.IQuestionService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/question")
@EnableResponseAdvice
public class QuestionController {

    @Resource
    private IQuestionService questionService;


    /**
     * 查询分页
     * @param queryQuestionDto
     * @return
     */
    @PostMapping("/page")
    public Page<QuestionVo> questionVoPageList( @RequestBody QueryQuestionDto queryQuestionDto){
        return questionService.pageQuestionList(queryQuestionDto);
    }

    @PostMapping("/add")
    public Boolean  addQuestion(@Validated @RequestBody AddQuestionDto addQuestionDto){
        return questionService.addQuestion(addQuestionDto);
    }

    @PostMapping("/update")
    public Boolean updateQuestion(@Validated @RequestBody UpdateQuestionDto updateQuestionDto){
        return questionService.updateQuestion(updateQuestionDto);
    }


    @PostMapping("/remove")
    public Boolean removeQuestion(@RequestParam("qids")List<Integer> qids){
        return questionService.removeQuestion(qids);
    }

    @GetMapping("/{id}")
    public QuestionVo getById(@PathVariable("id") Integer id){
        return questionService.getQuestionById(id);
    }




}
