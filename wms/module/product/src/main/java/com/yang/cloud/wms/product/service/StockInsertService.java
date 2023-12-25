package com.yang.cloud.wms.product.service;

import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;

public interface StockInsertService {

    public void incrStock(UpdateStockVo updateStockVo, String message);
}
