package com.yang.cloud.wms.common.entity.user;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yang.cloud.wms.common.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户(User)实体类
 *
 * @author makejava
 * @since 2024-01-05 13:39:46
 */
@Data
@TableName("USER")
@Accessors(chain = true)
public class User extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 密钥
     */
    private String salt;
    /**
     * 用户锁
     */
    private Boolean isLocked;
    /**
     * 姓名
     */
    private String name;
    /**
     * 电话
     */
    private String phone;
    /**
     * 性别;0：female 1:female
     */
    private Integer gender;
    /**
     * 生日
     */
    private Date birthday;
}

