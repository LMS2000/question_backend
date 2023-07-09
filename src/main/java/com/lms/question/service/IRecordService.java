package com.lms.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.SaveUserRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.vo.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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







    GetQuestionsAndRecordVo getCurrentUserRecords(Integer ubid);

     //实时
    //获取用户答题数量，正确数量，错误数量，
    GetUserAccuracyRateVo  getUserRate(HttpServletRequest request);


    //获取用户错误最多的题目
    GetUserMistakeQuestionVo getMistakeQuestions(HttpServletRequest request);


}
