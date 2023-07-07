package com.lms.question;

import com.lms.question.entity.dto.AddUserDto;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.service.IUserService;
import com.lms.question.utis.CacheUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = MainApplication.class)
public class mainTest {


    /**
     * 测试缓冲用户记录
     */
    @Test
    public void test2(){

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
