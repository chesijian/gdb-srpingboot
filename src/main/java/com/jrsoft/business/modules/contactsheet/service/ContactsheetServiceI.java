package com.jrsoft.business.modules.contactsheet.service;

import java.util.List;
import java.util.Map;

public interface ContactsheetServiceI {
	
	
	/**
	 * 根据项目id查询工程联系单
	 * @param projUid
	 * @param start
	 * @param end
	 * @param search
	 * @param isCompanyAdmin
	 * @param userUid
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchContactsheetList(String projUid, int start, int end, String search,
			boolean isCompanyAdmin, String userUid, String companyUid,String dataType);
	
	
	/**
	 * 根据id查询联系单信息
	 * @param id
	 * @return
	 */
	Map<String, Object> searchContactsheetById(String id);

	
	/**
	 * 删除联系单
	 * @param id
	 */
	void delete(String id);

	
	/**
	 * 更新联系单审批状态
	 * @param id
	 * @param status
	 */
	void updateContactsheetStatusById(String id, Integer status);
	
	

}
