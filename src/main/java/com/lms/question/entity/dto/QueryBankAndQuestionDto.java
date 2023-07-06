package com.lms.question.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class QueryBankAndQuestionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer type;

    private String questionStem;
}
