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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/question")
@EnableResponseAdvice
@Api(description = "题目管理")
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
    @ApiOperation("分页查询题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Page<QuestionVo> questionVoPageList(@RequestBody QueryQuestionDto queryQuestionDto) {
        return questionService.pageQuestionList(queryQuestionDto);
    }

    @PostMapping("/add")
    @ApiOperation("添加题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean addQuestion(@Validated @RequestBody AddQuestionDto addQuestionDto) {
        return questionService.addQuestion(addQuestionDto);
    }

    @PostMapping("/update")
    @ApiOperation("更新题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean updateQuestion(@Validated @RequestBody UpdateQuestionDto updateQuestionDto) {
        return questionService.updateQuestion(updateQuestionDto);
    }


    @PostMapping("/remove")
    @ApiOperation("批量删除题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean removeQuestion(@RequestParam("qids") List<Integer> qids) {
        return questionService.removeQuestion(qids);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id获取题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public QuestionVo getById(@PathVariable("id") Integer id) {
        return questionService.getQuestionById(id);
    }


    @PostMapping("/import")
    @ApiOperation("excel导入题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean importQuestions(@RequestPart("file") MultipartFile file) {
        return questionService.importQuestions(file);
    }

    @GetMapping("/export")
    @ApiOperation("导入excel题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public void exportQuestoins(HttpServletResponse response){
        //获取easyexcel导出excel的二进制然后返回
         questionService.exportQuestions(response);
    }


    @GetMapping("/export/template")
    @ApiOperation("下载题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public void exportTemplate(HttpServletResponse response){
        questionService.exportTemplates(response);
    }



}
