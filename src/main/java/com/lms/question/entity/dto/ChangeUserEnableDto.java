package com.lms.question.entity.dto;


import com.lms.question.valid.RangeCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class ChangeUserEnableDto implements Serializable {


    @Positive(message = "id不合法")
    private Integer uid;
    @RangeCheck(range = {0,1})
    private Integer enable;
}
