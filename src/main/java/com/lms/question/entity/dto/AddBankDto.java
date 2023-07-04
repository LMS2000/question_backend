package com.lms.question.entity.dto;

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
public class AddBankDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "题库名称不能为空")
    @NotBlank(message = "题库名称不能为空")
    private String name;

    @Length(max = 255)
    private String remark;
}
