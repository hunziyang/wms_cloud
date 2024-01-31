package com.yang.cloud.wms.product.controller;

import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import com.yang.cloud.wms.core.annotation.AnonymousController;
import com.yang.cloud.wms.core.contextHolder.UserContextHolder;
import com.yang.cloud.wms.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AnonymousController("/t")
public class TestController {

    @Autowired
    private StockService stockService;

    @GetMapping("/test")
    public Result test() {
        System.out.println(UserContextHolder.getUserId());
        System.out.println(UserContextHolder.getUsername());
        System.out.println(UserContextHolder.getJwt());
        return Result.success("zzz-yanbg:" + UserContextHolder.getRequestId());
    }

    @PostMapping("/decr")
    public Result decr(@RequestBody @Validated UpdateStockMoreVo updateStockMoreVo){
        stockService.decer(updateStockMoreVo);
        return Result.success();
    }
}
