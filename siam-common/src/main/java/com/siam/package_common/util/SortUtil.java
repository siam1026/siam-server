package com.siam.package_common.util;

import java.util.*;

/**
 * @author Davis
 * @Date 2023-03-10 8:42 下午
 */
public class SortUtil {

    private static List<Map<String,Object>> getList(int count){
        List<Map<String,Object>> lists = new ArrayList<>();
        for(int i=0;i<count;i++){
            Map<String, Object> map = new HashMap<>();
            map.put("age", 10 + i);
            map.put("score", 10D + (double)i);
            if(i%2 == 0){
                map.put("age", 100 + i);
                map.put("score", 100D + (double)i);
            }
            lists.add(map);
        }
        return lists;
    }

    /**
     * 对List<Map<String,Object>>中int类型字段进行排序
     * @param lists
     * @param sort 排序，asc/desc
     * @param key 排序字段
     * @return
     */
    public static List<Map<String,Object>> sortIntMap(List<Map<String, Object>> lists, String sort, String key){
        if(sort.equals("asc")){
            lists.sort(Comparator.comparingInt(o -> Integer.parseInt(o.get(key).toString())));
        } else {
            Collections.sort(lists, (o1, o2) -> Integer.parseInt(o2.get(key).toString()) - Integer.parseInt(o1.get(key).toString()));
        }
        return lists;
    }

    /**
     * 对List<Map<String,Object>>中double类型字段进行排序
     * @param lists
     * @param sort 排序，asc/desc
     * @param key 排序字段
     * @return
     */
    public static List<Map<String,Object>> sortDoubleMap(List<Map<String, Object>> lists, String sort, String key){
        if(sort.equals("asc")){
            lists.sort(Comparator.comparingDouble(o -> Double.parseDouble(o.get(key).toString())));
        } else {
            Collections.sort(lists, (o1, o2) -> {
                Double d1 = Double.parseDouble(o2.get(key).toString());
                Double d2 = Double.parseDouble(o1.get(key).toString());
                if(d1 > d2){
                    return 1;
                }
                return -1;
            });
        }
        return lists;
    }

    public static void main(String[] args) {
        List<Map<String,Object>> lists = getList(6);

        String sort = "desc";
        String key = "score";
        System.out.println("------------ 排序前 ------------");
        print(lists, key);

        //sortIntMap(lists, sort, key);
        sortDoubleMap(lists, sort, key);

        System.out.println("------------ 排序后 ------------");
        print(lists, key);
    }

    private static void print(List<Map<String,Object>> lists, String key){
        for(int i=0;i<lists.size();i++){
            System.out.println(key + " = " + lists.get(i).get("score"));
        }
    }
}