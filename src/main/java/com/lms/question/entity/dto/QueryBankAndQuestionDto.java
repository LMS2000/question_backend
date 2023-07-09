package com.lms.question.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@ApiModel(value="QueryBankAndQuestionDto", description="条件查询题目类型和模糊查询题干")
public class QueryBankAndQuestionDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "题目类型")
    private Integer type;
    @ApiModelProperty(value = "题干")
    private String questionStem;
}
