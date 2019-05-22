package org.bogies.rbacs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.bogies.common.constants.ResourceConstants;
import org.bogies.common.constants.RoleConstants;
import org.bogies.common.utils.EncryptUtil;
import org.bogies.rbacs.dao.rbac.ResourcesDao;
import org.bogies.rbacs.entity.ResourceEntity;
import org.bogies.rbacs.entity.RoleEntity;
import org.bogies.rbacs.entity.SwaggerApiDocs;
import org.bogies.rbacs.entity.SwaggerApiDocsPaths;
import org.bogies.rbacs.service.ResourcesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
/**
 * @Description: resources Table options
 * @author: renkun
 * @date: 2018年12月13日上午9:26:39
 */
public class ResourcesServiceImpl implements ResourcesService {
    private final Logger logger = LoggerFactory.getLogger(ResourcesServiceImpl.class);
	@Autowired
	private ResourcesDao resDao;
	
	@Override
	public int insert(ResourceEntity resInfo)  throws RuntimeException {
		if (null == resInfo.getType()) {
			throw new RuntimeException("请指定资源类型: API,PAGE,AUTH");
		}
		resInfo.setType(resInfo.getType().toUpperCase());
		if (StringUtils.isEmpty(resInfo.getId())) {
			String id;
			switch (resInfo.getType()) {
			case ResourceConstants.TYPE_API:
			case ResourceConstants.TYPE_PAGE:
				id = EncryptUtil.md5(resInfo.getPath()+resInfo.getReqMethod()+resInfo.getServiceName());
				break;
			case ResourceConstants.TYPE_AUTH:
				id = EncryptUtil.md5(UUID.randomUUID().toString());
				break;
			default:
				throw new RuntimeException("请指定资源类型: API,PAGE,AUTH");
			}
			resInfo.setId(id);
		}
		return resDao.insert(resInfo);
	}
	@Override
	public PageInfo<ResourceEntity> getResources(int page, int pageSize, ResourceEntity filter) {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<ResourceEntity> pageInfo = null;
		List<ResourceEntity> resourcesRecord = resDao.getResources(filter);
		if (null != resourcesRecord) {
			pageInfo = new PageInfo<ResourceEntity>(resourcesRecord);
		}
		return pageInfo;
	}
	@Override
	public PageInfo<ResourceEntity> getResourcesByUserId(int page, int pageSize, 
			String userId, ResourceEntity filter) throws RuntimeException {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<ResourceEntity> pageInfo = null;
		List<ResourceEntity> resourcesRecord = resDao.getResourcesByUserId(userId, filter);
		if (null != resourcesRecord) {
			pageInfo = new PageInfo<ResourceEntity>(resourcesRecord);
		}
		return pageInfo;
	}
	@Override
	public boolean checkPermitByUserId(String userId, String resId) {
		int count = resDao.checkPermitByUserId(userId, resId, RoleConstants.ROLE_ANONYMOUS.getRoleId());
		return (0 != count);
	}
	@Override
	public boolean checkPermitByRoleId(String roleId, String resId) {
		int count = resDao.checkPermitByRoleId(roleId, resId, RoleConstants.ROLE_ANONYMOUS.getRoleId());
		return (0 != count);
	}
	@Override
	public int update(ResourceEntity resInfo) throws RuntimeException {
		return resDao.update(resInfo);
	}
	@Override
	public int delete(String id) throws RuntimeException {
		if (StringUtils.isBlank(id)) {
			throw new RuntimeException("请设置要删除资源的ID");
		}
		
		return resDao.delete(id);
	}
	@Override
	public PageInfo<RoleEntity> getResourceRoles(int page, int pageSize, 
			String resId, String serviceName) throws RuntimeException {
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<RoleEntity> roleListPage = null;
		List<RoleEntity> roleList = resDao.getInRoles(resId, serviceName);
		if (null != roleList) {
			roleListPage = new PageInfo<RoleEntity>(roleList);
		}
		
		return roleListPage;
	}
	@Override
	public List<ResourceEntity> extractSwaggerApiDocs(String httpRes,String servicesName) {
		SwaggerApiDocs httpResJson = JSON.parseObject(httpRes, SwaggerApiDocs.class);
		Map<String, Map<String, SwaggerApiDocsPaths>> pathsMap = httpResJson.getPaths()==null?new HashMap<>():httpResJson.getPaths();
		List<ResourceEntity> insertLi = new ArrayList<ResourceEntity>();
		List<ResourceEntity> updateLi = new ArrayList<ResourceEntity>();
		
		for (String key : pathsMap.keySet()) {
			
			Map<String, SwaggerApiDocsPaths> value = pathsMap.get(key);
			for (String method : value.keySet()) {
				
				key = key.toLowerCase();
				method = method.toLowerCase();
				
				String idMd5 = EncryptUtil.md5(key+method+servicesName);
				ResourceEntity vda = new ResourceEntity();
				vda.setId(idMd5);
				vda.setPath(key);
				vda.setReqMethod(method);
				vda.setType("api");
				SwaggerApiDocsPaths swaggerV2ApiPaths = value.get(method);
				
				vda.setSummary(swaggerV2ApiPaths.getSummary());
				vda.setServiceName(servicesName);
				vda.setDescription(swaggerV2ApiPaths.getDescription());
				if(resDao.existById(idMd5)==0) {
					//insertLi.add(vda);
					resDao.insert(vda);
				}else {
					updateLi.add(vda);
					resDao.update(vda);
				}
				
			}
		}
		
		logger.info("<==    Updates: "+updateLi.size());
		updateLi.addAll(insertLi);
		logger.info("总共： "+updateLi.size()+" 条记录");
		if(updateLi.isEmpty()) {
			return null;
		}else {
			return updateLi;
		}
	}
}
