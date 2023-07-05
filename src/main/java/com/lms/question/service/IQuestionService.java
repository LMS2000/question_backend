package com.lms.question.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.AddQuestionDto;
import com.lms.question.entity.dto.QueryQuestionDto;
import com.lms.question.entity.dto.UpdateQuestionDto;
import com.lms.question.entity.vo.QuestionVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//题目管理
public interface IQuestionService extends IService<Question> {

    //curd

    Boolean addQuestion(AddQuestionDto addQuestionDto);

    Boolean updateQuestion(UpdateQuestionDto updateQuestionDto);

    Boolean removeQuestion(List<Integer> qids);


    Page<QuestionVo> pageQuestionList(QueryQuestionDto queryQuestionDto);

    QuestionVo  getQuestionById(Integer id);


    Boolean  importQuestions(MultipartFile file);

    void exportQuestions(HttpServletResponse response);

    void exportTemplates(HttpServletResponse response);
}
