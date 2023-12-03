//package com.siam.system.modular.package_goods;
//
//import com.siam.system.modular.package_goods.entity.*;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.lang.reflect.Field;
//
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@EnableConfigurationProperties
//public class GenerateShowDocTest {
//
//    /**
//     * 自动生成mapper.xml中的if判断标签
//     */
//    @Test
//    public void generateMapperXml(){
//        System.out.println("############## START showDoc标签打印 #############\n");
//        Field[] declaredFields = com.siam.system.modular.package_goods.entity.PointsMallOrder.class.getDeclaredFields();
//        for (Field field : declaredFields) {
//            String ifLabel = null;
//            ifLabel = "|"+ field.getName() +" | "+ field.getType().getSimpleName() +"|主键id  |";
//
//            if(ifLabel != null){
//                System.out.println(ifLabel);
//            }
//        }
//        System.out.println("\n############## END xml标签打印 #############");
//    }
//
//    /** 驼峰转下划线 */
//    String getDbFieldName(String fieldName){
//        return fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
//    }
//}