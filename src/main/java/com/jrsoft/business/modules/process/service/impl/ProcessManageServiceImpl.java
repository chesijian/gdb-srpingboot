package com.jrsoft.business.modules.process.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;


import com.jrsoft.business.modules.construction.service.InspectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.process.dao.ProcessManageDao;
import com.jrsoft.business.modules.process.model.ProcessCheckout;
import com.jrsoft.business.modules.process.model.ProcessCheckoutList;
import com.jrsoft.business.modules.process.model.ProcessDet;
import com.jrsoft.business.modules.process.model.ProcessTask;
import com.jrsoft.business.modules.process.model.ProcessTaskList;
import com.jrsoft.business.modules.process.service.ProcessDetService;
import com.jrsoft.business.modules.process.service.ProcessManageServiceI;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

@Service
@Transactional()
public class ProcessManageServiceImpl implements ProcessManageServiceI{

	
	@Autowired
	private ProcessManageDao processManageDao;
	
	@Autowired
	private InspectService inspectService;
	




	@Override
	public void export(String companyUid, String projUid, String projName, Integer status, String areaUid, String abarbeitungUid, String search, HttpServletResponse response) {

	}

	@Override
	public List<Map<String, Object>> searchProcessList(String projUid, String userUid, String companyUid, String search,
			boolean isCompanyAdmin, String dbType, String formType) {

		List<Map<String,Object>> data = new ArrayList<>();
		Map<String,Object> qualityMap = new HashMap<>();
		qualityMap.put("label","工序");
		qualityMap.put("type", formType);
		qualityMap.put("id","root");
		data.add(qualityMap);

		if(data.size()>0){
			for(Map<String,Object> itemObj : data){
				this.getChildrenCheckItem(itemObj,companyUid,projUid, formType);
			}

		}
		return data;
		

	}
	
	
	
	public void getChildrenCheckItem(Map<String,Object> checkItem,String companyUid,String projUid, String formType) {
		List<Map<String,Object>> data = processManageDao.getCheckItemCatalog(companyUid,String.valueOf(checkItem.get("id")), formType, projUid);
		if(data.size()>0){
			checkItem.put("children",data);
			checkItem.put("left",false);
			/*checkItem.setChildren(data);
			checkItem.setLeaf(false);*/
			for(Map<String,Object> itemObj : data){
				this.getChildrenCheckItem(itemObj,companyUid, projUid, formType);
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
	
	
	/**
	 * 递归查询工序
	 * @param children
	 */
	private void childrenProcess(List<Map<String, Object>> children, String projUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		for(Map<String, Object> map : children) {
			String parentUid = (String) map.get("id");
			List<Map<String, Object>> list = processManageDao.searchChildrenProcess(projUid, parentUid,true, dbType);
			if(list.size() > 0) {
				map.put("children", list);
				childrenProcess(list, projUid);
			}
		}
	}


	@Override
	public List<Map<String, Object>> searchInspectList(String processId, String userUid, String projUid,
			String search, int start, int end, boolean isCompanyAdmin, String dbType) {
		if(dbType.equals("mysql")) {
			int start2 = start -1;
			return processManageDao.searchInspectListMySql(processId, userUid, projUid, search, start2, end, isCompanyAdmin);
		}else if(dbType.equals("mssql")) {
			return processManageDao.searchInspectList(processId, userUid, projUid, search, start, end, isCompanyAdmin);
		}
		return null;
	}


	@Override
	public void delete(String id) {
		processManageDao.delete(id);
	}


	@Override
	public int searchMaxSort(String parentUid) {
		try {
			return processManageDao.searchMaxSort(parentUid);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	public Map<String, Object> edit(String id) {
		return processManageDao.edit(id);
	}


	@Override
	public Map<String, Object> editInspect(String id) {
		return processManageDao.editInspect(id);
	}


	@Override
	public void deleteInspect(String[] id) {
		processManageDao.deleteInspect(id);
	}


	@Override
	public int InspectMaxSort(String parentUid) {
		try {
			return processManageDao.InspectMaxSort(parentUid);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	public List<Map<String, Object>> searchProcessCheckout(String projUid, String userUid, String companyUid,
			String search, int start, int end, boolean isCompanyAdmin, String dbType) {
		if(dbType.equals("mysql")) {
			int start2 = start -1;
			return processManageDao.searchProcessCheckoutMySql(projUid, userUid, companyUid, search, 
					start2, end, isCompanyAdmin);
		}else if(dbType.equals("mssql")) {
			return processManageDao.searchProcessCheckout(projUid, userUid, companyUid, search, 
					start, end, isCompanyAdmin);
		}
		return null;
	}


	@Override
	public Map<String, Object> editCheckoutById(String id) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return processManageDao.editCheckoutById(id, dbType);
	}


	@Override
	public void deleteCheckoutByIds(String[] idss) {
		processManageDao.deleteCheckoutByIds(idss);
	}


	@Override
	public int checkoutMaxSort(String projUid, String dbType) {
		try {
			return processManageDao.checkoutMaxSort(projUid, dbType);
		} catch (Exception e) {
			e.printStackTrace();
			return 1001;
		}
	}


	@Override
	public void saveProcessCheckout(ProcessCheckoutList data, int max) {
		if(CommonUtil.ifNotEmpty(data)){
			
			for (ProcessCheckout c : data.getProcesscheck()) {
				max++;
				c.setId(CommonUtil.getUUID());
				c.setCode(String.valueOf(max));
				//save(c);
			}
		}
	}


	@Override
	public Map<String, Object> processTaskTree(String projUid, String companyUid,String parentUid,String purpose) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String,Object>> data = inspectService.getPartTree(projUid,companyUid,parentUid,purpose,"");
		if(data.size()>0){
			result.put("data", data);
			for(Map<String,Object> itemObj : data){
				List<Map<String,Object>> children = inspectService.getPartTree(projUid,companyUid,String.valueOf(itemObj.get("id")),purpose,"");
				
				//根据区域查询检验批
				List<Map<String, Object>> processList = processManageDao.getProcessCheckout(projUid, String.valueOf(itemObj.get("id")));
				
				itemObj.put("sign2","sign2");
				
				if(children.size() > 0 || processList.size() > 0) {
					itemObj.put("leaf",false);
				}else {
					itemObj.put("leaf",true);
				}
				
				if(processList.size() > 0) {
//					itemObj.put("leaf",false);
					for(Map<String,Object> p : processList){
						p.put("sign2", "sign2");
						p.put("check", "check");
						children.add(p);
					}
					
					/*itemObj.put("children",processList);*/
					itemObj.put("sign2","sign2");
				}
				
				if(children.size() > 0) {
//					itemObj.put("leaf",false);
					itemObj.put("children",children);
					for(Map<String,Object> c : children){
						c.put("sign2", "sign2");
					}
					itemObj.put("sign2","sign2");
					recursionProcessTask(children, projUid, companyUid);
				}
				
			}
		}
		return result;
	}


	private void recursionProcessTask(List<Map<String, Object>> list, String projUid, String companyUid) {
		for(Map<String,Object> item : list){
			List<Map<String,Object>> children = inspectService.getPartTree(projUid,companyUid,String.valueOf(item.get("id")),"","");
			//根据区域查询检验批
			List<Map<String, Object>> processList = processManageDao.getProcessCheckout(projUid, String.valueOf(item.get("id")));
			
			if(children.size() > 0 || processList.size() > 0) {
				item.put("leaf",false);
			}else {
				item.put("leaf",true);
			}
			
			
			if(processList.size() > 0) {
//				item.put("children", processList);
				for(Map<String,Object> p : processList){
					p.put("sign2", "sign2");
					p.put("check", "check");
					children.add(p);
				}
				item.put("sign2","sign");
//				item.put("leaf",false);
			}
			
			if(children.size() > 0) {
				item.put("children", children);
				for(Map<String,Object> c : children){
					c.put("sign2", "sign2");
				}
				item.put("sign2","sign2");
//				item.put("leaf",false);
				recursionProcessTask(children, projUid, companyUid);
			}
			
		}
	}


	@Override
	public List<Map<String, Object>> searchProcessTaskList(String projUid, String userUid, String companyUid,
			String search, int start, int end, boolean isCompanyAdmin, String examineStatus, String constructionStatus,
			String partUid, String principalUid, String dbType) {
		if(dbType.equals("mysql")) {
			int start2 = start - 1;
			return processManageDao.searchProcessTaskListMySql(projUid, userUid, companyUid,
					search, start2, end, isCompanyAdmin, examineStatus, constructionStatus,
					partUid, principalUid);
		}else if(dbType.equals("mssql")) {
			return processManageDao.searchProcessTaskList(projUid, userUid, companyUid,
					search, start, end, isCompanyAdmin, examineStatus, constructionStatus,
					partUid, principalUid);
		}
		return null;
	}


	@Override
	public void saveProcessTask(ProcessTaskList data, int max) {
		if (CommonUtil.ifNotEmpty(data)) {
			for(ProcessTask task : data.getTask()) {
				max++;
				if(task.getId() == null) {
					task.setId(CommonUtil.getUUID());
					task.setCode(String.valueOf(max));
					task.setStatus(0);
					task.setConstructionStatus(0);
					task.setEnable(1);
					//taskService.save(task);
				}
				//taskService.update(task);
			}
				
		}
	}


	@Override
	public void deleteProcessTaskByIds(String[] ids) {
		processManageDao.deleteProcessTaskByIds(ids);
	}


	@Override
	public int searchTaskSortMax(String projUid, String dbType) {
		return processManageDao.searchTaskSortMax(projUid, dbType);
	}


	@Override
	public Map<String, Object> editProcessTaskById(String id, String dbType) {
		String userUid = SessionUtil.getUserUid();
		if(dbType.equals("mysql")) {
			return processManageDao.editProcessTaskByIdMySql(id, userUid);
		}else if(dbType.equals("mssql")) {
			return processManageDao.editProcessTaskById(id, userUid);
		}
		return null;
	}


	@Override
	public List<Map<String, Object>> searchPersonnel(String principal, String projUid) {
		String[] pIds = principal.split(",");
		return processManageDao.searchPersonnel(pIds, projUid);
 	}


	@Override
	public Map<String, Object> searchPartAndProcessTask(String projUid, String parentUid,String search) {
		Map<String, Object> result = new HashMap<>();
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		List<Map<String, Object>> data = processManageDao.searchPartAndProcessTask(projUid, parentUid, dbType);
		if(data.size() > 0) {
			for(Map<String, Object> map : data) {
				String id = (String) map.get("id");
				List<Map<String, Object>> pro = processManageDao.searchTaskCountAndProcessCount(id, dbType);
				
				Long processCount = 0L;
				Long processTaskCount = 0L;
				List<String> processSums = new ArrayList<String>();	//保存工序名称, 用于去除重复
				
				if(map.get("parentUid").equals("root")) {
					if(pro.size() > 0) {
						for(Map<String, Object> p : pro) {
							processSums.add(String.valueOf(p.get("PROCESS_NAME_")));
							Long taskSum = (Long)p.get("taskCount");
							Long processSum = (Long)p.get("processCount");
							processCount += processSum;
							processTaskCount += taskSum;
							
						}
						
					}
					
					List<Map<String, Object>> data2 = processManageDao.searchPartAndProcessTask(projUid, id, dbType);
					if(data2.size() > 0) {
						for(Map<String, Object> map2 : data2) {
							String id2 = (String) map2.get("id");
							List<Map<String, Object>> pro2 = processManageDao.searchTaskCountAndProcessCount(id2, dbType);
							if(pro2.size() > 0) {
								for(Map<String, Object> p2 : pro2) {
									processSums.add(String.valueOf(p2.get("PROCESS_NAME_")));
									Long taskSum = (Long)p2.get("taskCount");
									Long processSum = (Long)p2.get("processCount");
									processCount += processSum;
									processTaskCount += taskSum;
									
								}
							}
							
							List<Map<String, Object>> data3 = processManageDao.searchPartAndProcessTask(projUid, id2, dbType);
							if(data3.size() > 0) {
								for(Map<String, Object> map3 : data3) {
									String id3 = (String) map3.get("id");
									List<Map<String, Object>> pro3 = processManageDao.searchTaskCountAndProcessCount(id3, dbType);
									if(pro3.size() > 0) {
										for(Map<String, Object> p3 : pro3) {
											processSums.add(String.valueOf(p3.get("PROCESS_NAME_")));
											if(dbType.equals("mysql")) {
												Long taskSum = (Long)p3.get("taskCount");
												Long processSum = (Long)p3.get("processCount");
												processCount += processSum;
												processTaskCount += taskSum;
											}else {
												Integer taskSum = (Integer)p3.get("taskCount");
												Integer processSum = (Integer)p3.get("processCount");
												processCount += processSum;
												processTaskCount += taskSum;
											}
											
										}
									}
									
									List<Map<String, Object>> data4 = processManageDao.searchPartAndProcessTask(projUid, id3, dbType);
									if(data4.size() > 0) {
										for(Map<String, Object> map4 : data4) {
											String id4 = (String) map4.get("id");
											List<Map<String, Object>> pro4 = processManageDao.searchTaskCountAndProcessCount(id4, dbType);
											if(pro4.size() > 0) {
												for(Map<String, Object> p4 : pro4) {
													processSums.add(String.valueOf(p4.get("PROCESS_NAME_")));
													if(dbType.equals("mysql")) {
														Long taskSum = (Long)p4.get("taskCount");
														Long processSum = (Long)p4.get("processCount");
														processCount += processSum;
														processTaskCount += taskSum;
													}else {
														Integer taskSum = (Integer)p4.get("taskCount");
														Integer processSum = (Integer)p4.get("processCount");
														processCount += processSum;
														processTaskCount += taskSum;
													}
												}
											}
										}
									}
									
									
								}
							}
							
						}
					}
					for  ( int  i  =   0 ; i  <  processSums.size()  -   1 ; i ++ )  {       
					      for  ( int  j  =  processSums.size()  -   1 ; j  >  i; j -- )  {       
					           if  (processSums.get(j).equals(processSums.get(i)))  {       
					        	   processSums.remove(j);       
					            }        
					        }        
					      }        
					map.put("taskCount", processTaskCount);
					map.put("processCount", processSums.size());
					
				}else {
					if(CommonUtil.ifNotEmpty(pro)) {
						Long p = 0L;
						Long t = 0L;
						for(Map<String, Object> r : pro) {
							if(dbType.equals("mysql")) {
								t += (Long)r.get("taskCount");
								p += (Long)r.get("processCount");
							}else {
								t += (Integer)r.get("taskCount");
								p += (Integer)r.get("processCount");
							}
						}
						map.put("taskCount", t);
						map.put("processCount", p);
					}else {
						map.put("taskCount", 0);
						map.put("processCount", 0);
					}
					
				}
				
			}
			result.put("data", data);
			
		}
		
		if(!parentUid.equals("root")) {
			//根据区域查询工序任务
			String userUid = SessionUtil.getUserUid();
			List<Map<String, Object>> processList = processManageDao.getProcessTaskByPartId(projUid, parentUid, userUid, dbType,search);
			result.put("partList", processList);
		}
		
		return result;
	}

	
	@Override
	public List<Map<String, Object>> searchExamineNape(String processId, String taskId,Integer inspectType) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return processManageDao.searchExamineNape(processId, taskId, inspectType,dbType);
	}


	@Override
	public List<Map<String, Object>> searchProcessTaskByProIdAndPartId(String processId, String partId, String projUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return processManageDao.searchProcessTaskByProIdAndPartId(processId, partId, projUid, dbType);
	}


	@Override
	public List<Map<String, Object>> searchAreaFirstFloor(String id, String dbType) {
		return processManageDao.searchAreaFirstFloor(id, dbType);
	}


	@Override
	public List<Map<String, Object>> searchAreaProcess(String id, String dbType) {
		return processManageDao.searchAreaProcess(id, dbType);
	}

	@Override
	public Map<String, Object> getStatisticsNumObj(String projUid,String areaUid) {
		return processManageDao.getStatisticsNumObj(projUid,areaUid);
	}

	@Override
	public Map<String, Object> searchHomeMessageByProjUid(String projUid, String date, String dbType) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Map<String, Object>> constructionRate = null;
		if(date.length() == 7) {	//月份
			constructionRate = processManageDao.searchConstructionRateMonth(projUid, date, dbType);	//施工率, 合格率, 整改完结率
		}else if(date.length() == 4){		//年份
			constructionRate = processManageDao.searchConstructionRateYear(projUid, date, dbType);	//施工率, 合格率, 整改完结率
		}
		//查询问题数
		Integer issueSum = processManageDao.searchIssueCount(projUid);
		//查询整改数
		List<Map<String, Object>> abarbeitungSum = processManageDao.searchabarbeitungCount(projUid, date, dbType);
		
		Map<String, List<Map<String, Object>>> mapList = new HashMap<>();
		
		if(constructionRate.size() > 0) {
			
			Double notConstruction = 0.0;	//未施工数
			Double construction = 0.0;	//施工数
			Double alreadyConstruction = 0.0;	//已施工数
			Double accompConstruction = 0.0;	//完成施工数
			Double checkCount = 0.0;	//验收数
			
			
			for(Map<String, Object> map : constructionRate) {
				int constructionStatus = (int)map.get("status");
				
				construction++;	//累计施工数
				
				if(constructionStatus == 0) {
					notConstruction++;
				}else if(constructionStatus == 1) {
//					construction++;
				}else if(constructionStatus == 3){
					accompConstruction++;
				}else if(constructionStatus == 2){
					checkCount++;
				}
				
				
				if(mapList.containsKey(map.get("createTime"))) {
					mapList.get(map.get("createTime")).add(map);
				}else {
					List<Map<String, Object>> cildr = new ArrayList<Map<String, Object>>();
					cildr.add(map);
					mapList.put((String) map.get("createTime"), cildr);
				}
				
				
			}
			
			if(abarbeitungSum.size() > 0) {
				for(Map<String, Object> map : abarbeitungSum) {
					if(mapList.containsKey(map.get("createTime"))) {
						map.put("abarbe", "abarbe");
						mapList.get(map.get("createTime")).add(map);
					}else {
						map.put("abarbe", "abarbe");
						List<Map<String, Object>> cildr = new ArrayList<Map<String, Object>>();
						cildr.add(map);
						mapList.put((String) map.get("createTime"), cildr);
					}
				}
			}
			
			
			alreadyConstruction = construction - notConstruction;	//计算已施工数
			Double accompConstruction2 = checkCount + accompConstruction;	//计算完成施工数
			Double checkCount2 = accompConstruction;	//计算已验收数
			
			
			
			
			Double constructionRates = null;
			if(alreadyConstruction != 0 && construction != 0) {
				Double c = (alreadyConstruction / construction) *100;
				BigDecimal bg = new BigDecimal(c);
				constructionRates = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				
			}else {
				constructionRates = 0.0;
			}
			Double qualifiedRates = null;
			if(checkCount2 != 0 && accompConstruction2 != 0) {
				Double b = (checkCount2/accompConstruction2)*100;
				BigDecimal bg = new BigDecimal(b);
				qualifiedRates = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}else {
				qualifiedRates = 0.0;
			}
			
			Double abarbeitungSums = 0.0;
			double abarbeitungRates = 0.0;
			for(Map<String, Object> map : abarbeitungSum) {
				if(dbType.equals("mysql")) {
					Long m = (Long)map.get("abarbeitungStatus");
					abarbeitungSums += m;
				}else {
					Integer m = (Integer)map.get("abarbeitungStatus");
					abarbeitungSums += m;
				}
				
			}
			if(abarbeitungSums != 0 && issueSum != 0) {
				double d = abarbeitungSums / issueSum;
				double d1 = d*100;
				BigDecimal bg = new BigDecimal(d1);
				abarbeitungRates = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}else {
				abarbeitungRates = 0.0;
			}
			
			
			result.put("construction", construction);
			result.put("alreadyConstruction", alreadyConstruction);
			result.put("accompConstruction", accompConstruction2);
			result.put("checkCount", checkCount2);
			result.put("abarbeitungCount", abarbeitungSums);
			result.put("issueCount", issueSum);
			
			result.put("constructionRates", constructionRates);
			result.put("qualifiedRates", qualifiedRates);
			result.put("abarbeitungRates", abarbeitungRates);
			
			//根据时间排序
			Map<String, List<Map<String, Object>>> sortMap = 
					new TreeMap<String, List<Map<String, Object>>>(new MapKeyComparator());
			
			sortMap.putAll(mapList);
			
			
			result.put("list", sortMap);
			
		}
		
		
		return result;
	}
	
	
	class MapKeyComparator implements Comparator<String>{

	    @Override
	    public int compare(String str1, String str2) {
	        
	        return str1.compareTo(str2);
	    }
	}

	
	@Override
	public List<Map<String, Object>> searchAreaTop(String projUid,String parentUid) {
		return processManageDao.searchAreaTop(projUid,parentUid);
	}


	@Override
	public void updateInspectStatus(String taskId) {
		processManageDao.updateInspectStatus(taskId);
		processManageDao.updateTaskStatus(taskId);
	}


	@Override
	public Map<String, Object> searchDrawingInfoByAreaId(String id) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return processManageDao.searchDrawingInfoByAreaId(id, dbType);
	}


	@Override
	public List<Map<String, Object>> searchWeChatProcessList(String projUid, String parentUid, String search) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		List<Map<String, Object>> inspectData = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = processManageDao.searchChildrenProcess(projUid, parentUid,false, dbType);	//查询工序下级
		/*if(list.size() > 0) {
			for(Map<String, Object> map : list) {
				map.put("flag", "flag");
				List<Map<String, Object>> inspect = processManageDao.searchInspectLists((String)map.get("id"));	//查询检查项
				if(inspect.size() > 0) {
					for(Map<String, Object> map2 : inspect) {
						inspectData.add(map2);
					}
				}
			}
		}
		if(inspectData.size() > 0) {
			for(Map<String, Object> d : inspectData) {
				list.add(d);
			}
		}*/
		
		return list;
	}


	@Override
	public Map<String, Object> checkInspection(String projUid, String[] names) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		for(String name : names) {
			Map<String, Object> map = processManageDao.checkInspection(projUid, name);
			if((int)map.get("countName") > 0) {
				result.put("message", name);
			}
			break;
		}
		
		return result;
	}


	@Override
	public void searchProcessDetails(String taskId, String ids, String type) {
		if(ids.length() > 0) {
			String[] idss = ids.split(",");
			for(String id : idss) {
				Integer count = processManageDao.searchProcessDetails(taskId, id);
				if(count > 0) {
					processManageDao.updateInspectStatusByInspectid(id, type, taskId);
				}else {
					ProcessDet p = new ProcessDet();
					p.setProcessId(id);
					p.setProcessTaskId(taskId);
					p.setStatus(type);
					//processDetService.save(p);
				}
			}
		}
	}


	/*@Override
	public void export(String companyUid, String projUid, String projName, Integer status, String areaUid, String abarbeitungUid, 
			String search, HttpServletResponse response) {
		try {
			//1、创建工作簿
			HSSFWorkbook workbook = new HSSFWorkbook();
			//1.2、创建列标题样式
			HSSFCellStyle style = createCellStyle(workbook, (short)12);
			//2、创建工作表
			HSSFSheet sheet = workbook.createSheet(SessionUtil.getCompanyName()+"-(项目信息)");
			//2.1、设置工作表的默认列宽
			sheet.setDefaultColumnWidth(17);
			
			HSSFCellStyle cellStyle = workbook.createCellStyle();   
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中    
			
			HSSFRow rowTitle = sheet.createRow(0);
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,13));
			HSSFCell yearCell= rowTitle.createCell(0);
			yearCell.setCellStyle(cellStyle);
			yearCell.setCellValue(SessionUtil.getCompanyName()+"-"+projName+"(进度管理问题整改)");
			
			
			//3、创建行
			//3.1、创建头标题行并设置样式
			HSSFRow row1 = sheet.createRow(1);
			HSSFCell cell = row1.createCell(0);
			
			String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
			List<Map<String, Object>> data = processManageDao.export(companyUid, projUid, status, areaUid, abarbeitungUid, search, dbType);
			
			//创建列标题行并设置样式
			String[] titles = {"序号","编号","区域","检查项","整改负责人","整改期限","检查日期","描述"};
			for(int i = 0; i < titles.length; i++){
				HSSFCell cell1 = row1.createCell(i);
				//加载头标题样式
				cell.setCellStyle(style);
				cell1.setCellValue(titles[i]);
			}
			
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			
			//4、创建单元格
			if(data != null && data.size() > 0){
				for(int j = 0; j < data.size(); j++){
					HSSFRow row = sheet.createRow(j+2);
					row.createCell(0).setCellValue(j+1);	//编号
					row.createCell(1).setCellValue((String)data.get(j).get("inspectPart"));	//区域
					row.createCell(2).setCellValue((String)data.get(j).get("inspectItem"));	//检查项
					row.createCell(2).setCellValue((String)data.get(j).get("rectifyPrincipal"));	//整改负责人
					row.createCell(2).setCellValue((String)data.get(j).get("RECTIFY_DEADLINE_"));	//整改期限
					row.createCell(2).setCellValue((String)sdf2.format(data.get(j).get("CREATE_TIME_")));	//检查日期
					row.createCell(2).setCellValue((String)data.get(j).get("desc_"));	//描述
					
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String temp = projName+"(进度管理问题整改)";
			Date date = new Date();
			String fileName = temp+sdf.format(date)+".xls";  //文件名
			
			fileName = java.net.URLDecoder.decode(fileName,"UTF-8");
			OutputStream os = response.getOutputStream();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO8859-1") + "\";");
			
			//5、输出
			workbook.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	
	/**
	 * 创建单元格样式
	 * @param workbook 工作簿
	 * @param fontSize 字体大小
	 * @return 单元格样式
	 */
	/*public static HSSFCellStyle createCellStyle(HSSFWorkbook workbook, short fontSize){
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
		//创建字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
		font.setFontHeightInPoints(fontSize);
		//样式中加载字体
		style.setFont(font);
		return style;
	}*/


	
	
	
}
