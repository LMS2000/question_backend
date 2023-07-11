package com.lms.question.utis;

import cn.hutool.Hutool;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lms.contants.HttpCode;
import com.lms.question.config.PredictServerProperties;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.UserBank;
import com.lms.question.entity.vo.PredictDataVo;
import com.lms.question.entity.vo.PredictVo;
import com.lms.question.exception.BusinessException;
import com.lms.question.service.IBankService;
import com.lms.question.service.IUserBankService;

import java.io.File;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HttpApiUtil {


    //获取预测成绩列表，计算平均分并返回
    public static float getPredictData(PredictServerProperties predictServerProperties, Integer uid) {

        // 构建Http请求
        HttpRequest request = HttpUtil.createPost(predictServerProperties.getPredictUrl());
        File file = FileUtil.file("classpath:static/" + uid + "/data.csv"); // 如果data.csv位于classpath:static目录下
        // 设置请求参数
        request.form("file", file);
        // 发送请求
        HttpResponse response = request.execute();

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
        return averageFloat;
    }




    //根据数据生成csv文件
    public static void buildCSVFile( Integer uid) {
        //构建预测需要的数据

        IUserBankService userBankService = SpringUtil.getBean(IUserBankService.class);
        IBankService bankService = SpringUtil.getBean(IBankService.class);
        Map<Integer, Bank> bankMap = bankService.list().stream().collect(Collectors.toMap(Bank::getId, Function.identity()));

        List<UserBank> userBanks = userBankService.list(new QueryWrapper<UserBank>().eq("user_id", uid));
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


        String filePath = "classpath:static/" + uid + "/data.csv";
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
                file = new File(uri.getPath(), Integer.toString(uid) + "/data.csv");
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
}
