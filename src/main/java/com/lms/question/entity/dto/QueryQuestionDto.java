package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
import com.lms.question.valid.RangeCheck;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class QueryQuestionDto extends CustomPage implements Serializable {
    private static final long serialVersionUID = 1L;



    private String questionStem;
    @RangeCheck(range = {0,1,2,3,4})
    private Integer type;

}
