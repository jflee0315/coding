package com.sidecarhealth.assignmemt.coding.dao.util.redis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Use this to invalidate employee number in Redis after the method is executed.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InvalidateEmployeeCount {

}