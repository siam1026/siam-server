package com.siam.system.modular.package_user.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.internal.LinkedTreeMap;
import com.siam.package_common.constant.BusinessType;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.*;
import com.siam.system.modular.package_goods.service.CouponsService;
import com.siam.system.modular.package_goods.service.CouponsMemberRelationService;
import com.siam.system.modular.package_goods.service.SettingService;
import com.siam.system.modular.package_goods.service.SmsLogService;
import com.siam.system.modular.package_goods.entity.Coupons;
import com.siam.system.modular.package_goods.entity.CouponsMemberRelation;
import com.siam.system.modular.package_goods.entity.Setting;
import com.siam.system.modular.package_goods.entity.SmsLog;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.controller.member.WxLoginController;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberBillingRecord;
import com.siam.system.modular.package_user.entity.MemberInviteRelation;
import com.siam.system.modular.package_user.mapper.MemberMapper;
import com.siam.system.modular.package_user.model.dto.MemberBillingRecordDto;
import com.siam.system.modular.package_user.model.example.MemberExample;
import com.siam.system.modular.package_user.model.param.MemberParam;
import com.siam.system.modular.package_user.model.result.MemberResult;
import com.siam.system.modular.package_user.service.MemberBillingRecordService;
import com.siam.system.modular.package_user.service.MemberInviteRelationService;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.util.TokenUtil;
import com.siam.package_weixin_basic.config.WxSession;
import com.siam.package_weixin_basic.util.WXBizDataCrypt;
import com.siam.package_weixin_basic.util.WxQrCodeUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Data
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Value("${wxPublicPlatform.appId}")
    private String wxPublicPlatformAppId;

    @Value("${wxPublicPlatform.secret}")
    private String wxPublicPlatformSecret;

    @Autowired
    private MemberMapper memberMapper;

//    @Autowired
//    private OrderService orderService;

    @Autowired
    private WXBizDataCrypt wXBizDataCrypt;

//    @Autowired
//    private WxPublicPlatformSubscribeUserService wxPublicPlatformSubscribeUserService;

    @Autowired
    private OSSUtils ossUtils;

    @Autowired
    private SettingService settingService;

    @Autowired
    private MemberBillingRecordService memberBillingRecordService;

    @Autowired
    private CouponsMemberRelationService couponsMemberRelationService;

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private MemberInviteRelationService memberInviteRelationService;

    @Autowired
    private WxQrCodeUtils wxQrCodeUtils;

    @Autowired
    private SmsLogService smsLogService;

//    @Autowired
//    private PointsMallCouponsMemberRelationService pointsMallCouponsMemberRelationService;

//    @Override
//    public MemberResult loginByMobile(MemberParam param) {
//        WapLogResult basicResult = new WapLogResult();
//
//        Member dbMember = memberMapper.selectByMobile(mobile);
//        if (dbMember == null) {
//            basicResult.setSuccess(false);
//            basicResult.setCode(BasicResultCode.ERR);
//            basicResult.setMessage("该账号不存在");
//            return basicResult;
//        }
//
//        // 判断验证码是否匹配
//        SmsLog smsLog = smsLogService.getLastLog(mobile, BusinessType.SMS_LOG_TYPE_LOGIN, 5);
//        if (smsLog == null || !smsLog.getVerifyCode().equals(mobileCode)) {
//            basicResult.setSuccess(false);
//            basicResult.setCode(BasicResultCode.ERR);
//            basicResult.setMessage("手机验证码错误");
//            return basicResult;
//        }
//
//        // 更新member记录
//        Member updateMember = new Member();
//        updateMember.setId(dbMember.getId());
//        updateMember.setLoginCount(dbMember.getLoginCount() + 1);
//        updateMember.setLastLoginTime(new Date());
//        memberMapper.updateByPrimaryKeySelective(updateMember);
//
//        // 生成token
//        String token = GenerateNo.getUUID();
//
//        // 添加membertoken记录
//        MemberToken memberToken = new MemberToken();
//        memberToken.setMemberId(dbMember.getId());
//        memberToken.setUsername(dbMember.getUsername());
//        memberToken.setToken(token);
//        memberToken.setType(BusinessType.MEMBER_TOKEN_TYPE_WAP);
//        memberToken.setCreateTime(new Date());
//        memberTokenService.insertSelective(memberToken);
//
//        basicResult.setToken(token);
//        basicResult.setSuccess(true);
//        basicResult.setCode(BasicResultCode.SUCCESS);
//        basicResult.setMessage("登录成功");
//        return basicResult;
//    }

    public int countByExample(MemberExample example){
        return memberMapper.countByExample(example);
    }

    public void insertSelective(Member record){
        memberMapper.insertSelective(record);
    }

    public List<Member> selectByExample(MemberExample example){
        return memberMapper.selectByExample(example);
    }

    public Member selectByPrimaryKey(Integer id){
        return memberMapper.selectByPrimaryKey(id);
    }

    public void updateByExampleSelective(Member record, MemberExample example){
        memberMapper.updateByExampleSelective(record, example);
    }

    public void updateByPrimaryKeySelective(Member record){
        memberMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public String encryptionBySalt(String password, String passwordSalt) {
        // 生成盐

        return null;
    }

    @Override
    public Member selectByMobile(String mobile) {
        return memberMapper.selectByMobile(mobile);
    }

    @Override
    public Page getListByPage(MemberParam param) {
        Page<Map<String, Object>> page = memberMapper.getListByPage(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }
    @Override
    public Integer findMemberByOpenId(String openId) {
        return memberMapper.findMemberByOpenId(openId);
    }

    @Override
    public Member findMemberByOneKeyLogin(Member member) throws Exception {
        //获取手机号码
       if(!StringUtils.isEmpty(member.getOpenId())&&!StringUtils.isEmpty(member.getNickname())&&!StringUtils.isEmpty(member.getMobile())){
               member.setMobile(member.getMobile());
            //如果手机号码是否绑定
           Integer id=memberMapper.findMemberByMobile(member.getMobile());
           //当前用户不存在插入
           if(id==null){
               //绑定微信
               member.setIsBindWx(true);
               memberMapper.insert(member);
           }else{
               //通过id修改当前已存在的用户信息
               member.setId(id);
               //绑定微信
               memberMapper.updateMemberByMobile(member.getMobile());
               memberMapper.updateByPrimaryKey(member);
           }
       }
        return member;
    }

    @Override
    public Page getList(int pageNo, int pageSize, MemberParam member) {
       Page<Map<String, Object>> page = memberMapper.getListByPage(new Page(pageNo, pageSize), member);
       return page;
    }

    @Override
    public String getNextVipNo() {
        String vipNo= memberMapper.findMaxVipNo();
        Integer noNum = 1;
        if (vipNo != null) {
            noNum = Integer.parseInt(vipNo)+1;
        }
        String result = String.format("%010d", noNum);

        return result;
    }

    @Override
    public List<Member> selectAllMemberNoneCoupons() {
        return memberMapper.selectAllMemberNoneCoupons();
    }

    @Override
    public List<Member> selectAllMemberNoneCouponsByPointsMall() {
        return memberMapper.selectAllMemberNoneCouponsByPointsMall();
    }

    @Override
    public void updateIsRemindNewPeople() {

        memberMapper.updateIsRemindNewPeople();

        /*List<Map<String, Object>> memberList=memberMapper.getListByPage(new Page(pageNo, pageSize), null);
        for(int i=0;i<memberList.size();i++){
            Integer id = (Integer) memberList.get(i).get("id");
            OrderExample orderExample=new OrderExample();
            orderExample.createCriteria().andMemberIdEqualTo(id);
            orderExample.createCriteria().andStatusEqualTo(6);
            List<Order>orders=orderService.selectByExample(orderExample);
            if(orders.size()>0){
                Member member=new Member();
                member.setIsRemindNewPeople(false);
                member.setisNewPeople(false);
                memberMapper.updateByPrimaryKeySelective(member);
            }
        }*/
    }

    @Override
    public Page<Member> purchasedList(MemberParam param) {
        Page<Member> page = memberMapper.purchasedList(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public Page<Member> unPurchasedList(MemberParam param) {
        Page<Member> page = memberMapper.unPurchasedList(new Page(param.getPageNo(), param.getPageSize()), param);
        return page;
    }

    @Override
    public MemberResult login(MemberParam param) {
        Member dbMember = memberMapper.selectByUsernameOrMobile(param.getUsername());
        if (dbMember == null) {
            throw new StoneCustomerException("该账号不存在");
        }

        // 判断密码是否匹配
        String password = Base64Utils.decode(param.getPassword());
        password = CommonUtils.genMd5Password(password, dbMember.getPasswordSalt());
        if (!password.equals(dbMember.getPassword())) {
            throw new StoneCustomerException("密码错误");
        }

        // 更新用户登录信息
        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setLoginCount(dbMember.getLoginCount() + 1);
        updateMember.setLastLoginTime(new Date());
        memberMapper.updateByPrimaryKeySelective(updateMember);

        // 生成token
        String token = TokenUtil.generateToken(dbMember);

        //创建登录会话
        memberSessionManager.createSession(token, dbMember);

        //创建登录cookie
        TokenUtil.addLoginCookie(token);

        MemberResult memberResult = new MemberResult();
        memberResult.setToken(token);
        return memberResult;
    }

    @Override
    public int selectCountWeChatRegister(Date startTime, Date endTime) {
        return memberMapper.selectCountRegister(Quantity.INT_1, startTime, endTime);
    }

    @Override
    public int selectCountMobileCodeRegister(Date startTime, Date endTime) {
        return memberMapper.selectCountRegister(Quantity.INT_2, startTime, endTime);
    }

    @Override
    public int selectCountGeneralRegister(Date startTime, Date endTime) {
        int countWeChatRegister = memberMapper.selectCountRegister(Quantity.INT_1, startTime, endTime);
        int countMobileCodeRegister = memberMapper.selectCountRegister(Quantity.INT_2, startTime, endTime);
        return countWeChatRegister + countMobileCodeRegister;
    }

    @Override
    public int selectCountRegister(Date startTime, Date endTime) {
        return memberMapper.selectCountRegister(null, startTime, endTime);
    }

    @Override
    public int selectCountInviteRegister(Date startTime, Date endTime) {
        return memberMapper.selectCountRegister(Quantity.INT_3, startTime, endTime);
    }

    @Override
    public void updateIsRequestWxNotify() {
        memberMapper.updateIsRequestWxNotify();
    }

    @Override
    public String generateVipNo() {
        //会员编号/会员卡号 生成规则：开头四位数(8888) + 12位随机数
        String vipNo = null;
        int result = 0;
        while (result < 10){
            String startNo = "8888";
            Random r = new Random();
            StringBuilder code = new StringBuilder();
            for(int i = 0; i < 12; i++) {
                code.append(r.nextInt(10));
            }
            vipNo = startNo.concat(code.toString());

            //校验会员编号是否重复
            MemberExample example = new MemberExample();
            example.createCriteria().andVipNoEqualTo(vipNo);
            int count = memberMapper.countByExample(example);
            if(count == 0){
                break;
            }
            result++;
        }
        if (vipNo == null){
            throw new StoneCustomerException("会员编号生成错误");
        }
        return vipNo;
    }

    @Override
    public void queryWxPublicPlatformOpenId() throws IOException {
        //获取access_token
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxPublicPlatformAppId + "&secret=" + wxPublicPlatformSecret);
        HttpResponse httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("access_token") == null){
            throw new StoneCustomerException(500, "微信公众号-获取access_token失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
        String access_token = (String) map.get("access_token");

        //获取用户列表
        httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token);
        httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("data") == null){
            throw new StoneCustomerException(500, "微信公众号-获取用户列表失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
        }
        List<String> openidList = (List<String>) ((LinkedTreeMap) map.get("data")).get("openid");

        List<Map<String, Object>> userInfoList = new ArrayList<>();

        //批量获取用户基本信息，最多支持一次拉取100条
        List batchOpenidList = new ArrayList();
        for (int i = 0; i < openidList.size(); i++) {
            Map subMap = new HashMap();
            subMap.put("openid", openidList.get(i));
            batchOpenidList.add(subMap);
            if((i+1)%100==0 || i==openidList.size()-1){
                //已经汇集100个openid 或 到达列表末尾，则进行用户基本信息批量查询操作
                Map paramsMap = new HashMap();
                paramsMap.put("user_list", batchOpenidList);
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + access_token);
                httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
                if(map.get("user_info_list") == null){
                    throw new StoneCustomerException(500, "微信公众号-批量获取用户基本信息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
                }
                List<Map<String, Object>> batchUserInfoList = (List<Map<String, Object>>) map.get("user_info_list");
                //将数据组装进完整的用户基本信息集合
                userInfoList.addAll(batchUserInfoList);
                //清空集合数据
                batchOpenidList.clear();
            }
        }

//        //将新的记录存入数据库
//        for (Map<String, Object> user : userInfoList) {
//            WxPublicPlatformSubscribeUserExample example = new WxPublicPlatformSubscribeUserExample();
//            example.createCriteria().andOpenidEqualTo(user.get("openid").toString());
//            int count = wxPublicPlatformSubscribeUserService.countByExample(example);
//            if(count == 0){
//                WxPublicPlatformSubscribeUser subscribeUser = new WxPublicPlatformSubscribeUser();
//                subscribeUser.setSubscribe(user.get("subscribe").toString());
//                subscribeUser.setOpenid(user.get("openid").toString());
//                subscribeUser.setNickname(user.get("nickname").toString());
//                subscribeUser.setSex(user.get("sex").toString());
//                subscribeUser.setLanguage(user.get("language").toString());
//                subscribeUser.setCity(user.get("city").toString());
//                subscribeUser.setProvince(user.get("province").toString());
//                subscribeUser.setCountry(user.get("country").toString());
//                subscribeUser.setHeadimgurl(user.get("headimgurl").toString());
//                subscribeUser.setSubscribeTime(user.get("subscribe_time").toString());
//                subscribeUser.setRemark(user.get("remark").toString());
//                subscribeUser.setGroupid(user.get("groupid").toString());
//                subscribeUser.setTagidList(user.get("tagid_list").toString());
//                subscribeUser.setSubscribeScene(user.get("subscribe_scene").toString());
//                subscribeUser.setQrScene(user.get("qr_scene").toString());
//                subscribeUser.setQrSceneStr(user.get("qr_scene_str").toString());
//                subscribeUser.setCreateTime(new Date());
//                subscribeUser.setUpdateTime(new Date());
//                wxPublicPlatformSubscribeUserService.insertSelective(subscribeUser);
//            }
//        }
    }

    @Override
    public BasicResult querySingleWxPublicPlatformOpenId(Integer id) throws IOException {
        BasicResult basicResult = new BasicResult();
        //获取access_token
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + wxPublicPlatformAppId + "&secret=" + wxPublicPlatformSecret);
        HttpResponse httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        Map<String, Object> map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("access_token") == null){
            log.error("微信公众号-获取access_token失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            basicResult.setSuccess(false);
            basicResult.setMessage("微信公众号api调用失败，请联系管理员");
        }
        String access_token = (String) map.get("access_token");

        //获取用户列表
        httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token);
        httpResponseGetToken = new DefaultHttpClient().execute(httpGet);
        map = GsonUtils.toMap(EntityUtils.toString(httpResponseGetToken.getEntity(), StandardCharsets.UTF_8));
        if(map.get("data") == null){
            log.error("微信公众号-获取用户列表失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
            basicResult.setSuccess(false);
            basicResult.setMessage("微信公众号api调用失败，请联系管理员");
        }
        List<String> openidList = (List<String>) ((LinkedTreeMap) map.get("data")).get("openid");

        //新关注信息列表
        List<String> newOpenidList = new ArrayList<>();

        Map filterMap = new HashMap();

//        //查询微信公众号已关注用户信息表，判断是否新的关注信息
//        WxPublicPlatformSubscribeUserExample example = new WxPublicPlatformSubscribeUserExample();
//        List<WxPublicPlatformSubscribeUser> wxPublicPlatformSubscribeUserList = wxPublicPlatformSubscribeUserService.selectByExample(example);
//        for (WxPublicPlatformSubscribeUser subscribeUser : wxPublicPlatformSubscribeUserList) {
//            filterMap.put(subscribeUser.getOpenid(), null);
//        }
//        for (String openid : openidList) {
//            if (!filterMap.containsKey(openid)){
//                newOpenidList.add(openid);
//            }
//        }

        List<Map<String, Object>> userInfoList = new ArrayList<>();
        //批量获取用户基本信息，最多支持一次拉取100条
        List batchOpenidList = new ArrayList();
        for (int i = 0; i < newOpenidList.size(); i++) {
            Map subMap = new HashMap();
            subMap.put("openid", newOpenidList.get(i));
            batchOpenidList.add(subMap);
            if((i+1)%100==0 || i==newOpenidList.size()-1){
                //已经汇集100个openid 或 到达列表末尾，则进行用户基本信息批量查询操作
                Map paramsMap = new HashMap();
                paramsMap.put("user_list", batchOpenidList);
                HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + access_token);
                httpPost.setEntity(new StringEntity(GsonUtils.toJson(paramsMap), HTTP.UTF_8));
                HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
                map = GsonUtils.toMap(EntityUtils.toString(httpResponse.getEntity(), StandardCharsets.UTF_8));
                if(map.get("user_info_list") == null){
                    log.error("微信公众号-批量获取用户基本信息失败，errcode=".concat(map.get("errcode").toString()).concat("，errmsg=").concat(map.get("errmsg").toString()));
                    basicResult.setSuccess(false);
                    basicResult.setMessage("微信公众号api调用失败，请联系管理员");
                }
                List<Map<String, Object>> batchUserInfoList = (List<Map<String, Object>>) map.get("user_info_list");
                //将数据组装进完整的用户基本信息集合
                userInfoList.addAll(batchUserInfoList);
                //清空集合数据
                batchOpenidList.clear();
            }
        }

//        //将新的记录存入数据库
//        for (Map<String, Object> user : userInfoList) {
//            WxPublicPlatformSubscribeUser subscribeUser = new WxPublicPlatformSubscribeUser();
//            subscribeUser.setSubscribe(user.get("subscribe").toString());
//            subscribeUser.setOpenid(user.get("openid").toString());
//            subscribeUser.setNickname(user.get("nickname").toString());
//            subscribeUser.setSex(user.get("sex").toString());
//            subscribeUser.setLanguage(user.get("language").toString());
//            subscribeUser.setCity(user.get("city").toString());
//            subscribeUser.setProvince(user.get("province").toString());
//            subscribeUser.setCountry(user.get("country").toString());
//            subscribeUser.setHeadimgurl(user.get("headimgurl").toString());
//            subscribeUser.setSubscribeTime(user.get("subscribe_time").toString());
//            subscribeUser.setRemark(user.get("remark").toString());
//            subscribeUser.setGroupid(user.get("groupid").toString());
//            subscribeUser.setTagidList(user.get("tagid_list").toString());
//            subscribeUser.setSubscribeScene(user.get("subscribe_scene").toString());
//            subscribeUser.setQrScene(user.get("qr_scene").toString());
//            subscribeUser.setQrSceneStr(user.get("qr_scene_str").toString());
//            subscribeUser.setCreateTime(new Date());
//            subscribeUser.setUpdateTime(new Date());
//            wxPublicPlatformSubscribeUserService.insertSelective(subscribeUser);
//        }

//        //查看目标用户是否有关注信息
//        Member dbMember = memberMapper.selectByPrimaryKey(id);
//        example = new WxPublicPlatformSubscribeUserExample();
//        example.createCriteria().andNicknameEqualTo(dbMember.getUsername());
//        List<WxPublicPlatformSubscribeUser> subscribeUserList = wxPublicPlatformSubscribeUserService.selectByExample(example);
//        if(subscribeUserList==null || subscribeUserList.isEmpty()){
//            log.debug("没有查询到该用户的微信公众号关注信息，请检查关注状态");
//            basicResult.setSuccess(false);
//            basicResult.setMessage("没有查询到该用户的微信公众号关注信息，请检查关注状态");
//        }else if(subscribeUserList.size() == 1){
//            //赋值微信公众号openId
//            Member updateMember = new Member();
//            updateMember.setId(id);
//            updateMember.setWxPublicPlatformOpenId(subscribeUserList.get(0).getOpenid());
//            this.updateByPrimaryKeySelective(updateMember);
//            basicResult.setSuccess(true);
//            basicResult.setMessage("查询绑定成功");
//        }else if(subscribeUserList.size() > 1){
//            log.debug("查询到该用户的微信公众号关注信息有多条，请联系管理员处理");
//            basicResult.setSuccess(false);
//            basicResult.setMessage("查询到该用户的微信公众号关注信息有多条，请联系管理员处理");
//        }
        return basicResult;
    }

    @Override
    public MemberResult verificationLogin(MemberParam param) {
        MemberResult memberResult = new MemberResult();
        log.debug("\n\n邀请者id : " + param.getInviterId());

        if("123456".equals(param.getMobileCode())){
            //万能验证码，放行
        }else{
            // 判断验证码是否匹配
            SmsLog smsLog = smsLogService.getLastLog(param.getMobile(), BusinessType.SMS_LOG_TYPE_LOGIN, 5);
            if (smsLog==null || !smsLog.getVerifyCode().equals(param.getMobileCode())) {
                throw new StoneCustomerException("手机验证码错误");
            }
        }

        //获取小程序openId
        WxSession wxSession = WxLoginController.getSessionKeyFromWxByCode(param.getCode());
        if (wxSession.getOpenid() == null) {
            throw new StoneCustomerException("用户唯一标识获取失败");
        }
        log.debug("\n\n自动注册获取的openid：" + wxSession.getOpenid());

        // 判断是否已经注册
        Member dbMember = memberMapper.selectByMobile(param.getMobile());
        if (dbMember != null) {
            //用户已经注册，直接进行登录操作
            //更新用户登录信息
            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setLoginCount(dbMember.getLoginCount() + 1);
            updateMember.setLastLoginTime(new Date());
            memberMapper.updateByPrimaryKeySelective(updateMember);
        }else{
            //用户未操作，进行自动注册操作
            dbMember = this.autoRegister(param, Member.REGISTER_WAY_OF_MOBILECODE);
        }

        // 生成token
        String token = TokenUtil.generateToken(dbMember);

        //创建登录会话
        memberSessionManager.createSession(token, dbMember);

        //创建登录cookie
        TokenUtil.addLoginCookie(token);

        memberResult.setOpenId(wxSession.getOpenid());
        memberResult.setToken(token);
        return memberResult;
    }

    @Override
    public MemberResult wxLogin(MemberParam param) {
        MemberResult memberResult = new MemberResult();
        log.debug("\n\n邀请者id : " + param.getInviterId());

        // 判断是否已经注册
        Member dbMember = memberMapper.selectByMobile(param.getMobile());
        if (dbMember != null) {
            //用户已经注册，直接进行登录操作
            //更新member记录
            Member updateMember = new Member();
            updateMember.setId(dbMember.getId());
            updateMember.setLoginCount(dbMember.getLoginCount() + 1);
            updateMember.setIsBindWx(true);
            updateMember.setLastLoginTime(new Date());
            //如果用户之前未绑定微信，则需要更新用户的若干字段信息
            if(!dbMember.getIsBindWx()){
                updateMember.setUsername(param.getUsername());
                updateMember.setHeadImg(param.getHeadImg());
                updateMember.setSex(param.getSex());
                updateMember.setOpenId(param.getOpenId());
                updateMember.setIsBindWx(true);
            }
            memberMapper.updateByPrimaryKeySelective(updateMember);
        } else {
            //用户未操作，进行自动注册操作
            dbMember = this.autoRegister(param, Member.REGISTER_WAY_OF_WECHAT);
        }

        // 生成token
        String token = TokenUtil.generateToken(dbMember);

        //创建登录会话
        memberSessionManager.createSession(token, dbMember);

        //创建登录cookie
        TokenUtil.addLoginCookie(token);

        memberResult.setToken(token);
        return memberResult;
    }

    /**
     * 自动注册
     */
    private Member autoRegister(MemberParam param, int registerWay){
        //获取会员卡号
        String vipNo = this.generateVipNo();

        //获取积分设置
        Setting setting = settingService.selectCurrent();
        BigDecimal registrationRewardPoints = setting.getRegistrationRewardPoints();
        registrationRewardPoints = registrationRewardPoints == null ? BigDecimal.ZERO : registrationRewardPoints;

        //新用户注册赠送邀请新用户注册奖励金额
        BigDecimal registrationRewardInviteRewardAmount = setting.getRegistrationRewardInviteRewardAmount();

        //默认密码为123456
        String passwordSalt = CommonUtils.genSalt();
        String password = "123456";
        password = CommonUtils.genMd5Password(password, passwordSalt);

        //TODO MARK - 新用户默认余额给1000元
        BigDecimal balance = new BigDecimal(1000);

        // 添加member记录
        Member insertMember = new Member();
        insertMember.setUsername(param.getUsername());
        insertMember.setMobile(param.getMobile());
        insertMember.setPassword(password);
        insertMember.setPasswordSalt(passwordSalt);
        /*insertMember.setNickname(member.getNickname());*/
        insertMember.setBalance(balance);
        insertMember.setLoginCount(Quantity.INT_1);
        insertMember.setInviteCode(null);
        insertMember.setIsDisabled(false);
        insertMember.setHeadImg(param.getHeadImg());
        insertMember.setRoles(null);
        insertMember.setSex(param.getSex());
        insertMember.setEmail(null);
        insertMember.setIsDisabled(false);
        insertMember.setIsDeleted(false);
        insertMember.setOpenId(param.getOpenId());
        insertMember.setIsBindWx(true);
        insertMember.setPoints(registrationRewardPoints);
        insertMember.setVipStatus(Quantity.INT_0);
        insertMember.setVipType(Quantity.INT_0);
        insertMember.setVipStartTime(null);
        insertMember.setVipEndTime(null);
        insertMember.setType(Quantity.INT_1);
        insertMember.setVipNo(vipNo);
        insertMember.setIsNewPeople(true);
        insertMember.setIsRemindNewPeople(true);
        insertMember.setRegisterWay(Quantity.INT_1);
        insertMember.setInviteRewardAmount(registrationRewardInviteRewardAmount);
        insertMember.setTotalPoints(registrationRewardPoints);
        insertMember.setCreateTime(new Date());
        insertMember.setUpdateTime(new Date());
        insertMember.setLastLoginTime(new Date());
        memberMapper.insertSelective(insertMember);

        //从数据库中获取注册好的用户信息
        Member dbMember = memberMapper.selectByMobile(param.getMobile());

        //生成邀请分享太阳码
        String path = "data/images/invite_suncode/v1.0";
        String fileName = "suncode_" + insertMember.getId() + ".png";
        String savePath = path + "/" + fileName;
        wxQrCodeUtils.generateSunCode("pages/login/choose/choose", "inviterId="+insertMember.getId(), savePath);
        Member updateMember = new Member();
        updateMember.setId(insertMember.getId());
        updateMember.setInviteSuncode(savePath);
        memberMapper.updateByPrimaryKeySelective(updateMember);

        //生成积分账单
        if (registrationRewardPoints.compareTo(BigDecimal.ZERO) > 0) {
            MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
            memberBillingRecord.setMemberId(insertMember.getId());
            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_POINTS);
            memberBillingRecord.setType(MemberBillingRecord.TYPE_REGISTER_REWARD_POINTS);
            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
            memberBillingRecord.setNumber(registrationRewardPoints);
            memberBillingRecord.setCreateTime(new Date());
            memberBillingRecord.setMessage("新用户注册赠送积分");
            memberBillingRecordService.insertSelective(memberBillingRecord);
        }
        if(registrationRewardInviteRewardAmount.compareTo(BigDecimal.ZERO) > 0){
            //增加用户账单记录
            MemberBillingRecord memberBillingRecord = new MemberBillingRecord();
            memberBillingRecord.setMemberId(insertMember.getId());
            memberBillingRecord.setType(MemberBillingRecord.TYPE_REGISTER_REWARD_INVITE_REWARD_AMOUNT);
            memberBillingRecord.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
            memberBillingRecord.setCoinType(MemberBillingRecord.COIN_TYPE_INVITE_REWARD_AMOUNT);
            memberBillingRecord.setNumber(registrationRewardInviteRewardAmount);
            memberBillingRecord.setMessage("新用户注册赠送奖励金额");
            memberBillingRecord.setCreateTime(new Date());
            memberBillingRecordService.insertSelective(memberBillingRecord);
        }

        //赠送系统默认优惠券-新人3折卷
        Coupons dbCoupons = couponsService.selectByPrimaryKey(BusinessType.NEW_PEOPLE_COUPONS_ID);
        if (dbCoupons == null) {
            throw new StoneCustomerException("系统默认优惠券-新人3折卷不存在");
        }
        CouponsMemberRelation couponsMemberRelation = new CouponsMemberRelation();
        couponsMemberRelation.setCouponsId(BusinessType.NEW_PEOPLE_COUPONS_ID);
        couponsMemberRelation.setMemberId(insertMember.getId());
        couponsMemberRelationService.insertSelective(couponsMemberRelation);

        log.debug("邀请者id:" + param.getInviterId());
        if (param.getInviterId() != null && !param.getInviterId().equals("") && !param.getInviterId().equals("undefined")) {
            Member inviter = memberMapper.selectByPrimaryKey(Integer.valueOf(param.getInviterId()));
            log.debug("通过id获取邀请者对象:"+inviter);
            if (inviter != null && inviter.getId() != null) {
                log.debug("邀请者id:"+inviter.getId());
                log.debug("start-------创建用户邀请关系");
                //创建用户邀请关系
                MemberInviteRelation memberInviteRelation = new MemberInviteRelation();
                memberInviteRelation.setInviterId(Integer.valueOf(param.getInviterId()));
                memberInviteRelation.setMemberId(insertMember.getId());
                memberInviteRelation.setCreateTime(new Date());
                memberInviteRelationService.insertSelective(memberInviteRelation);
                log.debug("end-------创建用户邀请关系");

                log.debug("start-------发送邀请优惠卷");
                //发送邀请卷
                Coupons inviteCoupons = couponsService.selectByPrimaryKey(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID);
                if (inviteCoupons == null) {
                    throw new StoneCustomerException("系统默认优惠券-邀请新人卷不存在");
                }
                CouponsMemberRelation inviteCouponsMemberRelation = new CouponsMemberRelation();
                inviteCouponsMemberRelation.setCouponsId(BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID);
                inviteCouponsMemberRelation.setMemberId(Integer.valueOf(param.getInviterId()));
                couponsMemberRelationService.insertSelective(inviteCouponsMemberRelation);
                log.debug("end-------发送邀请优惠卷");

                //将注册方式改为邀请注册
                updateMember = new Member();
                updateMember.setId(insertMember.getId());
                updateMember.setRegisterWay(Member.REGISTER_WAY_OF_INVITE);
                memberMapper.updateByPrimaryKeySelective(updateMember);
            }
        }

        return dbMember;
    }

    @Override
    public MemberResult getLoginMemberInfo(MemberParam param) {
        MemberResult memberResult = new MemberResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Member member = memberMapper.selectByPrimaryKey(loginMember.getId());

        MemberBillingRecordDto memberBillingRecordDto;

        //余额的账单类型
        List<Integer> balanceTypeList = new ArrayList<>();
        balanceTypeList.add(MemberBillingRecord.TYPE_VIP_RECHARGE_REWARD_BALANCE);

        //计算今日获得余额
        memberBillingRecordDto = new MemberBillingRecordDto();
        memberBillingRecordDto.setMemberId(loginMember.getId());
        memberBillingRecordDto.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
        memberBillingRecordDto.setCoinType(MemberBillingRecord.COIN_TYPE_BALANCE);
        memberBillingRecordDto.setTypeList(balanceTypeList);
        BigDecimal daySumBalance = memberBillingRecordService.selectSumNumber(memberBillingRecordDto, DateUtilsExtend.getDayBegin(), DateUtilsExtend.getDayEnd());

        //计算今日获得积分
        memberBillingRecordDto = new MemberBillingRecordDto();
        memberBillingRecordDto.setMemberId(loginMember.getId());
        memberBillingRecordDto.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
        memberBillingRecordDto.setCoinType(MemberBillingRecord.COIN_TYPE_POINTS);
        BigDecimal daySumPoints = memberBillingRecordService.selectSumNumber(memberBillingRecordDto, DateUtilsExtend.getDayBegin(), DateUtilsExtend.getDayEnd());

        //昨日收益-邀请新用户注册奖励金额/佣金
        memberBillingRecordDto = new MemberBillingRecordDto();
        memberBillingRecordDto.setMemberId(loginMember.getId());
        memberBillingRecordDto.setOperateType(MemberBillingRecord.OPERATE_TYPE_ADD);
        memberBillingRecordDto.setCoinType(MemberBillingRecord.COIN_TYPE_INVITE_REWARD_AMOUNT);
        BigDecimal daySumInviteRewardAmount = memberBillingRecordService.selectSumNumber(memberBillingRecordDto, DateUtilsExtend.getBeginDayOfYesterday(), DateUtilsExtend.getEndDayOfYesterDay());

        //处理返回结构
        BeanUtils.copyProperties(member, memberResult);
        memberResult.setTotayGainBalance(daySumBalance);
        memberResult.setTotayGainPoints(daySumPoints);
        memberResult.setYesterdayGainInviteRewardAmount(daySumInviteRewardAmount);
        return memberResult;
    }

    @Override
    public void removeBindingWx(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        // 更新member记录
        Member member = new Member();
        member.setId(loginMember.getId());
        member.setUpdateTime(new Date());
        member.setIsBindWx(false);
        memberMapper.updateByPrimaryKeySelective(member);
    }

    @Override
    public void uploadHeadImg(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        if (param.getFile() == null || param.getFile().getSize() == 0) {
            throw new StoneCustomerException("图片不能为空");
        }

        String headImg = ossUtils.uploadImage(param.getFile(), "member", loginMember.getId());

        // 更新member记录
        Member updateMember = new Member();
        updateMember.setId(loginMember.getId());
        updateMember.setHeadImg(headImg);
        updateMember.setUpdateTime(new Date());
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    @Override
    public void updatePassword(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        // 判断原密码是否匹配
        Member member = memberMapper.selectByPrimaryKey(loginMember.getId());
        String oldPassword = Base64Utils.decode(param.getOldPassword());
        oldPassword = CommonUtils.genMd5Password(oldPassword, member.getPasswordSalt());
        if (!oldPassword.equals(member.getPassword())) {
            throw new StoneCustomerException("原密码错误");
        }

        // 对新密码进行盐值加密
        String newPassword = Base64Utils.decode(param.getNewPassword());
        newPassword = CommonUtils.genMd5Password(newPassword, member.getPasswordSalt());
        if (newPassword.equals(member.getPassword())) {
            throw new StoneCustomerException("新密码不能与原密码一样");
        }

        // 更新member记录
        Member updateMember = new Member();
        updateMember.setId(loginMember.getId());
        updateMember.setPassword(newPassword);
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    @Override
    public void logout(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

//        //记录退出日志
//        LogManager.me().executeLog(LogTaskFactory.exitLog(LoginContextHolder.getContext().getUser().getId(), getIp()));

        //删除Auth相关cookies
        TokenUtil.deleteLoginCookie();

        //删除会话
        memberSessionManager.removeSession(TokenUtil.getToken());
    }

    @Override
    public void update(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //目前只准修改邮箱地址、性别、真实姓名
        Member updateMember = new Member();
        updateMember.setId(loginMember.getId());
        updateMember.setEmail(param.getEmail());
        updateMember.setSex(param.getSex());
        updateMember.setRealName(param.getRealName());
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    @Override
    public void updateIsRemindNewPeople(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Member updateMember = new Member();
        updateMember.setId(loginMember.getId());
        updateMember.setIsRemindNewPeople(false);
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    @Override
    public void updateIsRequestWxNotify(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Member updateMember = new Member();
        updateMember.setId(loginMember.getId());
        updateMember.setIsRequestWxNotify(false);
        updateMember.setLastRequestWxNotifyTime(new Date());
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    @Override
    public void updateLastUseAddress(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        Member updateMember = new Member();
        updateMember.setId(loginMember.getId());
        updateMember.setLastUseTime(new Date());
        updateMember.setLastUseAddress(param.getLastUseAddress());
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    @Override
    public void forgetPaymentPasswordStep1(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Member dbMember = memberMapper.selectByPrimaryKey(loginMember.getId());
        if(!dbMember.getMobile().equals(param.getMobile())){
            throw new StoneCustomerException("手机号错误");
        }

        if("123456".equals(param.getMobileCode())) {
            //万能验证码，放行
        }else{
            // 判断验证码是否匹配
            SmsLog smsLog = smsLogService.getLastLog(param.getMobile(), BusinessType.SMS_LOG_TYPE_FINDPWD, 5);
            if (smsLog==null || !smsLog.getVerifyCode().equals(param.getMobileCode())) {
                throw new StoneCustomerException("手机验证码错误");
            }
        }

        //TODO-将验证码改为已验证
    }

    @Override
    public void forgetPaymentPasswordStep2(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Member dbMember = memberMapper.selectByPrimaryKey(loginMember.getId());

        //TODO-长时间未操作，请返回第一步重新操作

        //Base64解密
        String paymentPassword = Base64Utils.decode(param.getPaymentPassword());

        String paymentPasswordSalt = dbMember.getPaymentPasswordSalt() != null ? dbMember.getPaymentPasswordSalt() : CommonUtils.genSalt();
        if(org.apache.commons.lang3.StringUtils.isNotBlank(paymentPassword) && paymentPassword.length()!=6){
            throw new StoneCustomerException("密码长度错误");
        }
        paymentPassword = CommonUtils.genMd5Password(paymentPassword, paymentPasswordSalt);

        //修改用户支付密码
        Member updateMember = new Member();
        updateMember.setId(dbMember.getId());
        updateMember.setPaymentPassword(paymentPassword);
        updateMember.setPaymentPasswordSalt(paymentPasswordSalt);
        memberMapper.updateByPrimaryKeySelective(updateMember);
    }

    @Override
    public void verifyPaymentPassword(MemberParam param) {
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());
        Member dbMember = memberMapper.selectByPrimaryKey(loginMember.getId());

        //判断密码是否匹配
        String paymentPassword = Base64Utils.decode(param.getPaymentPassword());
        paymentPassword = CommonUtils.genMd5Password(paymentPassword, dbMember.getPaymentPasswordSalt());
        if(!paymentPassword.equals(dbMember.getPaymentPassword())){
            throw new StoneCustomerException("支付密码输入错误，请重新输入");
        }
    }
}