package com.yang.cloud.wms.product.service.impl;

import com.yang.cloud.wms.common.entity.product.Stock;
import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;
import com.yang.cloud.wms.product.exception.SqlIncrException;
import com.yang.cloud.wms.product.mapper.StockMapper;
import com.yang.cloud.wms.product.service.StockInsertService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockInsertServiceImpl implements StockInsertService {

    private static final String CREATE = "create";
    @Autowired
    private StockMapper stockMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void incrStock(UpdateStockVo updateStockVo, String message) {
        int count;
        if (CREATE.equals(message)) {
            Stock stock = new Stock();
            BeanUtils.copyProperties(updateStockVo, stock);
            count = stockMapper.insert(stock);
        } else {
            count = stockMapper.incrStock(updateStockVo);
        }
        if (count == 0) {
            throw new SqlIncrException();
        }
    }
}
