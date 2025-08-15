package com.siam.system.modular.package_goods.service.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsMenuDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsExample;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PointsMallGoodsService extends IService<PointsMallGoods> {
    int countByExample(PointsMallGoodsExample example);

    List<PointsMallGoods> selectByExample(PointsMallGoodsExample example);

    Page<PointsMallGoods> getListByPage(int pageNo, int pageSize, PointsMallGoods goods);

    Page<Map<String, Object>> getListByPageJoinPointsMallMenu(int pageNo, int pageSize, PointsMallGoodsMenuDto goodsPointsMallMenuDto);

    Page<Map<String, Object>> getListByPageJoinPointsMallMenuPointsMallOrderByLatelyMonthlySales(int pageNo, int pageSize, PointsMallGoodsMenuDto goodsPointsMallMenuDto);

    /*Page<Map<String, Object>> getListByPageJoinSpecification(int pageNo, int pageSize, PointsMallGoodsSpecificationDto goodsSpecificationDto);*/

    /**
     * 增加库存数量
     * @param id
     * @param number
     */
    void increaseStock(int id, int number);

    /**
     * 减少库存数量
     * @param id
     * @param number
     */
    void decreaseStock(int id, int number);

    /**
     * 解析商品导入Excel表格
     * @param inputStream
     * @return
     */
    List<PointsMallGoods> parseExcel(InputStream inputStream);

    /**
     * 解析Excel表格 plus加强版
     * @param inputStream
     * @return
     */
    List<PointsMallGoods> parseExcel_plus(InputStream inputStream);

    /**
     * 生成商品导出Excel表格
     * @param list
     * @return
     */
    XSSFWorkbook createExcel(List<PointsMallGoods> list);

    /**
     * 修改商品状态
     * @param ids
     * @param status
     */
    void updateStatus(List<Integer> ids, Integer status);

    /**
     * 查询累计销量前三的商品
     * @param
     */
    List<PointsMallGoods> getListByTotalSalesTop3();

    /**
     * 查询近一月销量前三的商品
     * PS：要筛选出商品状态为已上架、订单状态为已完成的数据
     * => 排除掉被删除的商品、售罄的商品
     * @param
     */
    List<Map<String, Object>> getListByLatelyMonthlySalesTop3(Date startTime, Date endTime);

    /**
     * 查询近一月销量前XX位的商品
     * PS：要筛选出商品状态为已上架、订单状态为已完成的数据
     * => 排除掉被删除的商品、售罄的商品
     * @param
     */
    List<Map<String, Object>> getListByLatelyMonthlySalesTopNumber(Date startTime, Date endTime, Integer topNumber);

    /**
     * 更新商品的销量
     * @param goodsId 商品id
     * @param num 销量变动值
     */
    void updateSales(Integer goodsId, Integer num);

    /**
     * 商品月销量清零
     */
    void monthlySalesReset();

    /**
     * 查询最新上架的一件商品(其实是最新创建并上架的一件商品)
     * @return
     */
    List<PointsMallGoods> getListByLastestShelvesTop1();

    Page<Map<String, Object>> recommendGoodsList(int pageNo, int pageSize, PointsMallGoods goods);

    long selectLatelyMonthlySalesById(Integer id);
}