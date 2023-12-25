package com.yang.cloud.wms.common.vo.productService;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UpdateStockMoreVo {

    @NotEmpty(message = "updateStockVoList 不能为空组")
    private List<@Valid UpdateStockVo> updateStockVoList;
}
