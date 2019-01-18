package org.ants.rbacs.constants;

public enum RoleConstants {
	/** 匿名角色ID, 未登录用户都属于此角色 */
	ROLE_ANONYMOUS("anonymous", "匿名角色", "未登录用户都属于此角色"),
	/** 管理员角色ID */
	ROLE_ADMIN("admin", "管理员角色", "超级管理员");
	
	private String roleId;
	private String roleName;
	private String roleDesc;
	
	private RoleConstants(String roleId, String roleName, String roleDesc) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}

	public String getRoleId() {
		return roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
}
