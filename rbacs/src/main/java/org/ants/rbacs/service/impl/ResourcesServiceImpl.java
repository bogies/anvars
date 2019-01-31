package org.ants.rbacs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import org.ants.common.constants.ErrorConstants;
import org.ants.common.constants.RoleConstants;
import org.ants.common.entity.Result;
import org.ants.common.utils.EncryptUtil;
import org.ants.rbacs.dao.ResourcesDao;
import org.ants.rbacs.entity.SwaggerApiDocs;
import org.ants.rbacs.entity.SwaggerApiDocsPaths;
import org.ants.rbacs.model.ResourcesModel;
import org.ants.rbacs.model.RoleModel;
import org.ants.rbacs.service.ResourcesService;
import org.apache.commons.lang3.StringUtils;

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
	
	private Gson gson = new Gson();
	
	@Override
	public Result insertResources(List<ResourcesModel> insertLi) {
		
		insertLi.forEach(args->{
			String idMd5 = EncryptUtil.md5(args.getPath().toLowerCase()+args.getReqMethod().toLowerCase()+args.getServicesName());
			args.setId(idMd5);
		});
		for (ResourcesModel rm : insertLi) {
			if(resDao.existById(rm.getId())!=0) {
				return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "存在重复主键");
			}
		}
		int res = resDao.insert(insertLi);
		return Result.success(res);
	}
	@Override
	public PageInfo<ResourcesModel> getResources(int page, int pageSize,ResourcesModel rm) {
		
		if (page < 1) {
			page = 1;
		}
		if (pageSize < 1) {
			pageSize = 10;
		}
		PageHelper.startPage(page, pageSize);
		PageInfo<ResourcesModel> pageInfo = null;
		List<ResourcesModel> resourcesRecord = resDao.selectAll(rm);
		if (null != resourcesRecord) {
			pageInfo = new PageInfo<ResourcesModel>(resourcesRecord);
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
	public int updateResources(String id, String summary, String description) {
		return resDao.updateById(id,summary,description);
	}
	@Override
	public Result deleteById(String id) {
		
		Result rlt = null;
		if (StringUtils.isBlank(id)) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "id null");
		} else {
			int deleteCount = resDao.deleteById(id);
			if (deleteCount > 0) {
				rlt = Result.success(id);
			} else {
				rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "删除失败");
			}
		}
		
		return rlt;
	}
	@Override
	public List<RoleModel> getResourceRoles(String resId) {
		return resDao.queryResRoles(resId);
	}
	@Override
	public List<ResourcesModel> extractSwaggerApiDocs(String httpRes,String servicesName) {

		SwaggerApiDocs httpResJson = gson.fromJson(httpRes, SwaggerApiDocs.class);
		Map<String, Map<String, SwaggerApiDocsPaths>> pathsMap = httpResJson.getPaths()==null?new HashMap<>():httpResJson.getPaths();
		List<ResourcesModel> insertLi = new ArrayList<ResourcesModel>();
		List<ResourcesModel> updateLi = new ArrayList<ResourcesModel>();
		
		for (String key : pathsMap.keySet()) {
			
			Map<String, SwaggerApiDocsPaths> value = pathsMap.get(key);
			for (String method : value.keySet()) {
				
				key = key.toLowerCase();
				method = method.toLowerCase();
				
				String idMd5 = EncryptUtil.md5(key+method+servicesName);
				ResourcesModel vda = new ResourcesModel();
				vda.setId(idMd5);
				vda.setPath(key);
				vda.setReqMethod(method);
				vda.setType("api");
				SwaggerApiDocsPaths swaggerV2ApiPaths = value.get(method);
				
				vda.setSummary(swaggerV2ApiPaths.getSummary());
				vda.setServicesName(servicesName);
				vda.setDescription(swaggerV2ApiPaths.getDescription());
				if(resDao.existById(idMd5)==0) {
					insertLi.add(vda);
				}else {
					updateLi.add(vda);
				}
				
			}
		}
		
		if(!insertLi.isEmpty()) {
			resDao.insert(insertLi);
		}
		for (ResourcesModel resourcesTable : updateLi) {
			resDao.updateById(resourcesTable);
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
