package com.lms.question;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lms.question.config.PredictServerProperties;
import com.lms.question.constants.UserConstant;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.Record;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.vo.PredictDataVo;
import com.lms.question.entity.vo.PredictVo;
import com.lms.question.entity.vo.UserQuestionBrushingVo;
import com.lms.question.entity.vo.UserVo;
import com.lms.question.service.IBankService;
import com.lms.question.service.IRecordService;
import com.lms.question.service.IUserBankService;
import com.lms.question.service.IUserService;
import com.lms.question.utis.CacheUtils;
import com.lms.question.utis.HttpApiUtil;
import com.lms.redis.RedisCache;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.lms.question.entity.factory.factory.UserFactory.USER_CONVERTER;


@SpringBootTest(classes = MainApplication.class)
public class mainTest {


    /**
     * 测试缓冲用户记录
     */

    @Resource
    private RedisCache redisCache;

    @Resource
    private IUserBankService userBankService;

    @Resource
    private IUserService userService;


    @Resource
    private IRecordService recordService;

    @Resource
    private IBankService bankService;

    @Resource
    private PredictServerProperties predictServerProperties;

    @Test
    public void test2() {

//        HttpApiUtil.getPredictData(predictServerProperties);


        //获取全部
//        List<User> users = userService.list(new QueryWrapper<User>().eq("user_role", UserConstant.DEFAULT_ROLE));
//
//        List<UserVo> userVos = USER_CONVERTER.toListUserVo(users);
//
//
//
//        //封装 key为 用户id  value为用户的对应的全部userbank的id
//        Map<UserVo,List<Integer>> userBankMap=new HashMap<>();
//        for (UserVo userVo : userVos) {
//            List<Integer> uesrBankIds = userBankService.list(new QueryWrapper<UserBank>().eq("user_id", userVo.getUid())).stream().map(UserBank::getId).collect(Collectors.toList());
//            userBankMap.put(userVo,uesrBankIds);
//        }
//
//        //查询所有的count刷题量，封装uid
//
//        List<UserQuestionBrushingVo> userQuestionBrushingVos=new ArrayList<>();
//
//        userBankMap.forEach((key,value)->{
//            if(value!=null&&value.size()>0){
//                long count = recordService.count(new QueryWrapper<Record>().in("user_bank_id", value));
//                userQuestionBrushingVos.add(UserQuestionBrushingVo.builder().user(key).questionAmount(count).build());
//            }
//
//        });
//
        //设置缓冲
        CacheUtils.getUserQuestionAmount();


    }

    @Test
    public void test3() {

//        IUserBankService userBankService = SpringUtil.getBean(IUserBankService.class);
//        IBankService bankService = SpringUtil.getBean(IBankService.class);

        Map<Integer, Bank> bankMap = bankService.list().stream().collect(Collectors.toMap(Bank::getId, Function.identity()));

        List<UserBank> userBanks = userBankService.list(new QueryWrapper<UserBank>().eq("user_id", 1));
        // key 为题库id  value为刷题次数
        Map<Integer, Long> countMap = userBanks.stream().collect(Collectors.groupingBy(UserBank::getBankId, Collectors.counting()));

        List<PredictDataVo> predictDataVoList = new ArrayList<>();
        userBanks.forEach(userBank -> {
            Float score = userBank.getScore();
            Integer times = countMap.get(userBank.getBankId()).intValue();
            String bankName = bankMap.get(userBank.getBankId()).getName();
            Integer type = userBank.getType();
            Duration duration = Duration.between(userBank.getCreateTime(), userBank.getUpdateTime());
            int minutes = (int) duration.toMinutes();
            predictDataVoList.add(PredictDataVo.builder().title(bankName).times(times).time(minutes).score(score).type(type).build());
        });


        String filePath = "classpath:static/" + 1 + "/data.csv";
        ClassLoader classLoader = ClassUtil.getClassLoader();
        File file = null;

        URL resourceUrl = classLoader.getResource(filePath);
        if (resourceUrl != null) {
            // 文件已存在，直接获取文件
            file = new File(resourceUrl.getFile());
        } else {
            // 文件不存在，创建新文件
            try {
                URI uri = classLoader.getResource("static/").toURI();
                file = new File(uri.getPath(), Integer.toString(1) + "/data.csv");
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            // 创建ExcelWriter对象，并设置文件路径和写入的目标类
            ExcelWriter excelWriter = EasyExcel.write(file, PredictDataVo.class).build();


            // 获取Sheet对象
            WriteSheet writeSheet = EasyExcel.writerSheet("Sheet1").build();

            // 写入内容数据
            excelWriter.write(predictDataVoList, writeSheet);

            // 关闭ExcelWriter对象，释放资源
            excelWriter.finish();
        }
    }


    @Test
    public void test5(){
        // 构建Http请求
        HttpRequest request = HttpUtil.createPost(predictServerProperties.getPredictUrl());
        File file = FileUtil.file("classpath:static/" + 1 + "/data.csv"); // 如果data.csv位于classpath:static目录下
        // 设置请求参数
        request.form("file", file);
        // 发送请求
        HttpResponse response = request.execute();
        System.out.println(response.body());

        // 获取响应结果
        List<PredictVo> predictVos = JSON.parseArray(response.body(), PredictVo.class);
        List<Double> preScores = predictVos.stream().map(PredictVo::getPredictedScore).collect(Collectors.toList());
        BigDecimal sum = BigDecimal.ZERO;
        for (Double preScore : preScores) {
            sum = sum.add(BigDecimal.valueOf(preScore));
        }
        BigDecimal average = sum.divide(BigDecimal.valueOf(preScores.size()), 2, RoundingMode.HALF_UP);

        // 转换为float类型
        float averageFloat = average.floatValue();
        System.out.println(averageFloat);
    }

}
