package com.lms.question.controller;

import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.vo.GetQuestionsAndRecordVo;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.entity.vo.UserBankRecordVo;
import com.lms.question.service.IRecordService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/record")
@EnableResponseAdvice
public class RecordController {


    @Resource
    private IRecordService recordService;


    @PostMapping("/page/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public UserBankRecordVo getUserBankRecordVo(@PathVariable("id") Integer id,
                                                @RequestBody QueryRecordDto queryRecordDto){
       return recordService.getRecordByUserBankId(id,queryRecordDto);
    }

    @GetMapping("/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public RecordVo  getByRecordId(@PathVariable("id") Integer id){
        return recordService.getByRecordId(id);
    }


    /**
     * 修改用户题目的得分
     * @param updateUserScoreDto
     * @return
     */
    @PostMapping("/update")
    public Boolean updateUserScore(@RequestBody UpdateUserScoreDto updateUserScoreDto){
       return recordService.updateUserScore(updateUserScoreDto);
    }

    //
    /**
     * 根据模式，题库id，和用户id获取用户最近练习记录和题库的一套题
     * @param id
     * @return
     */
    @GetMapping("/get/page/{id}/{type}")
    public GetQuestionsAndRecordVo getQuestionsByMode(@PathVariable("id") Integer id, @PathVariable("type") Integer  type,
                                                      HttpServletRequest request){
        return recordService.getQuestionsByMode(id,type,request);
    }

//    //临时保存用户记录
//
//    //
//    public Boolean saveTempUserBankRecord(){
//
//    }


}
