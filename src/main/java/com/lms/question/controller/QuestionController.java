package com.lms.question.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.AddQuestionDto;
import com.lms.question.entity.dto.QueryQuestionDto;
import com.lms.question.entity.dto.UpdateQuestionDto;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.service.IQuestionService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/question")
@EnableResponseAdvice
public class QuestionController {

    @Resource
    private IQuestionService questionService;


    /**
     * 查询分页
     *
     * @param queryQuestionDto
     * @return
     */
    @PostMapping("/page")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Page<QuestionVo> questionVoPageList(@RequestBody QueryQuestionDto queryQuestionDto) {
        return questionService.pageQuestionList(queryQuestionDto);
    }

    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean addQuestion(@Validated @RequestBody AddQuestionDto addQuestionDto) {
        return questionService.addQuestion(addQuestionDto);
    }

    @PostMapping("/update")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean updateQuestion(@Validated @RequestBody UpdateQuestionDto updateQuestionDto) {
        return questionService.updateQuestion(updateQuestionDto);
    }


    @PostMapping("/remove")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean removeQuestion(@RequestParam("qids") List<Integer> qids) {
        return questionService.removeQuestion(qids);
    }

    @GetMapping("/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public QuestionVo getById(@PathVariable("id") Integer id) {
        return questionService.getQuestionById(id);
    }


    @PostMapping("/import")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean importQuestions(@RequestPart("file") MultipartFile file) {
        return questionService.importQuestions(file);
    }

    @GetMapping("/export")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public void exportQuestoins(HttpServletResponse response){
        //获取easyexcel导出excel的二进制然后返回
         questionService.exportQuestions(response);
    }


    @GetMapping("/export/template")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public void exportTemplate(HttpServletResponse response){
        questionService.exportTemplates(response);
    }



}
