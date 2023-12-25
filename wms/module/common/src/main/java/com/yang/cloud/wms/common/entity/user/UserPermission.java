package com.yang.cloud.wms.common.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户权限(UserPermission)实体类
 *
 * @author makejava
 * @since 2024-01-06 09:54:29
 */
@Data
@Accessors(chain = true)
@TableName("USER_PERMISSION")
public class UserPermission extends BaseEntity {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 权限ID
     */
    private Long permissionId;

}

