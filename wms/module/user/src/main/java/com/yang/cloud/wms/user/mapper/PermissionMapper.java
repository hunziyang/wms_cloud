package com.yang.cloud.wms.user.mapper;

import com.yang.cloud.wms.common.entity.user.Permission;
import com.yang.cloud.wms.core.mybatis.WmsMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper extends WmsMapper<Permission> {

    List<String> userPermissionDetail(@Param("id") Long id);
}
