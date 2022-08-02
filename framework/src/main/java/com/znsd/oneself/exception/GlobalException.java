package com.znsd.oneself.exception;

import com.znsd.oneself.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @ClassName GlobalException
 * @Author tao.he
 * @Since 2022/6/15 10:17
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalException {
    @ExceptionHandler(BizException.class)
    public Result<?> handleSupportException(BizException e){
        final Result<?> error = Result.error(e.getCode(), e.getErrMsg());
        log.warn("请求id:"+ error.getRequestId() ,e);
        return error;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        return Result.error("99999", message);
    }
}
