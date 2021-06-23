package com.jrsoft.business.modules.construction.service.impl;


import com.jrsoft.business.modules.construction.dao.InspectDao;
import com.jrsoft.business.modules.construction.dao.MessageDao;
import com.jrsoft.business.modules.construction.domain.CheckItem;
import com.jrsoft.business.modules.construction.domain.CheckRecord;
import com.jrsoft.business.modules.construction.service.InspectService;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.exception.EngineException;
import com.jrsoft.engine.model.org.OrgUser;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional()
public class InspectServiceImpl implements InspectService {
	@Autowired
	private InspectDao inspectDao;
	@Autowired
	private MessageDao messageDao;

	@Override
	public List<CheckItem> getCheckItemData(String companyUid, String parentUid, String formType, String projUid, String itemLb, int start, int end, Boolean ifCatalog, String type,
											String search, String projLx, String projPurpose) {
		String dbType = CommonUtil.getDatabaseType();
		List<CheckItem> data = inspectDao.getCheckItemData(companyUid,parentUid,formType,projUid,itemLb,start,end,ifCatalog,type,search,dbType,projLx,projPurpose);

		return data;
	}

	@Override
	public int getCheckItemTotalCount(String companyUid, String parentUid, String formType, String projUid, String itemLb,Boolean ifCatalog,String type) {
		return inspectDao.getCheckItemTotalCount(companyUid,parentUid,formType,projUid,itemLb,ifCatalog,type);
	}

	@Override
	public List<Map<String,Object>> getCheckItemCatalog(String companyUid, String formType, String projUid) {
		List<Map<String,Object>> data = new ArrayList<>();
		
		if(formType.equals("measure")) {
			Map<String,Object> qualityMap = new HashMap<>();
			qualityMap.put("label","实测");
			qualityMap.put("type",formType);
			qualityMap.put("id","root");
			data.add(qualityMap);
		}else {
			Map<String,Object> qualityMap = new HashMap<>();
			qualityMap.put("label","质量");
			qualityMap.put("type","quality");
			qualityMap.put("id","root");
			data.add(qualityMap);

			Map<String,Object> safetyMap = new HashMap<>();
			safetyMap.put("label","安全");
			safetyMap.put("type","safety");
			safetyMap.put("id","root");
			data.add(safetyMap);
		}
		

		if(data.size()>0){
			for(Map<String,Object> itemObj : data){
				this.getChildrenCheckItem(itemObj,companyUid,projUid, formType);
			}

		}
		return data;
	}

	/**
	 * 懒加载获取检查项目录数据
	 * @param parentUid
	 * @param formType
	 * @param projUid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getInspectItemCatalog(String formType, String projUid,String parentUid,String type) {
		List<Map<String,Object>> data = inspectDao.getInspectItemCatalog(parentUid,formType,projUid, type);

		return data;
	}

	public void getChildrenCheckItem(Map<String,Object> checkItem, String companyUid, String projUid, String formType) {
		List<Map<String,Object>> data = inspectDao.getCheckItemCatalog(companyUid,String.valueOf(checkItem.get("id")),String.valueOf(checkItem.get("type")),projUid, formType);
		if(data.size()>0){
			checkItem.put("children",data);
			checkItem.put("left",false);
			/*checkItem.setChildren(data);
			checkItem.setLeaf(false);*/
			for(Map<String,Object> itemObj : data){
				this.getChildrenCheckItem(itemObj,companyUid,projUid, formType);
				/*List<CheckItem> children = inspectDao.getCheckItemData(companyUid,String.valueOf(itemObj.getId()));
				if(children.size()>0){
					itemObj.setChildren(children);
					itemObj.setLeaf(false);
				}else{
					itemObj.setLeaf(true);
				}*/
			}

		}else{
			checkItem.put("left",true);
		}
	}

	@Override
	public List<CheckItem> getCheckItemStandards(String parentUid) {
		String dbType = CommonUtil.getDatabaseType();
		List<CheckItem> data = inspectDao.getCheckItemStandardsByParentId(parentUid,dbType);

		/*if(data.size()>0){
			for(CheckItem itemObj : data){
				List<CheckItem> children = this.getCheckItemStandards(String.valueOf(itemObj.getId()));
				if(children.size()>0){
					itemObj.setChildren(children);
					itemObj.setLeaf(false);
				}else{
					itemObj.setLeaf(true);
				}
			}

		}*/
		return data;
	}

	@Override
	public void batchDeleteCheckItem(String[] ids, String tableName) {
		inspectDao.batchDeleteCheckItem(ids, tableName);
	}

	/**
	 * excel导入检查项
	 * @param tempSheet
	 *//*
	@Override
	public int importCheckItem(Sheet tempSheet) {
		int result = 0;
		try{
			//int j = paramSheet.getColumns();
			List<CheckItem> dataList = new ArrayList<CheckItem>();

			OrgUser orgUser = SessionUtil.getUser();
			CheckItem item =null;
			int k = tempSheet.getRows();
			for (int m = 1; m < k; m++){
				String itemName = tempSheet.getCell(1, m).getContents(); //检查项
				String[] itemNameArr = itemName.split("/");
				item = new CheckItem();
				item.setCreateUser(orgUser.getId());
				item.setCompany(SessionUtil.getCompanyUid());
				item.setSubCompany(SessionUtil.getSubCompanyId());
				item.setCreateTime(new Date());
				item.setId(CommonUtil.getUUID());
				item.setSign(tempSheet.getCell(0, m).getContents());
				item.setType(tempSheet.getCell(2, m).getContents());
				item.setCheckRequire(tempSheet.getCell(3, m).getContents());

				if(itemNameArr.length>1){
					List<CheckItem> itemList = new ArrayList<CheckItem>();
					for (int i=0;i<itemNameArr.length;i++) {
						Map<String, Object> itemObj =inspectDao.findItemByName(itemNameArr[i]);
						CheckItem obj = new CheckItem();
						if(itemObj!=null){
							obj.setId(String.valueOf(itemObj.get("id")));
						}else{

							obj.setCreateUser(orgUser.getId());
							obj.setCompany(SessionUtil.getCompanyUid());
							obj.setSubCompany(SessionUtil.getSubCompanyId());
							obj.setCreateTime(new Date());
							obj.setId(CommonUtil.getUUID());
							item.setItemName(itemNameArr[i]);
							obj.setSign(tempSheet.getCell(0, m).getContents());
							obj.setType(tempSheet.getCell(2, m).getContents());
							obj.setCheckRequire(tempSheet.getCell(3, m).getContents());
							item.setParentUid(i==0? "root":itemList.get(i-1).getId());
							dataList.add(obj);
						}
						itemList.add(obj);

					}
				}else{
					item.setItemName(tempSheet.getCell(1, m).getContents());
					item.setParentUid("root");
				}
				dataList.add(item);

			}
			result =inspectDao.insertCheckItemByBatch(dataList,"T_INSPECT_ITEM");
		}catch (Exception localException){
			localException.printStackTrace();
		}
		return result;

	}*/
	public String getCheckItemParentUid(String id, List<CheckItem> list) {
		String resutl ="root";
		for (CheckItem item : list) {
			/*if(id.equals(item.getSign())){
				resutl=item.getId();
			}*/
			if(!item.getItemName().equals("质量") && !item.getItemName().equals("安全")) {
//				if(id.length() > 3) {
//					String sign = id.substring(0, id.length() - 2);
//					if(sign.equals(item.getSign())) {
//						resutl=item.getId();
//					}
//				}
				if(item.getPId().equals(id)) {
					resutl=item.getId();
				}
				
			}
			
			
		}
		return  resutl;
	}
	@Override
	public void importCheckItemFromStandard(List<CheckItem> list,String sqlTableName, String parentId) {
		String companyUid =SessionUtil.getCompanyUid();
		String formType = list.get(0).getFormType();
		List<CheckItem> newCheckList=new ArrayList<CheckItem>();
		List<CheckItem> result = new ArrayList<CheckItem>();
		//设置id
		for (CheckItem item: list) {
//				String pId = item.getParentUid();
				String id = item.getId();
				item.setPId(item.getId());
				item.setId(CommonUtil.getUUID());
				item.setProjUid(item.getProjUid());
				item.setFormType(formType);
				/*if("分项".equals(item.getLb())){
					List<CheckItem> checkItemList = inspectDao.getCheckItemFromStandardDet(id);
					for (CheckItem child :checkItemList) {
						child.setId(CommonUtil.getUUID());
						child.setParentUid(item.getId());
						child.setProjUid(item.getProjUid());
						child.setFormType(formType);
						child.setProjType(0);
						child.setCompany(SessionUtil.getCompanyUid());
						result.add(child);
					}
				}*/
				item.setCompany(SessionUtil.getCompanyUid());
				item.setCreateTime(new Date());
				newCheckList.add(item);
			}
		
				/*CheckItem itemObj = inspectDao.getCheckItemFromStandarCount(item.getSign(), companyUid, item.getProjUid(), sqlTableName, item.getFormType(), parentId, item.getItemName());
				if(itemObj != null) {
					if(itemObj.getItemName().equals(item.getItemName())) {
						item.setId(itemObj.getId());
					}else {
						item.setId(CommonUtil.getUUID());
						item.setProjUid(item.getProjUid());
						item.setFormType(formType);
						if("分项".equals(item.getLb())){
							List<CheckItem> checkItemList = inspectDao.getCheckItemFromStandardDet(item.getSign());
							for (CheckItem child :checkItemList) {
								child.setId(CommonUtil.getUUID());
								child.setParentUid(item.getId());
								child.setProjUid(item.getProjUid());
								child.setFormType(formType);
								child.setProjType(0);
								child.setCompany(SessionUtil.getCompanyUid());
								result.add(child);
							}
						}
						item.setCompany(SessionUtil.getCompanyUid());
						item.setCreateTime(new Date());
						newCheckList.add(item);
					}
				}else {
					item.setId(CommonUtil.getUUID());
					item.setProjUid(item.getProjUid());
					item.setFormType(formType);
					if("分项".equals(item.getLb())){
						List<CheckItem> checkItemList = inspectDao.getCheckItemFromStandardDet(item.getSign());
						for (CheckItem child :checkItemList) {
							child.setId(CommonUtil.getUUID());
							child.setParentUid(item.getId());
							child.setProjUid(item.getProjUid());
							child.setFormType(formType);
							child.setProjType(0);
							child.setCompany(SessionUtil.getCompanyUid());
							result.add(child);
						}
					}
					item.setCompany(SessionUtil.getCompanyUid());
					item.setCreateTime(new Date());
					newCheckList.add(item);
				}*/
				
//				if(itemObj==null){
//					item.setId(CommonUtil.getUUID());
//					item.setProjUid(item.getProjUid());
//					item.setFormType(formType);
//					if("分项".equals(item.getLb())){
//						List<CheckItem> checkItemList = inspectDao.getCheckItemFromStandardDet(item.getSign());
//						for (CheckItem child :checkItemList) {
//							child.setId(CommonUtil.getUUID());
//							child.setParentUid(item.getId());
//							child.setProjUid(item.getProjUid());
//							child.setFormType(formType);
//							child.setProjType(0);
//							child.setCompany(SessionUtil.getCompanyUid());
//							result.add(child);
//						}
//					}
//					item.setCompany(SessionUtil.getCompanyUid());
//					item.setCreateTime(new Date());
//					newCheckList.add(item);
//				}else {
//					item.setId(itemObj.getId());
//				}
			
//		}
		//设置parentUid
		for (CheckItem item: newCheckList) {
			if(item.getParentUid().equals("root")) {
				item.setParentUid(parentId);
			}else {
				String parentUid =getCheckItemParentUid(item.getParentUid(),list);
				item.setParentUid(parentUid);
			}
			
			/*CheckItem parentitemObj = inspectDao.getCheckItemFromStandarCount(item.getParentUid(),companyUid,item.getProjUid(),sqlTableName, item.getFormType(), parentId, item.getItemName());
			if(parentitemObj==null){
				if(item.getParentUid().equals("root")) {
					item.setParentUid(parentId);
				}else {
					String parentUid =getCheckItemParentUid(item.getParentUid(),list);
					item.setParentUid(parentUid);
				}
			}else{
				item.setParentUid(parentitemObj.getId());
			}*/
		}
//		newCheckList.addAll(result);
		if(newCheckList.size()==0){
			throw new EngineException("不能重复导入!",500101);
		}else{
			inspectDao.insertCheckItemByBatch(newCheckList,sqlTableName);
		}


	}

	@Override
	public ReturnPageJson<List<Map<String, Object>>> getCheckRecordDatas(String companyUid, String status, String checkType, String search, int pageIndex, int pageSize, String projUid,
																		 String dataType, String fromType, boolean isCompanyAdmin, String userUid,
																		 String principal, String partUid, String processId, String lb, String processStatus, String areaId, String processPerson, String myAbarbeitung, String dataStatus) {
		String dbType = CommonUtil.getDatabaseType();
		int start = (pageIndex-1)*pageSize;

		ReturnPageJson returnJson = new ReturnPageJson();
		List<Map<String, Object>> data= inspectDao.getCheckRecordDatas(companyUid,status,checkType,search,start,pageSize,projUid,dataType,fromType,
				isCompanyAdmin,userUid,principal,partUid, processId, lb, processStatus, areaId, processPerson, myAbarbeitung,dbType,dataStatus);
		int total = inspectDao.getCheckRecordTotalNum(companyUid,status,checkType,search,start,pageSize,projUid,dataType,fromType,
				isCompanyAdmin,userUid,principal,partUid, processId, lb, processStatus, areaId, processPerson, myAbarbeitung,dbType,dataStatus);
		returnJson.setData(data);
		returnJson.setTotalCount(total);
		return returnJson;
	}

	@Override
	public Map<String, Object> getCheckRecordTotalCount(String companyUid, String status, String checkType, String search, String projUid, String dataType, String fromType,
										boolean isCompanyAdmin, String userUid, String principal,String partUid, 
										String processId, String type, String processStatus, String areaId, String processPerson, String myAbarbeitung,String lb) {
		String dbType = CommonUtil.getDatabaseType();
		return inspectDao.getCheckRecordTotalCount(companyUid,status,checkType,search,projUid,dataType,fromType,isCompanyAdmin,userUid,principal,partUid,dbType, 
				processId, type, processStatus, areaId, processPerson, myAbarbeitung ,lb);
	}

	@Override
	public Map<String, Object> getCheckRecordById(String id, String userUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			return inspectDao.getCheckRecordByIdMySql(id,userUid, dbType);
		}else {
			return inspectDao.getCheckRecordById(id,userUid, dbType);
		}
	}

	@Override
	public Map<String, Object> getCheckItemById(String id) {
		return inspectDao.getCheckItemById(id);
	}

	@Override
	public void deleteById(String id) {
		inspectDao.deleteById(id);
		messageDao.deleteByBusinessKey(id);
	}

	/**
	 * 获取检查统计数量数据
	 * @param companyUid
	 * @param checkType
	 * @param projUid
	 * @return
	 */
	@Override
	public Map<String, Object> getStatisticCountData(String companyUid, String checkType, String projUid,
			String statisticsDate,String formType, String dbType) {
		return inspectDao.getStatisticCountData(companyUid,checkType,projUid,statisticsDate,formType, dbType);
	}

	/**
	 * 获取检查统计图表数据
	 * @param companyUid
	 * @param statisticsDate
	 * @param projUid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getStatisticChartsData(String companyUid, String statisticsDate, String projUid,String formType,String checkType) {
		String dbType = CommonUtil.getDatabaseType();
		return inspectDao.getStatisticChartsData(companyUid,statisticsDate,projUid,formType,dbType,checkType);
	}

	@Override
	public Map<String, Object> getStatisticTableData(String companyUid, String statisticsDate, String projUid,String checkType) {
		String dbType = CommonUtil.getDatabaseType();
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> dataList = inspectDao.getStatisticPartData(statisticsDate,projUid,checkType,dbType);
		List<Map<String, Object>> itemNameList = inspectDao.getStatisticItemNameData(statisticsDate,projUid,checkType,dbType);
		for (Map<String, Object> map : dataList) {
			for (Map<String, Object> itemName : itemNameList) {
				Map<String, Object> data = inspectDao.getStatisticObj(String.valueOf(map.get("partUid")),String.valueOf(itemName.get("itemName")));
				Double percent = "0".equals(String.valueOf(data.get("totalNum")))? 0:Double.valueOf(String.valueOf(data.get("completeNum")))/Double.valueOf(String.valueOf(data.get("totalNum")));
				//data.put("percent",percent);
				Object completeNum =data.get("completeNum")==null? 0:data.get("completeNum");
				String numStr = completeNum+"/"+data.get("totalNum");
				map.put(String.valueOf(itemName.get("itemParentUid"))+"percent",(percent*100)+"%");
				map.put(String.valueOf(itemName.get("itemParentUid"))+"numStr",numStr);
			}
		}
		result.put("dataList",dataList);
		result.put("itemNameList",itemNameList);
		//return inspectDao.getStatisticTableData(companyUid,statisticsDate,projUid,checkType,dbType);
		return result;
	}

	/**
	 * 获取检查记录中检查部位数据
	 * @param projUid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCheckPartDatas(String projUid, String checkType) {
		return inspectDao.getCheckPartDatas(projUid,checkType);
	}

	/**
	 *PC端获取部位树结构数据
	 * @param projUid
	 * @param companyUid
	 * @param parentUid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getPartTree(String projUid, String companyUid,String parentUid,String purpose,String parentName) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		
		if(dbType.equals("mysql")) {
			List<Map<String,Object>> data = inspectDao.getPartTreeMySql(projUid,companyUid,parentUid,purpose,parentName);
			if(data.size()>0){
				for(Map<String,Object> itemObj : data){
					String tempParentName=String.valueOf(itemObj.get("parentName"))+">"+String.valueOf(itemObj.get("label"));
					List<Map<String,Object>> children = this.getPartTree(projUid,companyUid,String.valueOf(itemObj.get("id")),purpose,tempParentName);
					if(children.size()>0){
						itemObj.put("leaf",false);
						itemObj.put("children",children);
					}else{
						itemObj.put("leaf",true);
					}
				}
			}
			return data;
		}else if(dbType.equals("mssql")) {
			List<Map<String,Object>> data = inspectDao.getPartTree(projUid,companyUid,parentUid);
			if(data.size()>0){
				for(Map<String,Object> itemObj : data){
					List<Map<String,Object>> children = this.getPartTree(projUid,companyUid,String.valueOf(itemObj.get("id")),purpose,"");
					if(children.size()>0){
						itemObj.put("leaf",false);
						itemObj.put("children",children);
					}else{
						itemObj.put("leaf",true);
					}
				}
			}
			return data;
		}
		return null;
	}

	/**
	 * 新增检查记录
	 * @param list
	 */
	@Override
	public void saveCheckRecord(List<CheckRecord> list) {
		for (CheckRecord item :list) {
			item.setId(CommonUtil.getUUID());
			item.setCompany(SessionUtil.getCompanyUid());
			item.setCreateUser(SessionUtil.getUserUid());
			item.setCompany(SessionUtil.getCompanyUid());
			//item.setSubCompany(SessionUtil.getSubCompanyId());
			item.setCreateTime(new Date());
		}
		inspectDao.insertCheckRecordBatch(list);
	}

	@Override
	public void updateCheckRecord(String markerId, CheckRecord checkRecord) {
		inspectDao.updateCheckRecord(markerId, checkRecord);
	}

	@Override
	public List<Map<String, Object>> getMeasurePoitData(String id) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return  inspectDao.getMeasurePoitData(id, dbType);
	}

	/**
	 * 获取权限人员
	 * @param projUid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getAuthorityMember(String projUid,String roleType) {
		String[] roleIds = roleType.split(",");
		return inspectDao.getAuthorityMember(projUid,roleIds);
	}

	@Override
	public Map<String, Object> getMeasureCountData(String companyUid, String projUid, String statisticsDate) {
		String dbType = CommonUtil.getDatabaseType();
		return  inspectDao.getMeasureCountData(companyUid,projUid,statisticsDate,dbType);
	}

	@Override
	public List<Map<String, Object>> getMeasureChartsData(String companyUid, String statisticsDate, String projUid) {
		String dbType = CommonUtil.getDatabaseType();
		return inspectDao.getMeasureChartsData(companyUid,statisticsDate,projUid,dbType);
	}

	@Override
	public List<Map<String, Object>> getMeasureTableData(String companyUid, String statisticsDate, String projUid) {
		String dbType = CommonUtil.getDatabaseType();
		return inspectDao.getMeasureTableData(companyUid,statisticsDate,projUid,dbType);
	}

	@Override
	public Map<String, Object> getDrawingMeasureData(String id,String projUid) {
		String dbType = CommonUtil.getDatabaseType();
		return inspectDao.getDrawingMeasureData(id,projUid,dbType);
	}

	/**
	 * 批量删除测点
	 * @param ids
	 */
	@Override
	public void batchDeletePoitRecord(String[] ids) {
		inspectDao.batchDeletePoitRecord(ids);
	}

	/**
	 * 获取微信端检查统计数量数据
	 * @param companyUid
	 * @param projUid
	 * @param statisticsDate
	 * @param formType
	 * @return
	 */
	@Override
	public Map<String, Object> getStatisticCountDataOfWechat(String companyUid, String projUid, String statisticsDate, String formType, String checkType) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return inspectDao.getStatisticCountDataOfWechat(companyUid,projUid,statisticsDate,formType, checkType,dbType);
	}

	@Override
	public List<Map<String, Object>> getCheckerStatistictData(String userField, String statisticsDate, String projUid,
			String formType, String checkType) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return inspectDao.getCheckerStatistictData(userField,statisticsDate,projUid,formType, checkType,dbType);
	}

	@Override
	public List<Map<String, Object>> getRectifyStatistictData(String statisticsDate, String projUid, String formType, String checkType) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return inspectDao.getRectifyStatistictData(statisticsDate,projUid,formType,checkType, dbType);
	}

	/**
	 * 导出检查记录
	 * @param companyUid
	 * @param status
	 * @param checkType
	 * @param search
	 * @param projUid
	 * @param dataType
	 * @param fromType
	 * @param isCompanyAdmin
	 * @param userUid
	 * @param principal
	 * @param partUid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> exportCheckRecord(String companyUid, String status, String checkType, String search, String projUid, String dataType, String fromType, boolean isCompanyAdmin, String userUid, String principal, String partUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return inspectDao.exportCheckRecord(companyUid,status,checkType,search,projUid,dataType,fromType,isCompanyAdmin,userUid,principal,partUid, dbType);
	}

	/**
	 * 监管版检查统计
	 * @param companyUid
	 * @param statisticsDate
	 * @return
	 */
	@Override
	public Map<String, Object> getStatisticCountDataForSupervise(String companyUid, String statisticsDate,String checkType) {
		return inspectDao.getStatisticCountDataForSupervise(companyUid,statisticsDate,checkType);
	}

	/**
	 * 监管版检查统计热力图数据
	 * @param companyUid
	 * @param statisticsDate
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getInspectHeatMapData(String companyUid, String statisticsDate,String checkType) {
		return inspectDao.getInspectHeatMapData(companyUid,statisticsDate,checkType);
	}

	/**
	 * 监管版检查统计图表数据
	 * @param companyUid
	 * @param statisticsDate
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getInspectChartData(String companyUid, String statisticsDate,String checkType) {
		return inspectDao.getInspectChartData(companyUid,statisticsDate,checkType);
	}

	
	@Override
	public List<CheckItem> getCheckItemEnterprise(String parentUid, String moduleType, String companyUid, 
			String projType, String projPurpose, String type, String id) {
		List<CheckItem> data = new ArrayList<CheckItem>();
		if(parentUid.equals("root")) {
			if(type != null) {
				if(type.equals("quality")) {
					CheckItem qualityMap = new CheckItem();
					qualityMap.setItemName("质量");
					qualityMap.setType("quality");
					qualityMap.setId("root");
					data.add(qualityMap);
				}else if(type.equals("safety")) {
					CheckItem safetyMap = new CheckItem();
					safetyMap.setItemName("安全");
					safetyMap.setType("safety");
					safetyMap.setId("root");
					data.add(safetyMap);
				}
			}

		}
		
		if(data.size()>0){
			for(CheckItem itemObj : data){
				this.searchChildrenCheckItem(itemObj,companyUid, moduleType, projType, projPurpose, parentUid, id);
			}

		}else {
			data = this.searchChildrenCheckItem(data,companyUid, moduleType, projType, projPurpose, parentUid, id);
		}
		
//		List<CheckItem> data = inspectDao.getCheckItemEnterprise(parentUid, moduleType, companyUid, projType, projPurpose);	// 查询第一层
		return data;
	}

	private void searchChildrenCheckItem(CheckItem itemObj, String companyUid, String moduleType, String projType,
			String projPurpose, String parentUid, String id) {
		List<CheckItem> data = inspectDao.getCheckItemEnterprise(itemObj.getId(), moduleType, companyUid, projType, projPurpose, itemObj.getType(), id);	// 查询第一层
		if(data.size() > 0) {
			itemObj.setLeaf(false);
		}else {
			itemObj.setLeaf(true);
		}
		itemObj.setChildren(data);
	}

	private List<CheckItem> searchChildrenCheckItem(List<CheckItem> result, String companyUid, String moduleType, String projType, 
			String projPurpose, String parentUid, String id) {
		return result = inspectDao.getCheckItemEnterprise(parentUid, moduleType, companyUid, projType, projPurpose, "", id);	// 查询第一层
	}
	
	
	
	/**
	 * 查询企业库树
	 */
	@Override
	public List<CheckItem> getCheckItemEnterprise2(String companyUid, String projType, String projPurpose, String type, String id) {
		return inspectDao.searchInspectItemList(companyUid, projType, projPurpose, type, id);
	}
	
	
	@Override
	public Map<String, Object> getUserPermission(String companyUid, String projUid, String type, String userType,String search) {
		Map<String, Object> userMap = inspectDao.getUserPermission(companyUid, projUid, type, userType);
		if(userMap!=null){
			String userIds =  String.valueOf(userMap.get("userUidArr"));
			if(userIds!=null){
				String[] ids = userIds.split(",");
				List<Map<String, Object>> data = inspectDao.getPermissionUsers(ids,search);
				userMap.put("data",data);
			}
			String roleArr =  String.valueOf(userMap.get("roleArr"));
			if(roleArr!=null){
				String[] ids = roleArr.split(",");
				List<Map<String, Object>> data = inspectDao.getPermissionRoleUsers(ids,projUid,search);
				userMap.put("roleUsers",data);
			}
		}

		return userMap;
	}

	
	@Override
	public List<Map<String, Object>> searchPlanInsideQuality(String companyUid, String projUid, String type,
			String status, String userUid, int start, int end, String lb) {
		return inspectDao.searchPlanInsideQuality(companyUid, projUid, type, status, userUid, start, end, lb);
	}

	
	@Override
	public List<CheckItem> getCheckItemEnterpriseList(String parentUid, String companyUid, int start, int end) {
		return inspectDao.getCheckItemEnterpriseList(parentUid, companyUid, start, end);
	}

	
	@Override
	public void deletePermissionUser(String projUid, String type, String groupName) {
		inspectDao.deletePermissionUser(projUid, type, groupName);
	}

	/**
	 * 获取待检查数据
	 * @param projUid
	 * @param userUid
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getWaitingCheckDatas(String projUid, String userUid, int start, int end,String lb) {
		return inspectDao.getWaitingCheckDatas(projUid,userUid,start,end,lb);
	}

	@Override
	public List<Map<String, Object>> getAreaPurposes(String projUid) {
		return inspectDao.getAreaPurposes(projUid);
	}

	@Override
	public List<Map<String, Object>> getCheckRecordItemList(String projUid, String checkType) {
		return inspectDao.getCheckRecordItemList(projUid,checkType);
	}
}
