package com.lms.question.entity.enums;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum QuestionTypeEnum {

    SINGLE(0),
    MULTIPLE(1),
    GAP_FILLING(2),
    TRUE_OR_FALSE(3),
    SUBJECTIVE(4);

    private final Integer value;

    QuestionTypeEnum(Integer value) {
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(QuestionTypeEnum::getValue).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static QuestionTypeEnum getEnumByValue(Integer value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (QuestionTypeEnum mockTypeEnum : QuestionTypeEnum.values()) {
            if (mockTypeEnum.value.equals(value)) {
                return mockTypeEnum;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }
}
