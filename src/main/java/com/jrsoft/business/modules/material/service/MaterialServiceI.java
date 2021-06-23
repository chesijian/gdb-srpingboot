package com.jrsoft.business.modules.material.service;

import java.util.List;
import java.util.Map;


public interface MaterialServiceI{
	
	
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
	List<Map<String, Object>> searchMaterialList(String projUid, int start, int end, String search,
			boolean isCompanyAdmin, String userUid, String companyUid, Integer status);
	
	
	/**
	 * 根据id查询物料
	 * @param id
	 * @return
	 */
	Map<String, Object> searchMaterialById(String id);

	
	/**
	 * 根据公司查询物资库
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> materialLibrary(String companyUid,String search, String projUid);

	
	/**
	 * 根据物资id查询检查项
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> searchInspectByMaterialId(String id, String projUid);

	
	/**
	 * 批量更新物料检查项状态
	 * @param id
	 * @param ids
	 */
	void updateInspectByMaterialId(String id, String ids);

	
	/**
	 * 编辑物资库
	 * @param id
	 * @return
	 */
	Map<String, Object> editMaterials(String id);

	
	/**
	 * 删除物资库
	 * @param id
	 */
	void deleteMaterials(String id);

	
	/**
	 * 删除物料
	 * @param id
	 */
	void delete(String id);

	
	/**
	 * 根据条形码id查询物资库
	 * @param barCode
	 * @return
	 */
	Map<String, Object> searchMaterialBarCode(String barCode);

	
	/**
	 * 查询过磅信息列表
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchMaterialWeigh(int start, int end, String search, 
			String userUid, String companyUid, int status);
	
	
}
