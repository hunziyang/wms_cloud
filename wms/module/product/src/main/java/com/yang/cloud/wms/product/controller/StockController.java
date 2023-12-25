package com.yang.cloud.wms.product.controller;


import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;
import com.yang.cloud.wms.core.annotation.WmsController;
import com.yang.cloud.wms.product.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WmsController("/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping("/incrStock")
    public Result incrStockOne(@RequestBody @Validated UpdateStockVo updateStockVo){
        stockService.incrStock(updateStockVo);
        return Result.success();
    }

    @PostMapping("/decrStock")
    public Result decrStockOne(@RequestBody @Validated UpdateStockVo updateStockVo){
        stockService.decrStock(updateStockVo);
        return Result.success();
    }

    @PostMapping("/decrStockMore")
    public Result decrStockMore(@RequestBody @Validated UpdateStockMoreVo updateStockMoreVo){
        stockService.decrStockMore(updateStockMoreVo);
        return Result.success();
    }
}
