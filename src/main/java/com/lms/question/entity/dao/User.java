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
public class User {

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    private String username;

    private String nickname;
    private String password;

    private String email;

    private  Integer enable;

    private String avatar;

    @TableField("delete_flag")
    private Integer deleteFlag;

    @TableField("user_role")
    private  String userRole;


    private String remark;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
