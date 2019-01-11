package org.ants.gateway.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: HeaderTokenEntity
 * @Description: 服务间通讯header token配置
 * @author: Jerry
 * @date: 2018年12月14日 上午8:55:41
 */
@Component
@ConfigurationProperties(prefix = "header-token")
public class HeaderTokenEntity {
	private String key;
	private long timeout;
	
	public long getTimeout() {
		return timeout;
	}
	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
