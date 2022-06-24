package com.znsd.oneself.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.CellData;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ConverterUtils;
import com.google.common.collect.Maps;
import com.znsd.oneself.message.Result;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName ExcelService
 * @Author tao.he
 * @Since 2022/6/24 11:17
 */
@Service
@Slf4j
public class ExcelService {
    public Result<?> generateSql(MultipartFile multipartFile, boolean generateTable, Integer headRow) throws IOException {
        if (generateTable) {
            //todo 生成临时表
        }
        final InputStream inputStream = multipartFile.getInputStream();
        Map<String,String> result = Maps.newHashMap();
        try(ExcelReader excelReader = EasyExcel.read(inputStream, getListener(result)).build();) {
            final List<ReadSheet> readSheets = excelReader.excelExecutor().sheetList();
            for (ReadSheet readSheet : readSheets) {
                readSheet.setHeadRowNumber(headRow);
                //解析
                excelReader.read(readSheet);
            }
        }
        return Result.success(result);
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
                    final List<StringValue> stringValues = value.stream().map(Object::toString).map(StringValue::new).collect(Collectors.toList());
                    ExpressionList expressionList = new ExpressionList();
                    expressionList.addExpressions(stringValues);
                    expressionLists.add(expressionList);
                }
                multiExpressionList.addExpressionLists(expressionLists);
                insert.setItemsList(multiExpressionList);
                // 设置插入值
                insert.setItemsList(multiExpressionList);
                log.info("SQL:{}",insert.toString());
                result.put(table,insert.toString());
            }

        };
    }
}
