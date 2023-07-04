package com.lms.question.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class LoginUserVo implements Serializable {
    private static final long serialVersionUID = 1L;


    private Integer uid;
    private String username;

    private String nickname;


    private String userRole;
}
