package com.znsd.oneself.validation.constraints;


import com.znsd.oneself.validation.EnumStrCollectionValidator;
import com.znsd.oneself.validation.EnumStrValidator;
import com.znsd.oneself.validation.EnumValidate;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @version 1.0
 * @Title: EnumStrValidate.java
 * @Description: 值范围在枚举value的校验注解。支持校验的数据类型：String，Collection<String> 及子类
 * @使用说明: 直接在需要校验的属性上添加 @EnumStrValidate(枚举.class) ，其中枚举实现EnumValidate接口。eg: @EnumStrValidate(AppId.class)
 * @since 2021/1/27
 */

@Constraint(validatedBy = {EnumStrValidator.class, EnumStrCollectionValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public  @interface EnumStrValidate {

    String message() default "{e3plus.validation.constraints.EnumStrValidate.message}";

    /**
     * 枚举的Class
     * @return
     */
    Class<? extends Enum<? extends EnumValidate<String>>> value();

    /**
     * 空值是否校验，默认空值校验通过
     * @return
     */
    boolean isValidateNull() default false;

    Class[]groups()default {};

    Class[]payload()default {};

}
