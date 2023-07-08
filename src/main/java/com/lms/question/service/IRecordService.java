package com.lms.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.SaveUserRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.vo.GetQuestionsAndRecordVo;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.entity.vo.UserBankRecordVo;

import javax.servlet.http.HttpServletRequest;

public interface IRecordService extends IService<Record> {


    //根据user_bank_id 获取用户的某次练习的记录的全部题目

    UserBankRecordVo getRecordByUserBankId(Integer ubid,QueryRecordDto queryRecordDto);

    //查询用户的答题情况

    RecordVo getByRecordId(Integer rid);
//
//    //修改记录
//    Boolean
    Boolean updateUserScore(UpdateUserScoreDto updateUserScoreDto);


    GetQuestionsAndRecordVo getQuestionsByMode(Integer id, Integer type, HttpServletRequest request);

    Boolean saveTempUserRecord(SaveUserRecordDto saveTempUserRecordDto);

    //提交用户答题情况，计算各个题目的得分和用户这套卷子的总分

    Boolean  calculateScore(SaveUserRecordDto saveUserRecordDto);

    //获取刷题最多的十个用户

    //获取用户答题数量，正确数量，错误数量，错误最多的体型列表（3种）


    //获取推荐的题目

    //  获取 当前用户的 正确率，刷题次数，当前的平均分

    GetQuestionsAndRecordVo getCurrentUserRecords(Integer ubid);






}
