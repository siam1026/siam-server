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
package com.siam.package_common.constant;

/**
 * 通用常量
 *
 * @author xuyuxiang
 * @date 2020/3/11 16:51
 */
public interface CommonConstant {

    /**
     * id
     */
    String ID = "id";

    /**
     * 名称
     */
    String NAME = "name";

    /**
     * 编码
     */
    String CODE = "code";

    /**
     * 值
     */
    String VALUE = "value";

    /**
     * 备注
     */
    String REMARK = "remark";

    /**
     * 默认标识状态的字段名称
     */
    String STATUS = "status";

    /**
     * 默认逻辑删除的状态值
     */
    String DEFAULT_LOGIC_DELETE_VALUE = "2";

    /**
     * 用户代理
     */
    String USER_AGENT = "User-Agent";

    /**
     * 请求头token表示
     */
    String AUTHORIZATION = "Authorization";

    /**
     * token名称
     */
    String TOKEN_NAME = "token";

    /**
     * token类型
     */
    String TOKEN_TYPE_BEARER = "Bearer";

    /**
     * 首页提示语
     */
    String INDEX_TIPS = "Welcome To New Guns";

    /**
     * 未知标识
     */
    String UNKNOWN = "Unknown";

    /**
     * 默认包名
     */
    String DEFAULT_PACKAGE_NAME = "cn.stylefeng";

    /**
     * 默认密码
     */
    String DEFAULT_PASSWORD = "ZCBMzcbm13570@$^*)";

    /**
     * 请求号在header中的唯一标识
     */
    String REQUEST_NO_HEADER_NAME = "Request-No";

    /**
     * 请求头 APPKEY 表示
     */
    String APPKEY = "appkey";


    /**
     * 请求头 CLIENTID 表示
     */
    String CLIENT_ID = "CLIENTID";

    /**
     * 调试模式
     */
    String DEBUG = "debug";
}
