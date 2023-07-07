package com.lms.question.constants;

public interface QuestionConstant {

    /*
    单选
     */
    Integer SINGLE =0;

    /*
     多选
     */
    Integer MULTIPLE=1;

    /*
     填空
     */
    Integer  GAP_FILLING=2;

    /*
     判断
     */
    Integer TRUE_OR_FALSE=3;

    /*
    主观
     */
    Integer SUBJECTIVE=4;

    //选项分割
    String OPTIONS_SUFFIX=";";

    //题目正确
    Integer  CORRECT=1;
    //题目错误
    Integer UNCORRECT=0;

}
