package com.lms.question.entity.dto;


import com.lms.question.valid.RangeCheck;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryUserPageDto implements Serializable {




    private Integer pageNum;
    private Integer pageSize;
    private String username;

    @RangeCheck(range = {0,1})
    private Integer enable;
}
