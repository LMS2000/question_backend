package com.lms.question.entity.dto;

import com.lms.question.entity.vo.RecordVo;
import com.lms.question.valid.RangeCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Slf4j
public class SaveUserRecordDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "用户练习记录不能为空")
    private List<RecordVo> recordVoList;


}
