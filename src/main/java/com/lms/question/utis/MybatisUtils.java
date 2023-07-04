package com.lms.question.utis;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.Question;
import com.lms.question.service.IBankService;
import com.lms.question.service.IQuestionService;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class MybatisUtils {

    //查找符合条件的记录是否存在
    public static <T> boolean existCheck(IService<T> service, Map<String,Object> map){
        QueryWrapper<T> wrapper=new QueryWrapper<>();
        map.forEach(wrapper::eq);
        return service.count(wrapper)>0;
    }


    public static boolean checkBids(List<Integer> bids){
        if(bids==null||bids.size()<1)return false;
        IBankService bankService = SpringUtil.getBean(IBankService.class);
        long bidCount = bankService.count(new QueryWrapper<Bank>().in("id", bids));
        return bids.size()==bidCount;
    }

    public static boolean checkQids(List<Integer> qids){
        if(qids==null||qids.size()<1)return false;
        IQuestionService questionService = SpringUtil.getBean(IQuestionService.class);
        long qidCount = questionService.count(new QueryWrapper<Question>().in("id", qids));
        return qids.size()==qidCount;
    }
}
