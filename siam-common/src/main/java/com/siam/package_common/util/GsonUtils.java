package com.siam.package_common.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gson工具类
 **/
public class GsonUtils {

    private GsonUtils(){}

    /*private static Gson gson = new Gson();*/
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static <T> T toBean(String json, Class<T> clz){
        return gson.fromJson(json, clz);
    }

    public static Map<String, Object> toMap(String json){
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String,Object>>(){}.getType());
        return map;
    }

    public static <T> Map<String, T> toMap(String json, Class<T> clz){
        Map<String, JsonObject> map = gson.fromJson(json, new TypeToken<Map<String,JsonObject>>(){}.getType());
        Map<String, T> result = new HashMap<>();
        for(String key:map.keySet()){
            result.put(key,gson.fromJson(map.get(key),clz) );
        }
        return result;
    }

    public static <T> List<T> toList(String json, Class<T> clz){
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        List<T> list  = new ArrayList<>();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, clz));
        }
        return list;
    }
}