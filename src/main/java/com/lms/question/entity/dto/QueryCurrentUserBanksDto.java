package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class QueryCurrentUserBanksDto extends CustomPage implements Serializable {

    //练习或者考试状态
    private  Integer type;

    private Integer submit;
}
