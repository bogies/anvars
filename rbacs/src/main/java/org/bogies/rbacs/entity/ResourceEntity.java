package org.bogies.rbacs.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceEntity {
	private String id;
	private String type;
	private String path;
	private String reqMethod;
	private String summary;
	private String pageName;
	private String serviceName;
	private String description;
	private String extJson;
}