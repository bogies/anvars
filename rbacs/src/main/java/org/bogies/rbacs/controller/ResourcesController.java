package org.bogies.rbacs.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;
import org.bogies.common.constants.ErrorConstants;
import org.bogies.common.entity.Result;
import org.bogies.common.utils.HttpServiceUtil;
import org.bogies.rbacs.entity.ConsoulServices;
import org.bogies.rbacs.entity.ResourceEntity;
import org.bogies.rbacs.service.ConsoulService;
import org.bogies.rbacs.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/resources")
/**
 * @Description: 资源管理
 * @author: Jerry
 * @date: 2018年12月13日上午9:15:56
 */
public class ResourcesController {
	@Autowired
	private ResourcesService resService;
	@Autowired
	private ConsoulService consoulService;
	@Value("${swagger.v2ApiDocs.mapping}")
	private String swaggerV2ApiDocsMapping;
	
	@ApiOperation(value="获取资源列表", notes="")
    @ApiImplicitParams({
	    	@ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "type", value = "接口类型", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "path", value = "接口地址", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "reqMethod", value = "请求方式", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "summary", value = "接口概要", required = false, dataType = "String"),
            @ApiImplicitParam(name = "servicesName", value = "服务名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageName", value = "相关页面", required = false, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result getRes(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, 
			ResourceEntity filter) {
		PageInfo<ResourceEntity> pageInfo = resService.getResources(page, pageSize, filter);
		return Result.success(pageInfo);
	}
	@ApiOperation(value="获取已授权给用户的资源列表", notes="")
    @ApiImplicitParams({
	    	@ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "userId", value = "相关页面", required = false, dataType = "String"),
	        @ApiImplicitParam(name = "type", value = "接口类型", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "path", value = "接口地址", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "reqMethod", value = "请求方式", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "summary", value = "接口概要", required = false, dataType = "String"),
            @ApiImplicitParam(name = "servicesName", value = "服务名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageName", value = "相关页面", required = false, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/byuserid", method = RequestMethod.GET)
	public Result getResByUserId(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, 
			@QueryParam("userId") String userId, ResourceEntity filter) {
		PageInfo<ResourceEntity> pageInfo = resService.getResourcesByUserId(page, pageSize, userId, filter);
		return Result.success(pageInfo);
	}
	@ApiOperation(value="修改资源", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "type", value = "接口类型", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "path", value = "接口地址", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "reqMethod", value = "请求方式", required = false, dataType = "String"),
	    	@ApiImplicitParam(name = "summary", value = "接口概要", required = false, dataType = "String"),
            @ApiImplicitParam(name = "servicesName", value = "服务名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pageName", value = "相关页面", required = false, dataType = "String"),
            @ApiImplicitParam(name = "description", value = "接口详细说明", required = false, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Result updateRes(HttpServletRequest request, ResourceEntity resInfo) {
		int res = resService.update(resInfo);
		return Result.success(res);
	}
	@ApiOperation(value="添加资源", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "主键标识", required = true, dataType = "String"),
    	@ApiImplicitParam(name = "type", value = "接口类型", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "path", value = "接口地址", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "reqMethod", value = "请求方式", required = false, dataType = "String"),
    	@ApiImplicitParam(name = "summary", value = "接口概要", required = false, dataType = "String"),
        @ApiImplicitParam(name = "servicesName", value = "服务名", required = false, dataType = "String"),
        @ApiImplicitParam(name = "pageName", value = "相关页面", required = false, dataType = "String"),
        @ApiImplicitParam(name = "description", value = "接口详细说明", required = false, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result insertRes(ResourceEntity resInfo) {
		Result rlt;
		try {
			int count = resService.insert(resInfo);
			rlt = Result.success(count);
		} catch (Exception e) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), e.getCause().toString());
		}
		
		return rlt;
	}
	@ApiOperation(value="根据ID删除资源", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键标识", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public Result deleteRes(@QueryParam("id") String id) {
		Result rlt;
		try {
			int count = resService.delete(id);
			rlt = Result.success(count);
		} catch (Exception e) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), e.getCause().toString());
		}
		
		return rlt;
	}
	@ApiOperation(value="获取资源所属服务的角色列表", notes="根据资源ID获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每面数量", required = true, dataType = "int"),
            @ApiImplicitParam(name = "resId", value = "资源id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "serviceName", value = "服务标识", required = false, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value="/roles", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public Result getInRoles(HttpSession session, 
			@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, 
			@QueryParam("resId") String resId, @QueryParam("serviceName") String serviceName) {
		PageInfo<?> roleListPage = resService.getResourceRoles(page, pageSize, resId, serviceName);
		Result rlt = null;
		if (null == roleListPage) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "获取角色列表失败");
		} else {
			rlt = Result.success(roleListPage);
		}
		
		return rlt;
	}
	@ApiOperation(value="权限验证", notes="检查用户是否拥有指定资源的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/auth" ,method = RequestMethod.GET)
	public Result checkPermit(HttpServletRequest request, @QueryParam("resId") String resId) {
		Result rlt = null;
		
		/*boolean isPermit;
		String userId = request.getHeader(RequestHeaderConstants.USER_ID);
		if (StringUtils.isEmpty(userId)) {
			isPermit = resService.checkPermitByRoleId(RoleConstants.ROLE_ANONYMOUS.getRoleId(), resId);
		} else {
			isPermit = resService.checkPermitByUserId(userId, resId);
		}
		if (isPermit) {
			rlt = Result.success();
		} else {
			rlt = Result.fail(ErrorConstants.SE_UNAUTHORIZED);
		}*/
		// 测试代码, 开放所有权限
		rlt = Result.success();
		
		return rlt;
	}
	@ApiOperation(value="Add Swagger v2-api Paths", notes="将Swagger v2-api Paths 添加至 resources 表中")
	@ResponseBody
	@RequestMapping(value = "/swaggerV2ApiPaths", method = RequestMethod.GET)
	public Result swaggerV2ApiPaths(HttpServletRequest request) {
		
		List<ResourceEntity> res = null;
		Map<String, ConsoulServices> consoulInfos = consoulService.getInfos();
		if(consoulInfos!=null) {
			
			for (String key : consoulInfos.keySet()) {
				
				ConsoulServices cse = consoulInfos.get(key);
				String url = "http://"+cse.getAddress()+":"+cse.getPort()+swaggerV2ApiDocsMapping;
				String httpRes = new HttpServiceUtil().getHttpRes(url,"GET", null);
				if (httpRes!=null) {
					res = resService.extractSwaggerApiDocs(httpRes,cse.getService());
				}
			}
		}
		return Result.success(res);
	}
}
