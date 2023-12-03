package com.siam.system.modular.package_goods;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * excel报表导出测试
 *
 * @return
 * @author 暹罗
 */
public class ExcelReportExportTest {

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\project_report.xlsx");
        if(file.exists()){
            parseExcel(new FileInputStream(file));
            exportExcel(new FileInputStream(file));
        }
    }

    public static void parseExcel(InputStream inputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 如果模板啥也没有，lastRowNum也是为0
            int lastRowNum = sheet.getLastRowNum();
            if(lastRowNum == 0){
                throw new RuntimeException("模板为空");
            }

            // 解析Excel中的内容
            for (Row row : sheet) {
                for (Cell cell : row) {
                    if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        if (cell.getRichStringCellValue().getString().equals("${product_type}")){
                            int rowNum = row.getRowNum();
                            int cellNum = cell.getColumnIndex();
                            System.out.println("find ${product_type} in rowNum-" + rowNum + ", cellNum-" + cellNum);
                        } else if (cell.getRichStringCellValue().getString().equals("${stage_name}")){
                            int rowNum = row.getRowNum();
                            int cellNum = cell.getColumnIndex();
                            System.out.println("find ${stage_name} in rowNum-" + rowNum + ", cellNum-" + cellNum);
                        }
                    }
                }
            }

            // 关闭资源
            workbook.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取单元格的值
     * PS: Excel单元格在填写时前后空格会被自动忽略掉
     * @param xssfCell
     * @param cellType 单元格数据类型
     * @param required 是否必填
     * @return
     */
    public static Object getValue(XSSFCell xssfCell, int cellType, Boolean required){
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



    public static void exportExcel(InputStream inputStream) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\export_demo.xlsx");

        // 将Excel数据写入输出流
        workbook.write(fileOutputStream);

        // 关闭资源
        fileOutputStream.flush();
        fileOutputStream.close();
        workbook.close();
    }
}