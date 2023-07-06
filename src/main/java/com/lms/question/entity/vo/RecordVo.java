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
public class RecordVo implements Serializable {
    private static final long serialVersionUID = 1L;



    private Integer id;
    /*
       用户题库id（用户刷一套题）
     */
    private Integer userBankId;



    /*
     题目id
     */
    private Integer questionId;

    private QuestionVo questionVo;


    /*
       用户答案
     */
    private String userAnswer;


    private Integer correct;

    /*
       用户得分
     */
    private Float score;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
