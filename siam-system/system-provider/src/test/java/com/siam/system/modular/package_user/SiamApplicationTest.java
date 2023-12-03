package com.siam.system.modular.package_user;

import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_user.entity.MemberWithdrawRecord;
import com.siam.system.modular.package_user.service.MemberService;
import com.siam.system.modular.package_user.service.MemberWithdrawRecordService;
import com.siam.system.util.TokenUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties
public class SiamApplicationTest {

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private MemberService memberService;

    @Autowired
    MemberWithdrawRecordService memberWithdrawRecordService;

    @Test
    public void testGetMaxVipNo(){
        String i = memberService.getNextVipNo();
        System.out.println(i);
    }

    @Test
    public void test(){
        Member member = new Member();
        member.setId(9798798);
        member.setPassword("sfdasfad");
        String token = TokenUtil.generateToken(member);
        System.out.println("\n\ntoken = " + token);
    }

    @Test
    public void test2(){
//        String token = "001";
//        Member member = memberService.selectByMobile("18711389426");
//        memberSessionManager.createSession(token, member);
//
//        Member loginMember = memberSessionManager.getSession(token);
//        System.out.println(loginMember + " -- mobile: " + loginMember.getMobile());


//        memberService.selectByExample(new MemberExample());

        System.out.println(TokenUtil.getToken());
    }


    @Test
    public void testInsertMemberWithdrawRecord(){
        MemberWithdrawRecord memberWithdrawRecord = new MemberWithdrawRecord();
        memberWithdrawRecord.setMemberId(1);
        memberWithdrawRecord.setOrderNo("1");
        memberWithdrawRecord.setCoinType(1);
        memberWithdrawRecord.setWithdrawAmount(BigDecimal.ZERO);
        memberWithdrawRecord.setPlatformFee(BigDecimal.ZERO);
        memberWithdrawRecordService.save(memberWithdrawRecord);
    }
}