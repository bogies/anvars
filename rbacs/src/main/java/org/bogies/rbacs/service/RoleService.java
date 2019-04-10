package org.bogies.rbacs.service;

import org.bogies.rbacs.model.RoleModel;
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
	public RoleModel getById(String id);
	/**
	 * 获取角色列表
	 * @param roleFilter
	 * @param page
	 * @param pageSize
	 * @return：PageInfo
	 */
	public PageInfo<?> getRoles(RoleModel roleFilter, int page, int pageSize);
	/**
	 * 新增角色
	 * @param role
	 * @return：int
	 */
	public int add(RoleModel role);
	/**
	 * 修改角色
	 * @param role
	 * @return：int
	 */
	public int update(RoleModel role);
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
	public PageInfo<?> getMembers(String roleId, int page, int pageSize);
	/**
	 * 获取一个角色中没有的用户列表
	 * @param roleId 角色id
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageInfo<?> getUnauthMembers(String roleId, int page, int pageSize);
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
	public int addResource(String roleId, String[] resIds);
	/**
	 * 删除资源从指定角色
	 * @param roleId 角色id
	 * @param resIds 资源id列表
	 * @return 影响的数量
	 */
	public int removeResource(String roleId, String resIds);
	/**
	 * 添加用户到指定角色
	 * @param operatorId 操作者的用户id
	 * @param roleId 角色id
	 * @param userIds 用户id列表
	 * @return 影响的数量
	 */
	public int addUser(String operatorId, String roleId, String[] userIds);
	/**
	 * 删除用户到指定角色
	 * @param roleId 角色id
	 * @param userIds 用户id列表
	 * @return 影响的数量
	 */
	public int removeUser(String roleId, String userIds);
}
