package com.chunqiu.mrjuly.common.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @Retention：注解的保留位置
 * @Retention(RetentionPolicy.SOURCE) //注解仅存在于源码中，在class字节码文件中不包含
 * @Retention(RetentionPolicy.CLASS) // 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得，
 * @Retention(RetentionPolicy.RUNTIME) // 注解会在class字节码文件中存在，在运行时可以通过反射获取到
 */
@Retention(RetentionPolicy.RUNTIME)
/**
 * @Target:注解的作用目标
 * @Target(ElementType.TYPE)   //接口、类、枚举、注解
 * @Target(ElementType.FIELD) //字段、枚举的常量
 * @Target(ElementType.METHOD) //方法
 * @Target(ElementType.PARAMETER) //方法参数
 * @Target(ElementType.CONSTRUCTOR)  //构造函数
 * @Target(ElementType.LOCAL_VARIABLE)  //局部变量
 * @Target(ElementType.ANNOTATION_TYPE)  //注解
 * @Target(ElementType.PACKAGE) ///包
 */
@Target(ElementType.METHOD)
/**
 * @Document 说明该注解将被包含在javadoc中
 */
@Documented
public @interface RequestTimes {
    //单位时间允许访问次数 - - -默认值是2
    int count() default 2;

    //设置单位时间为1分钟 - - - 默认值是1分钟
    long time() default 60 * 1000;

}
