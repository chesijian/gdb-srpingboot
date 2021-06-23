package com.jrsoft.business.modules.fileManage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.model.org.OrgRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.fileManage.dao.FileManageDao;
import com.jrsoft.business.modules.fileManage.model.FileBean;
import com.jrsoft.business.modules.fileManage.service.FileManageServiceI;
import com.jrsoft.engine.common.utils.CommonUtil;

@Service
@Transactional()
public class FileManageServiceImpl implements FileManageServiceI{
	
	@Autowired
	private FileManageDao fileManageDao;
	
	@Override
	public List<Map<String, Object>> searchBypRrojectId(String projUid, String parentUid, String userUid, String companyUid, boolean isCompanyAdmin, String search) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return fileManageDao.searchBypRrojectId(projUid, parentUid, userUid, companyUid, isCompanyAdmin, search, dbType);
	}
	
	
	@Override
	public Boolean moveFolder(String checkedId, String moveId) {
		return fileManageDao.moveFolder(checkedId, moveId);
	}
	
	
	@Override
	public List<Map<String, Object>> subordinateFiles(String id_, String userUid, boolean isCompanyAdmin) {
		return fileManageDao.subordinateFiles(id_, userUid, isCompanyAdmin);
	}
	
	
	@Override
	public Boolean deleteTop(String arrId) {
		return fileManageDao.deleteTop(arrId);
	}
	
	
	@Override
	public Map<String, Object> updateFileName(String name, String id) {
		return fileManageDao.updateFileName(name, id);
	}
	
	
	@Override
	public int checkFileName(String parentUid, String companyUid, String fileName, String projUid) {
		return fileManageDao.checkFileName(parentUid, companyUid, fileName, projUid);
	}


	@Override
	public List<Map<String, Object>> checkMoveNoOneself(String parentUid, String id) {
		return fileManageDao.checkMoveNoOneself(parentUid, id);
	}


	@Override
	public Map<String, Object> updateFile(String id) {
		return fileManageDao.updateFile(id);
	}


	@Override
	public void moveFile(String checkedId, String moveId) {
		String[] ids = checkedId.split(",");
		fileManageDao.moveFile(ids, moveId);
	}


	@Override
	public void removeFile(String[] ids) {
		fileManageDao.removeFile(ids);
	}


	@Override
	public List<Map<String, Object>> searchFolderRecursion(String projUid, String parentUid, String userUid, String companyUid, boolean isCompanyAdmin) {
		//根据项目id查询第一层文件夹
		List<OrgRole> roleList = SessionUtil.getRoleSet();
		List<Map<String, Object>> list = fileManageDao.searchFolderList(projUid, parentUid, userUid, companyUid, isCompanyAdmin,roleList);
		if(list.size() > 0) {
			for(Map<String, Object> map : list) {
				String id = (String) map.get("id");
				List<Map<String, Object>> list2 = fileManageDao.searchBelow(id, userUid, companyUid, isCompanyAdmin,roleList);
				map.put("children", list2);	
				if(list2.size() > 0) {
					searchNxit(list2, userUid, companyUid, isCompanyAdmin);
				}
			}
		}
		
		return list;
	}


	private void searchNxit(List<Map<String, Object>> list2, String userUid, String companyUid, boolean isCompanyAdmin) {
		List<OrgRole> roleList = SessionUtil.getRoleSet();
		for(Map<String, Object> map : list2) {
			String id = (String) map.get("id");
			List<Map<String, Object>> list3 = fileManageDao.searchBelow(id, userUid, companyUid, isCompanyAdmin,roleList);
			map.put("children", list3);
			if(list3.size() > 0 ) {
				searchNxit(list3, userUid, companyUid, isCompanyAdmin);
			}
		}
	}


	@Override
	public ReturnPageJson searchAccessoryByFolderId(String projUid, String id, int pageIndex, int pageSize,
													String search, boolean isCompanyAdmin, String userUid, String companyUid) {
		int start = (pageIndex-1)*pageSize;
		List<OrgRole> roleList =SessionUtil.getRoleSet();
		List<Map<String, Object>> data = fileManageDao.searchAccessoryByFolderIdMySql(projUid, id, start, pageSize, search, isCompanyAdmin,
				userUid, companyUid,roleList);
		int totalCount = fileManageDao.searchAccessoryTotalCount(projUid, id, search, isCompanyAdmin,
				userUid, companyUid,roleList);

		ReturnPageJson returnJson = new ReturnPageJson();
		returnJson.setData(data);
		returnJson.setTotalCount(totalCount);
		return returnJson;
	}


	@Override
	public List<Map<String, Object>> searchFileBypRrojectId(String projUid, String parentUid, String userUid,
			String companyUid, boolean isCompanyAdmin, String search) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return fileManageDao.searchFileBypRrojectId(projUid, parentUid, userUid, companyUid, isCompanyAdmin, search, dbType);
	}


	@Override
	public Map<String, Object> updateFolder(String id, String dbType) {
		return fileManageDao.updateFolder(id, dbType);
	}


	@Override
	public List<FileBean> getFileList(String id, String dbType) {
		return fileManageDao.getFileList(id, dbType);
	}


	@Override
	public Map<String, Object> getFileLists(String projUid, String id) {
		return fileManageDao.getFileLists(projUid, id);
	}


	@Override
	public void searchByCatalogue(String catalogueId, String projUid, String parentUid) {
		//insert 选中的目录
		Map<String, Object> resultMap = fileManageDao.searchSelectCatalogue(catalogueId);
		String selectId = (String) resultMap.get("ID_");  //id
		String selectCreateUser = (String) resultMap.get("CREATE_USER_");  //创建人
		Date selectCreateTime = (Date) resultMap.get("CREATE_TIME_");  //创建时间
		String selectUpdateUser = (String) resultMap.get("UPDATE_USER_");  //更新人
		Date selectUpdateTime = (Date) resultMap.get("UPDATE_TIME_");  //更新时间
		String selectName = (String) resultMap.get("NAME_");  //名称
		// PARENT_UID_  新增文件夹项目保存的id
		String selectCompany = (String) resultMap.get("COMPANY_");  //公司编码
		String selectSubCompany = (String) resultMap.get("SUB_COMPANY_");  //子公司编码
		Integer selectLb = (Integer) resultMap.get("LB_");  //类别
		//insert保存
		String uid = UUID.randomUUID().toString();
		fileManageDao.insertSelectEntity(uid, projUid, selectCreateUser, selectCreateTime, selectUpdateUser, selectUpdateTime, selectName, selectCompany, selectSubCompany, selectLb);
		// 查询目录下的第一层
		List<Map<String, Object>> firstCatalogueList = fileManageDao.searchFirstCatalogue(catalogueId);
		//遍历
		if(firstCatalogueList.size() > 0) {
			for (Map<String, Object> m : firstCatalogueList) {
				String id = (String) m.get("ID_");  //id
				String createUser = (String) m.get("CREATE_USER_");  //创建人
				Date createTime = (Date) m.get("CREATE_TIME_");  //创建时间
				String updateUser = (String) m.get("UPDATE_USER_");  //更新人
				Date updateTime = (Date) m.get("UPDATE_TIME_");  //更新时间
				String name = (String) m.get("NAME_");  //名称
				// PARENT_UID_  新增文件夹项目保存的id
				String company = (String) m.get("COMPANY_");  //公司编码
				String subCompany = (String) m.get("SUB_COMPANY_");  //子公司编码
				Integer lb = (Integer) m.get("LB_");  //类别
				//insert保存
				String keyId = UUID.randomUUID().toString();
				// uid 上级id
				// keyId 主键id
				fileManageDao.insertEntity(keyId, uid, createUser, createTime, updateUser, updateTime, name, company, subCompany, lb);
				
				NextCatalogues(id, keyId);
				
			}
		}
	}
	
	
	
	/**
	 * Xu
	 * 递归insert文件及文件夹
	 * @param id
	 */
	private void NextCatalogues(String id, String keyId) {
		//下一级目录
		List<Map<String, Object>> NextCatalogueList = fileManageDao.searchNextCatalogue(id);
		
		if(NextCatalogueList.size() > 0) {
			for (Map<String, Object> f : NextCatalogueList) {
				String id2 = (String) f.get("ID_");  //创建人
				String createUser2 = (String) f.get("CREATE_USER_");  //创建人
				Date createTime2 = (Date) f.get("CREATE_TIME_");  //创建时间
				String updateUser2 = (String) f.get("UPDATE_USER_");  //更新人
				Date updateTime2 = (Date) f.get("UPDATE_TIME_");  //更新时间
				String name2 = (String) f.get("NAME_");  //名称
				// PARENT_UID_  新增文件夹项目保存的id
				String company2 = (String) f.get("COMPANY_");  //公司编码
				String subCompany2 = (String) f.get("SUB_COMPANY_");  //子公司编码
				Integer lb2 = (Integer) f.get("LB_");  //类别
				//insert保存
				String pid = UUID.randomUUID().toString();
				fileManageDao.insertEntity(pid, keyId, createUser2, createTime2, updateUser2, updateTime2, name2, company2, subCompany2, lb2);
				
				List<Map<String, Object>> NextCatalogueList2 = fileManageDao.searchNextCatalogue(id2);
				if(NextCatalogueList2.size() > 0) {
					NextCatalogues(id2,pid);
				}
			}
		}
	}
	
	
	@Override
	public List<Map<String, Object>> searchDirectoryList(String parentUid,String companyUid) {
		List<Map<String, Object>> data = fileManageDao.searchDirectoryList(parentUid,companyUid);
		if(data.size() > 0) {
			for(Map<String, Object> map : data) {
				String id = (String)map.get("id");
				List<Map<String, Object>> data2 = fileManageDao.searchDirectoryList(id,companyUid);
				if(data2.size() > 0) {
					map.put("leaf", false);
				}else {
					map.put("leaf", true);
					
				}
			}
		}
		
		return data;
	}


	@Override
	public void removeCatalogue(String[] idss) {
		fileManageDao.removeCatalogue(idss);
	}


	@Override
	public Map<String, Object> searchCatalogueById(String id) {
		return fileManageDao.searchCatalogueById(id);
	}

	@Override
	public List<Map<String, Object>> getDocumentDirectoryTemplate(String parentUid) {
		String companyUid = SessionUtil.getCompanyUid();
		List<Map<String, Object>> data = fileManageDao.searchDirectoryList(parentUid,companyUid);
		if(data.size() > 0) {
			for(Map<String, Object> map : data) {
				String id = (String)map.get("id");
				List<Map<String, Object>> data2 = getDocumentDirectoryTemplate(id);
				if(data2.size() > 0) {
					map.put("leaf", false);
					map.put("children",data2);
				}else {
					map.put("leaf", true);

				}
			}
		}
		return data;
	}
}
