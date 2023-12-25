package com.yang.cloud.wms.user.service.impl;

import com.yang.cloud.wms.common.dto.userService.user.JwtUserInfoDto;
import com.yang.cloud.wms.common.entity.user.User;
import com.yang.cloud.wms.common.entity.user.UserRole;
import com.yang.cloud.wms.common.result.Result;
import com.yang.cloud.wms.common.result.ResultUserCode;
import com.yang.cloud.wms.common.vo.userService.user.LoginVo;
import com.yang.cloud.wms.common.vo.userService.user.RegisterVo;
import com.yang.cloud.wms.user.mapper.PermissionMapper;
import com.yang.cloud.wms.user.mapper.UserMapper;
import com.yang.cloud.wms.user.mapper.UserRoleMapper;
import com.yang.cloud.wms.user.service.UserService;
import com.yang.cloud.wms.user.utils.JwtUtil;
import com.yang.cloud.wms.user.utils.SaltUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    private static final String SALT_PREFIX = "$WMS$";
    private static final Long USER_ROLE_ID = 1L;
    private static final Long EXPIRE_TIME = 8L;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional
    public void register(RegisterVo registerVo) {
        User user = new User();
        BeanUtils.copyProperties(registerVo, user);
        String salt = SaltUtil.getSalt();
        // 方法盐值头必须加上"$***$"
        String password = Md5Crypt.md5Crypt(
                user.getPassword().getBytes(StandardCharsets.UTF_8),
                String.format("%s%s", SALT_PREFIX, salt),
                SALT_PREFIX);
        user.setSalt(salt)
                .setPassword(password);
        userMapper.insert(user);
        UserRole userRole = new UserRole()
                .setUserId(user.getId())
                .setRoleId(USER_ROLE_ID);
        userRoleMapper.insert(userRole);
    }

    @Override
    public Result<JwtUserInfoDto> login(LoginVo loginVo) {
        User user = userMapper.selectByUsername(loginVo.getUsername());
        if (ObjectUtils.isEmpty(user)) {
            return Result.error(ResultUserCode.USER_NOT_EXISTS);
        }
        if (user.getIsLocked()) {
            return Result.error(ResultUserCode.USER_LOCKED);
        }
        String password = Md5Crypt.md5Crypt(loginVo.getPassword().getBytes(StandardCharsets.UTF_8),
                String.format("%s%s", SALT_PREFIX, user.getSalt()),
                SALT_PREFIX);
        if (!password.equals(user.getPassword())) {
            return Result.error(ResultUserCode.USER_BAD_CREDENTIALS);
        }
        String jwt = JwtUtil.sign(user.getUsername());
        List<String> permissions = permissionMapper.userPermissionDetail(user.getId());
        JwtUserInfoDto jwtUserInfoDto = new JwtUserInfoDto();
        BeanUtils.copyProperties(user, jwtUserInfoDto);
        jwtUserInfoDto.setPermissions(permissions)
                .setJwtToken(jwt);
        redisTemplate.opsForValue().set(jwt, jwtUserInfoDto, EXPIRE_TIME, TimeUnit.HOURS);
        return Result.success(jwtUserInfoDto);
    }

    @Override
    public User userDetail(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public Result<JwtUserInfoDto> getUserInfoByJwt(String jwt) {
        if (!redisTemplate.hasKey(jwt)) {
            return Result.error(ResultUserCode.AUTHENTICATION_ERROR);
        }
        JwtUserInfoDto jwtUserInfoDto = (JwtUserInfoDto) redisTemplate.opsForValue().get(jwt);
        if (ObjectUtils.isEmpty(jwtUserInfoDto)) {
            return Result.error(ResultUserCode.AUTHENTICATION_ERROR);
        }
        return Result.success(jwtUserInfoDto);
    }
}
