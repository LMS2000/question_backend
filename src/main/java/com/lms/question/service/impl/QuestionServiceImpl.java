package com.lms.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.dao.QuestionBank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.AddQuestionDto;
import com.lms.question.entity.dto.QueryQuestionDto;
import com.lms.question.entity.dto.UpdateQuestionDto;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.exception.BusinessException;
import com.lms.question.mapper.QuestionMapper;
import com.lms.question.mapper.UserMapper;
import com.lms.question.service.IQuestionService;
import com.lms.question.service.IUserService;
import com.lms.question.utis.MybatisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
;
import static com.lms.question.entity.factory.factory.QuestionFactory.QUESTION_CONVERTER;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    /**
     * 添加题目
     * @param addQuestionDto
     * @return
     */
    @Override
    public Boolean addQuestion(AddQuestionDto addQuestionDto) {
        //校验选项
       Question question=new Question();
        BeanUtils.copyProperties(addQuestionDto,question);
        return this.save(question);
    }

    /**
     * 修改题目
     * @param updateQuestionDto
     * @return
     */
    @Override
    public Boolean updateQuestion(UpdateQuestionDto updateQuestionDto) {

        Question question=new Question();
        BeanUtils.copyProperties(updateQuestionDto,question);
        return this.updateById(question);
    }

    /**
     * 删除题目
     * @param qids
     * @return
     */
    @Override
    public Boolean removeQuestion(List<Integer> qids) {

        BusinessException.throwIfNot(MybatisUtils.checkQids(qids));

        return this.removeBatchByIds(qids);
    }


    /**
     * 查询分页
     * @param queryQuestionDto
     * @return
     */
    @Override
    public Page<QuestionVo> pageQuestionList(QueryQuestionDto queryQuestionDto) {

        String questionStem = queryQuestionDto.getQuestionStem();
        Integer type = queryQuestionDto.getType();
        Integer pageNum = queryQuestionDto.getPageNum();
        Integer pageSize = queryQuestionDto.getPageSize();
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(questionStem), "question_stem", questionStem)
                .eq(validType(type),"type",type);

        Page<Question> pageBank = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Question> records = pageBank.getRecords();
        List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(records);
        Page<QuestionVo> result = new Page<>(pageNum, pageSize, pageBank.getTotal());
        result.setRecords(questionVos);
        return result;
    }

    @Override
    public QuestionVo getQuestionById(Integer id) {
        Question question = this.getById(id);
        return QUESTION_CONVERTER.toQuestionVo(question);
    }

    private boolean validType(Integer type){
        List<Integer> types = List.of(0, 1, 2, 3, 4);
        return ObjectUtils.isNotEmpty(type)&&types.contains(type);
    }
}
