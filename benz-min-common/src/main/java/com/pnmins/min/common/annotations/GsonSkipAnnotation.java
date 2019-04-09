package com.pnmins.min.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName GsonSkipAnnotation
 * @Description 自定义注解相关设置
 * @Author qr
 * @Date 2019/3/7 16:27
 **/
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GsonSkipAnnotation {
     boolean serialize() default true;
     boolean deserialize() default true;
}
