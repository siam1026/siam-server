package com.siam.system.modular.package_goods.controller.member.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_goods.service.internal.PointsMallMenuService;
import com.siam.package_common.entity.BasicData;
import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.constant.BasicResultCode;
import com.siam.package_common.constant.Quantity;
import com.siam.system.modular.package_goods.entity.internal.PointsMallMenu;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest/pointsMall/menu")
@Transactional(rollbackFor = Exception.class)
@Api(tags = "菜单分类模块相关接口", description = "PointsMallMenuController")
public class PointsMallMenuController {

    @Autowired
    private PointsMallMenuService menuService;

    /**
     * 菜单列表
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/list")
    public BasicResult list(@RequestBody @Validated(value = {}) PointsMallMenu menu){
        BasicData basicResult = new BasicData();

        Page<PointsMallMenu> page = menuService.getList(menu.getPageNo(), menu.getPageSize(), menu);

        return BasicResult.success(page);
    }

    /**
     * 菜单列表(一次性返回菜单+商品数据)
     *
     * @return
     * @author 暹罗
     */
    @PostMapping(value = "/listWithGoods")
    public BasicResult listWithPointsMallGoods(@RequestBody @Validated(value = {}) PointsMallMenu menu){
        BasicData basicResult = new BasicData();

        Map<Integer, Map<String, Object>> filterMap = new LinkedHashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();

        //过滤数据
        List<Map<String, Object>> list = menuService.getListByPageJoinPointsMallGoods(menu);
        list.forEach(map -> {
            if(filterMap.containsKey(map.get("id"))){
                //只查询2=已上架 4=售罄的商品
                if(map.get("goodsId") != null && (Quantity.INT_2 == (int) map.get("goodsStatus") || Quantity.INT_4 == (int) map.get("goodsStatus"))){
                    //存入商品数据
                    Map<String, Object> goodsMap = new LinkedHashMap<>();
                    goodsMap.put("goodsId", map.get("goodsId"));
                    goodsMap.put("goodsName", map.get("goodsName"));
                    goodsMap.put("mainImage", map.get("mainImage"));
                    goodsMap.put("goodsPrice", map.get("goodsPrice"));
                    goodsMap.put("salePrice", map.get("salePrice"));
                    goodsMap.put("stock", map.get("stock"));
                    goodsMap.put("goodsStatus", map.get("goodsStatus"));
                    goodsMap.put("isSale", map.get("isSale"));
                    /*goodsMap.put("packingCharges", map.get("packingCharges"));*/
                    ((ArrayList) filterMap.get(map.get("id")).get("goodsList")).add(goodsMap);
                }
            }else{
                //存入菜单数据
                Map<String, Object> menuMap = new LinkedHashMap<>();
                menuMap.put("id", map.get("id"));
                menuMap.put("shopId", map.get("shopId"));
                menuMap.put("name", map.get("name"));
                menuMap.put("description", map.get("description"));
                menuMap.put("sortNumber", map.get("sortNumber"));
                menuMap.put("createTime", map.get("createTime"));
                menuMap.put("updateTime", map.get("updateTime"));
                menuMap.put("goodsList", new ArrayList<>());

                //只查询2=已上架 4=售罄的商品
                if(map.get("goodsId") != null && (Quantity.INT_2 == (int) map.get("goodsStatus") || Quantity.INT_4 == (int) map.get("goodsStatus"))){
                    //存入商品数据
                    Map<String, Object> goodsMap = new LinkedHashMap<>();
                    goodsMap.put("goodsId", map.get("goodsId"));
                    goodsMap.put("goodsName", map.get("goodsName"));
                    goodsMap.put("mainImage", map.get("mainImage"));
                    goodsMap.put("goodsPrice", map.get("goodsPrice"));
                    goodsMap.put("salePrice", map.get("salePrice"));
                    goodsMap.put("stock", map.get("stock"));
                    goodsMap.put("goodsStatus", map.get("goodsStatus"));
                    goodsMap.put("isSale", map.get("isSale"));
                    /*goodsMap.put("packingCharges", map.get("packingCharges"));*/
                    ((ArrayList) menuMap.get("goodsList")).add(goodsMap);
                }

                filterMap.put((Integer) map.get("id"), menuMap);
            }
        });

        //组装数据进行返回
        for (Integer key : filterMap.keySet()){
            resultList.add(filterMap.get(key));
        }

        basicResult.setData(resultList);
        basicResult.setSuccess(true);
        basicResult.setCode(BasicResultCode.SUCCESS);
        basicResult.setMessage("查询成功");
        return basicResult;
    }
}