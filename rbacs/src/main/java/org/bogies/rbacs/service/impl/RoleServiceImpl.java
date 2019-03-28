package org.bogies.rbacs.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.bogies.common.constants.RoleConstants;
import org.bogies.common.entity.MembersEntity;
import org.bogies.rbacs.dao.RoleDao;
import org.bogies.rbacs.model.ResourcesModel;
import org.bogies.rbacs.model.RoleModel;
import org.bogies.rbacs.service.RoleService;

@Service
/**
 * @Description: 角色管理功能实现
 * @author: Jerry
 * @date: 2018年12月13日上午9:26:48
 */
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * @Title: initReservedRoles
	 * @Description: 初始化基本角色, 这些角色不需要修改和删除
	 * @see org.bogies.rbacs.service.RoleService#initReservedRoles()
	 */
	@Override
	public void initReservedRoles() {
		// 检查管理员角色
		RoleModel role = this.getById(RoleConstants.ROLE_ADMIN.getRoleId());
		if (null == role) {
			role = new RoleModel();
			role.setId(RoleConstants.ROLE_ADMIN.getRoleId());
			role.setName(RoleConstants.ROLE_ADMIN.getRoleName());
			role.setDescription(RoleConstants.ROLE_ADMIN.getRoleDesc());
			role.setReserved(1);
			roleDao.add(role);
		}
		// 检查匿名角色
		role = this.getById(RoleConstants.ROLE_ANONYMOUS.getRoleId());
		if (null == role) {
			role = new RoleModel();
			role.setId(RoleConstants.ROLE_ANONYMOUS.getRoleId());
			role.setName(RoleConstants.ROLE_ANONYMOUS.getRoleName());
			role.setDescription(RoleConstants.ROLE_ANONYMOUS.getRoleDesc());
			role.setReserved(1);
			roleDao.add(role);
		}
	}
	@Override
	public PageInfo<RoleModel> getRoles(RoleModel roleFilter, int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<RoleModel> roleListPage = null;
		List<RoleModel> roleList = roleDao.getRoles(roleFilter);
		if (null != roleList) {
			roleListPage = new PageInfo<RoleModel>(roleList);
		}
		
		return roleListPage;
	}
	@Override
	public RoleModel getById(String id) {
		return roleDao.getById(id);
	}
	@Override
	public int add(RoleModel role) {
		role.setId(UUID.randomUUID().toString());
		return roleDao.add(role);
	}

	@Override
	public int update(RoleModel role) {
		return roleDao.update(role);
	}

	@Override
	@Transactional
	public int delete(String id) {
		roleDao.removeUsers(id, "");
		roleDao.removeResource(id, "");
		return roleDao.delete(id);
	}
	@Override
	public PageInfo<MembersEntity> getMembers(String roleId, int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<MembersEntity> userListPage = null;
		List<MembersEntity> userList = roleDao.getMembers(roleId);
		if (null != userList) {
			userListPage = new PageInfo<MembersEntity>(userList);
		}
		
		return userListPage;
	}
	@Override
	public PageInfo<?> getUnauthMembers(String roleId, int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<MembersEntity> userListPage = null;
		List<MembersEntity> userList = roleDao.getUnauthMembers(roleId);

		if (null != userList) {
			userListPage = new PageInfo<MembersEntity>(userList);
		}
		
		return userListPage;
	}
	@Override
	public PageInfo<?> getRes(String roleId, int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<ResourcesModel> resListPage = null;
		List<ResourcesModel> resList = roleDao.getRes(roleId);
		if (null != resList) {
			resListPage = new PageInfo<ResourcesModel>(resList);
		}
		
		return resListPage;
	}
	@Override
	public PageInfo<?> getUnauthRes(String roleId, int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<ResourcesModel> resListPage = null;
		List<ResourcesModel> resList = roleDao.getUnauthRes(roleId);
		if (null != resList) {
			resListPage = new PageInfo<ResourcesModel>(resList);
		}
		
		return resListPage;
	}
	@Override
	public int addResource(String roleId, String[] resIds) {
		return roleDao.addResource(roleId, resIds);
	}
	@Override
	public int removeResource(String roleId, String resIds) {
		return roleDao.removeResource(roleId, resIds);
	}
	@Override
	public int addUser(String roleId, String[] userIds) {
		return roleDao.addUsers(roleId, userIds);
	}
	@Override
	public int removeUser(String roleId, String userIds) {
		return roleDao.removeUsers(roleId, userIds);
	}
}
