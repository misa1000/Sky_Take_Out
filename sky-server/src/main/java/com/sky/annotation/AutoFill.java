package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//指定该注解只可以加在方法上
@Retention(RetentionPolicy.RUNTIME)//指定作用范围（运行时）
public @interface AutoFill {
    //注解里面的元素，可以进行数据的存储。枚举类型
OperationType value();
}
