
package com.siam.system.modular.auth.context;

import cn.hutool.core.util.ObjectUtil;
import com.siam.package_common.context.login.AdminLoginContext;
import com.siam.package_common.exception.AuthException;
import com.siam.package_common.exception.enums.AuthExceptionEnum;
import com.siam.package_common.pojo.login.SysLoginAdmin;
import com.siam.system.modular.package_user.auth.cache.AdminSessionManager;
import com.siam.system.modular.package_user.entity.Admin;
import com.siam.system.util.TokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录用户上下文实现类
 *
 * @author
 * @date 2020/3/13 12:19
 */
@Component
public class AdminLoginContextSpringSecurityImpl implements AdminLoginContext {

    @Autowired
    private AdminSessionManager adminSessionManager;

    private AdminLoginContextSpringSecurityImpl() {

    }

    /**
     * 获取当前登录用户
     *
     * @author
     * @date 2020/3/13 14:42
     */
    @Override
    public SysLoginAdmin getSysLoginAdmin() {
        Admin loginAdmin = adminSessionManager.getSession(TokenUtil.getToken());
        if (ObjectUtil.isEmpty(loginAdmin)) {
            throw new AuthException(AuthExceptionEnum.LOGIN_EXPIRED);
        } else {
            SysLoginAdmin sysLoginAdmin = new SysLoginAdmin();
            BeanUtils.copyProperties(loginAdmin, sysLoginAdmin);
            return sysLoginAdmin;
        }
    }

    /**
     * 管理员类型（0超级管理员 1非管理员）
     * 判断当前登录用户是否是超级管理员
     *
     * @author
     * @date 2020/3/23 17:51
     */
    @Override
    public boolean isSuperAdmin() {
        //TODO - 临时限制
        /*return this.isAdmin(AdminTypeEnum.SUPER_ADMIN.getCode());*/
        return getSysLoginAdmin().getUsername().equals("admin");
    }
}