package com.yang.cloud.wms.common.entity.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 库存(Stock)实体类
 *
 * @author makejava
 * @since 2024-01-08 15:34:43
 */
@TableName("STOCK")
@Data
@Accessors(chain = true)
public class Stock extends BaseEntity {
    /**
     * 商品ID
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer count;
}

