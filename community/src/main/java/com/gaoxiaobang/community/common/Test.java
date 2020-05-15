package com.gaoxiaobang.community.common;

import jdk.nashorn.internal.objects.annotations.Function;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Test {
    String value()  default "";
}
