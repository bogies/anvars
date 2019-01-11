package org.ants.rbacs.entity;

import java.util.Map;

/**
 * @Description: Swagger related data
 * @author: renkun
 * @date: 2018年12月13日上午9:19:43
 */
public class SwaggerApiDocs {
	
//	private String swagger;
//	private SwaggerApiDocsInfo info;
//	private String host;
//	private String basePath;
//	private List<SwaggerApiDocsTags> tags;
	private Map<String, Map<String, SwaggerApiDocsPaths>> paths;
//	private Map<String, SwaggerApiDocsDefinitions> definitions;
	
	public Map<String, Map<String, SwaggerApiDocsPaths>> getPaths() {
		return paths;
	}

	public void setPaths(Map<String, Map<String, SwaggerApiDocsPaths>> paths) {
		this.paths = paths;
	}
}