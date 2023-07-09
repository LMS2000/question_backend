package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
@ApiModel(value="QueryRecordDto", description="题目记录查询")
public class QueryRecordDto  implements Serializable {
    //根据 题目类型，题干，，正确或者错误，查询
    /*
    题目类型
     */
    @ApiModelProperty(value = "题目类型")
    private Integer type;

    @ApiModelProperty(value = "题干")
    private String questionStem;
    @ApiModelProperty(value = "是否正确")
    private Integer correct;
}
