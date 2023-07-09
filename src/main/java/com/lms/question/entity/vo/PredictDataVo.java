package com.lms.question.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class PredictDataVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "time", index = 0)
    private Integer time;
    @ExcelProperty(value = "type", index = 1)
    private Integer type;
    @ExcelProperty(value = "times", index = 2)
    private Integer times;
    @ExcelProperty(value = "title", index = 3)
    private String title;
    @ExcelProperty(value = "score", index = 4)
    private Float score;
}
