package org.ants.tommy.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;  
import org.springframework.stereotype.Component;

@Component
public class ConfigEntity {
	@Value("${serviceName}")
	private String serviceName;
	@Value("${signKey}")
	private String signKey;
	@Value("${gateway}")
	private String gateway;
	@Value("${loginUrl}")
	private String loginUrl;
	@Value("${serviceAuthUrl}")
	private String serviceAuthUrl;
	@Value("${permitAll}")
	private String permitAll;
	@Value("${permitAuthUrl}")
	private String permitAuthUrl;
	/*
	 * 无需权限验证的页面表达式列表
	 */
	private List<Pattern> allPermitUrlPattern = new ArrayList<>();
	
	public void init() {
    	String currentUrl;
    	Pattern currentPattern;
    	if (StringUtils.isBlank(permitAll)) {
    		return;
    	}
    	String[] permitAllList = permitAll.split(",");
		for (String url : permitAllList) {
			if (StringUtils.isBlank(url)) {
				continue;
			}
    		currentUrl = url.replaceAll("\\*\\*", "(.*?)").toLowerCase();
            currentPattern = Pattern.compile(currentUrl, Pattern.CASE_INSENSITIVE);
            allPermitUrlPattern.add(currentPattern);
    	}
    }
	public boolean isPermitAllUrl(String reqUrl) {
    	for (Pattern pattern : allPermitUrlPattern) {
            if (pattern.matcher(reqUrl.toLowerCase()).find()) {
                return true;
            }
        }
        return false;
    }
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getSignKey() {
		return signKey;
	}
	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	public String getPermitAll() {
		return permitAll;
	}
	public void setPermitAll(String permitAll) {
		this.permitAll = permitAll;
	}
	public String getPermitAuthUrl() {
		return permitAuthUrl;
	}
	public void setPermitAuthUrl(String permitAuthUrl) {
		this.permitAuthUrl = permitAuthUrl;
	}
	public String getServiceAuthUrl() {
		return serviceAuthUrl;
	}
	public void setServiceAuthUrl(String serviceAuthUrl) {
		this.serviceAuthUrl = serviceAuthUrl;
	}
}
