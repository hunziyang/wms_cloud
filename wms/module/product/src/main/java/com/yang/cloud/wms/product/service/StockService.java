package com.yang.cloud.wms.product.service;

import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;

import java.util.Map;

public interface StockService {

    Map<String, String> incrStockMore(UpdateStockMoreVo updateStockMoreVo);
    void decrStockMore(UpdateStockMoreVo updateStockMoreVo);
}
