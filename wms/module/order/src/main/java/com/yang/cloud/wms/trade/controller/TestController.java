package com.yang.cloud.wms.trade.controller;

import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.core.annotation.WmsController;
import com.yang.cloud.wms.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms.trade.feign.ProductFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@WmsController("/test")
public class TestController {

    @Autowired
    private ProductFeignService productFeignService;

    @GetMapping("/test")
    public Result test(){
        String body = productFeignService.test().getBody();
        String requestId = UserContextHolder.getRequestId();
        return Result.success(requestId.equals(body));
    }
}
