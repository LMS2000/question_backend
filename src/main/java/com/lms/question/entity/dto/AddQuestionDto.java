package com.lms.question.entity.dto;

import com.lms.question.valid.RangeCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class AddQuestionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "题干不能为空")
    @NotBlank(message = "题干不能为空")
    private String questionStem;

    @RangeCheck(range = {0,1,2,3,4})
    private Integer type;
    /*
     选项
    */
    private String options;

    /*
     答案
     */
    @NotNull(message = "题目不能为空")
    @NotBlank(message = "题目不能为空")
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
