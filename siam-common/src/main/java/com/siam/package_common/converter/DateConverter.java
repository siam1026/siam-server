package com.siam.package_common.converter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 表单形式的全局时间类型转换器(表单传参方式)【自身服务接口访问无效，feign客户端调用生效】
 */
@Slf4j
@Component
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String str) {
        log.debug("\n\n》》》 DateConverter.convert - 进入全局日期转换器方法");
        log.debug("\n\n》》》 DateConverter.convert - str = " + JSONObject.toJSONString(str));
        //处理空字符串场景
        if(StringUtils.isBlank(str)){
            return null;
        }
        str = str.replaceAll("/", "-");
        String[] formatArray = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS"};
        for (String formatStr : formatArray) {
            SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
            try {
                return sdf.parse(formatStr);
            } catch (ParseException e) {
                continue;
            }
        }
        log.debug("\n\n》》》 DateConverter.convert - 全局日期转换器 - 转换失败");
        return null;
    }
}