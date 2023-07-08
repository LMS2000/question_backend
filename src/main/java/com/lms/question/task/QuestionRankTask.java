package com.lms.question.task;

import cn.hutool.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lms.question.entity.dao.User;
import com.lms.question.service.IRecordService;
import com.lms.question.service.IUserBankService;
import com.lms.question.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QuestionRankTask {

    @Scheduled(cron = "0 0/30 * * * ?") // cron表达式:每三十分钟执行一次
    public void taskForCacheQuestionRank(){
        IUserService userService = SpringUtil.getBean(IUserService.class);

        IUserBankService userBankService = SpringUtil.getBean(IUserBankService.class);


        IRecordService recordService = SpringUtil.getBean(IRecordService.class);

//        userService.list(new QueryWrapper<User>().)

    }
}
