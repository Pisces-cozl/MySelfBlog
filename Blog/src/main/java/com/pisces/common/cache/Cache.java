package com.pisces.common.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {


    /**
     * @return  过期时间
     */
    long expire() default 1 * 60 * 1000;

    /**
     * @return  缓存标识 key
     */
    String name() default "";
}
