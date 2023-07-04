package com.lms.question.entity.dto;

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
public class QueryBankPageDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageNum;
    private Integer pageSize;
    private String name;
}
