package com.yang.cloud.wms.product.mapper;

import com.yang.cloud.wms.common.entity.product.Stock;
import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;
import com.yang.cloud.wms.core.mybatis.WmsMapper;

public interface StockMapper extends WmsMapper<Stock> {
    Integer incrStock(UpdateStockVo updateStockVo);

    Integer decrStock(UpdateStockVo updateStockVo);
}
