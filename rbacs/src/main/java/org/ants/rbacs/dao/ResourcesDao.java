package org.ants.rbacs.dao;

import java.util.List;

import org.ants.rbacs.model.ResourcesModel;
import org.ants.rbacs.model.RoleModel;
import org.apache.ibatis.annotations.Param;

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
	public List<ResourcesModel> selectAll(ResourcesModel rm);
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

	/**根据 id 更新 resources记录
	 * @param res
	 * @return int
	 */
	public int updateById(@Param("id")String id, @Param("summary")String summary, @Param("description") String description);
	/**
	 * 根据 id 删除 resources记录
	 * @param resId
	 * @return：int
	 */
	public int deleteById(@Param("id") String id);
	
	/**
	 * TODO
	 * @param resId
	 * @return：List<RoleModel>
	 */
	public List<RoleModel> queryResRoles(String resId);
	/**
	 * Insert Batch Swagger related data
	 * @param insertByBatch
	 * @return：int
	 */
	public int insert(List<ResourcesModel> insertLi);
	
	/**Update Batch Swagger related data 
	 * @param insertByBatch
	 * @return int
	 */
	public void updateById(ResourcesModel resourcesTable);
	
	/**TODO 根据path，req_method查询swagger_api中是否存在记录
	 * @param path
	 * @param reqMethod
	 * @return int
	 */
	public int existById(@Param("idMd5") String idMd5);
}
