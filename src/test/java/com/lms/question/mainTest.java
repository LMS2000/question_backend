package com.lms.question;


import com.lms.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;


@SpringBootTest(classes = MainApplication.class)
public class mainTest {


    /**
     * 测试缓冲用户记录
     */

    @Resource
    private RedisCache redisCache;
    @Test
    public void test2(){

        RedisTemplate redisTemplate = redisCache.redisTemplate;
        System.out.println(redisTemplate);

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
