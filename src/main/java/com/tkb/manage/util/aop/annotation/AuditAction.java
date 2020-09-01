package com.tkb.manage.util.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tkb.manage.util.aop.common.enums.Action;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditAction {
	/**
     * 操作類型
     */
    Action action() default Action.GET;

    /**
     * 目標table
     */
    String targetTable() default "";
    /**
     * @return 字段名稱
     */
    String name() default "";
}
