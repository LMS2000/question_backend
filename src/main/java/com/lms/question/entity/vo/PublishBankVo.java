package com.lms.question.entity.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class PublishBankVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /*
     用户已经在练习的但是没有提交的练习记录id
     */
    private List<Integer>  userBankIds;

    private Page<BankVo>   bankVoPage;
}
