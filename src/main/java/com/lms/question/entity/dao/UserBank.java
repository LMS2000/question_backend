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
public class UserBank {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /*
      用户id
     */
    @TableField("user_id")
    private Integer userId;

    /*
      练习类型，练习还是考试， 练习 0   考试 1
     */
    private Integer type;

    /*
     所属题库
     */
    @TableField("bank_id")
    private Integer bankId;

    /*
      是否提交
     */
    private Integer submit;

    /*
     总分数
     */
    private Float score;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
