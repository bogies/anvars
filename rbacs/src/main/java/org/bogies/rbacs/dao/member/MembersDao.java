package org.bogies.rbacs.dao.member;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.bogies.common.entity.MemberEntity;
import org.bogies.rbacs.entity.RoleEntity;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月13日上午9:19:28
 */
public interface MembersDao {
	
	/**
	 * 根据查找条件获取用户信息
	 * @param filter
	 * @return：用户列表
	 */
	public List<MemberEntity> getUsers(MemberEntity filter);
	/**
	 * 获取指定id列表中的用户列表
	 * @param ids 用户ID列表
	 * @return 用户列表
	 */
	public List<MemberEntity> getIncludeMembers(@Param("ids") String ids);
	/**
	 * 获取排除指定id列表的用户列表
	 * @param ids 要排除的用户ID列表
	 * @return 用户列表
	 */
	public List<MemberEntity> getExcludeMembers(@Param("ids") String ids);
	/**
	 * 添加用户
	 * @param loginUser
	 * @return：int
	 */
	public int insert(MemberEntity member);
	/**
	 * 删除用户
	 * @param id
	 * @return：int
	 */
	public int deleteById(@Param("id") String id);
	/**
	 * 根据登录用户名判断记录是否重复
	 * @param userName
	 * @return：int
	 */
	public int existByUsername(@Param("username") String useruame);
	/**
	 * 获取管理员记录数
	 * @return：int
	 */
	public int existOnlyAdmin();
	/**
	 * TODO
	 * @param id
	 * @return：UserModel
	 */
	public MemberEntity getById(@Param("id") String id);
	/**
	 * TODO
	 * @param username
	 * @return：UserModel
	 */
	public MemberEntity getByUsername(@Param("username") String username);
	/**
	 * 根据用户名统计用户数量
	 * @param username
	 * @return：int
	 */
	public int countByUsername(@Param("username") String username);
	/**
	 * 根据用户id获取用户所属的角色列表
	 * @param user
	 * @return：List<RoleModel>
	 */
	List<RoleEntity> findRoles(@Param("userId") String userId);
	/**
	 * 修改用户基本信息
	 * @param loginUser
	 * @return：int
	 */
	public int updateByUsername(@Param("members") MemberEntity members);
	/**
	 * 删除用户所有的角色
	 * @param userId
	 * @return：int
	 */
	public int clearUserRoles(@Param("userId") String userId);
}
