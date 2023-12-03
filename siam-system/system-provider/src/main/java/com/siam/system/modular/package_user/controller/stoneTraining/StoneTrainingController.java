package com.siam.system.modular.package_user.controller.stoneTraining;

import com.siam.package_common.entity.BasicResult;
import com.siam.package_common.util.JsonUtils;
import com.siam.package_common.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * STONE·训练营相关接口
 *
 * @author 暹罗
 */
@Slf4j
@RestController
@RequestMapping(value = "/stoneTraining")
@Transactional(rollbackFor = Exception.class)
public class StoneTrainingController {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户注册
     *
     * @author 暹罗
     */
    @PostMapping(value = "/register")
    public BasicResult login(@RequestBody @Validated(value = {}) StoneTrainingParam param) {
        String key = "stoneTraining:userList";
        ArrayList<StoneTrainingParam> userList;
        if(redisUtils.hasKey(key)){
            userList = (ArrayList) JsonUtils.toObject(redisUtils.get("stoneTraining:userList").toString(), ArrayList.class);
            userList.add(param);
            redisUtils.set(key, JsonUtils.toJson(userList));
        }else{
            userList = new ArrayList();
        }
        userList.add(param);
        redisUtils.set(key, JsonUtils.toJson(userList));
        return BasicResult.success();
    }
}