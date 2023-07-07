package com.lms.question.utis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lms.question.constants.RecordHeaderConstant;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.exception.BusinessException;
import com.lms.redis.RedisCache;
import org.springframework.data.redis.core.convert.Bucket;

import java.util.List;

//临时保存用户练习记录，
public class CacheUtils {





    //设置临时练习记录
    public static boolean  setTempRecord(Integer ubid,List<RecordVo> recordVoList){
        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        String userRecordHeader=getHeader(ubid);
        BusinessException.throwIf(CollectionUtil.isEmpty(recordVoList));
        redisCache.setCacheObject(userRecordHeader,recordVoList);
        return true;
    }

    public static String getHeader(Integer ubid){
        return RecordHeaderConstant.USER_RECORD+ubid;
    }



    //获取用户一次练习的临时答题情况的记录
    public static List<RecordVo>  getTempRecord(Integer ubid){
         String header=getHeader(ubid);
        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        return redisCache.getCacheObject(header);

    }


}
