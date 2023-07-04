package com.lms.question.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class BankAndQuestionVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private BankVo bankVo;

    private List<QuestionVo> questionVoList;
}
