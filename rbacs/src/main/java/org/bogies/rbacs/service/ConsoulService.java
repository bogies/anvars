package org.bogies.rbacs.service;

import java.util.Map;

import org.bogies.rbacs.entity.ConsoulServices;

/**@Description: TODO 获取consoul 注册信息
 * @author: renkun
 * @date: 2018年12月17日上午10:48:15
 */
public interface ConsoulService {
	
	public Map<String, ConsoulServices> getInfos();
}