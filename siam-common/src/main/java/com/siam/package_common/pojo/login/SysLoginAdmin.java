/*
Copyright [2020] [https://www.stylefeng.cn]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Guns采用APACHE LICENSE 2.0开源协议，您在使用过程中，需要注意以下几点：

1.请不要删除和修改根目录下的LICENSE文件。
2.请不要删除和修改Guns源码头部的版权声明。
3.请保留源码和相关描述文件的项目出处，作者声明等。
4.分发源码时候，请注明软件出处 https://gitee.com/stylefeng/guns-separation
5.在修改包名，模块名称，项目代码等时，请注明软件出处 https://gitee.com/stylefeng/guns-separation
6.若您的项目无法满足以上几点，可申请商业授权，获取Guns商业授权许可，请在官网购买授权，地址为 https://www.stylefeng.cn
 */
package com.siam.package_common.pojo.login;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录用户模型
 *
 * @author xuyuxiang
 * @date 2020/3/11 12:21
 */
@ToString
@Data
public class SysLoginAdmin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(notes = "管理员用户名")
    private String username;

    @ApiModelProperty(notes = "手机号")
    private String mobile;

    @ApiModelProperty(notes = "密码")
    private String password;

    @ApiModelProperty(notes = "密码盐值")
    private String passwordSalt;

    @ApiModelProperty(notes = "昵称")
    private String nickname;

    @ApiModelProperty(notes = "权限")
    private String roles;

    @ApiModelProperty(notes = "是否禁用 0=启用 1=禁用")
    private Boolean isDisabled;

    @ApiModelProperty(notes = "注册时间")
    private Date createTime;

    @ApiModelProperty(notes = "修改时间")
    private Date updateTime;

    @ApiModelProperty(notes = "最后登录时间")
    private Date lastLoginTime;

    /**
     * 具备应用信息
     */
    private List<Dict> apps;

    /**
     * 角色信息
     */
    private List<Dict> roleList;

    /**
     * 权限信息
     */
    private List<String> permissions;

    /**
     * 登录菜单信息，AntDesign版本菜单
     */
//    private List<LoginMenuTreeNode> menus;

    /**
     * 数据范围信息
     */
    private List<Long> dataScopes;

    /**
     * 项目数据范围信息
     */
    private List<Long> proDataScopes;

    /**
     * 设备数据范围信息
     */
    private List<Long> deviceDataScopes;

    /**
     * 租户信息
     */
    private Dict tenants;

    /**
     * Oauth 授权参数
     */
    private Map<String, Object> authParam;
//
//    /**
//     * 角色名称集合
//     */
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        ArrayList<GunsAuthority> grantedAuthorities = CollectionUtil.newArrayList();
//        if (ObjectUtil.isNotEmpty(roleList)) {
//            roleList.forEach(dict -> {
//                String roleName = dict.getStr(CommonConstant.NAME);
//                GunsAuthority gunsAuthority = new GunsAuthority(roleName);
//                grantedAuthorities.add(gunsAuthority);
//            });
//        }
//        return grantedAuthorities;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        //能生成loginUser就是jwt解析成功，没锁定
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        //能生成loginUser就是jwt解析成功，没锁定
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        //能生成loginUser就是jwt解析成功，没锁定
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        //能生成loginUser就是jwt解析成功，没锁定
//        return true;
//    }
}
