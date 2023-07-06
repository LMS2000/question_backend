package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class QueryRecordDto  implements Serializable {
    //根据 题目类型，题干，，正确或者错误，查询
    /*
    题目类型
     */
    private Integer type;


    private String questionStem;

    private Integer correct;
}
