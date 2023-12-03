package com.siam.system.modular.package_user.auth.cache;

import com.siam.system.modular.package_user.entity.Merchant;

/**
 * 会话管理
 */
public interface MerchantSessionManager {

    //缓存前缀
    String SESSION_PREFIX = "LOGIN_MERCHANT:";

    /**
     * 创建会话
     */
    void createSession(String token, Merchant merchant);

    /**
     * 获取会话
     */
    Merchant getSession(String token);

    /**
     * 删除会话
     */
    void removeSession(String token);

    /**
     * 是否已经登录
     */
    boolean haveSession(String token);
}