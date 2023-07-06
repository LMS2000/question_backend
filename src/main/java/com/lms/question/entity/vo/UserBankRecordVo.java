package com.lms.question.entity.vo;

import com.lms.question.entity.dao.Record;
import com.lms.question.mapper.UserBankMapper;
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
public class UserBankRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;


    private UserBankVo userBankVo;

    private List<RecordVo> recordVoList;



}
