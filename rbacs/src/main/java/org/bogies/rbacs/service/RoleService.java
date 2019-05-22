package org.bogies.rbacs.service;

import java.util.List;

import org.bogies.rbacs.entity.RoleEntity;

import com.github.pagehelper.PageInfo;

/**
 * @Description: 角色管理
 * @author: Jerry
 * @date: 2018年12月13日上午9:24:16
 */
public interface RoleService {
	/**
	 * 初始化保留角色
	 */
	public void initReservedRoles();
	/**
	 * 获取指定角色信息
	 * @param id
	 * @return：RoleModel
	 */
	public RoleEntity getById(String id);
	/**
	 * 获取角色列表
	 * @param roleFilter
	 * @param page
	 * @param pageSize
	 * @return：PageInfo
	 */
	public PageInfo<?> getRoles(RoleEntity roleFilter, int page, int pageSize);
	/**
	 * 获取指定用户所属的角色列表
	 * @param userId
	 * @param serviceName
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageInfo<?> getRolesByUserId(String userId, String serviceName, int page, int pageSize) throws RuntimeException;
	/**
	 * 新增角色
	 * @param role
	 * @return：int
	 */
	public int add(RoleEntity role);
	/**
	 * 修改角色
	 * @param role
	 * @return：int
	 */
	public int update(RoleEntity role);
	/**
	 * 删除角色
	 * @param id
	 * @return：int
	 */
	public int delete(String id);
	/**
	 * 获取角色的用户列表
	 * @param roleId
	 * @param resIds
	 * @return：int
	 */
	public List<String> getMemberIds(String roleId);
	/**
	 * 获取角色的资源列表
	 * @param roleId
	 * @param resIds
	 * @return：int
	 */
	public PageInfo<?> getRes(String roleId, int page, int pageSize);
	/**
	 * 获取角色未授权的资源列表
	 * @param roleId
	 * @param resIds
	 * @return：int
	 */
	public PageInfo<?> getUnauthRes(String roleId, int page, int pageSize);
	/**
	 * 添加资源到指定角色
	 * @param roleId 角色id
	 * @param resIds 资源id列表
	 * @return 影响的数量
	 */
	public int addResource(String roleId, String[] resIds, String serviceName);
	/**
	 * 删除资源从指定角色
	 * @param roleId 角色id
	 * @param resIds 资源id列表
	 * @return 影响的数量
	 */
	public int removeResource(String roleId, String resIds);
	/**
	 * 添加用户到指定角色
	 * @param roleId 角色id
	 * @param userIds 用户id列表
	 * @return 影响的数量
	 */
	public int addUser(String roleId, String[] userIds, String serviceName);
	/**
	 * 删除用户到指定角色
	 * @param roleId 角色id
	 * @param userIds 用户id列表
	 * @return 影响的数量
	 */
	public int removeUser(String roleId, String userIds);
}
