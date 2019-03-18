package org.bogies.rbacs.model;

/**
 * @Description: TODO
 * @author: renkun
 * @date: 2018年12月13日上午9:23:50
 */
public class RoleModel {
	private String id;
	private String name;
	private String description;
	/**1=保留角色*/
	private int reserved = 0;
	/**排序值, 默认为当前时间戳*/
	private long createTime = 0;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getReserved() {
		return reserved;
	}
	public void setReserved(int reserved) {
		this.reserved = reserved;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
