package org.bogies.rbacs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;
import org.bogies.common.constants.ErrorConstants;
import org.bogies.common.entity.JWTPayloadEntity;
import org.bogies.common.entity.Result;
import org.bogies.common.utils.ServiceAuthToken;
import org.bogies.rbacs.config.JWTConfig;
import org.bogies.common.entity.MemberEntity;
import org.bogies.rbacs.service.MembersService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/member")
/**
 * @Description: 用户管理接口
 * @author: Jerry
 * @date: 2018年12月13日上午9:18:18
 */
public class MembersController {
	private Logger logger = LoggerFactory.getLogger(MembersController.class);
	/// 其他服务端验证时间戳允许误差, 60秒 
	private static final int TIMESTAMP_DEV = 60000;
	@Autowired
	private JWTConfig jwtConfig;
	@Autowired
	private MembersService userService;
	
	@ApiOperation(value="web服务端验证", notes="通过签名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "String"),
            @ApiImplicitParam(name = "timestamp", value = "时间戳", required = true, dataType = "long"),
            @ApiImplicitParam(name = "sign", value = "签名", required = true, dataType = "String")
    })
	@ResponseBody
	@RequestMapping(value = "/service/login", method = RequestMethod.POST)
	public Result serverLogin(HttpServletRequest request, String serviceName, 
			long timestamp, String sign) {
		long time = Math.abs(System.currentTimeMillis() - timestamp);
		if (StringUtils.isBlank(serviceName)) {
			return Result.fail(ErrorConstants.SE_REQ_PARAMS);
		}
		if (time > TIMESTAMP_DEV) {
			return Result.fail(ErrorConstants.SE_TOKEN_TIMEOUT);
		}
		
		String newSign = ServiceAuthToken.makeToken(serviceName, timestamp, jwtConfig.getSecret());
		Result rlt;
		if (newSign.equals(sign)) {
			JWTPayloadEntity payload = new JWTPayloadEntity(serviceName, serviceName);
			rlt = Result.success(jwtConfig.generateToken(payload));
		} else {
			rlt = Result.fail(ErrorConstants.SE_LOGIN_ERROR);
		}
		
		return rlt;
	}
	@ApiOperation(value="获取用户列表或查找用户", notes="")
    @ApiImplicitParams({
	    	@ApiImplicitParam(name="page", value = "请求的页码", required = true, dataType = "int"),
	        @ApiImplicitParam(name="pageSize", value = "每页数量", required = true, dataType = "int"),
	        @ApiImplicitParam(name="username", value="要查找用户名", required = true, dataType="String"),
            @ApiImplicitParam(name="nickname", value="要查找的姓名或昵称", required = true, dataType="String")
    })
	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Result getUsers(MemberEntity filter, @QueryParam("page") int page, 
			@QueryParam("pageSize") int pageSize) {
		
		PageInfo<MemberEntity> userList = userService.getUsers(filter, page, pageSize);
		Result res = Result.success(userList);
		return res;
	}
}
