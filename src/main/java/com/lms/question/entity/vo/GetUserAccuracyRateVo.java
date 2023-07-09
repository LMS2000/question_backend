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
@ApiModel(value="GetUserAccuracyRateVo", description="用户正确率需要的数据")
@Slf4j
public class GetUserAccuracyRateVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "正确的题目数")
    private Long rightNum;
    @ApiModelProperty(value = "刷题的总题目数")
    private Long questionAmount;

}
