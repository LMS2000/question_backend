package com.lms.question.entity.dto;

import com.lms.question.valid.RangeCheck;
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
public class ChangePublishStatusDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Positive(message = "id不合法")
    @NotNull
    private Integer bid;
    @RangeCheck(range = {0,1})
    @NotNull
    private Integer status;
}
