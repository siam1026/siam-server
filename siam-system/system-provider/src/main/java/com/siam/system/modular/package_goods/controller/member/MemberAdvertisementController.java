package com.siam.system.modular.package_goods.controller.member;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.util.ImageComposeUtils;
import com.siam.package_common.util.OSSUtils;
import com.siam.package_weixin_basic.util.WxQrCodeUtils;
import com.siam.system.modular.package_goods.entity.Advertisement;
import com.siam.system.modular.package_goods.model.param.AdvertisementParam;
import com.siam.system.modular.package_goods.service.AdvertisementService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/advertisement")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "用户广告轮播图模块相关接口", description = "MemberAdvertisementController")
public class MemberAdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private WxQrCodeUtils wxQrCodeUtils;

    @Autowired
    private ImageComposeUtils imageComposeUtils;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @ApiOperation(value = "广告轮播图列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "广告轮播图表主键id", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "imageName", value = "轮播图名称", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "imagePath", value = "轮播图路径", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "description", value = "说明", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "type", value = "轮播图类型 1=首页轮播图 2=菜单页轮播图", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "sortNumber", value = "排序号", required = false, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "imageLinkUrl", value = "点击轮播图跳转的链接", required = false, paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "pageNo", value = "页码(值为-1不分页)", required = true, paramType = "query", dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "页数", required = true, paramType = "query", dataType = "int", defaultValue = "20"),
    })
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) AdvertisementParam advertisement, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Page<Advertisement> page = advertisementService.getListByPage(advertisement);

        if(advertisement.getType().equals(Quantity.INT_4)){
            //截取个人邀请分享太阳码的文件名
            int suncode_index = loginMember.getInviteSuncode().lastIndexOf("/");
            String suncode_fileName = loginMember.getInviteSuncode().substring(suncode_index+1);

            //如果类型为分享页面生成美图，则需要合成个人邀请二维码
            page.getRecords().forEach(dbAdvertisement -> {
                int index = dbAdvertisement.getImagePath().lastIndexOf("/");
                String poster_fileName = dbAdvertisement.getImagePath().substring(index+1);
                String fileName = suncode_fileName + "--" + poster_fileName;
                String filePath = "data/images/invite_poster_compose/" + loginMember.getId() + "/" + fileName;
                Boolean isExist = ossUtils.doesObjectExist(filePath);
                if (isExist){
                    dbAdvertisement.setImagePath(filePath);
                } else {
                    //合成并上传图片
                    String compose_path = "data/images/invite_poster_compose/" + loginMember.getId();
                    String compose_fileName = suncode_fileName + "--" + poster_fileName;
                    String savePath = compose_path + "/" + compose_fileName;
                    try {
                        imageComposeUtils.compoundImage(BusinessType.OSS_PREFIX + dbAdvertisement.getImagePath(), BusinessType.OSS_PREFIX + loginMember.getInviteSuncode(), savePath, loginMember.getVipNo());
                        dbAdvertisement.setImagePath(savePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        return BasicResult.success(page);
    }

    public static void main(String[] args) {
        String imagePath = "data/images/admin/1/siam_1590994340097.jpg";
        int index = imagePath.indexOf("siam_");
        String substring = imagePath.substring(index);
        System.out.println(substring);
    }
}