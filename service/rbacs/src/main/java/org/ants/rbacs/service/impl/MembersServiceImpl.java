package org.ants.rbacs.service.impl;

import java.util.List;
import java.util.UUID;

import org.ants.rbacs.dao.MembersDao;
import org.ants.rbacs.dao.RoleUsersDao;
import org.ants.rbacs.model.RoleModel;
import org.ants.rbacs.model.MembersModel;
import org.ants.rbacs.service.MembersService;
import org.ants.common.constants.ErrorConstants;
import org.ants.common.entity.Result;
import org.ants.common.utils.EncryptUtil;
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
	public MembersModel login(String username, String password) {
		MembersModel user = this.getByUsername(username);
		String hashPwd = this.makePassword(username, password);
		
		if (hashPwd.equals(user.getPassword())) {
			user.setPassword(null);
		} else {
			user = null;
		}
		
		return user;
	}
	@Override
	public PageInfo<MembersModel> getUsers(MembersModel filter,int page, int pageSize) {
		
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<MembersModel> pageInfo = null;
		List<MembersModel> resourcesRecord = membersDao.getUsers(filter);
		if (null != resourcesRecord) {
			pageInfo = new PageInfo<MembersModel>(resourcesRecord);
		}
		return pageInfo;
	}
	@Override
	public Result insert(List<MembersModel> insertLi,String userId) {
		
		insertLi.forEach(args->{
			args.setId(UUID.randomUUID().toString());
		});
		for (MembersModel rm : insertLi) {
			String passwordMD5 = makePassword(rm.getUsername(), rm.getPassword());
			rm.setPassword(passwordMD5);
			if(membersDao.getById(userId).getAdmin() < rm.getAdmin()) {
				return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "普通用户不能添加管理员账号");
			}else if(membersDao.existByUsername(rm.getUsername())!=0) {
				return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "用户名重复");
			}
		}
		int res = membersDao.insert(insertLi);
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
	public int update(MembersModel members) {
		
		if(members.getPassword()!=null && !members.getPassword().equals("")) {
			String passwordMD5 = makePassword(members.getUsername(), members.getPassword());
			members.setPassword(passwordMD5);
		}
		return membersDao.updateByUsername(members);
	}
	@Override
	public MembersModel getById(String id) {
		return membersDao.getById(id);
	}
	@Override
	public MembersModel getByUsername(String username) {
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
	public int updateUserInfo(MembersModel user) {
		return 0;
	}

	@Override
	public List<RoleModel> findRoles(String userId) {
		return null;
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
