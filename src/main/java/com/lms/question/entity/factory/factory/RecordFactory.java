package com.lms.question.entity.factory.factory;

import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.entity.vo.UserBankVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class RecordFactory {

    public static final RecordConverter RECORD_CONVERTER= Mappers.getMapper(RecordConverter.class);
    @Mapper
    public interface RecordConverter {
        @Mappings({

        }
        )
        RecordVo toRecordVo(Record record);
        List<Record>   toListRecord(List<RecordVo> recordVoList);
        List<RecordVo> toListRecordVo(List<Record> records);
    }
}
