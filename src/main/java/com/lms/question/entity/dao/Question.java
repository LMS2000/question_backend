package com.lms.question.entity.dao;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Question {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /*
    题干
     */
    @TableField("question_stem")
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

    @TableField("question_score")
    private Float questionScore;

    /*
    答案解析
     */
    private String explanation;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
