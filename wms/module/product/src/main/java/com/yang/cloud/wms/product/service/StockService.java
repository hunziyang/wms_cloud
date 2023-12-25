package com.yang.cloud.wms.product.service;

import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;

public interface StockService {
    void incrStock(UpdateStockVo updateStockVo);

    void decrStock(UpdateStockVo updateStockVo);

    void decrStockMore(UpdateStockMoreVo updateStockMoreVo);
}
