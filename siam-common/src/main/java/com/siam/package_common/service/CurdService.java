package com.siam.package_common.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 通用CURD接口
 */
public interface CurdService<M, T> extends IService<M> {

	/**
	 * 保存操作
	 * @param param
	 * @return
	 */
	int insert(T param);

	/**
	 * 删除操作
	 * @param id
	 * @return
	 */
	int delete(Long id);

	/**
	 * 批量删除操作
	 * @param idList
	 * @return
	 */
	int batchDelete(List<Long> idList);

	/**
	 * 修改操作
	 * @param param
	 * @return
	 */
	int update(T param);

    /**
     * 分页查询
	 * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
	 * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
	 * 影响服务层以上的分页接口，起到了解耦的作用
	 * @param param 自定义，统一分页查询请求
	 * @return Page 自定义，统一分页查询结果
     */
	Page<M> page(T param);

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	M findById(Long id);
}