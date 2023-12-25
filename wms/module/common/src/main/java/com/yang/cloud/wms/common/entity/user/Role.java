package com.yang.cloud.wms.common.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 角色(Role)实体类
 *
 * @author makejava
 * @since 2024-01-06 09:54:29
 */
@Data
@Accessors(chain = true)
@TableName("ROLE")
public class Role extends BaseEntity {
    /**
     * 名称
     */
    private String name;

}

