package com.siam.system.modular.package_user.controller.stoneTraining;

import lombok.Data;

/**
 * STONE·训练营相关实体类
 *
 * @author 暹罗
 */
@Data
public class StoneTrainingParam {

    //新密码
    private String mobile;

    //短信验证码
    private String mobileCode;

    //新密码
    private String password;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;
}