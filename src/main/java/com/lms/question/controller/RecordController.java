package com.lms.question.controller;

import com.lms.question.annotation.AuthCheck;
import com.lms.question.config.PredictServerProperties;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dto.QueryRecordDto;
import com.lms.question.entity.dto.SaveUserRecordDto;
import com.lms.question.entity.dto.UpdateUserScoreDto;
import com.lms.question.entity.vo.*;
import com.lms.question.service.IRecordService;
import com.lms.question.utis.CacheUtils;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.ehcache.Cache;
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


    /**
     * 获取刷题量怕排名前十的用户和
     * @return
     */
    @GetMapping("/get/amountRank")
    @ApiOperation("获取刷题量怕排名前十的用户和对应刷题量")
    public List<UserQuestionBrushingVo>  getUserQuestionRank(){
        return CacheUtils.getUserQuestionAmount();
    }

    /**
     * 获取用户的刷题量和正确数量
     * @param request
     * @return
     */
    @GetMapping("/get/amount")
    @ApiOperation("获取用户的刷题量和正确数量")
    public GetUserAccuracyRateVo getUserAccuracyRateVo(HttpServletRequest request){
        return recordService.getUserRate(request);
    }

    //推荐题目





    /**
     * 获取用户错误次数前三的题目个错误次数
     * @param request
     * @return
     */
    @GetMapping("/get/mistake")
    @ApiOperation("获取用户错误次数前三的题目个错误次数")
    public List<UserMistakeQuestionVo>  getUserMistakeQuestions(HttpServletRequest request){
        return recordService.getMistakeQuestions(request);
    }


    @GetMapping("/get/predict")
    @ApiOperation("预测用户最终的成绩")
    public Float getPredictScore(HttpServletRequest request){
        return recordService.getPredictScore(request);
    }


    @GetMapping("/get/recommend")
    @ApiOperation("获取推荐题库")
    public List<RecommendBankVo> getRecommendBanks(HttpServletRequest request){
        return recordService.getRecommendBanks(request);
    }

    //用户创建的时候就有一个自己的错题的题库

    //每次用户提交试卷的时候都会将写错的题目放到错题题库中

    //用户可以做错题。错题做对也不会移除错题集



}
