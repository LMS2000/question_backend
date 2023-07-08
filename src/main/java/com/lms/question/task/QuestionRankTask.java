package com.lms.question.task;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.vo.UserQuestionBrushingVo;
import com.lms.question.entity.vo.UserVo;
import com.lms.question.service.IRecordService;
import com.lms.question.service.IUserBankService;
import com.lms.question.service.IUserService;
import com.lms.question.utis.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lms.question.entity.factory.factory.UserFactory.USER_CONVERTER;

@Component
@Slf4j
public class QuestionRankTask {

    @Scheduled(cron = "0 0/30 * * * ?") // cron表达式:每三十分钟执行一次
    public void taskForCacheQuestionRank(){
        IUserService userService = SpringUtil.getBean(IUserService.class);

        IUserBankService userBankService = SpringUtil.getBean(IUserBankService.class);


        IRecordService recordService = SpringUtil.getBean(IRecordService.class);


        //获取全部
        List<User> users = userService.list(new QueryWrapper<User>().eq("user_role", UserConstant.DEFAULT_ROLE));

        List<UserVo> userVos = USER_CONVERTER.toListUserVo(users);



        //封装 key为 用户id  value为用户的对应的全部userbank的id
        Map<UserVo,List<Integer>> userBankMap=new HashMap<>();
        for (UserVo userVo : userVos) {
            List<Integer> uesrBankIds = userBankService.list(new QueryWrapper<UserBank>().eq("user_id", userVo.getUid())).stream().map(UserBank::getId).collect(Collectors.toList());
            userBankMap.put(userVo,uesrBankIds);
        }

        //查询所有的count刷题量，封装uid

        List<UserQuestionBrushingVo> userQuestionBrushingVos=new ArrayList<>();

        userBankMap.forEach((key,value)->{
            long count = recordService.count(new QueryWrapper<Record>().in("user_bank_id", value));
            userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().user(key).questionAmount(count).build());
        });

        //设置缓冲
        CacheUtils.setUserQuestionAmount(userQuestionBrushingVos);
    }
}
