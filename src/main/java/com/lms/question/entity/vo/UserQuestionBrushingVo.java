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
public class UserQuestionBrushingVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户
    private UserVo user;
    //刷题量
    private Long questionAmount;
}
