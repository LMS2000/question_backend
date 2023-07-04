package com.lms.question.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lms.question.valid.RangeCheck;
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
public class UpdateQuestionDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer id;


    /*
    题干
     */
    private String questionStem;


    /*
      题目类型
     */
    @RangeCheck(range = {0,1,2,3,4})
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

}
