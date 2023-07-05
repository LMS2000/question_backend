package com.lms.question.entity.dto;


import com.lms.page.CustomPage;
import com.lms.question.valid.RangeCheck;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class QueryUserPageDto extends CustomPage implements Serializable {




    private String username;

    @RangeCheck(range = {0,1})
    private Integer enable;
}
