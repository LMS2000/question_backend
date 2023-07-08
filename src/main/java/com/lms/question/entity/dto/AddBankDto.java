package com.lms.question.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
@ApiModel(value="AddBankDto", description="添加题库")
public class AddBankDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "题库名称不能为空")
    @NotBlank(message = "题库名称不能为空")
    @ApiModelProperty(value = "题库名称")
    private String name;

    @Length(max = 255)
    @ApiModelProperty(value = "备注")
    private String remark;
}
