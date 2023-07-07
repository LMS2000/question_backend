package com.lms.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.QueryRecordDto;
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

}
