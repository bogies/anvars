package org.ants.gateway.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName: PermitUrlEntity
 * @Description: TODO
 * @author: Jerry
 * @date: 2018年12月13日 下午2:27:23
 */
@Component
@ConfigurationProperties(prefix = "permiturl")
public class PermitUrlEntity {
	private List<String> permitAll;
    /** 无需验证权限过滤正则表达式 */
    private List<Pattern> allPermitUrlPattern = new ArrayList<>();
    
    public void init() {
    	String currentUrl;
    	Pattern currentPattern;
    	if (null == permitAll) {
    		return;
    	}
		for (String url : permitAll) {
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

	public List<String> getPermitAll() {
		return permitAll;
	}

	public void setPermitAll(List<String> permitAll) {
		this.permitAll = permitAll;
	}
}
