package com.gwg.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* com.gwg.controller.*..*.*(..))")
    private void point() {}


    public void logBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String url = request.getRequestURL().toString();
            String ip = request.getRemoteAddr();
            String class_method = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName()+"()";
            Object[] params = joinPoint.getArgs();
            this.logger.info("-----doBefore-----请求详情：{}",new RequestLog(url,ip,class_method,params));
        }
    }

    public void logPost(Object return_value) {
        this.logger.info("-----doPost-----return value is {}", return_value);
    }

    public void logThrow(Throwable throwable) {
        this.logger.info("-----doThrow-----异常对象：{}",throwable.getClass().getName());
    }

    public void logAfter() {
        this.logger.info("-----doFinally-----");
    }

    @Around("point()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        logBefore(joinPoint);
        Object return_value = null;
        try {
            return_value = joinPoint.proceed();
            logPost(return_value);
        } catch (Throwable throwable) {
            logThrow(throwable);
            logAfter();
            throw throwable;
        }
        logAfter();
        return return_value;
    }

    private static class RequestLog {
        private String url;
        private String ip;
        private String class_method;
        private Object[] params;

        public RequestLog(String url, String ip, String class_method, Object[] params) {
            this.url = url;
            this.ip = ip;
            this.class_method = class_method;
            this.params = params;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getClass_method() {
            return class_method;
        }

        public void setClass_method(String class_method) {
            this.class_method = class_method;
        }

        public Object[] getParams() {
            return params;
        }

        public void setParams(Object[] params) {
            this.params = params;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", class_method='" + class_method + '\'' +
                    ", params=" + Arrays.toString(params) +
                    '}';
        }
    }
}
