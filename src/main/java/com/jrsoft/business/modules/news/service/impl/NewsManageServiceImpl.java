package com.jrsoft.business.modules.news.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.news.dao.NewsManageDao;
import com.jrsoft.business.modules.news.service.NewsManageServiceI;

@Service
@Transactional()
public class NewsManageServiceImpl implements NewsManageServiceI{

	
	@Autowired
	private NewsManageDao newsDao;
	
	
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
	@Override
	public List<Map<String, Object>> searchNewsList(String projUid, String userUid, String companyUid, String search,
			int start, int end, boolean isCompanyAdmin, String type, String dbType) {
		if(dbType.equals("mysql")) {
			int starts = start - 1;
			return newsDao.searchNewsListMySql(projUid, userUid, companyUid, search, starts, end, isCompanyAdmin, type);
		}else if(dbType.equals("mssql")) {
			return newsDao.searchNewsList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin, type);
		}
		return null;
	}

	
	/**
	 * 根据id查询新闻公告详情
	 */
	@Override
	public Map<String, Object> searchNewsById(String id, String userUid, String dbType) {
		if(dbType.equals("mysql")) {
			return newsDao.searchNewsByIdMySql(id, userUid, dbType);
		}else if(dbType.equals("mssql")) {
			return newsDao.searchNewsById(id, userUid);
		}
		return null;
	}

	
	/**
	 * 根据ids删除新闻公告详情
	 */
	@Override
	public void deleteNewsByIds(String[] ids) {
		newsDao.deleteNewsByIds(ids);
	}
	
	

}
