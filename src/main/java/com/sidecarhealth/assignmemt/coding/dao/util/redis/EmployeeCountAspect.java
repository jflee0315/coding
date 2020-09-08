package com.sidecarhealth.assignmemt.coding.dao.util.redis;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import static com.sidecarhealth.assignmemt.coding.dao.redis.constant.Constant.EMPLOYEE_COUNT_KEY;

@Aspect
@Component
public class EmployeeCountAspect {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    Logger logger;

    /**
     * Invalidate the employee count in Redis.
     * This should be used when a operation changes the total number of employees.
     */
    @After("@annotation(InvalidateEmployeeCount)")
    public void invalidateEmployeeCount(JoinPoint joinPoint) {
        logger.info("invalidating redis string: " + EMPLOYEE_COUNT_KEY);
        this.redisTemplate.delete(EMPLOYEE_COUNT_KEY);
    }
}