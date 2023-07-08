package com.lms.question;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.vo.UserQuestionBrushingVo;
import com.lms.question.service.IUserBankService;
import com.lms.question.utis.CacheUtils;
import com.lms.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = MainApplication.class)
public class mainTest {


    /**
     * 测试缓冲用户记录
     */

    @Resource
    private RedisCache redisCache;

    @Resource
    private IUserBankService userBankService;
    @Test
    public void test2(){




//        Page<UserBank> page = userBankService.page(new Page<>(1, 10), new QueryWrapper<UserBank>()
//                .eq( "user_id", 1)
//                .eq( "type", 0)
//                .eq( "submit", 0));
//
//        List<UserBank> records = page.getRecords();
//        records.forEach(System.out::println);
//        List<UserBank> list = userBankService.list(new QueryWrapper<UserBank>()
//                .eq("user_id", 1)
//                .eq("type", 0)
//                .eq("submit", 0));
//        list.forEach(System.out::println);
//        List<UserQuestionBrushingVo> userQuestionBrushingVos=new ArrayList<>();

//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(1).questionAmount(20l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(2).questionAmount(30l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(3).questionAmount(40l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(4).questionAmount(50l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(5).questionAmount(60l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(6).questionAmount(70l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(7).questionAmount(80l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(8).questionAmount(90l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(9).questionAmount(100l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(10).questionAmount(30l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(11).questionAmount(40l).build());
//        userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().uid(12).questionAmount(50l).build());
//        CacheUtils.setUserQuestionAmount(userQuestionBrushingVos);

//        CacheUtils.getUserQuestionAmount();

//        List<RecordVo> list=new ArrayList<>();
//        list.add(RecordVo.builder().id(1).questionId(1).score(0f).build());
//        list.add(RecordVo.builder().id(2).questionId(2).score(10f).build());
//        CacheUtils.setTempRecord(1,1,0,list);
//
//       CacheUtils.getTempRecord(1, 1, 0).forEach(recordVo -> {
//           System.out.println(recordVo.getId());
//       });



    }

}
