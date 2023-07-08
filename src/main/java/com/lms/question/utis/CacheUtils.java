package com.lms.question.utis;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.lms.question.constants.RecordHeaderConstant;
import com.lms.question.entity.vo.RecordVo;
import com.lms.question.entity.vo.UserQuestionBrushingVo;
import com.lms.question.entity.vo.UserVo;
import com.lms.question.exception.BusinessException;
import com.lms.redis.RedisCache;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.stream.Collectors;

import static com.lms.question.constants.RecordHeaderConstant.USER_QUESTION_AMOUNT;


public class CacheUtils {





    //设置临时练习记录
    public static boolean  setTempRecord(Integer ubid,List<RecordVo> recordVoList){
        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        String userRecordHeader=getHeader(ubid);
        BusinessException.throwIf(CollectionUtil.isEmpty(recordVoList));
        redisCache.setCacheObject(userRecordHeader,recordVoList);
        return true;
    }


    public static  void removeTempRecord(Integer ubid){
        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        redisCache.deleteObject(getHeader(ubid));
    }

    //获取key
    public static String getHeader(Integer ubid){
        return RecordHeaderConstant.USER_RECORD+ubid;
    }



    //获取用户一次练习的临时答题情况的记录
    public static List<RecordVo>  getTempRecord(Integer ubid){
         String header=getHeader(ubid);
        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        return redisCache.getCacheObject(header);

    }

    //缓冲用户刷题信息

    public static void setUserQuestionAmount(List<UserQuestionBrushingVo> questionAmounts){

        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        RedisTemplate redisTemplate = redisCache.redisTemplate;
        for (UserQuestionBrushingVo obj : questionAmounts) {
            redisTemplate.opsForZSet().add(USER_QUESTION_AMOUNT,obj.getUser(),obj.getQuestionAmount());
        }
    }

    /**
     * 获取刷题量排名前十的
     * @return
     */
    public static  List<UserQuestionBrushingVo> getUserQuestionAmount(){
        RedisCache redisCache = SpringUtil.getBean(RedisCache.class);
        RedisTemplate redisTemplate = redisCache.redisTemplate;

        Set<ZSetOperations.TypedTuple<Object>> set = redisTemplate.opsForZSet().rangeByScoreWithScores(USER_QUESTION_AMOUNT, Double.MIN_VALUE, Double.MAX_VALUE);

        SortedSet<ZSetOperations.TypedTuple<Object>> sortedSet = new TreeSet<>((o1, o2) -> Double.compare(o2.getScore(), o1.getScore()));
        sortedSet.addAll(set);
        List<UserQuestionBrushingVo> resultList=new ArrayList<>();
        sortedSet.forEach(value->{
            resultList.add(UserQuestionBrushingVo.builder().user( (UserVo)value.getValue())
                    .questionAmount(Objects.requireNonNull(value.getScore()).longValue()).build());
      });
        return resultList;
    }

    //获取用户答题数量，正确数量，错误数量，错误最多的体型列表（3种）




}
