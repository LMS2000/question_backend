package com.lms.question.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lms.question.constants.BankConstant;
import com.lms.question.constants.QuestionConstant;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.dao.QuestionBank;

import com.lms.question.entity.dto.QueryBankAndQuestionDto;
import com.lms.question.entity.vo.*;
import com.lms.question.exception.BusinessException;
import com.lms.question.mapper.QuestionBankMapper;

import com.lms.question.service.*;
import com.lms.question.utis.MybatisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lms.question.entity.factory.factory.QuestionFactory.QUESTION_CONVERTER;

@Service
public class QuestionBankImpl extends ServiceImpl<QuestionBankMapper, QuestionBank> implements IQuestionBankService {


    @Resource
    private IBankService bankService;

    @Resource
    private IQuestionService questionService;


    @Resource
    private IUserBankService userBankService;

    /**
     * 获取题库已有的题目id和全部的题目列表
     * @param bid
     * @param queryBankAndQuestionDto
     * @return
     */
    @Override
    public BankAndQuestionVo getBankAndQuestion(Integer bid, QueryBankAndQuestionDto queryBankAndQuestionDto) {



        BankVo bankVo = bankService.getBankById(bid);
        BusinessException.throwIf(bankVo == null);
        List<Integer> bids = this.list(new QueryWrapper<QuestionBank>().eq("bid", bid)).stream()
                .map(QuestionBank::getQid).collect(Collectors.toList());

        bankVo.setQids(bids);
        Integer type = queryBankAndQuestionDto.getType();
        String questionStem = queryBankAndQuestionDto.getQuestionStem();
        List<Question> questionList = questionService.list(new QueryWrapper<Question>()
                .eq(ObjectUtils.isNotEmpty(type),"type",type)
                .like(StringUtils.isNotBlank(questionStem),"question_stem",questionStem));
        List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(questionList);


        return BankAndQuestionVo.builder().questionVoList(questionVos).bankVo(bankVo).build();
    }

    /**
     * 添加题库没有的题目，如果去除题目就删除
     * @param bid
     * @param qids
     * @return
     */
    @Override
    public Boolean addQuestionBank(Integer bid, List<Integer> qids) {

        //验证bid 验证qids

        BusinessException.throwIfNot(MybatisUtils.existCheck(bankService, Map.of("id", bid)));

        BusinessException.throwIfNot(MybatisUtils.checkQids(qids));


        //先获取题库下的已有的题目
        List<Integer> questionIdList = this.list(new QueryWrapper<QuestionBank>().eq("bid", bid)).stream().map(QuestionBank::getQid).collect(Collectors.toList());
        if (questionIdList.size() < 1) {

            List<QuestionBank> addList = new ArrayList<>();
            qids.stream().forEach(qid -> {
                addList.add(QuestionBank.builder().bid(bid).qid(qid).build());
            });
            if (qids.size() > 0) {
                return this.saveBatch(addList);
            }

        }
        //获取角色原来没有的题目
        List<Integer> addList = qids.stream()
                .filter(id -> !questionIdList.contains(id)).collect(Collectors.toList());
        //获取角色现在去除的题目
        List<Integer> deleteList = questionIdList.stream()
                .filter(id -> !qids.contains(id)).collect(Collectors.toList());

        List<QuestionBank> addQuestionBankList = new ArrayList<>();
        addList.forEach(qid -> {
            addQuestionBankList.add(QuestionBank.builder().bid(bid).qid(qid).build());
        });

        if (addQuestionBankList.size() > 0) {
            this.saveBatch(addQuestionBankList);
        }

        if (deleteList.size() > 0) {
            this.remove(new QueryWrapper<QuestionBank>()
                    .eq("bid", bid).in("qid", deleteList));
        }
        return true;

    }






    private boolean validType(Integer type) {
        List<Integer> types = List.of(0, 1, 2, 3, 4);
        return ObjectUtils.isNotEmpty(type) && types.contains(type);
    }
}
