package com.znsd.oneself.job.annotation;

import java.lang.annotation.*;

/**
 * @InterfaceName JobHandler
 * @Author HETAO
 * @Date 2022/2/27 9:53
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JobHandler {
    String value() default "";
}