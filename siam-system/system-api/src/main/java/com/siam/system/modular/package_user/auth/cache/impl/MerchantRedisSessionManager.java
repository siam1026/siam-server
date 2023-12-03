package com.siam.system.modular.package_user.auth.cache.impl;

import com.siam.package_common.util.JsonUtils;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MerchantRedisSessionManager implements MerchantSessionManager {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void createSession(String token, Merchant merchant) {
        //设置7天过期
        redisTemplate.opsForValue().set(SESSION_PREFIX + token, JsonUtils.toJson(merchant), 7 * 24, TimeUnit.HOURS);
    }

    @Override
    public Merchant getSession(String token) {
        Object o = redisTemplate.opsForValue().get(SESSION_PREFIX + token);
        if(o != null){
            Merchant merchant = (Merchant) JsonUtils.toObject(o.toString(), Merchant.class);
            return merchant;
        }
        return null;
    }

    @Override
    public void removeSession(String token) {
        redisTemplate.delete(SESSION_PREFIX + token);
    }

    @Override
    public boolean haveSession(String token) {
        return redisTemplate.hasKey(SESSION_PREFIX + token);
    }
}