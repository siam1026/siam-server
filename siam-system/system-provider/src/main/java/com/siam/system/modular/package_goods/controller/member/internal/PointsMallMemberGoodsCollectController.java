package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_user.auth.cache.MemberSessionManager;
import com.siam.system.modular.package_user.entity.Member;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsSpecificationOptionService;
import com.siam.system.modular.package_goods.service.internal.PointsMallMemberGoodsCollectService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallMemberGoodsCollectDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMemberGoodsCollect;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallMemberGoodsCollectExample;
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
@RequestMapping(value = "/rest/member/pointsMall/goodsCollect")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "用户商品收藏相关接口", description = "PointsMallMemberGoodsCollectController")
public class PointsMallMemberGoodsCollectController {

    @Autowired
    private MemberSessionManager memberSessionManager;

    @Autowired
    private PointsMallMemberGoodsCollectService memberGoodsCollectService;

    @Autowired
    private PointsMallGoodsSpecificationOptionService goodsSpecificationOptionService;

    /**
     * 商品收藏列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallMemberGoodsCollectDto memberGoodsCollectDto, HttpServletRequest request){
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
        Page<Map<String, Object>> page = memberGoodsCollectService.getListByPageJoinPointsMallGoods(memberGoodsCollectDto.getPageNo(), memberGoodsCollectDto.getPageSize(), memberGoodsCollectDto);

        return BasicResult.success(page);
    }

    /**
     * 新增商品收藏
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/insert")
    public BasicResult insert(@RequestBody @Validated(value = {}) PointsMallMemberGoodsCollect pointsMallMemberGoodsCollect, HttpServletRequest request){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //判断是否为第一次加入商品收藏，如果不是则数量加1
        PointsMallMemberGoodsCollectExample example = new PointsMallMemberGoodsCollectExample();
        example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                .andGoodsIdEqualTo(pointsMallMemberGoodsCollect.getGoodsId());
        List<PointsMallMemberGoodsCollect> PointsMallMemberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
        if(PointsMallMemberGoodsCollectList!=null && PointsMallMemberGoodsCollectList.size() > 0){
            throw new StoneCustomerException("您已经收藏过此商品");
        }

        //加入商品收藏
        pointsMallMemberGoodsCollect.setMemberId(loginMember.getId());
        memberGoodsCollectService.insertSelective(pointsMallMemberGoodsCollect);

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
    public BasicResult delete(@RequestBody @Validated(value = {}) PointsMallMemberGoodsCollect param){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //判断是否为第一次加入商品收藏，如果不是则数量加1
        PointsMallMemberGoodsCollectExample example = new PointsMallMemberGoodsCollectExample();
        example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                .andGoodsIdEqualTo(param.getGoodsId());
        List<PointsMallMemberGoodsCollect> pointsMallMemberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
        if(pointsMallMemberGoodsCollectList==null || pointsMallMemberGoodsCollectList.isEmpty()){
            throw new StoneCustomerException("该商品收藏记录不存在");
        }
        if(!pointsMallMemberGoodsCollectList.get(0).getMemberId().equals(loginMember.getId())){
            basicResult.setSuccess(false);
            basicResult.setCode(BasicResultCode.ERR);
            basicResult.setMessage("该商品收藏记录不是你的，不允许删除");
            return basicResult;
        }

        //删除该商品收藏
        memberGoodsCollectService.deleteByPrimaryKey(pointsMallMemberGoodsCollectList.get(0).getId());

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
    public BasicResult selectByGoodsId(@RequestBody @Validated(value = {}) PointsMallMemberGoodsCollect param){
        BasicData basicResult = new BasicData();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        //判断是否为第一次加入商品收藏，如果不是则数量加1
        PointsMallMemberGoodsCollectExample example = new PointsMallMemberGoodsCollectExample();
        example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                .andGoodsIdEqualTo(param.getGoodsId());
        List<PointsMallMemberGoodsCollect> pointsMallMemberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
        if(pointsMallMemberGoodsCollectList==null || pointsMallMemberGoodsCollectList.isEmpty()){
            basicResult.setData(null);
        }else{
            basicResult.setData(pointsMallMemberGoodsCollectList.get(0));
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
    public BasicResult batchDelete(@RequestBody @Validated(value = {}) PointsMallMemberGoodsCollect param                 , HttpServletRequest request){
        BasicResult basicResult = new BasicResult();
        Member loginMember = memberSessionManager.getSession(TokenUtil.getToken());

        param.getGoodsIdList().forEach(goodsId -> {
            //判断是否为第一次加入商品收藏，如果不是则数量加1
            PointsMallMemberGoodsCollectExample example = new PointsMallMemberGoodsCollectExample();
            example.createCriteria().andMemberIdEqualTo(loginMember.getId())
                    .andGoodsIdEqualTo(goodsId);
            List<PointsMallMemberGoodsCollect> pointsMallMemberGoodsCollectList = memberGoodsCollectService.selectByExample(example);
            if(pointsMallMemberGoodsCollectList==null || pointsMallMemberGoodsCollectList.isEmpty()){
                throw new StoneCustomerException("该商品收藏记录不存在");
            }
            if(!pointsMallMemberGoodsCollectList.get(0).getMemberId().equals(loginMember.getId())){
                throw new StoneCustomerException("该商品收藏记录不是你的，不允许删除");
            }

            //删除该商品收藏
            memberGoodsCollectService.deleteByPrimaryKey(pointsMallMemberGoodsCollectList.get(0).getId());
        });

        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("删除成功");
        return basicResult;
    }
}