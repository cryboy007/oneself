package com.znsd.oneself.controller;

import com.znsd.oneself.common.BusinessCode;
import com.znsd.oneself.exception.BizException;
import com.znsd.oneself.service.PersonService;
import com.znsd.oneself.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @ClassName MergerController
 * @Author tao.he
 * @Since 2022/7/25 11:14
 */
@Api(tags = "测试合并请求")
@RestController(value = "test-merger-controller")
@RequestMapping("merger")
@RequiredArgsConstructor
public class MergerController {

    private final PersonService personService;


    @GetMapping("queryById")
    @ApiOperation(value = "测试合并请求", notes = "测试合并请求", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long", paramType = "query") })
    public Result<?> queryById(@RequestParam(value = "id",required = true) Long id) {
        try {
            return Result.success(personService.getById(id));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

}
