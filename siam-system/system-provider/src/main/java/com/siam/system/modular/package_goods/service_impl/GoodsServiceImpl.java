package com.siam.system.modular.package_goods.service_impl;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.OSSUtils;
import com.siam.system.modular.package_goods.entity.*;
import com.siam.system.modular.package_goods.entity.internal.Printer;
import com.siam.system.modular.package_goods.mapper.*;
import com.siam.system.modular.package_goods.mapper.internal.PrinterMapper;
import com.siam.system.modular.package_goods.model.dto.GoodsMenuDto;
import com.siam.system.modular.package_goods.model.example.GoodsExample;
import com.siam.system.modular.package_goods.model.example.PictureUploadRecordExample;
import com.siam.system.modular.package_goods.service.*;
import com.siam.system.modular.package_goods.entity.Goods;
import com.siam.system.modular.package_goods.mapper.GoodsMapper;
import com.siam.system.modular.package_goods.service.GoodsService;
import com.siam.system.modular.package_goods.mapper.RawmaterialMapper;
import com.siam.system.modular.package_user.auth.cache.MerchantSessionManager;
import com.siam.system.modular.package_user.entity.Merchant;
import com.siam.system.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RawmaterialMapper rawmaterialMapper;

    @Autowired
    private MerchantSessionManager merchantSessionManager;

    @Autowired
    private MenuGoodsRelationService menuGoodsRelationService;

    @Autowired
    private GoodsSpecificationService goodsSpecificationService;

    @Autowired
    private GoodsSpecificationOptionService goodsSpecificationOptionService;

    @Autowired
    private PictureUploadRecordService pictureUploadRecordService;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private PrinterMapper printerMapper;

    @Autowired
    private OSSUtils ossUtils;

    @Override
    public int countByExample(GoodsExample example) {
        return goodsMapper.countByExample(example);
    }

    public void insert(Goods goods){
        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        //商品的主图 等于 商品轮播图的第一张图
        if(StringUtils.isNotBlank(goods.getSubImages())){
            goods.setMainImage(goods.getSubImages().split(",")[0]);
        }

        //添加商品记录
        //设置默认库存为0
        goods.setShopId(loginMerchant.getShopId());
        goods.setStock(Quantity.INT_0);
        goods.setMonthlySales(Quantity.INT_0);
        goods.setTotalSales(Quantity.INT_0);
        goods.setTotalComments(Quantity.INT_0);
        goods.setCreateTime(new Date());
        goods.setUpdateTime(new Date());
        //兑换商品所需积分数量新增时默认等于折扣价
        goods.setExchangePoints(goods.getPrice().intValue());
        goodsMapper.insert(goods);

        //建立商品与类别的关系
        MenuGoodsRelation insertMenuGoodsRelation = new MenuGoodsRelation();
        insertMenuGoodsRelation.setGoodsId(goods.getId());
        insertMenuGoodsRelation.setMenuId(goods.getMenuId());
        insertMenuGoodsRelation.setCreateTime(new Date());
        insertMenuGoodsRelation.setUpdateTime(new Date());
        menuGoodsRelationService.insertSelective(insertMenuGoodsRelation);

        //生成商品公共规格
        goodsSpecificationService.insertPublicGoodsSpecification(goods.getId());

        //TODO(MARK)-目前只能通过图片地址来判别重复，后续可以优化成那个啥位数来判定唯一
        //添加图片上传记录
        if(StringUtils.isNotBlank(goods.getSubImages())){
            String[] array = goods.getSubImages().split(",");
            for (String str : array) {
                PictureUploadRecordExample uploadRecordExample = new PictureUploadRecordExample();
                uploadRecordExample.createCriteria().andUrlEqualTo(str);
                int count = pictureUploadRecordService.countByExample(uploadRecordExample);
                if(count == 0){
                    PictureUploadRecord uploadRecord = new PictureUploadRecord();
                    uploadRecord.setShopId(loginMerchant.getShopId());
                    uploadRecord.setUrl(str);
                    uploadRecord.setModule(Quantity.INT_1);
                    uploadRecord.setCreateTime(new Date());
                    uploadRecord.setUpdateTime(new Date());
                    pictureUploadRecordService.insertSelective(uploadRecord);
                }
            }
        }
    }

    public List<Goods> selectByExample(GoodsExample example){
        return goodsMapper.selectByExample(example);
    }

    @Override
    public Page<Goods> getListByPage(int pageNo, int pageSize, Goods goods) {
        Page<Goods> page = goodsMapper.getListByPage(new Page(pageNo, pageSize), goods);
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinMenu(int pageNo, int pageSize, GoodsMenuDto goodsMenuDto) {
        log.debug("\n\n》》》 GoodsServiceImpl.getListByPageJoinMenu - goodsMenuDto = " + JSONObject.toJSONString(goodsMenuDto));
        log.debug("\n\n》》》 GoodsServiceImpl.getListByPageJoinMenu - goodsMapper = " + JSONObject.toJSONString(goodsMapper));
        Page<Map<String, Object>> page = goodsMapper.getListByPageJoinMenu(new Page(pageNo, pageSize), goodsMenuDto);
        for(Map<String, Object> map : page.getRecords()){
            Integer goodsId= (Integer) map.get("id");
            //根据商品id查询原料制作杯数
            List<Map<String, Object>> goodsStock = rawmaterialMapper.getGoodsStockById(goodsId);
            if(goodsStock.get(0) != null){
                map.put("stock", goodsStock.get(0).get("goodsStock"));//修改值
            }
        }
        return page;
    }

    @Override
    public Page<Map<String, Object>> getListByPageJoinMenuOrderByLatelyMonthlySales(int pageNo, int pageSize, GoodsMenuDto goodsMenuDto) {
        Page<Map<String, Object>> page = goodsMapper.getListByPageJoinMenuOrderByLatelyMonthlySales(new Page(pageNo, pageSize), goodsMenuDto);
        return page;
    }

    /*@Override
    public Page<Map<String, Object>> getListByPageJoinSpecification(int pageNo, int pageSize, GoodsSpecificationDto goodsSpecificationDto) {
        Page<Map<String, Object>> page = goodsMapper.getListByPageJoinSpecification(goodsSpecificationDto);
        return page;
    }*/

    @Override
    public void increaseStock(int id, int number) {
        Goods dbGoods = goodsMapper.selectById(id);
        //增加库存数量
        Goods updateGoods = new Goods();
        updateGoods.setId(dbGoods.getId());
        updateGoods.setStock(dbGoods.getStock() + number);
        goodsMapper.updateById(updateGoods);
    }

    @Override
    public void decreaseStock(int id, int number) {
        Goods dbGoods = goodsMapper.selectById(id);
        if(dbGoods.getStock() - number < 0){
            throw new StoneCustomerException(dbGoods.getName() + "库存不足，请减少该单品购买数量");
        }
        //减少库存数量
        Goods updateGoods = new Goods();
        updateGoods.setId(dbGoods.getId());
        updateGoods.setStock(dbGoods.getStock() - number);
        goodsMapper.updateById(updateGoods);
    }

    /**
     * 解析商品导入Excel文件
     * @param inputStream
     * @return
     */
    @Override
    public List<Goods> parseExcel(InputStream inputStream) {
        List<Goods> list = new ArrayList<Goods>();
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
                Goods goods = new Goods();
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
    public List<Goods> parseExcel_plus(InputStream inputStream) {
        //获取当前登录用户绑定的门店编号
        Merchant loginMerchant = merchantSessionManager.getSession(TokenUtil.getToken());

        List<Goods> list = new ArrayList<Goods>();
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
            columnList.add("商品类别");
            columnList.add("状态");
//            columnList.add("商品规格");
            columnList.add("一口价");
            columnList.add("包装费");
            columnList.add("商品描述");
            columnList.add("介绍图片");
            columnList.add("打印机编号");
            columnList.add("打印次数");

            if(!map.keySet().containsAll(columnList)){
                throw new RuntimeException("模板格式错误");
            }

            //获取工作表中的所有图片
            Map<String, XSSFPictureData> pictureMap = getPictures(sheet);

            // 解析Excel中的内容
            for(int i = 1; i <= lastRowNum; i++){
                if(i == 5){
                    return list;
                }
                Goods goods = new Goods();
                XSSFRow xssfRow = sheet.getRow(i);
                XSSFCell xssfCell = null;

                xssfCell = xssfRow.getCell(map.get("商品名称"));
                goods.setName((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true));

                xssfCell = xssfRow.getCell(map.get("商品类别"));
                goods.setMenuId(convertMenuId(xssfCell, loginMerchant));

//                xssfCell = xssfRow.getCell(map.get("商品规格"));
//                goods.setSpecList((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, true));

                xssfCell = xssfRow.getCell(map.get("状态"));
                goods.setStatus(convertStatus(xssfCell, loginMerchant));

                xssfCell = xssfRow.getCell(map.get("一口价"));
                goods.setPrice(BigDecimal.valueOf((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true)));

                xssfCell = xssfRow.getCell(map.get("包装费"));
                goods.setPackingCharges(BigDecimal.valueOf((double) getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, true)));

                xssfCell = xssfRow.getCell(map.get("商品描述"));
                goods.setDetail((String) getValue(xssfCell, Cell.CELL_TYPE_STRING, false));

                //处理主图和子图
                handleMainImage(loginMerchant, map, pictureMap, goods, xssfRow);

                xssfCell = xssfRow.getCell(map.get("打印机编号"));
                goods.setPrinterId(convertPrinterId(xssfCell, loginMerchant));

                xssfCell = xssfRow.getCell(map.get("打印次数"));
                goods.setPrintNum(Convert.toInt(getValue(xssfCell, Cell.CELL_TYPE_NUMERIC, false)));

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

    private void handleMainImage(Merchant loginMerchant, Map<String, Integer> map, Map<String, XSSFPictureData> pictureMap, Goods goods, XSSFRow xssfRow) {
        //                xssfCell = xssfRow.getCell(map.get("介绍图片"));
        String mapKey = xssfRow.getRowNum() + "-" + map.get("介绍图片");//指定行和列
        XSSFPictureData xssfPictureData= pictureMap.get(mapKey);
        byte[] data = xssfPictureData.getData();
        InputStream pictureInputStream = new ByteArrayInputStream(data);

        // 文件名处理
        String fileName = "siam_" + new Date().getTime() + ".jpg";
        // 根据模块名称、用户id作为文件夹命名
        String savePath = "data/images/merchant/" + loginMerchant.getId() + "/" + fileName;
        ossUtils.uploadImage(pictureInputStream, savePath);

        goods.setMainImage(savePath);
        goods.setSubImages(savePath); // 不设置的话修改商品页面回显会报错
    }

    /**
     * 获取Excel中的图片
     * @param xssfSheet
     * @return
     */
    public static Map<String, XSSFPictureData> getPictures(XSSFSheet xssfSheet){

        Map<String,XSSFPictureData> map=new HashMap<>();
        List<XSSFShape> list=xssfSheet.getDrawingPatriarch().getShapes();

        for (XSSFShape shape:list){

            XSSFPicture picture = (XSSFPicture) shape;
            XSSFClientAnchor xssfClientAnchor = (XSSFClientAnchor) picture.getAnchor();
            XSSFPictureData pdata = picture.getPictureData();
            // 行号-列号
            String key = xssfClientAnchor.getRow1() + "-" + xssfClientAnchor.getCol1();
            log.info("key数据:{}",key);
            map.put(key, pdata);

        }

        return map;
    }

    private Integer convertMenuId(XSSFCell xssfCell, Merchant loginMerchant) {
        String text = Convert.toStr(getValue(xssfCell, Cell.CELL_TYPE_STRING, true)).trim();
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Menu::getShopId, loginMerchant.getShopId());
        queryWrapper.eq(Menu::getName, text);
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        if(menus.isEmpty()){
            Menu menu = new Menu();
            menu.setName(text);
            menu.setShopId(loginMerchant.getShopId());
            menu.setCreateTime(new Date());
            menu.setUpdateTime(new Date());
            menuMapper.insert(menu);
            return menu.getId();
        }else{
            return menus.get(0).getId();
        }
    }

    private Integer convertStatus(XSSFCell xssfCell, Merchant loginMerchant) {
        String text = Convert.toStr(getValue(xssfCell, Cell.CELL_TYPE_STRING, true)).trim();
        if(text.equals("待上架")){
            return 1;
        }else if(text.equals("已上架")){
            return 2;
        }else if(text.equals("已下架")){
            return 3;
        }else if(text.equals("售罄")){
            return 4;
        }
        return null;
    }

    private String convertPrinterId(XSSFCell xssfCell, Merchant loginMerchant) {
        String result = "";
        String text = Convert.toStr(getValue(xssfCell, Cell.CELL_TYPE_STRING, true)).trim();
        String[] array = text.split("，");
        for (String str : array) {
            LambdaQueryWrapper<Printer> queryWrapper = new LambdaQueryWrapper();
            queryWrapper.eq(Printer::getShopId, loginMerchant.getShopId());
            queryWrapper.eq(Printer::getName, str);
            List<Printer> menus = printerMapper.selectList(queryWrapper);
            if(menus.isEmpty()){
                log.info("打印机编号错误，{text}", str);
                return null;
            }else{
                if(result.isEmpty()){
                    result += menus.get(0).getId();
                }else{
                    result += "," + menus.get(0).getId();
                }
            }
        }
        return result;
    }

    @Override
    public XSSFWorkbook createExcel(List<Goods> list) {
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
            Goods goods = list.get(i);
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
            Goods goods=goodsMapper.selectById(id);
            if (goods == null) {
                throw new StoneCustomerException("修改失败,商品未找到");
            }
            goods.setStatus(status);
            goodsMapper.updateById(goods);
        }
    }

    @Override
    public List<Goods> getListByTotalSalesTop3() {
        return goodsMapper.getListByTotalSalesTop3();
    }

    @Override
    public List<Map<String, Object>> getListByLatelyMonthlySalesTop3(Date startTime, Date endTime, Integer shopId) {
        return goodsMapper.getListByLatelyMonthlySalesTopNumber(startTime, endTime, Quantity.INT_3, shopId);
    }

    @Override
    public List<Map<String, Object>> getListByLatelyMonthlySalesTopNumber(Date startTime, Date endTime, Integer topNumber, Integer shopId) {
        return goodsMapper.getListByLatelyMonthlySalesTopNumber(startTime, endTime, topNumber, shopId);
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
    public List<Goods> getListByLastestShelvesTop1() {
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
}