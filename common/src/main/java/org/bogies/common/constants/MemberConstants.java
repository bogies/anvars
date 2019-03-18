package org.bogies.common.constants;

public enum MemberConstants {
	/** 匿名角色ID, 未登录用户都属于此角色 */
	MEMBER_ANONYMOUS("anonymous", "匿名用户");
	
	private String memberId;
	private String memberName;
	
	private MemberConstants(String memberId, String memberName) {
		this.memberId = memberId;
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}
