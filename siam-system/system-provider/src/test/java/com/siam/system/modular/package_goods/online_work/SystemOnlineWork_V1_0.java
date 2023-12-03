package com.siam.online_work;

import com.siam.system.modular.package_goods.service.CouponsGoodsRelationService;
import com.siam.system.modular.package_goods.service.GoodsService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Deprecated
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
public class SystemOnlineWork_V1_0 {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private CouponsGoodsRelationService goodsRelationService;

    /**
     * 将系统默认的新人3折卷、推荐新人3折卷与所有商品建立关联关系
     */
    /*@Test
    public void test(){
        Integer newPeopleCouponsId = BusinessType.NEW_PEOPLE_COUPONS_ID;
        Integer inviteNewPeopleCouponsId= BusinessType.INVITE_NEW_PEOPLE_COUPONS_ID;

        //查询出所有的商品
        List<Goods> allGoodsList = goodsService.selectByExample(new GoodsExample());
        for (Goods goods : allGoodsList) {
            CouponsGoodsRelation newPeopleCouponsGoodsRelation = new CouponsGoodsRelation();
            newPeopleCouponsGoodsRelation.setCouponsId(newPeopleCouponsId);
            newPeopleCouponsGoodsRelation.setGoodsId(goods.getId());
            newPeopleCouponsGoodsRelation.setCreateTime(new Date());
            goodsRelationService.insertSelective(newPeopleCouponsGoodsRelation);

            CouponsGoodsRelation inviteNewPeopleCouponsGoodsRelation = new CouponsGoodsRelation();
            inviteNewPeopleCouponsGoodsRelation.setCouponsId(inviteNewPeopleCouponsId);
            inviteNewPeopleCouponsGoodsRelation.setGoodsId(goods.getId());
            inviteNewPeopleCouponsGoodsRelation.setCreateTime(new Date());
            goodsRelationService.insertSelective(inviteNewPeopleCouponsGoodsRelation);
        }
    }*/
}