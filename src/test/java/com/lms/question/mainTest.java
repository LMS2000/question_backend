package com.lms.question;

import com.lms.question.entity.dto.AddUserDto;
import com.lms.question.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = MainApplication.class)
public class mainTest {


    @Resource
    private IUserService userService;
    @Test
    public void test2(){
        System.out.println(userService.userRegister(AddUserDto.builder()
                .userRole("admin").password("12345678").username("root").build()));

    }

}
