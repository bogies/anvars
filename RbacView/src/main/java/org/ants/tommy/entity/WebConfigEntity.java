package org.ants.tommy.entity;

import org.springframework.beans.factory.annotation.Value;  
import org.springframework.stereotype.Component;  
  
@Component
public class WebConfigEntity {
	@Value("${title}")
	public String TITLE;

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		this.TITLE = tITLE;
	}

}
