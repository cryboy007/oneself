package com.znsd.oneself.controller;

import com.znsd.oneself.common.BusinessCode;
import com.znsd.oneself.exception.BizException;
import com.znsd.oneself.message.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HelloController
 * @Author tao.he
 * @Since 2022/6/15 10:39
 */
@Api(tags = "测试")
@RestController(value = "test-hello")
@RequestMapping("hello")
public class HelloController {

    @GetMapping("globalError")
    @ApiOperation(value = "全局异常捕获测试", notes = "全局异常捕获测试", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isError", value = "是否抛出异常", required = false, dataType = "Boolean", paramType = "query") })
    public Result<?> testGlobalError(@RequestParam(value = "isError",required = false) boolean isError) {
        if (isError) {
            throw new BizException(BusinessCode.GLOBAL_TEST);
        }
        return Result.success();
    }
}
