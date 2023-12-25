package com.yang.cloud.wms.common.entity.trade;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 订单(Order)实体类
 *
 * @author makejava
 * @since 2024-01-15 14:34:19
 */
@TableName("TRADE")
@Data
@Accessors(chain = true)
public class Trade extends BaseEntity {
    /**
     * 订单
     */
    private String number;
}

