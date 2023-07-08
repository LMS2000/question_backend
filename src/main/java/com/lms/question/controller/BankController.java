package com.lms.question.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.*;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.PublishBankVo;
import com.lms.question.service.IBankService;
import com.lms.result.EnableResponseAdvice;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.lms.question.constants.BankConstant.PUBLISHED;

@RestController
@RequestMapping("/bank")
@EnableResponseAdvice
@Api(description = "题库管理")
public class BankController {

    @Resource
    private IBankService bankService;


    /**
     * 添加题库
     * @param addBankDto
     * @return
     */
    @PostMapping("/add")
    @ApiOperation("添加题库")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean addBank(@Validated @RequestBody AddBankDto addBankDto){
        return bankService.addBank(addBankDto);
    }


    @PostMapping("/remove")
    @ApiOperation("批量删除题库")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean removeBanks(@RequestParam("bids") List<Integer> bids){
        return bankService.removeBank(bids);
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("分页查询题库")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Page<BankVo> pageBankList( @RequestBody QueryBankPageDto queryBankPageDto){
        return bankService.pageBankList(queryBankPageDto);
    }

    /**
     * 修改题库信息
     * @param updateBankDto
     * @return
     */

    @PostMapping("/update")
    @ApiOperation("修改题库信息")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean updateBank(@Validated @RequestBody UpdateBankDto updateBankDto){
        return bankService.updateBank(updateBankDto);
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id获取题库")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BankVo getById(@PathVariable Integer id){
        return bankService.getBankById(id);
    }


    /**
     * 改变发布状态
     * @param changePublishStatusDto
     * @return
     */
    @PostMapping("/change/publish")
    @ApiOperation("改变题库发布状态")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean changePublish(@Validated @RequestBody ChangePublishStatusDto changePublishStatusDto){
       return bankService.changePublishBank(changePublishStatusDto);
    }

    /**
     * 获取发布的题库,如果当前的用户已经有在练习的题库，则应该显示继续学习
     * @param queryPublishDto
     * @return
     */
    @PostMapping("/page/publish")
    @ApiOperation("获取发布的题库")
    public PublishBankVo pagePublishBankList(@RequestBody QueryPublishDto queryPublishDto, HttpServletRequest request){
        return bankService.pagePublishBankList(queryPublishDto,request);
    }





}
