package com.znsd.oneself.common;

import com.znsd.oneself.exception.BizException;
import com.znsd.oneself.exception.ICode;
import com.znsd.oneself.utils.I18nUtil;

import java.text.MessageFormat;

/**
 * @enumName BusinessCode
 * @Author HETAO
 * @Date 2022/6/15 10:52
 */
public enum BusinessCode implements ICode {
    GLOBAL_TEST("business.0001", ""),
    ;

    private String code;

    private String message;

    @Override public String getCode() {
        return code;
    }

    @Override
    public String getMessage(String... args) {
        String msg = "";
        String code = this.getCode();
        try {
            msg = I18nUtil.getMessage(code, args);
        } catch (Exception e) {
            msg = this.getMessage(args);
        }
        return msg;
    }

    public void throwErr(String... args){
        throw new BizException(code, getMessage(args));
    }

    BusinessCode(String code , String message) {
        this.code = code;
        this.message = message;
    }
}
