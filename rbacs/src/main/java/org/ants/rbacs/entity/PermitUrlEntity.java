package org.ants.rbacs.entity;

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
    private List<String> devSwaggerUrl;
    /** swagger过滤正则表达式 */
    private List<Pattern> permitUrlPattern = new ArrayList<>();
    /** 无需验证权限过滤正则表达式 */
    private List<Pattern> allPermitUrlPattern = new ArrayList<>();
    
    public void init() {
    	String currentUrl;
    	Pattern currentPattern;
    	if (null != devSwaggerUrl) {
    		for (String url : devSwaggerUrl) {
        		currentUrl = url.replaceAll("\\*\\*", "(.*?)").toLowerCase();
                currentPattern = Pattern.compile(currentUrl, Pattern.CASE_INSENSITIVE);
                permitUrlPattern.add(currentPattern);
        	}
    	}
    	if (null != permitAll) {
    		for (String url : permitAll) {
        		currentUrl = url.replaceAll("\\*\\*", "(.*?)").toLowerCase();
                currentPattern = Pattern.compile(currentUrl, Pattern.CASE_INSENSITIVE);
                allPermitUrlPattern.add(currentPattern);
        	}
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
    public boolean isSwaggerUrl(String reqUrl) {
    	for (Pattern pattern : permitUrlPattern) {
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

	public List<String> getDevSwaggerUrl() {
		return devSwaggerUrl;
	}

	public void setDevSwaggerUrl(List<String> devSwaggerUrl) {
		this.devSwaggerUrl = devSwaggerUrl;
	}
}
