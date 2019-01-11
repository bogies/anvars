package org.ants.rbacs.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.ants.common.utils.HttpServiceUtil;
import org.ants.rbacs.entity.ConsoulServices;
import org.ants.rbacs.service.ConsoulService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
/**@Description: Get ConsoulService information
 * @author: renkun
 * @date: 2018年12月17日下午4:59:42
 */
public class ConsoulServiceImpl implements ConsoulService {
	
	@Value("${spring.cloud.consul.servicesUrl}")
	private String servicesUrl;
	
	Gson gson = new Gson();
	
	@Override
	public Map<String, ConsoulServices> getInfos() {

		String httpRes = new HttpServiceUtil().getHttpRes(servicesUrl, "GET", null);
		if(!httpRes.isEmpty()) {
			Map<String, ConsoulServices> map = gson.fromJson(httpRes, new TypeToken<HashMap<String, ConsoulServices>>() {}.getType());
			return map;
		}
		return null;
	}
}
