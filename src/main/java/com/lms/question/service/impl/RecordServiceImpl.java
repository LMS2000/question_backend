package com.lms.question.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.question.constants.BankConstant;
import com.lms.question.entity.dao.*;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.vo.*;
import com.lms.question.exception.BusinessException;
import com.lms.question.mapper.RecordMapper;
import com.lms.question.mapper.UserMapper;
import com.lms.question.service.*;
import com.lms.question.utis.MybatisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lms.question.entity.factory.factory.BankFactory.BANK_CONVERTER;
import static com.lms.question.entity.factory.factory.QuestionFactory.QUESTION_CONVERTER;
import static com.lms.question.entity.factory.factory.RecordFactory.RECORD_CONVERTER;
import static com.lms.question.entity.factory.factory.UserBankFactory.USER_BANK_CONVERTER;
import static com.lms.question.entity.factory.factory.UserFactory.USER_CONVERTER;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {


    @Resource
    private IQuestionService questionService;


    @Resource
    private IUserBankService userBankService;

    @Resource
    private IBankService bankService;

    @Resource
    private IUserService userService;

    @Resource
    private IQuestionBankService questionBankService;

    @Override
    public UserBankRecordVo getRecordByUserBankId(Integer ubid, QueryRecordDto queryRecordDto) {

        Integer type = queryRecordDto.getType();

        Integer correct = queryRecordDto.getCorrect();

        String questionStem = queryRecordDto.getQuestionStem();

        //先查询属于ubid并且 ==correct

        Map<Integer, Question> questionMap = questionService.list(new QueryWrapper<Question>()
                .like(StringUtils.isNotBlank(questionStem), "question_stem", questionStem)
                .eq(validType(type), "type", type)).stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));

        Set<Integer> qids = questionMap.keySet();

        List<Record> recordList = this.list(new QueryWrapper<Record>().eq(validCorrect(correct), "correct", correct)
                .in(CollectionUtil.isNotEmpty(qids), "question_id", qids)
                .eq("id", ubid));

        List<RecordVo> recordVos = RECORD_CONVERTER.toListRecordVo(recordList);
        recordVos.forEach(record -> {
            Integer questionId = record.getQuestionId();
          record.setQuestionVo(  QUESTION_CONVERTER
                  .toQuestionVo( questionMap.getOrDefault(questionId,null)));
        });

        //封装用户答题记录
        UserBank userBank = userBankService.getOne(new QueryWrapper<UserBank>().eq("id", ubid));

        UserBankVo userBankVo = USER_BANK_CONVERTER.toUserBankVo(userBank);


        Bank bank = bankService.getOne(new QueryWrapper<Bank>().eq("id", userBankVo.getBankId()));

        User user = userService.getOne(new QueryWrapper<User>().eq("uid", userBankVo.getUserId()));
        BankVo bankVo = BANK_CONVERTER.toBankVo(bank);
        UserVo userVo = USER_CONVERTER.toUserVo(user);
        userBankVo.setUser(userVo);
        userBankVo.setBank(bankVo);
        return UserBankRecordVo.builder().userBankVo(userBankVo).recordVoList(recordVos).build();
    }

    @Override
    public RecordVo getByRecordId(Integer rid) {
        //校验id
        Record record = this.getById(rid);

        Integer questionId = record.getQuestionId();

        QuestionVo questionVo = questionService.getQuestionById(questionId);
        RecordVo recordVo = RECORD_CONVERTER.toRecordVo(record);
        recordVo.setQuestionVo(questionVo);
        return recordVo;
    }

    @Override
    public Boolean updateUserScore(UpdateUserScoreDto updateUserScoreDto) {
        Integer questionId = updateUserScoreDto.getQuestionId();
        Integer score = updateUserScoreDto.getScore();
        Integer recordId = updateUserScoreDto.getRecordId();

        //判断修改的得分是否大于题目的总分数
        Question question = questionService.getById(questionId);
        BusinessException.throwIf(question==null);
        BusinessException.throwIf(score>question.getQuestionScore());
        return this.update(new UpdateWrapper<Record>().set("score",score).eq("id",recordId));
    }


    /**
     * 根据接收到的题库id和练习模式，有两个情况：
     * 1. 根据用户id和题库id 搜索到了user_bank表中存在用户最近练习这个题库并且未提交的记录，
     * 就需要设置用户的练习记录列表 user_bank
     *
     * @param id
     * @param type
     * @return
     */
    @Override
    public GetQuestionsAndRecordVo getQuestionsByMode(Integer id, Integer type, HttpServletRequest request) {

        //查找用户的最近没提交练习记录
//        UserBankVo userNotRecentlySubmitted = userBankService.getUserNotRecentlySubmitted(id, type, request);
        List<Integer> qids= questionBankService.list(new QueryWrapper<QuestionBank>().eq("bid", id)).stream().map(QuestionBank::getQid).collect(Collectors.toList());
        List<Question> questionList=null;
        //如果是考试状态没有答案
        if(type.equals(BankConstant.EXAMINATION_STATE)){
            questionList = questionService.list(new QueryWrapper<Question>().in("id", qids)
                    .select("id", "question_stem", "type", "options", "question_score"));
        }else{
            questionList = questionService.list(new QueryWrapper<Question>().in("id", qids)
                    .select("id", "question_stem", "type",
                            "options", "question_score","answer","explanation"));
        }
        List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(questionList);
//        List<Record> records = this.list(new QueryWrapper<Record>().eq("user_bank_id", userNotRecentlySubmitted.getId()));

        return GetQuestionsAndRecordVo.builder().questionVoList(questionVos).type(type).build();
    }

    private boolean validCorrect(Integer value){
        return ObjectUtils.isNotEmpty(value)&&(value.equals(0)||value.equals(1));
    }

    private boolean validType(Integer type) {
        List<Integer> types = List.of(0, 1, 2, 3, 4);
        return ObjectUtils.isNotEmpty(type) && types.contains(type);
    }

}
