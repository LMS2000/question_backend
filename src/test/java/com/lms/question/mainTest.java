package com.lms.question;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import com.lms.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.lms.question.entity.factory.factory.UserFactory.USER_CONVERTER;


@SpringBootTest(classes = MainApplication.class)
public class mainTest {


    /**
     * 测试缓冲用户记录
     */

    @Resource
    private RedisCache redisCache;

    @Resource
    private IUserBankService userBankService;

    @Resource
    private IUserService userService;



    @Resource
    private IRecordService recordService;

    @Test
    public void test2(){

        System.out.println(CacheUtils.getUserQuestionAmount());


        //获取全部
//        List<User> users = userService.list(new QueryWrapper<User>().eq("user_role", UserConstant.DEFAULT_ROLE));
//
//        List<UserVo> userVos = USER_CONVERTER.toListUserVo(users);
//
//
//
//        //封装 key为 用户id  value为用户的对应的全部userbank的id
//        Map<UserVo,List<Integer>> userBankMap=new HashMap<>();
//        for (UserVo userVo : userVos) {
//            List<Integer> uesrBankIds = userBankService.list(new QueryWrapper<UserBank>().eq("user_id", userVo.getUid())).stream().map(UserBank::getId).collect(Collectors.toList());
//            userBankMap.put(userVo,uesrBankIds);
//        }
//
//        //查询所有的count刷题量，封装uid
//
//        List<UserQuestionBrushingVo> userQuestionBrushingVos=new ArrayList<>();
//
//        userBankMap.forEach((key,value)->{
//            if(value!=null&&value.size()>0){
//                long count = recordService.count(new QueryWrapper<Record>().in("user_bank_id", value));
//                userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().user(key).questionAmount(count).build());
//            }
//
//        });
//
//        //设置缓冲
//        CacheUtils.setUserQuestionAmount(userQuestionBrushingVos);


    }

}
