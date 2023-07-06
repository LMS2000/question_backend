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

   /*
    条件分页查询
    */
    Page<QuestionVo> pageQuestionList(QueryQuestionDto queryQuestionDto);

    // 按照id获取题目
    QuestionVo  getQuestionById(Integer id);

   //导入题目
    Boolean  importQuestions(MultipartFile file);

    //导出题目
    void exportQuestions(HttpServletResponse response);

    //导出题目模板
    void exportTemplates(HttpServletResponse response);
}
