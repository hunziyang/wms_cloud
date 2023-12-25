package com.yang.cloud.wms.product.controller;

import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.core.annotation.AnonymousController;
import com.yang.cloud.wms.core.contextHolder.UserContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

@AnonymousController("/t")
public class TestController {

    private static final String USER_NAME = "USER_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String JWT = "JWT";
    private static final String REQUEST_ID = "REQUEST_ID";

    @GetMapping("/test")
    public Result test() {
        System.out.println(UserContextHolder.getUserId());
        System.out.println(UserContextHolder.getUsername());
        System.out.println(UserContextHolder.getJwt());
        return Result.success("zzz-yanbg:" + UserContextHolder.getRequestId());
    }
}
