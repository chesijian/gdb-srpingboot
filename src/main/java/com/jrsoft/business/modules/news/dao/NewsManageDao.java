package com.jrsoft.business.modules.news.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface NewsManageDao {
	
	
	/**
	 * 根据项目id查询新闻公告列表
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchNewsList(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("type")String type);
	
	
	/**
	 * 根据id查询新闻公告详情
	 * @param id
	 * @return
	 */
	Map<String, Object> searchNewsById(@Param("id")String id, @Param("userUid")String userUid);

	/**
	 * 根据ids删除新闻公告详情
	 * @param ids
	 */
	void deleteNewsByIds(@Param("ids")String[] ids);

	
	/**
	 * 根据项目id查询新闻公告列表(MySql)
	 * @param projUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @param start
	 * @param end
	 * @param isCompanyAdmin
	 * @return
	 */
	List<Map<String, Object>> searchNewsListMySql(@Param("projUid")String projUid, @Param("userUid")String userUid, @Param("companyUid")String companyUid, @Param("search")String search,
			@Param("start")int start, @Param("end")int end, @Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("type")String type);

	
	/**
	 * 根据id查询新闻公告详情
	 * @param id
	 * @param userUid
	 * @param dbType
	 * @return
	 */
	Map<String, Object> searchNewsByIdMySql(@Param("id") String id, @Param("userUid") String userUid, @Param("dbType") String dbType);

	
}
