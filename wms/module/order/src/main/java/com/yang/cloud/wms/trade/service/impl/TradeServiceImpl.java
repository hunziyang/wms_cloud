package com.yang.cloud.wms.trade.service.impl;

import com.yang.cloud.wms.common.entity.trade.Trade;
import com.yang.cloud.wms.common.entity.trade.TradeDetail;
import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import com.yang.cloud.wms.common.vo.productService.UpdateStockVo;
import com.yang.cloud.wms.common.vo.tradeService.TradeCreateVo;
import com.yang.cloud.wms.trade.feign.ProductFeignService;
import com.yang.cloud.wms.trade.mapper.TradeDetailMapper;
import com.yang.cloud.wms.trade.mapper.TradeMapper;
import com.yang.cloud.wms.trade.service.TradeService;
import com.yang.cloud.wms.trade.utils.NumberUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeMapper tradeMapper;

    @Autowired
    private TradeDetailMapper tradeDetailMapper;

    @Autowired
    private ProductFeignService productFeignService;

    @Override
    @Transactional
    public void create(TradeCreateVo tradeCreateVo) {
        String number = NumberUtil.getNumber();
        Trade trade = new Trade()
                .setNumber(number);
        tradeMapper.insert(trade);
        List<UpdateStockVo> updateStockVoList = new ArrayList<>();
        tradeCreateVo.getTradeDetailList().forEach(tradeDetailVo -> {
            TradeDetail tradeDetail = new TradeDetail();
            BeanUtils.copyProperties(tradeDetailVo, tradeDetail);
            tradeDetail.setTradeId(trade.getId());
            tradeDetailMapper.insert(tradeDetail);
            UpdateStockVo updateStockVo = new UpdateStockVo();
            BeanUtils.copyProperties(tradeDetailVo,updateStockVo);
            updateStockVoList.add(updateStockVo);
        });
        UpdateStockMoreVo updateStockMoreVo = new UpdateStockMoreVo();
        updateStockMoreVo.setUpdateStockVoList(updateStockVoList);
        try {
            productFeignService.decrStockMore(updateStockMoreVo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @GlobalTransactional
    public void createTest(TradeCreateVo tradeCreateVo) {
        String number = NumberUtil.getNumber();
        Trade trade = new Trade()
                .setNumber(number);
        tradeMapper.insert(trade);
        List<UpdateStockVo> updateStockVoList = new ArrayList<>();
        tradeCreateVo.getTradeDetailList().forEach(tradeDetailVo -> {
            TradeDetail tradeDetail = new TradeDetail();
            BeanUtils.copyProperties(tradeDetailVo, tradeDetail);
            tradeDetail.setTradeId(trade.getId());
            tradeDetailMapper.insert(tradeDetail);
            UpdateStockVo updateStockVo = new UpdateStockVo();
            BeanUtils.copyProperties(tradeDetailVo,updateStockVo);
            updateStockVoList.add(updateStockVo);
        });
        UpdateStockMoreVo updateStockMoreVo = new UpdateStockMoreVo();
        updateStockMoreVo.setUpdateStockVoList(updateStockVoList);
        productFeignService.decr(updateStockMoreVo);
    }
}
