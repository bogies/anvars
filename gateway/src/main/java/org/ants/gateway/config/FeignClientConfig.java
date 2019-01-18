package org.ants.gateway.config;

import java.util.UUID;
import org.ants.common.constants.RequestHeaderConstants;
import org.ants.common.entity.CustomRequestEntity;
import org.ants.common.utils.AuthToken;
import org.ants.gateway.entity.HeaderTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @ClassName: FeignClientConfig
 * @Description: feign client config
 * @author: Jerry
 * @date: 2018年12月19日 上午8:54:25
 */
@Configuration
public class FeignClientConfig {
	@Autowired
	private HeaderTokenEntity headerToken;
	@Value("${spring.cloud.consul.discovery.service-name}")
	private String serviceName;
	
	@Bean
	/**
	 * 内部服务间请求时, 自定义 header 头信息
	 */
    public RequestInterceptor headerInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				CustomRequestEntity reqHeader = new CustomRequestEntity();
				reqHeader.setCaller(serviceName);
				reqHeader.setTimestamp(System.currentTimeMillis());
				template.header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
				template.header(RequestHeaderConstants.AUTH_TOKEN, AuthToken.makeToken(reqHeader, headerToken.getKey()));
				template.header(RequestHeaderConstants.REQUEST_ID, UUID.randomUUID().toString());
			}
		};
	}
}
