package org.bogies.rbacs.entity;

/**
 * @Description: The SwaggerApiDocsInfo entity of Swagger V2Api subdirectory info
 * @author: renkun
 * @date: 2018年12月18日上午11:26:04
 */
public class SwaggerApiDocsInfo {
	private String description;
	private String version;
	private String title;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}