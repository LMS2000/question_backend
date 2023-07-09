package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
import com.lms.question.valid.RangeCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@ApiModel(value="QueryQuestionDto", description="分页查询题目")
public class QueryQuestionDto extends CustomPage implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "题干")
    private String questionStem;
    @ApiModelProperty(value = "题目类型")
    private Integer type;

}
