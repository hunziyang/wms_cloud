package com.yang.cloud.wms.trade;

import com.yang.cloud.wms.core.annotation.WmsApplication;
import com.yang.cloud.wms.core.utils.SpringUtil;
import org.springframework.cloud.openfeign.EnableFeignClients;

@WmsApplication
@EnableFeignClients
public class TradeApplication {
    public static void main(String[] args) {
        SpringUtil.run(TradeApplication.class, args);
    }
}
