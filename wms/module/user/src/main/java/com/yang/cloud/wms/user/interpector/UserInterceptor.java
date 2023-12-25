package com.yang.cloud.wms.user.interpector;

import com.yang.cloud.wms.user.contextHolder.UserContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String JWT = "JWT";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = request.getHeader(USER_NAME);
        Long userId = Long.valueOf(request.getHeader(USER_ID));
        String jwt = request.getHeader(JWT);
        UserContextHolder.setUsername(username);
        UserContextHolder.setUserId(userId);
        UserContextHolder.setJwt(jwt);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.close();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
