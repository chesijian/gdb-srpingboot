package com.jrsoft.business.modules.material.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface MaterialDao {
	
	
	/**
	 * 查询物料列表
	 * @param projUid
	 * @param start
	 * @param end
	 * @param search
	 * @param isCompanyAdmin
	 * @param userUid
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchMaterialList(@Param("projUid") String projUid, @Param("start") int start, @Param("end") int end, @Param("search") String search,
			@Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid, 
			@Param("companyUid") String companyUid, @Param("status") Integer status, @Param("dbType") String dbType);
	
	
	/**
	 * 根据id查询物料
	 * @param id
	 * @return
	 */
	Map<String, Object> searchMaterialById(@Param("id") String id, @Param("dbType") String dbType);

	
	/**
	 * 根据公司id查询物资库
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> materialLibrary(@Param("companyUid") String companyUid,@Param("search")String search,@Param("projUid")String projUid,@Param("dbType")String dbType);

	
	/**
	 * 根据id查询检查项
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchInspectById(@Param("id") String id, @Param("projUid") String projUid);

	
	/**
	 * 查询下级分部，子分部，子项
	 * @param id
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchSubordinateInspect(@Param("id") String id, @Param("projUid") String projUid);

	
	/**
	 * 批量更新物料检查项状态
	 * @param id
	 * @param ids
	 */
	void updateInspectByMaterialId(@Param("id") String id, @Param("ids") String ids);

	
	/**
	 * 编辑物资库
	 * @param id
	 * @return
	 */
	Map<String, Object> editMaterials(@Param("id") String id);

	
	/**
	 * 删除物资库
	 * @param id
	 */
	void deleteMaterials(@Param("id") String id);

	
	/**
	 * 删除物料明细
	 * @param id
	 */
	void deleteMaterialDetails(@Param("id") String id);

	
	/**
	 * 删除物料
	 * @param id
	 */
	void deleteMaterial(@Param("id") String id);

	
	/**
	 * 根据条形码id查询物资库
	 * @param barCode
	 * @return
	 */
	Map<String, Object> searchMaterialBarCode(@Param("barCode") String barCode);

	
	/**
	 * 查询过磅信息列表
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchMaterialWeigh(@Param("start") int start, @Param("end") int end, 
			@Param("search") String search, @Param("userUid") String userUid, 
			@Param("companyUid") String companyUid, @Param("status") int status, @Param("dbType") String dbType);
	
	

	
}
