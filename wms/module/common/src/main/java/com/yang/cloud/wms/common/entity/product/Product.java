package com.yang.cloud.wms.common.entity.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 商品(Product)实体类
 *
 * @author makejava
 * @since 2024-01-08 15:34:42
 */
@TableName("PRODCUT")
@Data
@Accessors(chain = true)
public class Product extends BaseEntity {
/**
     * 商品名称
     */
    private String name;
/**
     * 商品编码
     */
    private String code;
/**
     * 型号
     */
    private String sku;
}

