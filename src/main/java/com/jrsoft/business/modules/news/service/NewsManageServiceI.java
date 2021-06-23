package com.jrsoft.business.modules.news.service;

import java.util.List;
import java.util.Map;

public interface NewsManageServiceI {
	
	
	/**
	 * 根据项目id查询新闻公告列表
	 * @param projUid	项目id
	 * @param userUid	用户id
	 * @param companyUid	公司id
	 * @param search	搜索框
	 * @param start		从第几条数据考试
	 * @param end		从第几条结束
	 * @param isCompanyAdmin	是否管理员
	 * @return
	 */
	List<Map<String, Object>> searchNewsList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin, String type, String dbType);
	
	
	/**
	 * 根据id查询新闻公告详情
	 * @param id
	 * @return
	 */
	Map<String, Object> searchNewsById(String id, String userUid, String dbType);

	
	/**
	 * 根据ids删除新闻公告详情
	 * @param ids 
	 */
	void deleteNewsByIds(String[] ids);
	
	
	
}
