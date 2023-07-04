package com.lms.question.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class QuestionVo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;
    /*
    题干
     */
    private String questionStem;


    /*
      题目类型
     */
    private Integer type;

    /*
      选项
     */
    private String options;

    /*
     答案
     */
    private String answer;


    /*
       题目的总分
     */

    private Float questionScore;

    /*
    答案解析
     */
    private String explanation;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
