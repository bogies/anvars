package org.bogies.rbacs.service.impl;

import java.util.List;
import java.util.UUID;

import org.bogies.rbacs.dao.MembersDao;
import org.bogies.rbacs.dao.RoleUsersDao;
import org.bogies.rbacs.model.RoleModel;
import org.bogies.rbacs.service.MembersService;
import org.bogies.common.constants.ErrorConstants;
import org.bogies.common.entity.MembersEntity;
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
	public MembersEntity login(String username, String password) {
		MembersEntity user = this.getByUsername(username);
		String hashPwd = this.makePassword(username, password);
		
		if (hashPwd.equals(user.getPassword())) {
			user.setPassword(null);
		} else {
			user = null;
		}
		
		return user;
	}
	@Override
	public PageInfo<MembersEntity> getUsers(MembersEntity filter,int page, int pageSize) {
		
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<MembersEntity> pageInfo = null;
		List<MembersEntity> resourcesRecord = membersDao.getUsers(filter);
		if (null != resourcesRecord) {
			pageInfo = new PageInfo<MembersEntity>(resourcesRecord);
		}
		return pageInfo;
	}
	@Override
	public Result insert(List<MembersEntity> insertLi,String userId) {
		
		insertLi.forEach(args->{
			args.setId(UUID.randomUUID().toString());
		});
		for (MembersEntity rm : insertLi) {
			if(rm.getPassword()!=null && !rm.getPassword().equals("")) {
				String passwordMD5 = makePassword(rm.getUsername(), rm.getPassword());
				rm.setPassword(passwordMD5);
			}
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
	public Result update(MembersEntity members, String userId) {
		
		if(members.getPassword()!=null && !members.getPassword().equals("")) {
			String passwordMD5 = makePassword(members.getUsername(), members.getPassword());
			members.setPassword(passwordMD5);
		}
		if(membersDao.getById(userId).getAdmin() < members.getAdmin()) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "普通用户不能添加管理员账号");
		}
		return Result.success(membersDao.updateByUsername(members));
	}
	@Override
	public MembersEntity getById(String id) {
		return membersDao.getById(id);
	}
	@Override
	public MembersEntity getByUsername(String username) {
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
	public int updateUserInfo(MembersEntity user) {
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
