package com.yang.cloud.wms.user;

import com.yang.cloud.wms.core.annotation.WmsApplication;
import com.yang.cloud.wms.core.utils.SpringUtil;

@WmsApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringUtil.run(UserApplication.class,args);
    }
}
