package com.siam.system.modular.package_goods.service_impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.package_common.constant.Quantity;
import com.siam.package_common.exception.StoneCustomerException;
import com.siam.package_common.util.JsonUtils;
import com.siam.package_common.util.RedisUtils;
import com.siam.system.modular.package_goods.entity.Menu;
import com.siam.system.modular.package_goods.mapper.MenuMapper;
import com.siam.system.modular.package_goods.service.MenuService;
import com.siam.system.modular.package_goods.entity.Menu;
import com.siam.system.modular.package_goods.mapper.MenuMapper;
import com.siam.system.modular.package_goods.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void deleteByPrimaryKey(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void insertSelective(Menu menu) {
        menuMapper.insertSelective(menu);
    }

    @Override
    public Menu selectByPrimaryKey(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateByPrimaryKeySelective(Menu menu) {
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public List<Map<String, Object>> getList(Menu menu) {
        if(menu.getShopId() == null){
            throw new StoneCustomerException("店铺id不能为空");
        }
        List<Map<String, Object>> list = menuMapper.getList(menu);
        return list;
    }

    @Override
    public List<Map<String, Object>> getListByRedis(Menu menu) throws InterruptedException {
        if(menu.getShopId() == null){
            throw new StoneCustomerException("店铺id不能为空");
        }

        //1、空结果缓存，解决缓存穿透
        //2、设置过期时间(加随机值)，解决缓存雪崩
        //3、加锁，解决缓存击穿

        List<Map<String, Object>> menuList;
        if(redisUtils.hasKey("menuList")){
            menuList = (List<Map<String, Object>>) JsonUtils.toObject(redisUtils.get("menuList").toString(), List.class);
            log.debug("\n\n》》》 getListByRedis - 从Redis获取，" + menuList);
        }else{
//            //本地锁，synchronized
//            menuList = readByDBWithSynchronized(menu);
//            //Redis分布式锁，底层操作
//            menuList = readByDBWithRedisLock(menu);
            //Redisson分布式锁
            menuList = readByDBWithRedission(menu);
        }
        return menuList;
    }

    /**
     * 本地锁，synchronized
     *
     * @author 暹罗
     */
    public List<Map<String, Object>> readByDBWithSynchronized(Menu menu) throws InterruptedException {
        synchronized (this){
            //从数据库中获取数据
            return readByDB(menu);
        }
    }

    /**
     * Redis分布式锁，底层操作
     *
     * @author 暹罗
     */
    public List<Map<String, Object>> readByDBWithRedisLock(Menu menu) throws InterruptedException {
        //TODO(MARK) - 【加锁的坑】如果没有释放锁，会导致死锁问题；所以为了防止程序异常导致解锁失败，需要设置锁的自动过期时间；
        //TODO(MARK) - 【删锁的坑】业务执行超时，当业务执行完后，自己的锁早已过期，此时删除锁删除的可能是别人的锁；所以为了防止这种情况，在占锁的时候，可以把值指定为uuid，每个人匹配是自己的锁才能删除；
        //TODO(MARK) - 【删锁的坑】获取值 + 比对成功进行删除 = 原子操作；使用lua脚本实现；
        //TODO(MARK) - 总结：保证加索【占位+过期时间】 和 删除锁【判断+删除】的原子性；更难的是锁的自动续期；
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("lock", uuid, 30, TimeUnit.SECONDS);
        if(lock){
            //加锁成功
            List<Map<String, Object>> list;
            try{
                //从数据库中获取数据
                list = readByDB(menu);
            } finally {
                //删除锁
                /*String lockValue = (String) redisUtils.get("lock");
                if(uuid.equals(lockValue)){
                    redisUtils.delete("lock");
                }*/
                String script = "if redis.call('get', KEYS[1]) == ARGV[1]  then  return redis.call('del', KEYS[1])  else  return 0 end";
                redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList("lock"), uuid);
            }
            return list;
        }else{
            //加锁失败，休眠100ms进行重试
            //自旋的方式
            Thread.sleep(200);
            return this.readByDBWithRedisLock(menu);
        }
    }

    /**
     * Redisson分布式锁
     *
     * @author 暹罗
     */
    public List<Map<String, Object>> readByDBWithRedission(Menu menu) throws InterruptedException {
        RLock lock = redissonClient.getLock("menuList-lock");
        lock.lock();

        List<Map<String, Object>> list;
        try{
            //从数据库中获取数据
            list = readByDB(menu);
        } finally {
            lock.unlock();
        }
        return list;
    }

    /**
     * 从数据库中获取数据
     *
     * @author 暹罗
     */
    public List<Map<String, Object>> readByDB(Menu menu) throws InterruptedException {
        List<Map<String, Object>> menuList;
        //锁释放后，再查一次缓存
        if(redisUtils.hasKey("menuList")){
            menuList = (List<Map<String, Object>>) JsonUtils.toObject(redisUtils.get("menuList").toString(), List.class);
            log.debug("\n\n》》》 getListByRedis - 从Redis获取，" + menuList);
        }else{
            //模拟业务处理耗时10秒
            Thread.sleep(10 * 1000);
            menuList = menuMapper.getList(menu);
            //存入redis
            redisUtils.set("menuList", JsonUtils.toJson(menuList), 1*24*60*60);
            log.debug("\n\n》》》 getListByRedis - 从DB获取，" + menuList);
        }
        return menuList;
    }

    @Override
    public List<Map<String, Object>> getListJoinGoods(Menu menu) {
        if(menu.getShopId() == null){
            throw new StoneCustomerException("店铺id不能为空");
        }

        Map<Integer, Map<String, Object>> filterMap = new LinkedHashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();

        //过滤数据
        List<Map<String, Object>> list = menuMapper.getListJoinGoods(menu);
        list.forEach(map -> {
            if(filterMap.containsKey(map.get("id"))){
                //只查询2=已上架 4=售罄的商品
                if(map.get("goodsId") != null && (Quantity.INT_2 == (int) map.get("goodsStatus") || Quantity.INT_4 == (int) map.get("goodsStatus"))){
                    //存入商品数据
                    Map<String, Object> goodsMap = new LinkedHashMap<>();
                    goodsMap.put("goodsId", map.get("goodsId"));
                    goodsMap.put("goodsName", map.get("goodsName"));
                    goodsMap.put("mainImage", map.get("mainImage"));
                    goodsMap.put("goodsPrice", map.get("goodsPrice"));
                    goodsMap.put("salePrice", map.get("salePrice"));
                    goodsMap.put("stock", map.get("stock"));
                    goodsMap.put("goodsStatus", map.get("goodsStatus"));
                    goodsMap.put("isSale", map.get("isSale"));
                    goodsMap.put("packingCharges", map.get("packingCharges"));
                    ((ArrayList) filterMap.get(map.get("id")).get("goodsList")).add(goodsMap);
                }
            }else{
                //存入菜单数据
                Map<String, Object> menuMap = new LinkedHashMap<>();
                menuMap.put("id", map.get("id"));
                menuMap.put("shopId", map.get("shopId"));
                menuMap.put("name", map.get("name"));
                menuMap.put("description", map.get("description"));
                menuMap.put("sortNumber", map.get("sortNumber"));
                menuMap.put("createTime", map.get("createTime"));
                menuMap.put("updateTime", map.get("updateTime"));
                menuMap.put("goodsList", new ArrayList<>());

                //只查询2=已上架 4=售罄的商品
                if(map.get("goodsId") != null && (Quantity.INT_2 == (int) map.get("goodsStatus") || Quantity.INT_4 == (int) map.get("goodsStatus"))){
                    //存入商品数据
                    Map<String, Object> goodsMap = new LinkedHashMap<>();
                    goodsMap.put("goodsId", map.get("goodsId"));
                    goodsMap.put("goodsName", map.get("goodsName"));
                    goodsMap.put("mainImage", map.get("mainImage"));
                    goodsMap.put("goodsPrice", map.get("goodsPrice"));
                    goodsMap.put("salePrice", map.get("salePrice"));
                    goodsMap.put("stock", map.get("stock"));
                    goodsMap.put("goodsStatus", map.get("goodsStatus"));
                    goodsMap.put("isSale", map.get("isSale"));
                    goodsMap.put("packingCharges", map.get("packingCharges"));
                    ((ArrayList) menuMap.get("goodsList")).add(goodsMap);
                }

                filterMap.put((Integer) map.get("id"), menuMap);
            }
        });

        //组装数据进行返回
        for (Integer key : filterMap.keySet()){
            resultList.add(filterMap.get(key));
        }

        return resultList;
    }

    @Override
    public Page<Menu> getListByAdmin(Menu menu) {
        if(menu.getShopId() == null){
            throw new StoneCustomerException("店铺id不能为空");
        }
        Page<Menu> list = menuMapper.getListByAdmin(new Page(menu.getPageNo(), menu.getPageSize()), menu);
        return list;
    }
}