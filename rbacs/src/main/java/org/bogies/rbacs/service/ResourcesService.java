package org.bogies.rbacs.service;

import java.util.List;

import org.bogies.common.entity.Result;
import org.bogies.rbacs.model.ResourcesModel;
import org.bogies.rbacs.model.RoleModel;

import com.github.pagehelper.PageInfo;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月13日上午9:24:10
 */
public interface ResourcesService {
	
	Result insertResources(List<ResourcesModel> insertLi);
	/**删除 resources table 资源记录  
	 * @param id
	 * @return int
	 */
	Result deleteById(String id);
	/**模糊查询 Resources 记录 
	 * @param page
	 * @param pageSize
	 * @return PageInfo<ResourcesModel>
	 */
	public PageInfo<ResourcesModel> getResources(int page, int pageSize,ResourcesModel rm);
	/**更新 resources table 记录 
	 * @param res
	 * @return int
	 */
	int updateResources(String id, String summary, String description);
	/**
	 * 检查用户对资源的权限
	 * @param userId
	 * @param resId
	 * @return true 可访问; false 不可访问
	 */
	public boolean checkPermitByUserId(String userId, String resId);
	/**
	 * 检查角色对资源的权限
	 * @param roleId
	 * @param resId
	 * @return
	 */
	public boolean checkPermitByRoleId(String roleId, String resId);
	/**
	 * 拥有资源的角色列表
	 * @param resId
	 * @return：分页的角色列表
	 */
	public PageInfo<RoleModel> getResourceRoles(int page, int pageSize, String resId);
	/**TODO Filter Swagger Response Data
	 * @param ztreeData
	 * @param serverId
	 * @return Result
	 */
	List<ResourcesModel> extractSwaggerApiDocs(String httpRes, String serverId);
}
