package com.yang.cloud.wms.common.vo.tradeService;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TradeCreateVo {

    private List<@Valid TradeDetailVo> tradeDetailList;

    @Data
    public static class TradeDetailVo {
        @NotBlank(message = "productName 不能为空")
        private String productName;
        @NotNull(message = "productId 不能为空")
        private Long productId;
        @NotNull(message = "count 不能为空")
        private Integer count;
    }
}
