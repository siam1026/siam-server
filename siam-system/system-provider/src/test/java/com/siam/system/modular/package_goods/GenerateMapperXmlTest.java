package com.siam.system.modular.package_goods;

import com.siam.system.modular.package_user.entity.MerchantWithdrawRecord;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.math.BigDecimal;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@EnableConfigurationProperties
public class GenerateMapperXmlTest {

    /**
     * 自动生成mapper.xml中的if判断标签
     */
    @Test
    public void generateMapperXml(){
        System.out.println("############## START xml标签打印 #############\n");
        Field[] declaredFields = MerchantWithdrawRecord.class.getDeclaredFields();
        for (Field field : declaredFields) {
            String dbFieldName = getDbFieldName(field.getName());
            String ifLabel = null;
            if (field.getType() == String.class){
                ifLabel =
                        "<if test=\"paramCondition."+ field.getName() +" != null and paramCondition."+ field.getName() +" != ''\">"+
                                " and "+ dbFieldName +" like CONCAT('%',#{paramCondition."+ field.getName() +"},'%') "+
                                "</if>";
            }else if (field.getType()==Integer.class || field.getType()==Long.class || field.getType()== BigDecimal.class){
                ifLabel =
                        "<if test=\"paramCondition."+ field.getName() +" != null\">"+
                                " and "+ dbFieldName +" = #{paramCondition."+ field.getName() +"} "+
                                "</if>";
            }
            if(ifLabel != null){
                System.out.println(ifLabel);
            }
        }
        System.out.println("\n############## END xml标签打印 #############");
    }

    /** 驼峰转下划线 */
    String getDbFieldName(String fieldName){
        return fieldName.replaceAll("[A-Z]", "_$0").toLowerCase();
    }
}