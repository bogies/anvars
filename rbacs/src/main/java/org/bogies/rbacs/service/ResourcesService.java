package org.bogies.rbacs.service;

import java.util.List;
import org.bogies.rbacs.entity.ResourceEntity;
import org.bogies.rbacs.entity.RoleEntity;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 资源管理服务
 * @author: Jerry
 * @date: 2018年12月13日上午9:24:10
 */
public interface ResourcesService {
	/**
	 * 添加一个资源
	 * @param resInfo 资源信息
	 * @return 添加的数量
	 */
	int insert(ResourceEntity resInfo) throws RuntimeException;
	/** 
	 * 删除资源 
	 * @param id 要删除的资源id
	 * @return 删除的数量
	 */
	int delete(String id) throws RuntimeException;
	/**
	 * 获取或查询资源列表 
	 * @param page 页面
	 * @param pageSize 每页数量
	 * @return 资源分页列表
	 */
	public PageInfo<ResourceEntity> getResources(int page, int pageSize, ResourceEntity filter) throws RuntimeException;
	public PageInfo<ResourceEntity> getResourcesByUserId(int page, int pageSize, 
			String userId, ResourceEntity filter) throws RuntimeException;
	/**
	 * 根据资源ID, 更新资源
	 * @param resInfo 资源信息
	 * @return 更新的数量
	 */
	int update(ResourceEntity resInfo) throws RuntimeException;
	/**
	 * 检查用户是否对资源拥有权限
	 * @param userId 用户ID
	 * @param resId 资源ID
	 * @return true为是
	 */
	public boolean checkPermitByUserId(String userId, String resId) throws RuntimeException;
	/**
	 * 检查角色是否对资源拥有权限
	 * @param roleId 角色ID
	 * @param resId 资源ID
	 * @return true为是
	 */
	public boolean checkPermitByRoleId(String roleId, String resId) throws RuntimeException;
	/**
	 * 拥有资源的角色列表
	 * @param resId
	 * @return：分页的角色列表
	 */
	public PageInfo<RoleEntity> getResourceRoles(int page, int pageSize, String resId, String serviceName) throws RuntimeException;
	/**
	 * Filter Swagger Response Data
	 * @param ztreeData
	 * @param serverId
	 * @return Result
	 */
	List<ResourceEntity> extractSwaggerApiDocs(String httpRes, String serverId);
}
