package org.ants.gateway.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.ants.common.constants.RequestHeaderConstants;
import org.ants.common.entity.CustomRequestEntity;
import org.ants.common.utils.AuthToken;
import org.ants.gateway.entity.HeaderTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
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
	
	@Bean
	/**
	 * 自定义 header 头信息
	 */
    public RequestInterceptor headerInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template) {
				List<String> contentTypeList = Lists.newArrayList("application/x-www-form-urlencoded;charset=utf-8");
				CustomRequestEntity reqHeader = new CustomRequestEntity();
				reqHeader.setRequestId(UUID.randomUUID().toString());
				reqHeader.setCaller("Gateway");
				reqHeader.setTimestamp(System.currentTimeMillis());
				List<String> tokenList = Lists.newArrayList(AuthToken.makeToken(reqHeader, headerToken.getKey()));
				Map<String, Collection<String>> headers = ImmutableMap.of("Content-Type", contentTypeList, 
						RequestHeaderConstants.AUTH_TOKEN, tokenList);
				template.headers(headers);
			}
		};
	}
}
