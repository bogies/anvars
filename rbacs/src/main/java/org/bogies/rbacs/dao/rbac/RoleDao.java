package org.bogies.rbacs.dao.rbac;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bogies.rbacs.entity.ResourceEntity;
import org.bogies.rbacs.entity.RoleEntity;

/**
 * @Description: 角色管理
 * @author: Jerry
 * @date: 2018年12月13日上午9:19:12
 */
public interface RoleDao {
	/**
	 * TODO
	 * @param roleId
	 * @return：RoleModel
	 */
	public RoleEntity getById(@Param("id") String roleId);
	/**
	 * TODO
	 * @param role
	 * @return：List<RoleModel>
	 */
	public List<RoleEntity> getRoles(RoleEntity role);
	public List<RoleEntity> getRolesByUserId(@Param("userId") String userId, @Param("serviceName") String serviceName);
	/**
	 * 
	 * @param role
	 * @return：List<RoleModel>
	 */
	public List<RoleEntity> getResInRoles(@Param("resId") String resId);
	/**
	 * TODO
	 * @param role
	 * @return：int
	 */
	public int add(RoleEntity role);
	/**
	 * TODO
	 * @param role
	 * @return：int
	 */
	public int update(RoleEntity role);
	/**
	 * TODO
	 * @param roleId
	 * @return：int
	 */
	public int delete(@Param("id") String roleId);
	/**
	 * TODO
	 * @param roleId
	 * @param userIds
	 * @return：int
	 */
	public int addUsers(@Param("roleId") String roleId, 
			@Param("userIds") String[] userIds, @Param("serviceName") String serviceName);
	public int removeUsers(@Param("roleId") String roleId, @Param("userIds") String userIds);
	/**
	 * TODO
	 * @param roleId
	 * @param resIds
	 * @return：int
	 */
	public int addResource(@Param("roleId") String roleId, @Param("resIds") String[] resIds, 
			@Param("serviceName") String serviceName);
	public int removeResource(@Param("roleId") String roleId, @Param("resIds") String resIds);
	/**
	 * 获取属于指定角色的用户id列表
	 * @param roleId 角色ID
	 * @return：用户ID列表
	 */
	public List<String> getMemberIds(@Param("roleId") String roleId);
	/**
	 * 获取指定角色中的资源列表
	 * @param roleId 角色ID
	 * @return 资源信息列表
	 */
	public List<ResourceEntity> getRes(@Param("roleId") String roleId);
	/**
	 * 获取未在指定角色中的资源列表
	 * @param roleId 角色ID
	 * @return 资源信息列表
	 */
	public List<ResourceEntity> getUnauthRes(@Param("roleId") String roleId);
}
