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
public class Record {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /*
       用户题库id（用户刷一套题）
     */
    private Integer userBankId;

    /*
     题目id
     */
    private Integer questionId;


    /*
       用户答案
     */
    private String userAnswer;

    /*
       用户得分
     */
    private Float score;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
