package org.bogies.rbacs.entity;

/**
 * @Description: The SwaggerApiDocsPaths entity of Swagger V2Api subdirectory paths
 * @author: renkun
 * @date: 2018年12月18日上午11:18:45
 */
public class SwaggerApiDocsPaths {

	private String summary;
	private String operationId;
	private String deprecated;
	private String description;
	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}