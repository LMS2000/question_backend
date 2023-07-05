package com.lms.question.entity.dto;

import com.lms.page.CustomPage;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class QueryPublishDto extends CustomPage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;


}
