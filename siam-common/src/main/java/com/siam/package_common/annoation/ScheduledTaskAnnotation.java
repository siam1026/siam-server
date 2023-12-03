package com.siam.package_common.annoation;

import java.lang.annotation.*;

/**
 * 定时任务自定义注解
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ScheduledTaskAnnotation {

    //任务代码
    String code() default "";
}