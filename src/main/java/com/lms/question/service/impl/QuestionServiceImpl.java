package com.lms.question.service.impl;

import cn.hutool.Hutool;
import cn.hutool.core.io.FileUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lms.contants.HttpCode;
import com.lms.question.entity.dao.Bank;
import com.lms.question.entity.dao.Question;
import com.lms.question.entity.dao.QuestionBank;
import com.lms.question.entity.dao.User;
import com.lms.question.entity.dto.AddQuestionDto;
import com.lms.question.entity.dto.QueryQuestionDto;
import com.lms.question.entity.dto.UpdateQuestionDto;
import com.lms.question.entity.vo.BankVo;
import com.lms.question.entity.vo.QuestionVo;
import com.lms.question.exception.BusinessException;
import com.lms.question.listener.QuestionListener;
import com.lms.question.mapper.QuestionMapper;
import com.lms.question.mapper.UserMapper;
import com.lms.question.service.IQuestionBankService;
import com.lms.question.service.IQuestionService;
import com.lms.question.service.IUserService;
import com.lms.question.utis.MybatisUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
;
import static com.lms.question.constants.FileConstant.XLS;
import static com.lms.question.constants.FileConstant.XLSX;
import static com.lms.question.entity.factory.factory.QuestionFactory.QUESTION_CONVERTER;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {


    @Resource
    private QuestionListener listener;


    @Resource
    private IQuestionBankService questionBankService;

    /**
     * 添加题目
     *
     * @param addQuestionDto
     * @return
     */
    @Override
    public Boolean addQuestion(AddQuestionDto addQuestionDto) {
        //校验选项
        Question question = new Question();
        BeanUtils.copyProperties(addQuestionDto, question);
        return this.save(question);
    }

    /**
     * 修改题目
     *
     * @param updateQuestionDto
     * @return
     */
    @Override
    public Boolean updateQuestion(UpdateQuestionDto updateQuestionDto) {

        Question question = new Question();
        BeanUtils.copyProperties(updateQuestionDto, question);
        return this.updateById(question);
    }

    /**
     * 删除题目
     *
     * @param qids
     * @return
     */
    @Override
    public Boolean removeQuestion(List<Integer> qids) {
        BusinessException.throwIfNot(MybatisUtils.checkQids(qids));
        this.removeBatchByIds(qids);
        return questionBankService.remove(new QueryWrapper<QuestionBank>().in("qid", qids));

    }


    /**
     * 查询分页
     *
     * @param queryQuestionDto
     * @return
     */
    @Override
    public Page<QuestionVo> pageQuestionList(QueryQuestionDto queryQuestionDto) {

        String questionStem = queryQuestionDto.getQuestionStem();
        Integer type = queryQuestionDto.getType();
        Integer pageNum = queryQuestionDto.getPageNum();
        Integer pageSize = queryQuestionDto.getPageSize();
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(questionStem), "question_stem", questionStem)
                .eq(validType(type), "type", type);

        Page<Question> pageBank = this.page(new Page<>(pageNum, pageSize), queryWrapper);
        List<Question> records = pageBank.getRecords();
        List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(records);
        Page<QuestionVo> result = new Page<>(pageNum, pageSize, pageBank.getTotal());
        result.setRecords(questionVos);
        return result;
    }

    @Override
    public QuestionVo getQuestionById(Integer id) {
        Question question = this.getById(id);
        return QUESTION_CONVERTER.toQuestionVo(question);
    }

    @Override
    public Boolean importQuestions(MultipartFile file) {
        //校验文件格式
        validFile(file);
        ExcelReaderBuilder read = null;
        try {
            read = EasyExcel.read(file.getInputStream(), QuestionVo.class, listener);
            ExcelReaderSheetBuilder sheet = read.sheet();
            //读取表格
            sheet.doRead();
        } catch (Exception e) {
            throw new BusinessException(HttpCode.OPERATION_ERROR, "导入excel失败");
        }
        return true;
    }

    @Override
    public void exportQuestions(HttpServletResponse response) {
        //设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        try {
            //导出的文件名
            String filename = URLEncoder.encode("题目集", "utf-8");
            //设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
            //获得流对象
            ServletOutputStream outputStream = response.getOutputStream();
            //获得write对象
            ExcelWriterBuilder write = EasyExcel.write(outputStream, QuestionVo.class);
            //获得数据表对象
            ExcelWriterSheetBuilder sheet = write.sheet();
            //准备需要输出的数据   调用业务层，获得所有需要导出的数据
            List<Question> list = this.list(null);
            List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(list);
            //生成表格文件
            sheet.doWrite(questionVos);
        } catch (Exception e) {
            throw new BusinessException(HttpCode.OPERATION_ERROR, "导出失败");
        }

    }

    @Override
    public void exportTemplates(HttpServletResponse response) {
        //设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");

        try {
            //导出的文件名
            String filename = URLEncoder.encode("题目集模板", "utf-8");
            //设置响应头
            response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx");
            //获得流对象
            ServletOutputStream outputStream = response.getOutputStream();
            //获得write对象
            ExcelWriterBuilder write = EasyExcel.write(outputStream, QuestionVo.class);
            //获得数据表对象
            ExcelWriterSheetBuilder sheet = write.sheet();
            //准备需要输出的数据   调用业务层，获得所有需要导出的数据
            List<Question> list = new ArrayList<>();
            List<QuestionVo> questionVos = QUESTION_CONVERTER.toListQuestionVo(list);
            //生成表格文件
            sheet.doWrite(questionVos);
        } catch (Exception e) {
            throw new BusinessException(HttpCode.OPERATION_ERROR, "导出失败");
        }
    }

    private boolean validType(Integer type) {
        List<Integer> types = List.of(0, 1, 2, 3, 4);
        return ObjectUtils.isNotEmpty(type) && types.contains(type);
    }

    private void validFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        String fileExtension = FileUtil.getSuffix(originalFilename);
        assert fileExtension != null;
        final long ONE_M = 1024 * 1024 * 10L;
        if (size > ONE_M) {
            throw new BusinessException(HttpCode.PARAMS_ERROR, "文件大小不能超过 10M");
        }
        BusinessException.throwIfNot(fileExtension.equals(XLSX) || fileExtension.equals(XLS),
                HttpCode.PARAMS_ERROR, "只能是xlsx或者xls");

    }
}
