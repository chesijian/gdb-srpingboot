package com.jrsoft.business.modules.material.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.material.dao.MaterialDao;
import com.jrsoft.business.modules.material.model.Material;
import com.jrsoft.business.modules.material.repository.MaterialRepository;
import com.jrsoft.business.modules.material.service.MaterialServiceI;
import com.jrsoft.engine.base.service.BaseServiceImpl;
import com.jrsoft.engine.common.utils.CommonUtil;

@Service
@Transactional()
public class MaterialServiceImpl implements MaterialServiceI{

	
	@Autowired
	private MaterialDao materialDao;
	
	
	@Override
	public List<Map<String, Object>> searchMaterialList(String projUid, int start, int end, String search,
			boolean isCompanyAdmin, String userUid, String companyUid, Integer status) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return materialDao.searchMaterialList(projUid, start, end, search,
				isCompanyAdmin, userUid, companyUid, status, dbType);
	}


	@Override
	public Map<String, Object> searchMaterialById(String id) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return materialDao.searchMaterialById(id, dbType);
	}


	@Override
	public List<Map<String, Object>> materialLibrary(String companyUid,String search, String projUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return materialDao.materialLibrary(companyUid,search,projUid,dbType);
	}


	@Override
	public List<Map<String, Object>> searchInspectByMaterialId(String id, String projUid) {
		// 根据id查询检查项(用于保存所有的检查项)
		List<Map<String, Object>> list = materialDao.searchInspectById(id, projUid);
		
		//查询下级分部，子分部，子项
		List<Map<String, Object>> inspectList = materialDao.searchSubordinateInspect(id, projUid);
		if(inspectList.size() > 0) {
			for(Map<String, Object> map : inspectList) {
				String inspectId = (String)map.get("id");
				List<Map<String, Object>> list1 = materialDao.searchInspectById(inspectId, projUid);
				if(list1.size() > 0) {
					for(Map<String, Object> map1 : inspectList) {
						list.add(map1);
					}
				}
				List<Map<String, Object>> inspectList1 = materialDao.searchSubordinateInspect(inspectId, projUid);
				if(inspectList1.size() > 0) {
					//递归
					recursionInspect(inspectId, projUid, inspectList1, list);
				}
			}
		}
		
		return list;
	}


	private void recursionInspect(String inspectId, String projUid, List<Map<String, Object>> inspectList,
			List<Map<String, Object>> list) {
		for(Map<String, Object> map : inspectList) {
			String id = (String)map.get("id");
			
			List<Map<String, Object>> list1 = materialDao.searchInspectById(id, projUid);
			if(list1.size() > 0) {
				for(Map<String, Object> map1 : list1) {
					list.add(map1);
				}
			}
			List<Map<String, Object>> inspectList1 = materialDao.searchSubordinateInspect(id, projUid);
			if(inspectList1.size() > 0) {
				//递归
				recursionInspect(id, projUid, inspectList1, list);
			}
		}
	}


	@Override
	public void updateInspectByMaterialId(String id, String ids) {
		materialDao.updateInspectByMaterialId(id,ids);
	}


	@Override
	public Map<String, Object> editMaterials(String id) {
		return materialDao.editMaterials(id);
	}


	@Override
	public void deleteMaterials(String id) {
		materialDao.deleteMaterials(id);
	}


	@Override
	public void delete(String id) {
		//删除物料明细
		materialDao.deleteMaterialDetails(id);
		//删除物料
		materialDao.deleteMaterial(id);
	}


	@Override
	public Map<String, Object> searchMaterialBarCode(String barCode) {
		return materialDao.searchMaterialBarCode(barCode);
	}


	@Override
	public List<Map<String, Object>> searchMaterialWeigh(int start, int end, String search, 
			String userUid, String companyUid, int status) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return materialDao.searchMaterialWeigh(start, end, search, userUid, companyUid, status, dbType);
	}
	
	

	

}
