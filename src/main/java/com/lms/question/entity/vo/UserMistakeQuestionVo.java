package com.lms.question.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="GetUserMistakeQuestionVo", description="用户正确率需要的数据")
@Slf4j
public class UserMistakeQuestionVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "错误的题目")
    private QuestionVo question;
    @ApiModelProperty(value = "错误的次数")
    private Long mistakeNum;


}
