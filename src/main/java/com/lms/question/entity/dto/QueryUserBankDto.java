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
@ApiModel(value="QueryUserBankDto", description="用户练习记录分页查询")
public class QueryUserBankDto extends CustomPage implements Serializable {
    private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "用户名")
   private String username;

   //练习或者考试状态
   @ApiModelProperty(value = "题目类型")
   private  Integer type;
   //题库名
   @ApiModelProperty(value = "题库名")
   private String bankName;
   @ApiModelProperty(value = "是否提交")
   private Integer submit;

}
