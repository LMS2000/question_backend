package com.lms.question.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionBank {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /*
    题目id
     */
    private Integer qid;
    /*
    题库id
     */
    private Integer bid;
}
