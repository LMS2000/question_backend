package com.lms.question.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.entity.dto.AddBankDto;
import com.lms.question.entity.dto.QueryBankPageDto;
import com.lms.question.entity.dto.UpdateBankDto;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.service.IBankService;
import com.lms.result.EnableResponseAdvice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public Boolean addBank(@Validated @RequestBody AddBankDto addBankDto){
        return bankService.addBank(addBankDto);
    }


    @PostMapping("/remove")
    public Boolean removeBanks(@RequestParam("bids") List<Integer> bids){
        return bankService.removeBank(bids);
    }

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/page")
    public Page<BankVo> pageBankList( @RequestBody QueryBankPageDto queryBankPageDto){
        return bankService.pageBankList(queryBankPageDto);
    }

    /**
     * 修改题库信息
     * @param updateBankDto
     * @return
     */

    @PostMapping("/update")
    public Boolean updateBank(@Validated @RequestBody UpdateBankDto updateBankDto){
        return bankService.updateBank(updateBankDto);
    }


    @GetMapping("/{id}")
    public BankVo getById(@PathVariable Integer id){
        return bankService.getBankById(id);
    }
}
