package org.bogies.rbacs.service.impl;

import java.util.List;
import java.util.UUID;

import org.bogies.rbacs.dao.MembersDao;
import org.bogies.rbacs.dao.RoleUsersDao;
import org.bogies.rbacs.model.RoleModel;
import org.bogies.rbacs.service.MembersService;
import org.bogies.common.constants.ErrorConstants;
import org.bogies.common.entity.MemberEntity;
import org.bogies.common.entity.Result;
import org.bogies.common.utils.EncryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月13日上午9:28:23
 */
public class MembersServiceImpl implements MembersService {
	@Autowired
	private MembersDao membersDao;
	@Autowired
	private RoleUsersDao roleDao;

	@Override
	public MemberEntity login(String username, String password) {
		MemberEntity user = this.getByUsername(username);
		String hashPwd = this.makePassword(username, password);
		
		if (hashPwd.equals(user.getPassword())) {
			user.setPassword(null);
		} else {
			user = null;
		}
		
		return user;
	}
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
		List<MemberEntity> resourcesRecord = membersDao.getUsers(filter);
		if (null != resourcesRecord) {
			pageInfo = new PageInfo<MemberEntity>(resourcesRecord);
		}
		return pageInfo;
	}
	public boolean checkMemberRequired(MemberEntity member) {
		if (StringUtils.isBlank(member.getId())) {
			return false;
		}
		
		if (StringUtils.isBlank(member.getUsername())) {
			return false;
		}
		
		if (StringUtils.isBlank(member.getNickname())) {
			return false;
		}
		
		if (StringUtils.isBlank(member.getPassword())) {
			return false;
		}
		
		return true;
	}
	@Override
	public Result insert(MemberEntity member) {
		if(StringUtils.isBlank(member.getPassword())) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "密码不能为空");
		}
		member.setId(UUID.randomUUID().toString());
		if (!checkMemberRequired(member)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "用户信息不完整");
		}

		/// 加密明文密码
		String passwordMD5 = makePassword(member.getUsername(), member.getPassword());
		member.setPassword(passwordMD5);
		
		if(membersDao.existByUsername(member.getUsername())!=0) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "用户名重复");
		}
		int res = membersDao.insert(member);
		return Result.success(res);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result deleteById(String id) {
		Result rlt = null;
		if (StringUtils.isBlank(id)) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "id null");
		} else if(membersDao.existOnlyAdmin() <2){
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "至少保留一个管理员用户");
		}else{
			membersDao.deleteById(id); 
			roleDao.deleteByUserid(id); 
			rlt = Result.success(id);
		}
		return rlt;
	}
	@Override
	public Result update(MemberEntity member) {
		return Result.success(membersDao.updateByUsername(member));
	}
	@Override
	public MemberEntity getById(String id) {
		return membersDao.getById(id);
	}
	@Override
	public MemberEntity getByUsername(String username) {
		return membersDao.getByUsername(username);
	}

	@Override
	public Integer countByUsername(String username) {
		return membersDao.countByUsername(username);
	}

	@Override
	public int updatePwd(String userid, String password) {
		return 0;
	}

	@Override
	public int updateUserInfo(MemberEntity user) {
		return 0;
	}

	@Override
	public PageInfo<RoleModel> findRoles(int page, int pageSize, String userId) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<RoleModel> roleListPage = null;
		List<RoleModel> roleList = membersDao.findRoles(userId);
		if (null != roleList) {
			roleListPage = new PageInfo<RoleModel>(roleList);
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
	@Override
	public int delete(String id) {
		return membersDao.deleteById(id);
	}

	@Override
	public String makePassword(String username, String srcPwd) {
		String pwd = EncryptUtil.md5(username.toLowerCase()+"/"+srcPwd);
		
		return pwd;
	}
}
