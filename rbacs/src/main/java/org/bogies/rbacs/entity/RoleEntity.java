package org.bogies.rbacs.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * @Description: 角色信息
 * @author: Jerry
 * @date: 2018年12月13日上午9:23:50
 */
public class RoleEntity {
	private String id;
	private String name;
	private String serviceName;
	private String description;
	/**1=保留角色*/
	private int reserved = 0;
	/**排序值, 默认为当前时间戳*/
	private long createTime = 0;
}
