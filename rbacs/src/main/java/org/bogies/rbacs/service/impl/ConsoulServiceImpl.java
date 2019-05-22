package org.bogies.rbacs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.bogies.common.utils.HttpServiceUtil;
import org.bogies.rbacs.entity.ConsoulServices;
import org.bogies.rbacs.service.ConsoulService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

@Service
/**@Description: Get ConsoulService information
 * @author: renkun
 * @date: 2018年12月17日下午4:59:42
 */
public class ConsoulServiceImpl implements ConsoulService {
	
	//@Value("${spring.cloud.consul.servicesUrl}")
	private String servicesUrl = "s";
	
	@Override
	public Map<String, ConsoulServices> getInfos() {

		String httpRes = new HttpServiceUtil().getHttpRes(servicesUrl, "GET", null);
		if(!httpRes.isEmpty()) {
			Map<String, ConsoulServices> map = JSON.parseObject(httpRes, new TypeReference<Map<String, ConsoulServices>>(){});
			return map;
		}
		return null;
	}
}
