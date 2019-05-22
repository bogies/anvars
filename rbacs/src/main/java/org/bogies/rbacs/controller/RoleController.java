package org.bogies.rbacs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.bogies.common.constants.ErrorConstants;
import org.bogies.common.constants.RequestHeaderConstants;
import org.bogies.common.entity.Result;
import org.bogies.rbacs.entity.RoleEntity;
import org.bogies.rbacs.service.MembersService;
import org.bogies.rbacs.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/role")
/**
 * @Description: 角色管理
 * @author: Jerry
 * @date: 2018年12月13日上午9:18:04
 */
public class RoleController {
	private final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;
	@Autowired
	private MembersService memberService;

	@ApiOperation(value="获取角色列表", notes="根据过滤条件获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "要查询的角色名称", required = false, dataType = "String"),
            @ApiImplicitParam(name = "serviceName", value = "角色所属的服务", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每面数量", required = true, dataType = "int")
    })
	@ResponseBody
	@RequestMapping(value="", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public Result getRoles(HttpSession session, 
			@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, 
			RoleEntity roleFilter) {
		PageInfo<?> roleListPage = roleService.getRoles(roleFilter, page, pageSize);
		Result rlt = null;
		if (null == roleListPage) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "获取角色列表失败");
		} else {
			rlt = Result.success(roleListPage);
		}
		
		return rlt;
	}
	@ApiOperation(value="获取用户所属的角色列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(name = "serviceName", value = "所属的服务", required = false, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/byuserid", method=RequestMethod.GET)
	public Result getUserRoles(@QueryParam("userId") String userId,
			@QueryParam("serviceName") String serviceName, 
			@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, 
			HttpSession session) {
		if (StringUtils.isBlank(userId)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS);
		}
		
		Result rlt = null;
		if (StringUtils.isBlank(userId)) {
			rlt = Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "用户id不能为空");
		}
		
		PageInfo<?> roleList = roleService.getRolesByUserId(userId, serviceName, page, pageSize);
		if (null != roleList) {
			rlt = Result.success(roleList);
		} else {
			rlt = Result.fail("获取用户角色列表失败. UserID: "+userId);
		}
		return rlt;
	}
	@ApiOperation(value="添加角色", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "name", value = "角色名称", required = true, dataType = "String"),
        @ApiImplicitParam(name = "description", value = "角色描述", required = true, dataType = "String"),
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public Result add(HttpSession session, RoleEntity role) {
		int count = roleService.add(role);
		Result rlt = null;
		if (count > 0) {
			rlt = Result.success(role);
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "添加角色失败");
			LOGGER.error("添加角色失败! value: ?", JSON.toJSONString(role));
		}
		return rlt;
	}
	@ApiOperation(value="更新角色", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "要更新的角色ID", required = true, dataType = "String"),
    	@ApiImplicitParam(name = "name", value = "角色名称", required = true, dataType = "String"),
        @ApiImplicitParam(name = "description", value = "角色描述", required = true, dataType = "String"),
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.PUT, produces = "text/plain;charset=UTF-8")
	public Result update(HttpSession session, RoleEntity role) {
		int count = roleService.update(role);
		Result rlt = null;
		if (count > 0) {
			rlt = Result.success(role);
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "更新角色失败");
			LOGGER.error("更新角色失败! value: ?", JSON.toJSONString(role));
		}
		return rlt;
	}
	@ApiOperation(value="删除角色", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "要删除的角色ID", required = true, dataType = "String"),
    })
	@ResponseBody
	@RequestMapping(value = "", method=RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
	public Result delete(@QueryParam("id") String id, HttpSession session) {
		Result rlt = null;
		if (StringUtils.isBlank(id)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "id不能为空");
		}
		int count = roleService.delete(id);
		if (count > 0) {
			rlt = Result.success(id);
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "删除角色失败");
			LOGGER.error("删除角色失败. id: ?", id);
		}
		
		return rlt;
	}
	@ApiOperation(value="获取角色的资源列表", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
    	@ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int"),
    	@ApiImplicitParam(name = "roleId", value = "每角色id", required = true, dataType = "int")
    })
	@ResponseBody
	@RequestMapping(value = "/resources", method=RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public Result getRoleRes(HttpSession session, @QueryParam("page") int page, 
			@QueryParam("pageSize") int pageSize, @QueryParam("roleId") String roleId) {
		Result rlt = null;
		if (StringUtils.isBlank(roleId)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "角色id不能为空");
		}
		
		PageInfo<?> resPage = roleService.getRes(roleId, page, pageSize);
		if (null == resPage) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "获取角色用户列表失败");
			LOGGER.error("获取角色用户列表失败, 角色id: ?", roleId);
		} else {
			rlt = Result.success(resPage);
		}

		return rlt;
	}
	@ApiOperation(value="获取角色未授权的资源列表", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
    	@ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "int"),
    	@ApiImplicitParam(name = "roleId", value = "每角色id", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/unauth/resources", method=RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public Result getRoleUnauthRes(HttpSession session, @QueryParam("page") int page, 
			@QueryParam("pageSize") int pageSize, @QueryParam("roleId") String roleId) {
		Result rlt = null;
		if (StringUtils.isBlank(roleId)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS.getCode(), "角色id不能为空");
		}
		
		PageInfo<?> resPage = roleService.getUnauthRes(roleId, page, pageSize);
		if (null == resPage) {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "获取角色用户列表失败");
			LOGGER.error("获取角色用户列表失败, 角色id: ?", roleId);
		} else {
			rlt = Result.success(resPage);
		}

		return rlt;
	}
	@ApiOperation(value="添加资源到指定角色", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "roleId", value = "每角色id", required = true, dataType = "String"),
    	@ApiImplicitParam(name = "resIds", value = "资源ID列表,用逗号隔开", required = true, dataType = "String"), 
    	@ApiImplicitParam(name = "serviceName", value = "所属的服务名", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/resources", method=RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public Result addRoleRes(HttpSession session, @QueryParam("roleId") String roleId, 
			@QueryParam("resIds") String resIds, @QueryParam("serviceName") String serviceName) {
		if (StringUtils.isBlank(roleId) || StringUtils.isBlank(resIds) || StringUtils.isEmpty(serviceName)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS);
		}
		
		String[] resList = resIds.split(",");
		
		Result rlt = null;
		int count = roleService.addResource(roleId, resList, serviceName);
		if (count != 0) {
			rlt = Result.success();
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "添加资源到角色失败");
			LOGGER.error("添加资源到角色失败.角色id:?;资源列表:?", roleId, resIds);
		}
		
		return rlt;
	}
	@ApiOperation(value="删除资源从指定角色", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "roleId", value = "每角色id", required = true, dataType = "String"),
    	@ApiImplicitParam(name = "resIds", value = "资源ID列表,用逗号隔开", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/resources", method=RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
	public Result removeRoleRes(HttpSession session, String roleId, String resIds) {
		if (StringUtils.isBlank(roleId) || StringUtils.isBlank(resIds)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS);
		}
		resIds = "'"+resIds.replace(",", "','")+"'";
				
		Result rlt = null;
		/// 删除旧的分配资源
		int count = roleService.removeResource(roleId, resIds);
		if (count != 0) {
			rlt = Result.success();
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "删除资源到角色失败");
			LOGGER.error("删除资源到角色失败.角色id:?;资源列表:?", roleId, resIds);
		}
		
		return rlt;
	}
	@ApiOperation(value="获取角色中的用户列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每面数量", required = true, dataType = "int")
    })
	@ResponseBody
	@RequestMapping(value="/members", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public Result getMembers(HttpSession session, 
			@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, 
			@QueryParam("roleId") String roleId) {
		if (StringUtils.isEmpty(roleId)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS);
		}
		Result rlt = null;
		try {
			List<String> memberIdList = roleService.getMemberIds(roleId);
			PageInfo<?> membersPage = memberService.getIncludeMembers(memberIdList, page, pageSize);
			rlt = Result.success(membersPage);
		} catch (Exception e) {
			rlt = Result.fail(e.getCause().toString());
		}

		return rlt;
	}
	@ApiOperation(value="获取角色中未设置的的用户列表", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "请求的页码", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每面数量", required = true, dataType = "int")
    })
	@ResponseBody
	@RequestMapping(value="/unauth/members", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public Result getUnauthMembers(HttpSession session, 
			@QueryParam("page") int page, @QueryParam("pageSize") int pageSize, 
			@QueryParam("roleId") String roleId) {
		if (StringUtils.isEmpty(roleId)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS);
		}
		Result rlt = null;
		try {
			List<String> memberIdList = roleService.getMemberIds(roleId);
			PageInfo<?> membersPage = memberService.getExcludeMembers(memberIdList, page, pageSize);
			rlt = Result.success(membersPage);
		} catch (Exception e) {
			rlt = Result.fail(e.getCause().toString());
		}

		return rlt;
	}
	@ApiOperation(value="添加用户到指定的角色", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "String"),
            @ApiImplicitParam(name = "userIds", value = "用户ID列表, 用逗号隔开", required = true, dataType = "int"),
            @ApiImplicitParam(name = "serviceName", value = "角色所属的服务", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/member", method=RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public Result addUsers(HttpServletRequest request, @QueryParam("roleId") String roleId, 
			@QueryParam("userIds") String userIds, 
			@QueryParam("serviceName") String serviceName) {
		Result rlt = null;
		if (StringUtils.isBlank(roleId) || StringUtils.isEmpty(userIds) || 
				StringUtils.isEmpty(serviceName)) {
			rlt = Result.fail(ErrorConstants.SE_REQ_PARAMS);
			return rlt;
		}
		String[] userList = userIds.split(",");
		int count = roleService.addUser(roleId, userList, serviceName);
		if (count > 0) {
			rlt = Result.success();
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "角色分配用户失败");
			LOGGER.error("添加用户到角色失败. 角色id: ?;用户列表: ?", roleId, userIds);
		}
		return rlt;
	}
	@ApiOperation(value="删除用户从指定的角色", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色id", required = false, dataType = "String"),
            @ApiImplicitParam(name = "userIds", value = "用户ID列表, 用逗号隔开", required = true, dataType = "int"),
    })
	@ResponseBody
	@RequestMapping(value = "/member", method=RequestMethod.DELETE, produces = "text/plain;charset=UTF-8")
	public Result removeUsers(HttpSession session, String roleId, String userIds) {
		Result rlt = null;
		if (StringUtils.isBlank(roleId) || StringUtils.isEmpty(userIds)) {
			rlt = Result.fail(ErrorConstants.SE_REQ_PARAMS);
			return rlt;
		}
		
		userIds = "'"+userIds.replace(",", "','")+"'";

		int count = roleService.removeUser(roleId, userIds);
		if (count > 0) {
			rlt = Result.success();
		} else {
			rlt = Result.fail(ErrorConstants.SE_INTERNAL.getCode(), "删除用户从指定角色失败");
			LOGGER.error("删除用户从角色. 角色id: ?;用户列表: ?", roleId, userIds);
		}
		return rlt;
	}
}
