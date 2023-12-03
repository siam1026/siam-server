package com.siam.package_common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

public class JsonUtils {
    private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper JSON = new ObjectMapper();

    public JsonUtils() {
    }

    public static ObjectMapper getJSON() {
        return JSON;
    }

    public static String toJson(Object obj) {
        try {
            return obj == null ? null : JSON.writeValueAsString(obj);
        } catch (JsonProcessingException var2) {
            logger.error(var2.getMessage());
            var2.printStackTrace();
            return null;
        }
    }

    public static Map toMap(String str) {
        try {
            return str == null ? null : (Map)JSON.readValue(str, Map.class);
        } catch (IOException var2) {
            logger.error(var2.getMessage());
            var2.printStackTrace();
            return null;
        }
    }

    public static Object toObject(String str, Class clas) {
        try {
            return str == null ? null : JSON.readValue(str, clas);
        } catch (IOException var3) {
            logger.error(var3.getMessage());
            var3.printStackTrace();
            return null;
        }
    }
}
