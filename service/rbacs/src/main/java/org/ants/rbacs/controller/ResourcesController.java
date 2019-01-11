package org.ants.rbacs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.ants.common.entity.Result;
import org.ants.common.utils.HttpServiceUtil;
import org.ants.rbacs.entity.ConsoulServices;
import org.ants.rbacs.model.ResourcesModel;
import org.ants.rbacs.service.ConsoulService;
import org.ants.rbacs.service.ResourcesService;
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
 * @Description: resources Table Controller
 * @author: renkun
 * @date: 2018年12月13日上午9:15:56
 */
public class ResourcesController {
	
	@Autowired
	private ResourcesService resService;
	@Autowired
	private ConsoulService consoulService;
	
	@Value("${swagger.v2ApiDocs.mapping}")
	private String swaggerV2ApiDocsMapping;
	
	@ApiOperation(value="模糊查询 resources Table 接口信息，分页", notes="")
    @ApiImplicitParams({
	    	@ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
	        @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int"),
	    	@ApiImplicitParam(name = "path", value = "接口地址", required = true, dataType = "String"),
	    	@ApiImplicitParam(name = "reqMethod", value = "请求方式", required = true, dataType = "String"),
	    	@ApiImplicitParam(name = "summary", value = "接口概要", required = true, dataType = "String"),
            @ApiImplicitParam(name = "servicesName", value = "服务名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "description", value = "接口详细说明", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result getRes(@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, ResourcesModel rm) {
		PageInfo<ResourcesModel> pageInfo = resService.getResources(page, pageSize, rm);
		return Result.success(pageInfo);
	}
	@ApiOperation(value="修改 resources Table 接口信息", notes="修改 resources Table 接口信息（概要和详细说明）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "summary", value = "接口概要", required = true, dataType = "String"),
            @ApiImplicitParam(name = "description", value = "接口详细说明", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public Result updateRes(HttpServletRequest request,@QueryParam("id") String id, @QueryParam("summary") String summary, @QueryParam("description") String description) {
		
		int res = resService.updateResources(id,summary,description);
		return Result.success(res);
	}
	@ApiOperation(value="插入 resources Table 接口信息", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键标识", required = true, dataType = "String"),
            @ApiImplicitParam(name = "rm", value = "Resources Table 映射对象", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Result insertRes(ResourcesModel rm) {
		
		List<ResourcesModel> insertLi = new ArrayList<>();
		insertLi.add(rm);
		
		return resService.insertResources(insertLi);
	}
	@ApiOperation(value="删除 resources Table 接口信息，根据ID", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键标识", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public Result deleteRes(@QueryParam("id") String id) {
		return resService.deleteById(id);
	}
	@ApiOperation(value="权限验证", notes="检查用户是否拥有指定资源的权限")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户标识", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/auth" ,method = RequestMethod.GET)
	public Result checkPermit(HttpServletRequest request, String userId,
			@QueryParam("resId") String resId) {
		Result rlt = null;
		
		/*boolean isPermit;
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
		
		List<ResourcesModel> res = null;
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
