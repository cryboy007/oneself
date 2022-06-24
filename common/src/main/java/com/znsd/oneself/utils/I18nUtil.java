package com.znsd.oneself.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class I18nUtil {

    /*private static final Map<String, Locale> localMap;

    static {
        localMap = new HashMap<>();
        localMap.put("zh_CN", Locale.CHINA);
        localMap.put("en_US", Locale.US);
    }*/

    private static String locale;


    @Qualifier("messageSource")
    @Autowired
    private MessageSource autoMessageSource;
    private static MessageSource messageSource;

    @PostConstruct
    private void init() {
        messageSource = autoMessageSource;
    }

    public static String getMessage(String code, Object... paras) {
        return messageSource.getMessage(code, paras, LocaleContextHolder.getLocale());
    }

    public static String getMessage(String code) {
        return getMessage(code, null);
    }


}
