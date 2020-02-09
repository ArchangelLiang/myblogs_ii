package com.gwg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class MyExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 用来拦截所有异常，并转发到错误页面
     * @param request 发升错误的请求对象
     * @param exception 异常对象
     * @return 包含要前往的视图
     */
    @ExceptionHandler({Exception.class})
    public ModelAndView handlerException(HttpServletRequest request, Exception exception) {
        logger.error("请求url:{},出现的异常：{}", request.getRequestURL(), exception.getClass());
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(exception.getClass(), ResponseStatus.class);
        if (responseStatus != null) {
            request.setAttribute("javax.servlet.error.status_code",responseStatus.value().value());
            return new ModelAndView("forward:/error");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/error");
        modelAndView.addObject("url", request.getRequestURL());
        modelAndView.addObject("exe", exception);
        modelAndView.addObject("param",request.getParameterMap());
        return modelAndView;
    }

}
