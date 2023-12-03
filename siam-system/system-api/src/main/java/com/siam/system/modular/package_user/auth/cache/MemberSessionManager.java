package com.siam.system.modular.package_user.auth.cache;

import com.siam.system.modular.package_user.entity.Member;

/**
 * 会话管理
 */
public interface MemberSessionManager {

    //缓存前缀
    String SESSION_PREFIX = "LOGIN_USER:";

    /**
     * 创建会话
     */
    void createSession(String token, Member member);

    /**
     * 获取会话
     */
    Member getSession(String token);

    /**
     * 删除会话
     */
    void removeSession(String token);

    /**
     * 是否已经登录
     */
    boolean haveSession(String token);
}