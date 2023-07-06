package com.lms.question.controller;

import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.entity.vo.UserBankRecordVo;
import com.lms.question.service.IRecordService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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



    @PostMapping("/update")
    public Boolean updateUserScore(@RequestBody UpdateUserScoreDto updateUserScoreDto){
       return recordService.updateUserScore(updateUserScoreDto);
    }
}
