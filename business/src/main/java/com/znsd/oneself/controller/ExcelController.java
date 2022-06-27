package com.znsd.oneself.controller;

import com.znsd.oneself.message.Result;
import com.znsd.oneself.service.ExcelService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;

/**
 * @ClassName ExcelController
 * @Author tao.he
 * @Since 2022/6/24 11:14
 */
@RestController("api-excel")
@RequestMapping("/excel")
@Api("excel-控制器")
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelService excelService;

    @PostMapping(value = "/generateSql")
    @ApiOperation(value = "根据excel生成InsertSql", notes = "根据excel生成InsertSql", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "headRow", value = "标题索引", required = false, dataType = "Integer", paramType = "query",defaultValue = "1"),
            @ApiImplicitParam(name = "generateTable", value = "是否生成DDL", required = false, dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "export", value = "是否网页导出下载", required = false, dataType = "Boolean", paramType = "query")
    })
    public Result<?> generateSql(@RequestPart("file") MultipartFile multipartFile, Integer headRow, boolean generateTable,boolean export) throws IOException {
       return excelService.generateSql(multipartFile,generateTable,headRow,export);
    }
}
