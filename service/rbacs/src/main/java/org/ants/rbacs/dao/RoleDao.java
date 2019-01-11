package org.ants.rbacs.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.ants.rbacs.model.ResourcesModel;
import org.ants.rbacs.model.RoleModel;
import org.ants.rbacs.model.MembersModel;

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
	public RoleModel getById(@Param("id") String roleId);
	/**
	 * TODO
	 * @param role
	 * @return：List<RoleModel>
	 */
	public List<RoleModel> getRoles(@Param("role") RoleModel role);
	/**
	 * TODO
	 * @param role
	 * @return：int
	 */
	public int add(@Param("role") RoleModel role);
	/**
	 * TODO
	 * @param role
	 * @return：int
	 */
	public int update(@Param("role") RoleModel role);
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
	public int addUsers(@Param("roleId") String roleId, @Param("userIds") String[] userIds);
	public int removeUsers(@Param("roleId") String roleId, @Param("userIds") String userIds);
	/**
	 * TODO
	 * @param roleId
	 * @param resIds
	 * @return：int
	 */
	public int addResource(@Param("roleId") String roleId, @Param("resIds") String[] resIds);
	public int removeResource(@Param("roleId") String roleId, @Param("resIds") String resIds);
	/**
	 * 获取角色中的用户列表
	 * @param roleId
	 * @return：List<UserModel>
	 */
	public List<MembersModel> getMembers(@Param("roleId") String roleId);
	public List<MembersModel> getUnauthMembers(@Param("roleId") String roleId);
	public List<ResourcesModel> getRes(@Param("roleId") String roleId);
	public List<ResourcesModel> getUnauthRes(@Param("roleId") String roleId);
}
