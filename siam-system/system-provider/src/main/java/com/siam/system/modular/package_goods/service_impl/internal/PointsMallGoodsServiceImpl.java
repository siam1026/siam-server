package com.siam.system.modular.package_goods.service_impl.internal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.mapper.GoodsMapper;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsMapper;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsMenuDto;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsExample;
import com.siam.system.modular.package_goods.service.GoodsService;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsService;
import com.siam.system.modular.package_goods.mapper.internal.PointsMallGoodsMapper;
import com.siam.system.modular.package_goods.service.internal.PointsMallGoodsService;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.DateUtilsPlus;
import com.siam.system.modular.package_goods.model.dto.internal.PointsMallGoodsMenuDto;
import com.siam.system.modular.package_goods.entity.internal.PointsMallGoods;
import com.siam.system.modular.package_goods.model.example.internal.PointsMallGoodsExample;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@Service
public class PointsMallGoodsServiceImpl extends ServiceImpl<PointsMallGoodsMapper, PointsMallGoods> implements PointsMallGoodsService {

    @Autowired
    private PointsMallGoodsMapper goodsMapper;

    @Override
    public int countByExample(PointsMallGoodsExample example) {
        return goodsMapper.countByExample(example);
    }

    public List<PointsMallGoods> selectByExample(PointsMallGoodsExample example){
        return goodsMapper.selectByExample(example);
    }

    @Override
    public Page<PointsMallGoods> getListByPage(int pageNo, int pageSize, PointsMallGoods goods) {
        Page<PointsMallGoods> page = goodsMapper.getListByPage(new Page(pageNo, pageSize), goods);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinPointsMallMenu(int pageNo, int pageSize, PointsMallGoodsMenuDto goodsPointsMallMenuDto) {
        Page<Map<String, Object>> page = goodsMapper.getListByPageJoinPointsMallMenu(new Page(pageNo, pageSize), goodsPointsMallMenuDto);
        /*for(Map<String, Object> map : list){
            Integer goodsId= (Integer) map.get("id");
            //根据商品id查询原料制作杯数
            List<Map<String, Object>> goodsStock = rawmaterialMapper.getGoodsStockById(goodsId);
            if(goodsStock.get(0) != null){
                map.put("stock", goodsStock.get(0).get("goodsStock"));//修改值
            }
        }*/
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinPointsMallMenuPointsMallOrderByLatelyMonthlySales(int pageNo, int pageSize, PointsMallGoodsMenuDto goodsPointsMallMenuDto) {
        Page<Map<String, Object>> page = goodsMapper.getListByPageJoinPointsMallMenuPointsMallOrderByLatelyMonthlySales(new Page(pageNo, pageSize), goodsPointsMallMenuDto);
        return page;
    }

    /*@Override
    public Page<Map<String, Object>> getListByPageJoinSpecification(int pageNo, int pageSize, PointsMallGoodsSpecificationDto goodsSpecificationDto) {
        Page<Map<String, Object>> page = goodsMapper.getListByPageJoinSpecification(goodsSpecificationDto);
        return page;
    }*/

    @Override
    public void increaseStock(int id, int number) {
        PointsMallGoods dbPointsMallGoods = goodsMapper.selectById(id);
        //增加库存数量
        PointsMallGoods updatePointsMallGoods = new PointsMallGoods();
        updatePointsMallGoods.setId(dbPointsMallGoods.getId());
        updatePointsMallGoods.setStock(dbPointsMallGoods.getStock() + number);
        goodsMapper.updateByPrimaryKeySelective(updatePointsMallGoods);
    }

    @Override
    public void decreaseStock(int id, int number) {
        PointsMallGoods dbPointsMallGoods = goodsMapper.selectById(id);
        if(dbPointsMallGoods.getStock() - number < 0){
            throw new StoneCustomerException(dbPointsMallGoods.getName() + "库存不足，请减少该单品购买数量");
        }
        //减少库存数量
        PointsMallGoods updatePointsMallGoods = new PointsMallGoods();
        updatePointsMallGoods.setId(dbPointsMallGoods.getId());
        updatePointsMallGoods.setStock(dbPointsMallGoods.getStock() - number);
        goodsMapper.updateByPrimaryKeySelective(updatePointsMallGoods);
    }

    /**
     * 解析商品导入Excel文件
     * @param inputStream
     * @return
     */
    @Override
    public List<PointsMallGoods> parseExcel(InputStream inputStream) {
        List<PointsMallGoods> list = new ArrayList<PointsMallGoods>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 如果模板啥也没有，lastRowNum也是为0
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum == 0){
                throw new RuntimeException("模板为空");
            }

            // 检测模板列数是否正确
            XSSFRow firstRow = sheet.getRow(0);
            int cellNum = firstRow.getPhysicalNumberOfCells();
            String template = "#商品名称#分类id#品牌id#商品规格#商品详情#一口价#折扣价#库存#是否热门#是否新品#状态#";
            for(int i = 0; i < cellNum; i++){
                XSSFCell cell = firstRow.getCell(i);
                String cellValue = cell.getStringCellValue().trim();
                if(template.startsWith("#" + cellValue + "#")){
                    template = template.replace("#" + cellValue, "");
                }else{
                    throw new RuntimeException("模板格式错误，格式为：\t商品名称\t分类id\t品牌id\t商品规格\t商品详情\t一口价\t折扣价\t库存\t是否热门\t是否新品\t状态");
                }
            }

            // 解析Excel中的内容
            for(int i = 1; i <= lastRowNum; i++){
                PointsMallGoods goods = new PointsMallGoods();
                XSSFRow xssfRow = sheet.getRow(i);
                int xssfCellNum = firstRow.getPhysicalNumberOfCells();
                for(int z = 0; z < xssfCellNum; z++){
                    XSSFCell xssfCell = xssfRow.getCell(z);
                    switch(z){
                        case 0:
                            goods.setName((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true));
                            break;
                        /*case 1:
                            goods.setCategoryId(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));
                            break;
                        case 2:
                            goods.setBrandId(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));
                            break;
                        case 3:
                            goods.setSpecList((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true));
                            break;*/
                        case 4:
                            goods.setDetail((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, false));
                            break;
                        case 5:
                            goods.setPrice(BigDecimal.valueOf((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true)));
                            break;
                        case 6:
                            goods.setSalePrice(BigDecimal.valueOf((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true)));
                            break;
                        case 7:
                            goods.setStock(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));
                            break;
                        case 8:
                            goods.setIsHot(((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true)).equals("是") ? true : false);
                            break;
                        case 9:
                            goods.setIsNew(((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true)).equals("是") ? true : false);
                            break;
                        case 10:
                            goods.setStatus(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));
                            break;
                    }
                }
                list.add(goods);
            }

            // 关闭资源
            workbook.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取单元格的值
     * PS: Excel单元格在填写时前后空格会被自动忽略掉
     * @param xssfCell
     * @param cellType 单元格数据类型
     * @param required 是否必填
     * @return
     */
    public Object getValue(XSSFCell xssfCell, int cellType, Boolean required){
        Object result = null;

        if(xssfCell.getCellType() == Cell.CELL_TYPE_BLANK){
            if(required){
                String message = (xssfCell.getRowIndex()+1) + "行" + (xssfCell.getColumnIndex()+1) + "列必须填写";
                throw new RuntimeException(message);
            }
        }else if(xssfCell.getCellType() != cellType){
            String message = (xssfCell.getRowIndex()+1) + "行" + (xssfCell.getColumnIndex()+1) + "列数据类型错误";
            throw new RuntimeException(message);
        }

        switch(xssfCell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC:
                result = xssfCell.getNumericCellValue();
                break;
            case Cell.CELL_TYPE_STRING:
                result = xssfCell.getStringCellValue().trim();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = xssfCell.getBooleanCellValue();
                break;
        }
        return result;
    }

    /**
     * 解析商品导入Excel文件 加强/优化版
     * PS: 1、优化完之后发现这个版本的比之前switch写的更好看，代码灵活度、可阅读性增强许多；
     *     2、只要Excel中有我模板所需要的列即可，无论他们的顺序如何，或者新增了某些列都没有关系，我只会解析固定的模板列；
     *     3、只用list，不用map的思路行不通，因为需要判断模板列都存在，你用list.indexOf()判断，然后计数，必须要考虑重复计数的问题，
     *        这样子写起来很麻烦，所以用map这种方式挺好的；
     * @param inputStream
     * @return
     */
    @Override
    public List<PointsMallGoods> parseExcel_plus(InputStream inputStream) {
        List<PointsMallGoods> list = new ArrayList<PointsMallGoods>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 如果模板啥也没有，lastRowNum也是为0
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum == 0){
                throw new RuntimeException("模板为空");
            }

            // 检测模板列数是否正确
            XSSFRow firstRow = sheet.getRow(0);
            int cellNum = firstRow.getPhysicalNumberOfCells();

            // 将列头信息存入集合
            Map<String, Integer> map = new HashMap<>();
            for(int i = 0; i < cellNum; i++){
                XSSFCell cell = firstRow.getCell(i);
                String cellValue = cell.getStringCellValue().trim();
                map.put(cellValue, i);
            }

            // 初始化列头信息
            List<String> columnList = new ArrayList<>();
            columnList.add("商品名称");
            columnList.add("分类id");
            columnList.add("品牌id");
            columnList.add("商品规格");
            columnList.add("商品详情");
            columnList.add("一口价");
            columnList.add("折扣价");
            columnList.add("库存");
            columnList.add("是否热门");
            columnList.add("是否新品");
            columnList.add("状态");

            if(!map.keySet().containsAll(columnList)){
                throw new RuntimeException("模板格式错误，格式为：\t商品名称\t分类id\t品牌id\t商品规格\t商品详情\t一口价\t折扣价\t库存\t是否热门\t是否新品\t状态");
            }

            // 解析Excel中的内容
            for(int i = 1; i <= lastRowNum; i++){
                PointsMallGoods goods = new PointsMallGoods();
                XSSFRow xssfRow = sheet.getRow(i);
                XSSFCell xssfCell = null;

                xssfCell = xssfRow.getCell(map.get("商品名称"));
                goods.setName((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true));

                /*xssfCell = xssfRow.getCell(map.get("分类id"));
                goods.setCategoryId(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));

                xssfCell = xssfRow.getCell(map.get("品牌id"));
                goods.setBrandId(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));

                xssfCell = xssfRow.getCell(map.get("商品规格"));
                goods.setSpecList((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true));*/

                xssfCell = xssfRow.getCell(map.get("商品详情"));
                goods.setDetail((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, false));

                xssfCell = xssfRow.getCell(map.get("一口价"));
                goods.setPrice(BigDecimal.valueOf((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true)));

                xssfCell = xssfRow.getCell(map.get("折扣价"));
                goods.setSalePrice(BigDecimal.valueOf((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true)));

                xssfCell = xssfRow.getCell(map.get("库存"));
                goods.setStock(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));

                xssfCell = xssfRow.getCell(map.get("是否热门"));
                goods.setIsHot(((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true)).equals("是") ? true : false);

                xssfCell = xssfRow.getCell(map.get("是否新品"));
                goods.setIsNew(((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true)).equals("是") ? true : false);

                xssfCell = xssfRow.getCell(map.get("状态"));
                goods.setStatus(((int) ((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true))));

                list.add(goods);
            }

            // 关闭资源
            workbook.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public XSSFWorkbook createExcel(List<PointsMallGoods> list) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();

        // 初始化列头信息
        List<String> columnList = new ArrayList<>();
        columnList.add("主键id");
        columnList.add("商品名称");
        columnList.add("分类id");
        columnList.add("分类名称");
        columnList.add("品牌id");
        columnList.add("品牌名称");
        columnList.add("商品主图");
        columnList.add("商品子图");
        columnList.add("商品规格 JSON格式");
        columnList.add("商品详情 JSON格式");
        columnList.add("详情图片");
        columnList.add("一口价");
        columnList.add("折扣价");
        columnList.add("月销量");
        columnList.add("累计销量");
        columnList.add("累计评价");
        columnList.add("库存");
        columnList.add("是否热门");
        columnList.add("是否新品");
        columnList.add("状态 1=上架 0=下架 -1=删除");
        columnList.add("创建时间");
        columnList.add("修改时间");

        // 写入排头数据
        XSSFRow firstRow = sheet.createRow(0);
        for(int i = 0; i < columnList.size(); i++){
            XSSFCell cell = firstRow.createCell(i);
            cell.setCellValue(columnList.get(i));
        }

        // 写入商品数据 (需要考虑数据库中的枚举状态值是否要以中文展示)
        for(int i = 0; i < list.size(); i++){
            PointsMallGoods goods = list.get(i);
            XSSFRow row = sheet.createRow(i+1);
            row.createCell(columnList.indexOf("主键id")).setCellValue(goods.getId());
            row.createCell(columnList.indexOf("商品名称")).setCellValue(goods.getName());
            /*row.createCell(columnList.indexOf("分类id")).setCellValue(goods.getCategoryId());
            row.createCell(columnList.indexOf("分类名称")).setCellValue(goods.getCategoryName());
            row.createCell(columnList.indexOf("品牌id")).setCellValue(goods.getBrandId());
            row.createCell(columnList.indexOf("品牌名称")).setCellValue(goods.getBrandName());*/
            row.createCell(columnList.indexOf("商品主图")).setCellValue(goods.getMainImage());
            row.createCell(columnList.indexOf("商品子图")).setCellValue(goods.getSubImages());
            /*row.createCell(columnList.indexOf("商品规格 JSON格式")).setCellValue(goods.getSpecList());*/
            row.createCell(columnList.indexOf("商品详情 JSON格式")).setCellValue(goods.getDetail());
            row.createCell(columnList.indexOf("详情图片")).setCellValue(goods.getDetailImages());
            row.createCell(columnList.indexOf("一口价")).setCellValue(goods.getPrice().doubleValue());
            /*row.createCell(columnList.indexOf("折扣价")).setCellValue(goods.getSalePrice().doubleValue());*/
            row.createCell(columnList.indexOf("月销量")).setCellValue(goods.getMonthlySales());
            row.createCell(columnList.indexOf("累计销量")).setCellValue(goods.getTotalSales());
            row.createCell(columnList.indexOf("累计评价")).setCellValue(goods.getTotalComments());
            row.createCell(columnList.indexOf("库存")).setCellValue(goods.getStock());
            row.createCell(columnList.indexOf("是否热门")).setCellValue(goods.getIsHot()==true ? "是" : "否");
            row.createCell(columnList.indexOf("是否新品")).setCellValue(goods.getIsNew()==true ? "是" : "否");
            row.createCell(columnList.indexOf("状态 1=上架 0=下架 -1=删除")).setCellValue(getIdeaStatus(goods.getStatus()));

            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy年m月d日"));

            XSSFCell cell = row.createCell(columnList.indexOf("创建时间"));
            cell.setCellValue(goods.getCreateTime());
            cell.setCellStyle(cellStyle);

            XSSFCell cell02 = row.createCell(columnList.indexOf("修改时间"));
            cell02.setCellValue(goods.getUpdateTime());
            cell02.setCellStyle(cellStyle);
        }
        return workbook;
    }

    @Override
    public void updateStatus(List<Integer> ids, Integer status) {
        for (Integer id : ids) {
            PointsMallGoods goods=goodsMapper.selectById(id);
            if (goods == null) {
                throw new StoneCustomerException("修改失败,商品未找到");
            }
            goods.setStatus(status);
            goodsMapper.updateByPrimaryKeySelective(goods);
        }
    }

    @Override
    public List<PointsMallGoods> getListByTotalSalesTop3() {
        return goodsMapper.getListByTotalSalesTop3();
    }

    @Override
    public List<Map<String, Object>> getListByLatelyMonthlySalesTop3(Date startTime, Date endTime) {
        return goodsMapper.getListByLatelyMonthlySalesTopNumber(startTime, endTime, Quantity.INT_3);
    }

    @Override
    public List<Map<String, Object>> getListByLatelyMonthlySalesTopNumber(Date startTime, Date endTime, Integer topNumber) {
        return goodsMapper.getListByLatelyMonthlySalesTopNumber(startTime, endTime, topNumber);
    }

    @Override
    public void updateSales(Integer goodsId, Integer num) {
        //1、修改商品月销量
        goodsMapper.updateMonthlySales(goodsId, num);

        //2、修改商品总销量
        goodsMapper.updateTotalSales(goodsId, num);

    }

    @Override
    public void monthlySalesReset() {
        //所有商品月销量清零
        goodsMapper.monthlySalesReset();
    }

    @Override
    public List<PointsMallGoods> getListByLastestShelvesTop1() {
        return goodsMapper.getListByLastestShelvesTop1();
    }

    public String getIdeaStatus(int status){
        String result = null;
        switch (status){
            case 1:
                result = "上架";
                break;
            case 0:
                result = "下架";
                break;
            case -1:
                result = "删除";
                break;
        }
        return result;
    }

    @Override
    public Page<Map<String, Object>> recommendGoodsList(int pageNo, int pageSize, PointsMallGoods goods) {
        //计算结束时间
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        Date endTime = endCalendar.getTime();
        //计算开始时间
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtilsPlus.subtractDays(endTime, 30));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date startTime = startCalendar.getTime();

        Page<Map<String, Object>> page = goodsMapper.recommendGoodsList(new Page(pageNo, pageSize), goods, startTime, endTime);
        return page;
    }

    @Override
    public long selectLatelyMonthlySalesById(Integer id) {
        //计算结束时间
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        endCalendar.set(Calendar.MILLISECOND, 999);
        Date endTime = endCalendar.getTime();
        //计算开始时间
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(DateUtilsPlus.subtractDays(endTime, 30));
        startCalendar.set(Calendar.HOUR_OF_DAY, 0);
        startCalendar.set(Calendar.MINUTE, 0);
        startCalendar.set(Calendar.SECOND, 0);
        startCalendar.set(Calendar.MILLISECOND, 0);
        Date startTime = startCalendar.getTime();

        return goodsMapper.selectLatelyMonthlySalesById(id, startTime, endTime);
    }
}