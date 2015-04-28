package com.idove.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author : idove
 * @date : 2014-8-22
 * @
 * 
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)
@Documented
public @interface Permission {
	boolean value() default true;
}
