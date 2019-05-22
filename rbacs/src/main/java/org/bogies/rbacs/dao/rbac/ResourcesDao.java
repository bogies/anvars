package org.bogies.rbacs.dao.rbac;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.bogies.rbacs.entity.ResourceEntity;
import org.bogies.rbacs.entity.RoleEntity;

/**
 * @Description: Database resources Table for this request
 * @author: renkun
 * @date: 2018年12月13日上午9:18:32
 */
public interface ResourcesDao {
	
	/**模糊查询 Resource Table 记录
	 * @param rm 
	 * @param res
	 * @return：List<ResourceModel>
	 */
	public List<ResourceEntity> getResources(ResourceEntity filter);
	public List<ResourceEntity> getResourcesByUserId(@Param("userId") String userId, @Param("filter") ResourceEntity filter);
	/**
	 * 检查用户资源权限
	 * @param userId
	 * @param resId
	 * @param anonymousId 匿名角色id
	 * @return 查询到的数量
	 */
	public int checkPermitByUserId(@Param("userId") String userId, @Param("resId") String resId, 
			@Param("anonymousId") String anonymousId);
	public int checkPermitByRoleId(@Param("roleId") String userId, @Param("resId") String resId, 
			@Param("anonymousId") String anonymousId);
	/**
	 * 根据 id 删除 resources记录
	 * @param resId
	 * @return：int
	 */
	public int delete(@Param("id") String id);
	
	/**
	 * TODO
	 * @param resId
	 * @return：List<RoleModel>
	 */
	public List<RoleEntity> getInRoles(@Param("resId") String resId, @Param("serviceName") String serviceName);
	/**
	 * Insert Batch Swagger related data
	 * @param insertByBatch
	 * @return：int
	 */
	public int insert(ResourceEntity resInfo);
	
	/**Update Batch Swagger related data 
	 * @param insertByBatch
	 * @return int
	 */
	public int update(ResourceEntity resInfo);
	
	/**TODO 根据path，req_method查询swagger_api中是否存在记录
	 * @param path
	 * @param reqMethod
	 * @return int
	 */
	public int existById(@Param("id") String id);
}
