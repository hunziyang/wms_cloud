package com.yang.cloud.wms.product.controller;


import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import com.yang.cloud.wms.core.annotation.WmsController;
import com.yang.cloud.wms.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@WmsController("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/decrStockMore")
    public Result decrStockMore(@RequestBody @Validated UpdateStockMoreVo updateStockMoreVo){
        stockService.decrStockMore(updateStockMoreVo);
        return Result.success();
    }

    @PostMapping("/incrStockMore")
    public Result incrStockMore(@RequestBody @Validated UpdateStockMoreVo updateStockMoreVo){
        Map<String, String> failList = stockService.incrStockMore(updateStockMoreVo);
        return Result.success(failList);
    }
}
