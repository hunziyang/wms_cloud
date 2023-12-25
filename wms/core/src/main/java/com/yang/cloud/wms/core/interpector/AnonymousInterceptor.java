package com.yang.cloud.wms.core.interpector;

import com.yang.cloud.wms.core.contextHolder.UserContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnonymousInterceptor implements HandlerInterceptor {
    private static final String REQUEST_ID = "REQUEST_ID";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getHeader(REQUEST_ID);
        UserContextHolder.setRequestId(requestId);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.closeRequestId();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
