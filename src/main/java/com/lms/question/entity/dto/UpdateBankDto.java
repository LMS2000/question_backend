package com.lms.question.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class UpdateBankDto implements Serializable {
    private static final long serialVersionUID = 1L;


    @Positive(message = "非法id")
    @NotNull(message = "id不能为空")
    private Integer id;
    @Length(max = 255)
    private String remark;
}
