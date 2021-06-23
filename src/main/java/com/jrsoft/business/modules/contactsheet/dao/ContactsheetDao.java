package com.jrsoft.business.modules.contactsheet.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

public interface ContactsheetDao {
	
	
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
	List<Map<String, Object>> searchContactsheetList(@Param("projUid") String projUid, @Param("start") int start, @Param("end") int end, @Param("search") String search,
			@Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid, @Param("companyUid") String companyUid,
													 @Param("dbType") String dbType,@Param("dataType")String dataType);
	
	
	/**
	 * 根据id查询联系单信息
	 * @param id
	 * @return
	 */
	Map<String, Object> searchContactsheetById(@Param("id") String id, @Param("dbType") String dbType);

	
	/**
	 * 删除通知人与抄送人
	 * @param id
	 */
	void deletePersonnel(@Param("id") String id);

	
	/**
	 * 删除联系单
	 * @param id
	 */
	void deleteContactsheet(@Param("id") String id);

	
	/**
	 * 更新联系单审批状态
	 * @param id
	 * @param status
	 */
	void updateContactsheetStatusById(@Param("id") String id, @Param("status") Integer status);


}
