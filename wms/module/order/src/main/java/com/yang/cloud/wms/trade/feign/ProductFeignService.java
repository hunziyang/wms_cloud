package com.yang.cloud.wms.trade.feign;

import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.vo.productService.UpdateStockMoreVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "product-service",path = "/product-service")
public interface ProductFeignService {

    @PostMapping("/stock/decrStockMore")
    Result decrStockMore(@RequestBody UpdateStockMoreVo updateStockMoreVo);

    @GetMapping("/anonymous/t/test")
    Result<String> test();
}
