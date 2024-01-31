package com.yang.cloud.wms.trade.controller;

import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.tradeService.TradeCreateVo;
import com.yang.cloud.wms.core.annotation.WmsController;
import com.yang.cloud.wms.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms.trade.feign.ProductFeignService;
import com.yang.cloud.wms.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WmsController("/test")
public class TestController {

    @Autowired
    private ProductFeignService productFeignService;

    @Autowired
    private TradeService tradeService;

    @GetMapping("/test")
    public Result test(){
        String body = productFeignService.test().getBody();
        String requestId = UserContextHolder.getRequestId();
        return Result.success(requestId.equals(body));
    }


    @PostMapping("/create")
    public Result create(@RequestBody TradeCreateVo tradeCreateVo){
        tradeService.createTest(tradeCreateVo);
        return Result.success();
    }
}
