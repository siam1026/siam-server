package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.service.GoodsService;
import com.siam.system.modular.package_goods.service.GoodsSpecificationOptionService;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_goods.entity.internal.MemberGoodsCollect;
import com.siam.system.modular.package_goods.model.dto.internal.MemberGoodsCollectDto;
import com.siam.system.modular.package_goods.model.example.internal.MemberGoodsCollectExample;
import com.siam.system.modular.package_goods.service.internal.MemberGoodsCollectService;
import com.siam.system.util.TokenUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rest/member/goodsCollect")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "用户商品收藏相关接口", description = "MemberGoodsCollectController")
public class MemberGoodsCollectController {

    @Autowired
    private MemberGoodsCollectService memberGoodsCollectService;

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品收藏列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) MemberGoodsCollectDto memberGoodsCollectDto, HttpServletRequest request){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //TODO-在查询的时候进行埋点修改，跟以往的级联埋点 / 查询时统计不一样
        if (memberGoodsCollectDto.getIsBuy()!=null && memberGoodsCollectDto.getIsBuy()){
            //查询已买过的收藏商品，先进行埋点修改操作
            memberGoodsCollectService.updateIsBuy();
        }

        //只查询当前登录用户有效的商品收藏记录
        memberGoodsCollectDto.setMemberId(loginMember.getId());
        memberGoodsCollectDto.setIsGoodsExists(true);
        Page<Map<String, Object>> page = memberGoodsCollectService.getListByPageJoinGoods(memberGoodsCollectDto.getPageNo(), memberGoodsCollectDto.getPageSize(), memberGoodsCollectDto);

        return BasicResult.success(page);
    }

    /**
     * 新增商品收藏
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) MemberGoodsCollect memberGoodsCollect, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //判断是否为第一次加入商品收藏，如果不是则数量加1
        MemberGoodsCollectExample example = new MemberGoodsCollectExample();
        example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                .andGoodsIdEqualTo(memberGoodsCollect.getGoodsId());
        List<MemberGoodsCollect> MemberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
        if(MemberGoodsCollectList!=null && MemberGoodsCollectList.size() > 0){
            throw new StoneCustomerException("您已经收藏过此商品");
        }

        //查询该商品对应的门店信息
        Goods dbGoods = goodsService.getById(memberGoodsCollect.getGoodsId());
        if(dbGoods == null){
            throw new StoneCustomerException("该商品不存在");
        }
        memberGoodsCollect.setShopId(dbGoods.getShopId());

        //加入商品收藏
        memberGoodsCollect.setMemberId(loginMember.getId());
        memberGoodsCollectService.insertSelective(memberGoodsCollect);

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("新增成功");
        return basicResult;
    }

    /**
     * 删除商品收藏
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/delete")
    public BasicResult delete(@RequestBody @Validated(value = {}) MemberGoodsCollect param){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //判断是否为第一次加入商品收藏，如果不是则数量加1
        MemberGoodsCollectExample example = new MemberGoodsCollectExample();
        example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                .andGoodsIdEqualTo(param.getGoodsId());
        List<MemberGoodsCollect> memberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
        if(memberGoodsCollectList==null || memberGoodsCollectList.isEmpty()){
            throw new StoneCustomerException("该商品收藏记录不存在");
        }
        if(!memberGoodsCollectList.get(0).getMemberId().equals(loginMember.getId())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商品收藏记录不是你的，不允许删除");
            return basicResult;
        }

        //删除该商品收藏
        memberGoodsCollectService.deleteByPrimaryKey(memberGoodsCollectList.get(0).getId());

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    /**
     * 查看商品收藏记录
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/selectByGoodsId")
    public BasicResult selectByGoodsId(@RequestBody @Validated(value = {}) MemberGoodsCollect param){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //判断是否为第一次加入商品收藏，如果不是则数量加1
        MemberGoodsCollectExample example = new MemberGoodsCollectExample();
        example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                .andGoodsIdEqualTo(param.getGoodsId());
        List<MemberGoodsCollect> memberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
        if(memberGoodsCollectList==null || memberGoodsCollectList.isEmpty()){
            basicResult.setData(null);
        }else{
            basicResult.setData(memberGoodsCollectList.get(0));
        }

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }

    /**
     * 删除商品收藏
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/batchDelete")
    public BasicResult batchDelete(@RequestBody @Validated(value = {}) MemberGoodsCollect param                , HttpServletRequest request){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        param.getGoodsIdList().forEach(goodsId -> {
            //判断是否为第一次加入商品收藏，如果不是则数量加1
            MemberGoodsCollectExample example = new MemberGoodsCollectExample();
            example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                    .andGoodsIdEqualTo(goodsId);
            List<MemberGoodsCollect> memberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
            if(memberGoodsCollectList==null || memberGoodsCollectList.isEmpty()){
                throw new StoneCustomerException("该商品收藏记录不存在");
            }
            if(!memberGoodsCollectList.get(0).getMemberId().equals(loginMember.getId())){
                throw new StoneCustomerException("该商品收藏记录不是你的，不允许删除");
            }

            //删除该商品收藏
            memberGoodsCollectService.deleteByPrimaryKey(memberGoodsCollectList.get(0).getId());
        });

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}