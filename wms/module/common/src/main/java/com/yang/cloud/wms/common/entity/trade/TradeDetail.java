package com.yang.cloud.wms.common.entity.trade;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单详情(OrderDetail)实体类
 *
 * @author makejava
 * @since 2024-01-15 14:34:21
 */
@TableName("TRADE_DETAIL")
@Data
@Accessors(chain = true)
public class TradeDetail extends BaseEntity {
    /**
     * 订单编号
     */
    private Long tradeId;
    /**
     * 商品编号
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer count;

}

