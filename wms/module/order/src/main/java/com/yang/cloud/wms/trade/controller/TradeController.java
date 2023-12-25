package com.yang.cloud.wms.trade.controller;

import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.tradeService.TradeCreateVo;
import com.yang.cloud.wms.core.annotation.WmsController;
import com.yang.cloud.wms.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@WmsController("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;


    @PostMapping("/create")
    public Result create(@RequestBody @Validated TradeCreateVo tradeCreateVo){
        tradeService.create(tradeCreateVo);
        return Result.success();
    }

}
