package com.lms.question.entity.dto;

import com.lms.question.valid.RangeCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="AddQuestionDto", description="添加题目")
public class AddQuestionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "题干不能为空")
    @NotBlank(message = "题干不能为空")
    @ApiModelProperty(value = "题干")
    private String questionStem;

    @RangeCheck(range = {0,1,2,3,4})
    @ApiModelProperty(value = "题目类型，如单选多选")
    private Integer type;
    /*
     选项
    */
    @ApiModelProperty(value = "题目选项")
    private String options;

    /*
     答案
     */
    @NotNull(message = "题目不能为空")
    @NotBlank(message = "题目不能为空")
    @ApiModelProperty(value = "题目答案")
    private String answer;


    /*
       题目的总分
     */

    @ApiModelProperty(value = "题目分数")
    private Float questionScore;

    /*
    答案解析
     */
    @ApiModelProperty(value = "题目解析")
    private String explanation;
}
