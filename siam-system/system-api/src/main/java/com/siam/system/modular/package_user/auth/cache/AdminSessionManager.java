package com.siam.system.modular.package_user.auth.cache;

import com.siam.system.modular.package_user.entity.Admin;

/**
 * 会话管理
 */
public interface AdminSessionManager {

    //缓存前缀
    String SESSION_PREFIX = "LOGIN_ADMIN:";

    /**
     * 创建会话
     */
    void createSession(String token, Admin admin);

    /**
     * 获取会话
     */
    Admin getSession(String token);

    /**
     * 删除会话
     */
    void removeSession(String token);

    /**
     * 是否已经登录
     */
    boolean haveSession(String token);
}