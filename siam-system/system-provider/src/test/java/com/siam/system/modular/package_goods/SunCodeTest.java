package com.siam.system.modular.package_goods;

import com.siam.package_common.util.AliyunExpressUtils;
import com.siam.package_common.util.ImageComposeUtils;
import com.siam.package_common.util.OSSUtils;
import com.siam.package_weixin_basic.service.WxNotifyService;
import com.siam.package_weixin_basic.service.WxPublicPlatformNotifyService;
import com.siam.package_weixin_basic.util.WxQrCodeUtils;
import com.siam.package_weixin_pay.util.WxdecodeUtils;
import com.siam.system.SiamApplication;
import com.siam.system.modular.package_goods.service.*;
import com.siam.system.modular.package_order.service.OrderDetailService;
import com.siam.system.modular.package_order.service.OrderService;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.mapper.MemberMapper;
import com.siam.system.modular.package_user.service.AdminService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MerchantService;
import com.siam.system.modular.package_user.service_impl.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SiamApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class SunCodeTest {

    @Autowired
    private MessageService messageService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private CouponsMemberRelationService couponsMemberRelationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private GoodsRawmaterialRelationService rawmaterialRelationService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private WxNotifyService wxNotifyService;

    @Autowired
    private WxPublicPlatformNotifyService wxPublicPlatformNotifyService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private WxdecodeUtils wxdecodeUtils;

    @Autowired
    private AliyunExpressUtils aliyunExpressUtils;

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private WxQrCodeUtils wxQrCodeUtils;

    @Autowired
    private ImageComposeUtils imageComposeUtils;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 生成太阳码
     */
    @Test
    public void generateSunCode() throws Exception {
        //从数据库中获取注册好的用户信息
        String mobile = "18711389426";
        Member dbMember = memberMapper.selectByMobile(mobile);

        //生成邀请分享太阳码
        String path = "data/images/invite_suncode/v1.0";
        String fileName = "suncode_" + dbMember.getId() + ".png";
        String savePath = path + "/" + fileName;
        wxQrCodeUtils.generateSunCode(MemberServiceImpl.PAGE, "inviterId="+dbMember.getId(), savePath);
        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setInviteSuncode(savePath);
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    /**
     * 测试oss工具类
     */
    @Test
    public void testCompoundImage() throws Exception {
        /*Boolean exist = ossUtils.doesObjectExist("data/images/admin/1/siam_1574048067619.jpg");
        System.out.println("exist : " + exist);*/

        Integer memberId = 111;
        String path = "data/images/invite_suncode/test";
        String fileName = "suncode_" + memberId + ".png";
        String savePath = path + "/" + fileName;
        wxQrCodeUtils.generateSunCode(MemberServiceImpl.PAGE, "inviterId=111", savePath);

        String mainImagePath = "https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/invite_suncode/test/siamCompose.png";
        String qrCodePath = "https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/" + savePath;

        path = "data/images/invite_poster_compose/" + memberId;
        fileName = fileName + "#" +"siamCompose.png";
        savePath = path + "/" + fileName;

        imageComposeUtils.compoundImage(mainImagePath, qrCodePath, savePath, "8888711246079366");
    }
}