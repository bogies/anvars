package org.ants.tommy.session;

import org.ants.tommy.entity.ConfigEntity;
import org.ants.tommy.entity.WebConfigEntity;

public class AppConfig {
	static private AppConfig mInstance = null;
	
	private ConfigEntity config;
	private WebConfigEntity webConfig;
	
	private AppConfig() {
		
	}
	
	static public AppConfig instance() {
		if (null == mInstance) {
			mInstance = new AppConfig();
		}
		
		return mInstance;
	}

	public ConfigEntity getConfig() {
		return config;
	}

	public void setConfig(ConfigEntity config) {
		this.config = config;
		this.config.init();
	}

	public WebConfigEntity getWebConfig() {
		return webConfig;
	}

	public void setWebConfig(WebConfigEntity webConfig) {
		this.webConfig = webConfig;
	}
}
