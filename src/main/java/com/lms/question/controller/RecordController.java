package com.lms.question.controller;

import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.SaveUserRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.vo.GetQuestionsAndRecordVo;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.entity.vo.UserBankRecordVo;
import com.lms.question.service.IRecordService;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/record")
@EnableResponseAdvice
@Api(description = "作题记录管理")
public class RecordController {


    @Resource
    private IRecordService recordService;


    @PostMapping("/page/{id}")
    @ApiOperation("分页查询题库下的题目")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public UserBankRecordVo getUserBankRecordVo(@PathVariable("id") Integer id,
                                                @RequestBody QueryRecordDto queryRecordDto){
       return recordService.getRecordByUserBankId(id,queryRecordDto);
    }

    @GetMapping("/{id}")
    @ApiOperation("分页查询题库下的题目")
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
    @ApiOperation("修改用户题目的得分")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean updateUserScore(@RequestBody UpdateUserScoreDto updateUserScoreDto){
       return recordService.updateUserScore(updateUserScoreDto);
    }

    //
    /**
     * 根据模式，题库id，和用户id获取用户最近练习记录和题库的一套题
     * 返回的
     * @param id
     * @return
     */
    @GetMapping("/get/page/{id}/{type}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @ApiOperation("根据模式，题库id，和用户id获取用户最近练习记录和题库的一套题")
    public GetQuestionsAndRecordVo getQuestionsByMode(@PathVariable("id") Integer id, @PathVariable("type") Integer  type,
                                                      HttpServletRequest request){
        return recordService.getQuestionsByMode(id,type,request);
    }

    //临时保存用户记录

    //

    /**
     * 临时保存用户答题情况，需要recordList 用户记录ubid
     * @return
     */
    @PostMapping("/save/temp")
    @ApiOperation("临时保存用户答题情况，需要recordList 用户记录ubid")
    public Boolean saveTempUserBankRecord( @RequestBody SaveUserRecordDto saveUserRecordDto){
         return recordService.saveTempUserRecord(saveUserRecordDto);
    }

    /**
     *
     * @return
     */
    @PostMapping("/submit")
    @ApiOperation("提交全部题目，计算用户的得分")
    public Boolean submitPaper(@RequestBody SaveUserRecordDto saveUserRecordDto){
         return recordService.calculateScore(saveUserRecordDto);
    }

     //根据ubid获取题目 如果说命中缓冲有临时答题记录就

    /**
     * 根据ubid用户练习记录id获取题目列表和临时答题记录
     * @param ubid
     * @return
     */
    @GetMapping("/get/user/{ubid}")
    @ApiOperation("根据ubid用户练习记录id获取题目列表和临时答题记录")
    public  GetQuestionsAndRecordVo  getCurrentUserRecords(@PathVariable("ubid") Integer ubid){
          return recordService.getCurrentUserRecords(ubid);
    }

}
