package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
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
@ApiModel(value="QueryBankPageDto", description="分页查询题库")
public class QueryBankPageDto extends CustomPage implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "题库名")
    private String name;
}
