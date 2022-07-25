package com.znsd.oneself.service;

import cn.hutool.core.io.IoUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ConverterUtils;
import com.google.common.collect.Maps;
import com.znsd.oneself.util.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName ExcelService
 * @Author tao.he
 * @Since 2022/6/24 11:17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ExcelService {
    private final HttpServletResponse httpServletResponse;

    public Result<?> generateSql(MultipartFile multipartFile, boolean generateTable, Integer headRow, boolean export) throws IOException {
        if (generateTable) {
            //todo 生成临时表
        }
        final InputStream inputStream = multipartFile.getInputStream();
        Map<String,String> result = Maps.newHashMap();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("EXCEL转SQL任务");
        try(ExcelReader excelReader = EasyExcel.read(inputStream, getListener(result)).build();) {
            final List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();
            for (ReadSheet readSheet : readSheets) {
                readSheet.setHeadRowNumber(headRow);
                excelReader.read(readSheet);
            }
        }
        stopWatch.stop();
        log.debug("耗时:{}",stopWatch.prettyPrint());
        StringBuffer sb = new StringBuffer();
        result.forEach((k,v) -> sb.append("\r\n#").append(k).append("\r\n").append(v));
        final String[] split = Objects.requireNonNull(multipartFile.getOriginalFilename()).split("[.]");
        final String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + split[0]  + ".sql";
        //弹出下载
        if (export) {
            popWeb(sb.toString(), fileName);
            return null;
        }
        return Result.success(sb);
    }

    private void popWeb(String sb, String fileName) throws IOException {
        try (OutputStreamWriter writer = IoUtil.getWriter(httpServletResponse.getOutputStream(), StandardCharsets.UTF_8)) {
            httpServletResponse.setContentType("application/x-download;charset=utf-8");
            httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName,
                    StandardCharsets.UTF_8.toString()));
            writer.append(sb);
        }
    }


    private AnalysisEventListener<Map<String, Object>> getListener(Map<String, String> result) {
        return new AnalysisEventListener<Map<String,Object>>() {
            List<List<Object>> values = new ArrayList<>();
            String[] columns = new String[]{};

            @Override
            public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
                this.invokeHeadMap(ConverterUtils.convertToStringMap(headMap, context), context);
                columns = headMap.values().stream().map(ReadCellData::getStringValue).toArray(String[]::new);
                values.clear();
            }

            @Override
            public void invoke(Map<String,Object> data, AnalysisContext context) {
                values.add(new ArrayList<>(data.values()));
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                final String table = context.readSheetHolder().getSheetName();
                Insert insert = new Insert();
                insert.setTable(new Table(table));
                final int size = values.get(0).size();
                if (columns.length != size) {
                    final int i = columns.length - size;
                    if (i < 0) {
                        throw new RuntimeException("头信息与值不匹配");
                    }
                    for (int j = 0; j <i; j++) {
                        columns[columns.length - j - 1] = null;
                    }

                }
                insert.setColumns(Stream.of(columns)
                        .filter(Objects::nonNull)
                        .map(Column::new).collect(Collectors.toList()));
                final MultiExpressionList multiExpressionList = new MultiExpressionList();
                List<ExpressionList> expressionLists = new ArrayList<>();
                for (List<Object> value : values) {
                    final List<StringValue> stringValues =
                            value.stream().map(item -> new StringValue(item == null ? "" : item.toString())).collect(Collectors.toList());
                    ExpressionList expressionList = new ExpressionList();
                    expressionList.addExpressions(stringValues);
                    expressionLists.add(expressionList);
                }
                multiExpressionList.addExpressionLists(expressionLists);
                insert.setItemsList(multiExpressionList);
                // 设置插入值
                insert.setItemsList(multiExpressionList);
                result.put(table,insert.toString());
            }

        };
    }
}
