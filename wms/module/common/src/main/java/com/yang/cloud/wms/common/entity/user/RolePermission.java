package com.yang.cloud.wms.common.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色权限(RolePermission)实体类
 *
 * @author makejava
 * @since 2024-01-06 09:54:29
 */
@Data
@Accessors(chain = true)
@TableName("ROLE_PERMISSION")
public class RolePermission extends BaseEntity {

    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 权限ID
     */
    private String permissionId;
}

