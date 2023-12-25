package com.yang.cloud.wms.common.vo.productService;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateStockVo {

    @NotBlank(message = "productName 不能为空")
    private String productName;
    @NotNull(message = "productId 不能为空")
    private Long productId;
    @NotNull(message = "count 不能为空")
    private Integer count;
}
