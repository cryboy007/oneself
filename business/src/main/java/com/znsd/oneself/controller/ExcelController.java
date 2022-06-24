package com.znsd.oneself.controller;

import com.znsd.oneself.message.Result;
import com.znsd.oneself.service.ExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/generateSql")
    @ApiOperation(value = "根据excel生成InsertSql", notes = "根据excel生成InsertSql", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "是否生成DDL", required = true, dataType = "MultipartFile", paramType = "form"),
            @ApiImplicitParam(name = "headRow", value = "标题索引", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "generateTable", value = "是否生成DDL", required = false, dataType = "Boolean", paramType = "query")
    })
    public Result<?> generateSql(MultipartFile multipartFile,
                                 @RequestParam(value = "headRow",required = false,defaultValue = "1") Integer headRow,
                                 @RequestParam(value = "generateTable",required = false) boolean generateTable) throws IOException {
        return excelService.generateSql(multipartFile,generateTable,headRow);
    }
}
