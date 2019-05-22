package org.bogies.rbacs.service.impl;

import java.util.List;
import java.util.UUID;

import org.bogies.common.constants.RoleConstants;
import org.bogies.rbacs.dao.rbac.RoleDao;
import org.bogies.rbacs.entity.ResourceEntity;
import org.bogies.rbacs.entity.RoleEntity;
import org.bogies.rbacs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
		RoleEntity role = this.getById(RoleConstants.ROLE_ADMIN.getRoleId());
		if (null == role) {
			role = new RoleEntity();
			role.setId(RoleConstants.ROLE_ADMIN.getRoleId());
			role.setName(RoleConstants.ROLE_ADMIN.getRoleName());
			role.setDescription(RoleConstants.ROLE_ADMIN.getRoleDesc());
			role.setReserved(1);
			role.setServiceName("system");
			roleDao.add(role);
		}
		// 检查匿名角色
		role = this.getById(RoleConstants.ROLE_ANONYMOUS.getRoleId());
		if (null == role) {
			role = new RoleEntity();
			role.setId(RoleConstants.ROLE_ANONYMOUS.getRoleId());
			role.setName(RoleConstants.ROLE_ANONYMOUS.getRoleName());
			role.setDescription(RoleConstants.ROLE_ANONYMOUS.getRoleDesc());
			role.setReserved(1);
			role.setServiceName("system");
			roleDao.add(role);
		}
		// 检查安全保密角色
		role = this.getById(RoleConstants.ROLE_SECRECY.getRoleId());
		if (null == role) {
			role = new RoleEntity();
			role.setId(RoleConstants.ROLE_SECRECY.getRoleId());
			role.setName(RoleConstants.ROLE_SECRECY.getRoleName());
			role.setDescription(RoleConstants.ROLE_SECRECY.getRoleDesc());
			role.setReserved(1);
			role.setServiceName("system");
			roleDao.add(role);
		}
		// 检查安全审计员角色
		role = this.getById(RoleConstants.ROLE_AUDIT.getRoleId());
		if (null == role) {
			role = new RoleEntity();
			role.setId(RoleConstants.ROLE_AUDIT.getRoleId());
			role.setName(RoleConstants.ROLE_AUDIT.getRoleName());
			role.setDescription(RoleConstants.ROLE_AUDIT.getRoleDesc());
			role.setReserved(1);
			role.setServiceName("system");
			roleDao.add(role);
		}
	}
	@Override
	public PageInfo<RoleEntity> getRoles(RoleEntity roleFilter, int page, int pageSize) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<RoleEntity> roleListPage = null;
		List<RoleEntity> roleList = roleDao.getRoles(roleFilter);
		if (null != roleList) {
			roleListPage = new PageInfo<RoleEntity>(roleList);
		}
		
		return roleListPage;
	}
	@Override
	public PageInfo<?> getRolesByUserId(String userId, String serviceName, int page, int pageSize) throws RuntimeException {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<RoleEntity> roleListPage = null;
		List<RoleEntity> roleList = roleDao.getRolesByUserId(userId, serviceName);
		roleListPage = new PageInfo<RoleEntity>(roleList);
		
		return roleListPage;
	}
	@Override
	public RoleEntity getById(String id) {
		return roleDao.getById(id);
	}
	@Override
	public int add(RoleEntity role) {
		role.setId(UUID.randomUUID().toString());
		return roleDao.add(role);
	}

	@Override
	public int update(RoleEntity role) {
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
	public List<String> getMemberIds(String roleId) {
		return roleDao.getMemberIds(roleId);
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
		PageInfo<ResourceEntity> resListPage = null;
		List<ResourceEntity> resList = roleDao.getRes(roleId);
		if (null != resList) {
			resListPage = new PageInfo<ResourceEntity>(resList);
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
		PageInfo<ResourceEntity> resListPage = null;
		List<ResourceEntity> resList = roleDao.getUnauthRes(roleId);
		if (null != resList) {
			resListPage = new PageInfo<ResourceEntity>(resList);
		}
		
		return resListPage;
	}
	@Override
	public int addResource(String roleId, String[] resIds, String serviceName) {
		return roleDao.addResource(roleId, resIds, serviceName);
	}
	@Override
	public int removeResource(String roleId, String resIds) {
		return roleDao.removeResource(roleId, resIds);
	}
	@Override
	public int addUser(String roleId, String[] userIds, String serviceName) {
		// 操作用户是管理员或相同角色, 可添加用户当指定角色
		return roleDao.addUsers(roleId, userIds, serviceName);
	}
	@Override
	public int removeUser(String roleId, String userIds) {
		// 加事务, 删除后如果为 管理员 且用户数为0则退回并提示错误
		return roleDao.removeUsers(roleId, userIds);
	}
}
