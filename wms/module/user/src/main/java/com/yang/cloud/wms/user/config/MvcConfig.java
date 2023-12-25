package com.yang.cloud.wms.user.config;

import com.yang.cloud.wms.user.interpector.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private static final String REGISTER_PATH = "/user/register";
    private static final String LOGIN_PATH = "/user/login";
    private static final String JWT_PATH = "/user/getUserInfoByJwt";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(Arrays.asList(REGISTER_PATH, LOGIN_PATH, JWT_PATH));
    }
}
