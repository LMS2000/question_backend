package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
@ApiModel(value="QueryCurrentUserBanksDto", description="分页查询当前用户的练习记录")
public class QueryCurrentUserBanksDto extends CustomPage implements Serializable {

    //练习或者考试状态
    @ApiModelProperty(value = "练习模式，0为练习模式，1为考试模式")
    private  Integer type;
    @ApiModelProperty(value = "是否提交，0为未提交，1为提交")
    private Integer submit;
}
