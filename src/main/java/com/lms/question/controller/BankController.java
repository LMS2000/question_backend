package com.lms.question.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.annotation.AuthCheck;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dto.*;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.service.IBankService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.lms.question.constants.BankConstant.PUBLISHED;

@RestController
@RequestMapping("/bank")
@EnableResponseAdvice
public class BankController {

    @Resource
    private IBankService bankService;


    /**
     * 添加题库
     * @param addBankDto
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean addBank(@Validated @RequestBody AddBankDto addBankDto){
        return bankService.addBank(addBankDto);
    }


    @PostMapping("/remove")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean removeBanks(@RequestParam("bids") List<Integer> bids){
        return bankService.removeBank(bids);
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/page")
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
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public Boolean updateBank(@Validated @RequestBody UpdateBankDto updateBankDto){
        return bankService.updateBank(updateBankDto);
    }


    @GetMapping("/{id}")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BankVo getById(@PathVariable Integer id){
        return bankService.getBankById(id);
    }

    @PostMapping("/change/publish")
    public Boolean changePublish(@Validated @RequestBody ChangePublishStatusDto changePublishStatusDto){
       return bankService.changePublishBank(changePublishStatusDto);
    }

    @PostMapping("/page/publish")
    public Page<BankVo> pagePublishBankList(@RequestBody QueryPublishDto queryPublishDto){
        return bankService.pagePublishBankList(queryPublishDto);
    }
}
