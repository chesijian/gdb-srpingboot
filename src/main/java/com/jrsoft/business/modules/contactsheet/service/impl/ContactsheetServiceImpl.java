package com.jrsoft.business.modules.contactsheet.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.contactsheet.dao.ContactsheetDao;
import com.jrsoft.business.modules.contactsheet.service.ContactsheetServiceI;
import com.jrsoft.engine.common.utils.CommonUtil;

@Service
@Transactional()
public class ContactsheetServiceImpl implements ContactsheetServiceI{
	
	@Autowired
	private ContactsheetDao contactsheetDao;

	
	@Override
	public List<Map<String, Object>> searchContactsheetList(String projUid, int start, int end, String search,
			boolean isCompanyAdmin, String userUid, String companyUid,String dataType) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return contactsheetDao.searchContactsheetList(projUid, start, end, search, isCompanyAdmin, userUid, companyUid, dbType,dataType);
	}


	@Override
	public Map<String, Object> searchContactsheetById(String id) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return contactsheetDao.searchContactsheetById(id, dbType);
	}


	@Override
	public void delete(String id) {
		//删除通知人与抄送人
		contactsheetDao.deletePersonnel(id);
		//删除联系单
		contactsheetDao.deleteContactsheet(id);
	}


	@Override
	public void updateContactsheetStatusById(String id, Integer status) {
		contactsheetDao.updateContactsheetStatusById(id, status);
	}
	
	


}
