package org.bogies.rbacs.service.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bogies.common.entity.MemberEntity;
import org.bogies.rbacs.dao.member.MembersDao;
import org.bogies.rbacs.entity.RoleEntity;
import org.bogies.rbacs.service.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
/**
 * @Description: 用户信息服务
 * @author: Jerry
 * @date: 2018年12月13日上午9:28:23
 */
public class MembersServiceImpl implements MembersService {
	@Autowired
	private MembersDao membersDao;

	@Override
	public PageInfo<MemberEntity> getUsers(MemberEntity filter,int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<MemberEntity> pageInfo = null;
		List<MemberEntity> res = membersDao.getUsers(filter);
		pageInfo = new PageInfo<MemberEntity>(res);
		return pageInfo;
	}
	@Override
	public PageInfo<MemberEntity> getIncludeMembers(List<String> memberIdList, int page, int pageSize) throws RuntimeException {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageInfo<MemberEntity> pageInfo = null;
		String memberIds = "";
		if (memberIdList.isEmpty()) {
			pageInfo = new PageInfo<MemberEntity>();
			return pageInfo;
		}
		for (String memberId : memberIdList) {
			if (memberIds.length() == 0) {
				memberIds += "'"+memberId+"'";
			} else {
				memberIds += ",'"+memberId+"'";
			}
		}
		PageHelper.startPage(page, pageSize);
		List<MemberEntity> res = membersDao.getIncludeMembers(memberIds);
		pageInfo = new PageInfo<MemberEntity>(res);
		return pageInfo;
	}
	@Override
	public PageInfo<MemberEntity> getExcludeMembers(List<String> memberIdList, int page, int pageSize) throws RuntimeException{
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		String memberIds = "";
		if (!memberIdList.isEmpty()) {
			for (String memberId : memberIdList) {
				if (memberIds.length() == 0) {
					memberIds += "'"+memberId+"'";
				} else {
					memberIds += ",'"+memberId+"'";
				}
			}
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<MemberEntity> pageInfo = null;
		List<MemberEntity> resourcesRecord = membersDao.getExcludeMembers(memberIds);
		if (null != resourcesRecord) {
			pageInfo = new PageInfo<MemberEntity>(resourcesRecord);
		}
		return pageInfo;
	}
	@Override
	public PageInfo<RoleEntity> findRoles(int page, int pageSize, String userId) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<RoleEntity> roleListPage = null;
		List<RoleEntity> roleList = membersDao.findRoles(userId);
		if (null != roleList) {
			roleListPage = new PageInfo<RoleEntity>(roleList);
		}
		
		return roleListPage;
	}
	@Override
	public int addUserRole(String userId, String[] roleIds) {
		return 0;
	}
	@Override
	public int deleteUserRoleByUserId(@Param("userId") String userId) {
		return 0;
	}
}
