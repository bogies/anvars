package org.bogies.common.constants;

public enum RoleConstants {
	/** 匿名角色ID, 未登录用户都属于此角色 */
	ROLE_ANONYMOUS("anonymous", "匿名角色", "未登录用户都属于此角色", 1),
	/** 管理员角色ID */
	ROLE_ADMIN("admin", "管理员角色", "超级管理员", 2),
	// 安全保密员ID
	ROLE_SECRECY("role_secrecy", "安全保密", "安全保密员", 3),
	// 安全审计员ID
	ROLE_AUDIT("role_audit", "安全审计", "安全审计员", 4);
	
	private String roleId;
	private String roleName;
	private String roleDesc;
	private int reserved;
	
	private RoleConstants(String roleId, String roleName, String roleDesc, int reserved) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.reserved = reserved;
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
	public int getReserved() {
		return reserved;
	}
}
