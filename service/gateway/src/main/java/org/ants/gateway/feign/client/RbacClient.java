package org.ants.gateway.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: RbacClient
 * @Description: rbac服务的接口调用定义
 * @author: Jerry
 * @date: 2018年12月18日 上午10:10:47
 */
@FeignClient("rbac-service")
public interface RbacClient {
	@RequestMapping(method = RequestMethod.GET, value = "/resources/auth", 
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	/**
	 * 检查 Restful API 权限
	 * @param serviceName 请求API对应的服务名称
	 * @param uri 请求的API地址
	 * @return 
	 */
    String checkPermit(@RequestParam(value="resId") String resId);
}
