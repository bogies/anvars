package org.bogies.rbacs.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.github.pagehelper.PageInfo;
import org.bogies.rbacs.model.RoleModel;
import org.bogies.common.entity.MembersEntity;
import org.bogies.common.entity.Result;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月13日上午9:26:26
 */
public interface MembersService {
	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 */
	public MembersEntity login(String username, String password);
	/**
	 * 根据条件查找用户
	 * @param filter 查找过滤条件
	 * @return 用户信息列表
	 */
	public PageInfo<MembersEntity> getUsers(MembersEntity filter, int page, int pageSize);
	/**
	 * TODO 根据id查询一个登录用户
	 * @param id
	 * @return：UserModel
	 */
	public MembersEntity getById(String id);
	/**
	 * TODO 根据用户名获取用户信息
	 * @param username
	 * @return：UserModel
	 */
	public MembersEntity getByUsername(String username);
	
	/**TODO 
	 * @param username
	 * @return Integer
	 */
	public Integer countByUsername(String username);

	/**
	 * TODO 登录密码修改
	 * @param id
	 * @param password
	 * @return：int
	 */
	public int updatePwd(String id, String password);
	/**
	 * TODO 个人信息修改
	 * @param user
	 * @return：int
	 */
	public int updateUserInfo(MembersEntity user);
	/**
	 * TODO 根据用户id返回他所有的角色
	 * @param userId
	 * @return：List<RoleModel>
	 */
	public List<RoleModel> findRoles(String userId);
	/**
	 * TODO 添加用户
	 * @param insertLi
	 * @param userId 
	 * @return：int
	 */
	public Result insert(List<MembersEntity> insertLi, String userId);
	/**
	 * TODO 根据id用户
	 * @param 用户id
	 * @return：Result
	 */
	public Result deleteById(String id);
	/**
	 * TODO 修改用户基本信息
	 * @param loginUser
	 * @param userId 
	 * @return：int
	 */
	public Result update(MembersEntity loginUser, String userId);
	/**
	 * TODO 添加用户所属角色
	 * @param userId
	 * @param roleIds
	 * @return：int
	 */
	public int addUserRole(String userId, String[] roleIds);
	/**TODO 删除用户所有的角色
	 * @param userId
	 * @return int
	 */
	public int deleteUserRoleByUserId(@Param("userId") String userId);
	/**
	 * TODO 删除用户
	 * @param id
	 * @return：int
	 */
	public int delete(String id);
	/**
	 * TODO 生成加密的密码
	 * @param username
	 * @param srcPwd
	 * @return：String
	 */
	public String makePassword(String username, String srcPwd);
}
