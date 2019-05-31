package com.lgb.bootweb.annotation;

import com.lgb.bootweb.Enum.OptStatusEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysOptLog
{
    public  String sysName() default "";

    public  OptStatusEnum optStatus() ;
}