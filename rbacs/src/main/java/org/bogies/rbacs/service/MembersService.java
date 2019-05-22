package org.bogies.rbacs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bogies.common.entity.MemberEntity;
import org.bogies.rbacs.entity.RoleEntity;

import com.github.pagehelper.PageInfo;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月13日上午9:26:26
 */
public interface MembersService {
	/**
	 * 根据条件查找用户
	 * @param filter 查找过滤条件
	 * @return 用户信息列表
	 */
	public PageInfo<MemberEntity> getUsers(MemberEntity filter, int page, int pageSize);
	/**
	 * 获取指定id列表中的用户列表
	 * @param memberIds 用户ID列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return 用户信息列表
	 */
	public PageInfo<MemberEntity> getIncludeMembers(List<String> memberIdList, int page, int pageSize) throws RuntimeException;
	/**
	 * 获取排除指定id列表的用户列表
	 * @param memberIds 要排除的用户ID列表
	 * @param page 页码
	 * @param pageSize 每页数量
	 * @return 用户信息列表
	 */
	public PageInfo<MemberEntity> getExcludeMembers(List<String> memberIdList, int page, int pageSize) throws RuntimeException;
	/**
	 * 根据用户id返回他所有的角色
	 * @param userId
	 * @return：List<RoleModel>
	 */
	public PageInfo<RoleEntity> findRoles(int page, int pageSize, String userId);
	/**
	 * 添加用户到指定的角色列表
	 * @param userId 用户id
	 * @param roleIds 角色ID列表
	 * @return 成功的数量
	 */
	public int addUserRole(String userId, String[] roleIds);
	/**
	 * 删除用户所有的角色
	 * @param userId
	 * @return int
	 */
	public int deleteUserRoleByUserId(@Param("userId") String userId);
}
