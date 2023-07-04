package com.lms.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dao.User;
import com.lms.question.mapper.RecordMapper;
import com.lms.question.mapper.UserMapper;
import com.lms.question.service.IRecordService;
import com.lms.question.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

}
