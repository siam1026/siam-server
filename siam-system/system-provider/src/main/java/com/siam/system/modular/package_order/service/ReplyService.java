package com.siam.system.modular.package_order.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.siam.system.modular.package_order.entity.Reply;

import java.util.Map;

public interface ReplyService {

    /**
     * 创建
     * @param reply
     */
    void insertSelective(Reply reply);

    /**
     * 删除
     * @param id
     */
    void deleteByPrimaryKey(Integer id);

    /**
     * 回复列表
     * @param pageNo
     * @param pageSize
     * @param reply
     * @return
     */
    Page<Reply> getListByPage(int pageNo, int pageSize, Reply reply);

    /**
     * 查询某个评价下的回复内容列表
     */
    Page<Map<String, Object>> getMapListByPage(int pageNo, int pageSize, Reply reply);
}