package com.sky.Aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AspectAutoFill {
    //首先定义一个切入点，方便复用
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {}
    //指定切入点
    @Before("autoFillPointCut()")
    public void AutoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        log.info("进行字段自动填充");
        //首先需要知道sql类型是insert或者update
        //获取签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取方法上的注解
        AutoFill AutoFill = signature.getMethod().getAnnotation(AutoFill.class);
        //获取了方法上注解的值（枚举类型）
        OperationType value = AutoFill.value();
        //准备所要赋予的值
        LocalDateTime now = LocalDateTime.now();
        Long userId = BaseContext.getCurrentId();
        //获取拦截方法的对象(集合是一个对象数组）
        Object[] args = joinPoint.getArgs();
        Object a= args[0];
        //根据insert和select分别进行赋值,INSERT要对四个字段赋值，select只需对两个字段赋值
        if(value == OperationType.INSERT) {
            Method CreateTime =a.getClass().getMethod("setCreateTime",LocalDateTime.class);
            Method UpdateTime =a.getClass().getMethod("setUpdateTime",LocalDateTime.class);
            Method CreateUserID =a.getClass().getMethod("setCreateUser",long.class);
            Method UpdateUserID =a.getClass().getMethod("setUpdateUser",long.class);
            CreateTime.invoke(a,now);
            UpdateTime.invoke(a,now);
            CreateUserID.invoke(a,userId);
            UpdateUserID.invoke(a,userId);
        }else if(value == OperationType.UPDATE) {
                    //只需要对两个字段进行赋值
            Method UpdateTime =a.getClass().getMethod("setUpdateTime",LocalDateTime.class);
            Method UpdateUserID =a.getClass().getMethod("setUpdateUser",long.class);
            UpdateTime.invoke(a,now);
            UpdateUserID.invoke(a,userId);
        }
        //
    }
}