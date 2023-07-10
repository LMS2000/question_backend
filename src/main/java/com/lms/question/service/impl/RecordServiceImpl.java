package com.lms.question.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.contants.HttpCode;
import com.lms.question.config.PredictServerProperties;
import com.lms.question.constants.BankConstant;
import com.lms.question.constants.QuestionConstant;
import com.lms.question.entity.dao.*;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.SaveUserRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.enums.QuestionTypeEnum;
import com.lms.question.entity.factory.factory.RecordFactory;
import com.lms.question.entity.vo.*;
import com.lms.question.exception.BusinessException;
import com.lms.question.mapper.RecordMapper;
import com.lms.question.mapper.UserMapper;
import com.lms.question.service.*;
import com.lms.question.strategy.ScoringStrategy;
import com.lms.question.strategy.ScoringStrategyFactory;
import com.lms.question.utis.CacheUtils;
import com.lms.question.utis.HttpApiUtil;
import com.lms.question.utis.MybatisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lms.question.constants.BankConstant.NOT_SUBMMITTED;
import static com.lms.question.constants.BankConstant.SUBMITTED;
import static com.lms.question.constants.QuestionConstant.CORRECT;
import static com.lms.question.constants.QuestionConstant.UNCORRECT;
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


    @Resource
    private PredictServerProperties predictServerProperties;

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
            record.setQuestionVo(QUESTION_CONVERTER
                    .toQuestionVo(questionMap.getOrDefault(questionId, null)));
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
        BusinessException.throwIf(question == null);
        BusinessException.throwIf(score > question.getQuestionScore());
        return this.update(new UpdateWrapper<Record>().set("score", score).eq("id", recordId));
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

        BusinessException.throwIfNot(MybatisUtils.existCheck(bankService, Map.of("id", id)));
        //查找用户的最近没提交练习记录
        Integer uid = userService.getLoginUser(request).getUid();
        UserBankVo userNotRecentlySubmitted = userBankService.getUserNotRecentlySubmitted(id, type, request);
        List<RecordVo> tempRecord = null;
        if (userNotRecentlySubmitted != null) {
            tempRecord = CacheUtils.getTempRecord(userNotRecentlySubmitted.getId());
        } else {
            UserBank userBank = UserBank.builder().bankId(id).userId(uid).type(type).build();
            userBankService.save(userBank);
            userNotRecentlySubmitted = USER_BANK_CONVERTER.toUserBankVo(userBank);
        }
        List<Integer> qids = questionBankService.list(new QueryWrapper<QuestionBank>().eq("bid", id)).stream().map(QuestionBank::getQid).collect(Collectors.toList());

        //如果没有题目的情况下
        BusinessException.throwIf(qids.size()<1,HttpCode.OPERATION_ERROR,"该套试卷没有题目!");

        List<Question> questionList = null;
        //如果是考试状态没有答案
        if (type.equals(BankConstant.EXAMINATION_STATE)) {
            questionList = questionService.list(new QueryWrapper<Question>().in("id", qids)
                    .select("id", "question_stem", "type", "options", "question_score"));
        } else {
            questionList = questionService.list(new QueryWrapper<Question>().in("id", qids)
                    .select("id", "question_stem", "type",
                            "options", "question_score", "answer", "explanation"));
        }
        List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(questionList);
        return GetQuestionsAndRecordVo.builder().questionVoList(questionVos)
                .recordVoList(tempRecord).ubid(ObjectUtils.isNotEmpty(userNotRecentlySubmitted) ? userNotRecentlySubmitted.getId() : null).type(type).build();
    }

    /**
     * 保存临时练习记录
     *
     * @param saveTempUserRecordDto
     * @return
     */
    @Override
    public Boolean saveTempUserRecord(SaveUserRecordDto saveTempUserRecordDto) {


        List<RecordVo> recordVoList = saveTempUserRecordDto.getRecordVoList();
        Integer userBankId = recordVoList.get(0).getUserBankId();
        BusinessException.throwIfNot(MybatisUtils.existCheck(userBankService, Map.of("id", userBankId)));
        //缓冲
        CacheUtils.setTempRecord(userBankId, recordVoList);
        return true;
    }


    /**
     * 思路：
     * 首先获取目标题目的题库列表，然后进入策略模式填充每一个Record的分数，并且返回该题目的得分
     *
     * @param saveUserRecordDto
     * @return
     */
    @Override
    @Transactional
    public Boolean calculateScore(SaveUserRecordDto saveUserRecordDto) {

        List<RecordVo> recordVoList = saveUserRecordDto.getRecordVoList();
        //校验用户答题情况
        BusinessException.throwIf(recordVoList == null || recordVoList.size() < 1, HttpCode.PARAMS_ERROR,
                "答题列表不能为空");

        Integer userBankId = recordVoList.get(0).getUserBankId();
        UserBank userBank = userBankService.getById(userBankId);
        BusinessException.throwIf(userBank == null);
        Integer bankId = userBank.getBankId();
        List<Integer> qids = questionBankService.list(new QueryWrapper<QuestionBank>().eq("bid", bankId)).stream().map(QuestionBank::getQid).collect(Collectors.toList());
        //校验是否打完全部题目
        BusinessException.throwIf(recordVoList.size() != qids.size(), HttpCode.PARAMS_ERROR, "没有答题完整");

        Map<Integer, Question> questionMap = questionService.list(new QueryWrapper<Question>()
                        .in("id", qids)).stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));


        //开始记录分数
        float finalScore = 0f;  List<Record> records=null;
        try{
            //记录分数
            for (RecordVo recordVo : recordVoList) {
                Integer type = questionMap.get(recordVo.getQuestionId()).getType();
                QuestionTypeEnum questionTypeEnum = Optional.ofNullable(QuestionTypeEnum.getEnumByValue(type))
                        .orElse(QuestionTypeEnum.SINGLE);
                //获取生成器
                ScoringStrategy scoringStrategy = ScoringStrategyFactory.getScoringStrategy(questionTypeEnum);
                float scoring = scoringStrategy.scoring(recordVo, questionMap);
                finalScore += scoring;
            }
            //设置提交状态设置分数
            userBank.setScore(finalScore);
            userBank.setSubmit(SUBMITTED);
            //保存分数 更新用户这次记录的状态为提交状态
            userBankService.updateById(userBank);

             records = RECORD_CONVERTER.toListRecord(recordVoList);
            //删除缓冲
            CacheUtils.removeTempRecord(userBankId);
        }catch (Exception e){
             throw new BusinessException(HttpCode.OPERATION_ERROR,"计算分数失败");
        }
        //保存记录
        return this.saveBatch(records);
    }

    @Override
    public GetQuestionsAndRecordVo getCurrentUserRecords(Integer ubid) {

        UserBank userBank = userBankService.getById(ubid);

        BusinessException.throwIf(userBank == null);
        Integer bankId = userBank.getBankId();
        //获取题目集
        List<Integer> qids = questionBankService.list(new QueryWrapper<QuestionBank>()
                .eq("bid", bankId)).stream().map(QuestionBank::getQid).collect(Collectors.toList());
        List<Question> questionList = questionService.list(new QueryWrapper<Question>().in("id", qids));
        List<RecordVo> records = null;
        //如果已经提交的练习记录就从数据库查找，没有的话就从缓冲中取出
        if (userBank.getSubmit().equals(SUBMITTED)) {
            List<Record> recordList = this.list(new QueryWrapper<Record>().eq("user_bank_id", ubid));
            records = RECORD_CONVERTER.toListRecordVo(recordList);
        } else {
            records = CacheUtils.getTempRecord(ubid);
        }
        UserBankVo userBankVo = USER_BANK_CONVERTER.toUserBankVo(userBank);

        List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(questionList);

        return GetQuestionsAndRecordVo.builder().recordVoList(records)
                .questionVoList(questionVos).userBankVo(userBankVo).build();

    }

    /**
     * 获取用户的刷题量和正确率
     *
     * @return
     */
    @Override
    public GetUserAccuracyRateVo getUserRate(HttpServletRequest request) {
        Integer uid = userService.getLoginUser(request).getUid();
        List<Integer> ubids = userBankService.list(new QueryWrapper<UserBank>()
                .eq("user_id", uid).eq("submit", SUBMITTED)).stream().map(UserBank::getId).collect(Collectors.toList());
        //如果用户存在练习记录
        if (ubids.size() > 0) {
            //获取用户的全部刷题量和正确数量
            List<Record> records = this.list(new QueryWrapper<Record>().in("user_bank_id", ubids));
            long rightNum = records.stream().filter(record -> record.getCorrect().equals(CORRECT)).count();
            return GetUserAccuracyRateVo.builder().rightNum(rightNum).questionAmount((long) records.size()).build();
        } else {

            return GetUserAccuracyRateVo.builder().questionAmount(0L).rightNum(0L).build();
        }
    }

    /**
     * 获取用户错误最多的三个题目
     *
     * @param request
     * @return
     */
    @Override
    public List<UserMistakeQuestionVo> getMistakeQuestions(HttpServletRequest request) {
        Integer uid = userService.getLoginUser(request).getUid();
        List<Integer> ubids = userBankService.list(new QueryWrapper<UserBank>()
                .eq("user_id", uid)).stream().map(UserBank::getId).collect(Collectors.toList());
        //用户全部错误的题目
        if (ubids.size() > 0) {
            List<Question> questionList = questionService.list(new QueryWrapper<>(null));
            Map<Integer, QuestionVo> questionVoMap = QUESTION_CONVERTER.toListQuestionVo(questionList).stream().collect(Collectors.toMap(QuestionVo::getId, Function.identity()));

            List<Record> records = this.list(new QueryWrapper<Record>().in("user_bank_id", ubids)
                    .eq("correct", UNCORRECT));
            Map<Integer, Long> questionIdCountMap = records.stream()
                    .limit(3)
                    .collect(Collectors.groupingBy(Record::getQuestionId, Collectors.counting()));
            List<UserMistakeQuestionVo> resultList = new ArrayList<>();
            //添加用户错误前三的题目
            questionIdCountMap.forEach((key, value) -> {
                resultList.add(UserMistakeQuestionVo.builder()
                        .question(questionVoMap.get(key)).mistakeNum(value).build());
            });
            return resultList;
        }
        return null;
    }

    @Override
    public Float getPredictScore(HttpServletRequest request) {
        Integer uid = userService.getLoginUser(request).getUid();
        HttpApiUtil.buildCSVFile(uid);
        return HttpApiUtil.getPredictData(predictServerProperties, uid);
    }


    private boolean validCorrect(Integer value) {
        return ObjectUtils.isNotEmpty(value) && (value.equals(0) || value.equals(1));
    }

    private boolean validType(Integer type) {
        List<Integer> types = List.of(0, 1, 2, 3, 4);
        return ObjectUtils.isNotEmpty(type) && types.contains(type);
    }

}
