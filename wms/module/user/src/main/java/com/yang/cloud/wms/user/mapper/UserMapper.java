package com.yang.cloud.wms.user.mapper;

import com.yang.cloud.wms.common.entity.user.User;
import com.yang.cloud.wms.core.mybatis.WmsMapper;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends WmsMapper<User> {
    User selectByUsername(@Param("username") String username);
}
