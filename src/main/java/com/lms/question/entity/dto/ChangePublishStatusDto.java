package com.lms.question.entity.dto;

import com.lms.question.valid.RangeCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
@ApiModel(value="ChangePublishStatusDto", description="改变题目发布状态")
public class ChangePublishStatusDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Positive(message = "id不合法")
    @NotNull(message = "题库id不能为空")
    @ApiModelProperty(value = "用户名")
    private Integer bid;
    @RangeCheck(range = {0,1})
    @NotNull(message = "发布状态不能为空")
    @ApiModelProperty(value = "题库发布状态")
    private Integer status;
}
