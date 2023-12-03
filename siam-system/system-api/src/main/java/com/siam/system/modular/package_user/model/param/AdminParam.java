package com.siam.system.modular.package_user.model.param;

import com.siam.system.modular.package_user.entity.Admin;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * 用户表
 *
 * @author 暹罗
 */
@Data
public class AdminParam extends Admin {

    //短信验证码
    private String mobileCode;

    //微信code
    private String code;

    //邀请者id
    private String inviterId;

    //开始日期
    private Date startCreateTime;

    //结束日期
    private Date endCreateTime;

    //文件
    private MultipartFile file;

    //文件
    private List<MultipartFile> fileList;

    //旧密码
    private String oldPassword;

    //新密码
    private String newPassword;

    //页码
    private Integer pageNo = 1;

    //页面大小
    private Integer pageSize = 20;

    String goodsIdListStr;

    String relationListStr;
}