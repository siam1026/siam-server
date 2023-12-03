package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsSpecificationOptionService;
import com.siam.system.modular.package_goods.service.internal.PointsMallShoppingCartService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.util.GsonUtils;
import com.siam.system.modular.package_goods.entity.internal.PointsMallShoppingCart;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallShoppingCartExample;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/pointsMall/shoppingCart")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "购物车模块相关接口", description = "PointsMallShoppingCartController")
public class PointsMallShoppingCartController {

    @Autowired
    private PointsMallShoppingCartService shoppingCartService;

    @Autowired
    private PointsMallGoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    /**
     * 购物车列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallShoppingCart shoppingCart, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //只查询当前登录用户的购物车信息
        shoppingCart.setMemberId(loginMember.getId());
        //只查询商品有效的购物车记录
        shoppingCart.setIsGoodsExists(true);
        Page<Map<String, Object>> page = shoppingCartService.getListByPageJoinPointsMallGoods(shoppingCart.getPageNo(), shoppingCart.getPageSize(), shoppingCart);

        page.getRecords().forEach(shoppingCartMap -> {
            //商品规格选项值列表
            List<String> nameList = new ArrayList<>();
            //计算单品对应规格的价格
            Map<String, Object> map = GsonUtils.toMap((String) shoppingCartMap.get("specList"));
            for(String key : map.keySet()){
                nameList.add((String) map.get(key));
            }

            //正常情况下nameList不能为空，为空也要做特殊处理
            BigDecimal specOptionPrice = BigDecimal.ZERO;
            if(nameList.size() > 0){
                specOptionPrice = goodsSpecificationOptionService.selectSumPriceByGoodsIdAndName((int) shoppingCartMap.get("goodsId"), nameList);
            }

            //单品的总价 暂时不考虑折扣价
            BigDecimal price = ((BigDecimal) shoppingCartMap.get("goodsPrice")).add(specOptionPrice);
            shoppingCartMap.put("price", price);
        });

        return BasicResult.success(page);
    }

    /**
     * 新增购物车
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallShoppingCart shoppingCart, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //判断是否为第一次加入购物车，如果不是则数量加1
        PointsMallShoppingCartExample example = new PointsMallShoppingCartExample();
        example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                .andPointsMallGoodsIdEqualTo(shoppingCart.getGoodsId())
                .andSpecListEqualTo(shoppingCart.getSpecList());
        List<PointsMallShoppingCart> shoppingCartList = shoppingCartService.selectByExample(example);
        if(shoppingCartList!=null && shoppingCartList.size() > 0){
            PointsMallShoppingCart dbPointsMallShoppingCart = shoppingCartList.get(0);
            //修改购物车记录的 购买数量
            PointsMallShoppingCart updatePointsMallShoppingCart = new PointsMallShoppingCart();
            updatePointsMallShoppingCart.setId(dbPointsMallShoppingCart.getId());
            updatePointsMallShoppingCart.setNumber(dbPointsMallShoppingCart.getNumber() + Quantity.INT_1);
            shoppingCartService.updateByPrimaryKeySelective(updatePointsMallShoppingCart);

        }else{
            //第一次加入购物车
            shoppingCart.setMemberId(loginMember.getId());
            shoppingCart.setNumber(Quantity.INT_1);
            shoppingCartService.insertSelective(shoppingCart);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    /**
     * 修改购物车单品的购买数量
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/updateNumber")
    public BasicResult updateNumber(@RequestBody @Validated(value = {}) PointsMallShoppingCart shoppingCart, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        PointsMallShoppingCart dbPointsMallShoppingCart = shoppingCartService.selectByPrimaryKey(shoppingCart.getId());
        if(!dbPointsMallShoppingCart.getMemberId().equals(loginMember.getId())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该购物车单品不是你的，不允许修改");
            return basicResult;
        }
        if(shoppingCart.getType()!=Quantity.INT_0 && shoppingCart.getType()!=Quantity.INT_1){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("操作类型不正确");
            return basicResult;
        }
        if(shoppingCart.getNumber() <= Quantity.INT_0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("加减数量不能小于等于0");
            return basicResult;
        }

        //加减操作后，购物车单品的实际数量
        int actualNumber = 0;
        if(shoppingCart.getType() == Quantity.INT_0){
            actualNumber = dbPointsMallShoppingCart.getNumber() - shoppingCart.getNumber();
        }else if(shoppingCart.getType() == Quantity.INT_1){
            actualNumber = dbPointsMallShoppingCart.getNumber() + shoppingCart.getNumber();
        }

        if(actualNumber < Quantity.INT_0){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("加减数量不正确，导致修改后的购买数量小于0");
            return basicResult;
        }else if(actualNumber == Quantity.INT_0){
            //如果减去数量后数值为0，则删除该购物车单品
            shoppingCartService.deleteByPrimaryKey(dbPointsMallShoppingCart.getId());
        }else{
            //修改该购物车单品购买数量
            shoppingCart.setNumber(actualNumber);
            shoppingCartService.updateByPrimaryKeySelective(shoppingCart);
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("修改成功");
        return basicResult;
    }

    /**
     * 删除购物车单品
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallShoppingCart param){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        PointsMallShoppingCart dbPointsMallShoppingCart = shoppingCartService.selectByPrimaryKey(param.getId());
        if(!dbPointsMallShoppingCart.getMemberId().equals(loginMember.getId())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该购物车单品不是你的，不允许删除");
            return basicResult;
        }

        //删除该购物车单品
        shoppingCartService.deleteByPrimaryKey(param.getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}