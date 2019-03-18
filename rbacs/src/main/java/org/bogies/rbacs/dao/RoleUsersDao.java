package org.bogies.rbacs.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月13日上午9:19:12
 */
public interface RoleUsersDao {
	/**
	 * TODO
	 * @param roleId
	 * @return：int
	 */
	public int deleteByUserid(@Param("userid") String userid);
}
