package com.jrsoft.business.modules.progress.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import com.jrsoft.business.modules.progress.model.*;
import com.jrsoft.business.modules.progress.query.ScheduleQuery;
import com.jrsoft.engine.base.model.ReturnPageJson;
import com.jrsoft.engine.common.utils.AttachmentUtil;
import com.jrsoft.engine.exception.EngineException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.progress.dao.ProgressDao;
import com.jrsoft.business.modules.progress.service.IinksServiceI;
import com.jrsoft.business.modules.progress.service.ProgressServiceI;
import com.jrsoft.business.modules.progress.service.ScheduleServiceI;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

@Service
@Transactional()
public class ProgressServiceImpl implements ProgressServiceI{

	
	@Autowired
	private ProgressDao dao;
	
	@Override
	public List<Map<String, Object>> searchProgressList(String projUid, String userUid, String companyUid,
			String search, int start, int end, boolean isCompanyAdmin, String dbType) {
		if(dbType.equals("mysql")) {
			int start2 = start -1;
			return dao.searchProgressListMySql(projUid, userUid, companyUid, search, start2, end, isCompanyAdmin);
		}else if(dbType.equals("mssql")) {
			return dao.searchProgressList(projUid, userUid, companyUid, search, start, end, isCompanyAdmin);
		}
		return null;
	}
	
	
	/**
	 * 进度变更版本
	 */
	@Override
	public void updateProgressVersions(Schedule data, String userUid, boolean isCompanyAdmin, 
			String companyUid, String dbType) {
		// 查询进度Task与link
		List<Task> task = dao.getProgressTaskById(data.getId(), userUid, isCompanyAdmin, companyUid, "", dbType);
		//Collections.reverse(task);
		List<Iink> links = dao.getProgressRelevancekById(data.getId(), dbType);
		
		// 保存进度
		data.setId(CommonUtil.getUUID());
		data.setEnable(1);
		data.setCreateTime(new Date());
		data.setCreateUser(SessionUtil.getUserUid());
		dao.saveSchedule(data);
		//scheduleServiceI.save(data);
		
		String randomStr = CommonUtil.getUUID().substring(0, 5);
		List<Task> insertData = new ArrayList<>();
		if(task.size() > 0) {
			for (Task t : task) {
				t.setId(CommonUtil.getUUID());
				t.setScheduleUid(data.getId());
				if(!"0".equals(t.getParent())){
					t.setParent(t.getParent()+randomStr);
				}
				t.setCode(t.getCode()+randomStr);
				t.setCreateUser(SessionUtil.getUserUid());
				if(t.getStartDate() == "" || t.getStartDate().equals("")) {
					t.setStartDate(null);
				}
				if(t.getEndDate() == "" || t.getEndDate().equals("") || t.getEndDate() == null) {
					t.setEndDate(null);
				}
				if(t.getActStartDate() == null) {
					t.setActStartDate(null);
				}
				if(t.getActEndDate() == null) {
					t.setActEndDate(null);
				}
				//save(t);
				insertData.add(t);
			}
			
		}
		dao.insertTaskByBatch(insertData);
		
		if(links.size() > 0) {
			for(Iink l : links) {
				l.setId(CommonUtil.getUUID());
				l.setScheduleUid(data.getId());
				//iinksServiceI.save(l);
			}
		}
		
		//把其余的进度禁用
		dao.updateScheduleStatus(data.getId(), data.getProjUid());
	}

	@Override
	public Map<String, Object> getProgressById(String id, String dbType) {
		return dao.getProgressById(id, dbType);
	}

	@Override
	public void deleteByPrimaryKeys(String id) {
		dao.deleteTaskById(id);
		dao.deleteByPrimaryKey(id);
	}

	@Override
	public Map<String, Object> getProgressTaskByParent(String scheduleUid, String parentId) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		//查询任务之间的关系线
		List<Iink> links = dao.getProgressRelevancekById(scheduleUid, dbType);
//		reMap.put("tasks", data);
		reMap.put("links", links);
		return reMap;
	}

	@Override
	public Map<String, Object> getProgressTaskById(String id, String userUid, boolean isCompanyAdmin, String companyUid, String weChat) {
		Map<String, Object> reMap = new HashMap<String, Object>();
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		//查询进度任务
		List<Task> data = dao.getProgressTaskById(id, userUid, isCompanyAdmin, companyUid, weChat, dbType);
//		if(data.size() > 0 && data != null) {
//			for(Task task : data) {
//				List<Map<String, Object>> record = dao.searchTaskRecord(task.getId());
//				if(record.size() > 0 && record != null) {
//					task.setRecord(record);
//				}
//			}
//		}

		//查询任务之间的关系线
		List<Iink> links = dao.getProgressRelevancekById(id, dbType);
		reMap.put("tasks", data);
		reMap.put("links", links);
		return reMap;
	}

	@Override
	public Task getTaskDetailsById(String id) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		Task data = dao.getTaskDetailsById(id,dbType);
		if(data != null) {
			List<Map<String, Object>> record = dao.searchTaskRecord(data.getId());
			if(record.size() > 0 && record != null) {
				data.setRecord(record);
			}
		}

		return data;
	}

	@Override
	public void deleteTask(String id) {
		dao.deleteTask(id);
	}

	@Override
	public void deleteRelevance(String id) {
		dao.deleteRelevance(id);
	}

	@Override
	public void updateScheduleStatus(String id, String projUid) {
		dao.updateScheduleStatus(id, projUid);
	}

	@Override
	public Map<String, Object> saveTaskAndRelevance(TaskAndRelevance taskAndRelevance) {
		Map<String, Object> result = new HashMap<>();
		List<Task> insertTasks = new ArrayList<>();
		List<Task> updateTasks = new ArrayList<>();
		List<Iink> insertLinks = new ArrayList<>();
		if (CommonUtil.ifNotEmpty(taskAndRelevance)) {
			if (taskAndRelevance.getInsertTasks().size() > 0) {

				for (Task task : taskAndRelevance.getInsertTasks()) {
					task.setId(CommonUtil.getUUID());
					if(task.getStartDate() == "" || task.getStartDate().equals("")) {
						task.setStartDate(null);
					}
					if(task.getEndDate() == "" || task.getEndDate().equals("")) {
						task.setEndDate(null);
					}
					if(task.getActStartDate() == "" || task.getActStartDate().equals("")) {
						task.setActStartDate(null);
					}
					if(task.getActEndDate() == "" || task.getActEndDate().equals("")) {
						task.setActEndDate(null);
					}
					if(task.getActDuration() == null || task.getActDuration().equals("")) {
						task.setActDuration("0");
					}
					task.setCreateUser(SessionUtil.getUserUid());
					//save(task);
					String progress = task.getProgress();
					Double s = Double.parseDouble(progress) / 100;
					task.setProgress(s+"");
					insertTasks.add(task);
				}

			}
			
			if (taskAndRelevance.getUpdateTasks().size() > 0) {
				for (Task task : taskAndRelevance.getUpdateTasks()) {
					
					task.setCreateUser(SessionUtil.getUserUid());
					if(!CommonUtil.ifNotEmpty(task.getActDuration())) {
						task.setActDuration("0");
					}
					if(!CommonUtil.ifNotEmpty(task.getActEndDate())) {
						task.setActEndDate(null);
					}
					if(!CommonUtil.ifNotEmpty(task.getActStartDate())) {
						task.setActStartDate(null);
					}
					//save(task);
					updateTasks.add(task);
				}
			}
			
			if (taskAndRelevance.getDeleteTasks().size() > 0) {
				for (Task task : taskAndRelevance.getDeleteTasks()) {
					//deleteByPrimaryKey(task.getId());
				}
			}
			
			if (taskAndRelevance.getDeleteLinks().size() > 0) {
				for (Iink iink : taskAndRelevance.getDeleteLinks()) {
					//iinksServiceI.deleteByPrimaryKey(iink.getId());
				}
			}
			
			if (taskAndRelevance.getInsertLinks().size() > 0) {
				for (Iink iink : taskAndRelevance.getInsertLinks()) {
					iink.setId(CommonUtil.getUUID());
					//iinksServiceI.save(iink);
					insertLinks.add(iink);
				}
			}
		}
		
		result.put("insertTasks", insertTasks);
		result.put("updateTasks", updateTasks);
		result.put("insertLinks", insertLinks);

		return result;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	private static class NodeInfo{
		String code;
		int count;
		public void increment(){
			this.count++;
		}
	}

	@Override
	public void importExcelTasks(String fileId, String scheduleUid, String projUid){
			String files =AttachmentUtil.TMP_PATH + File.separator + fileId;
			
			//ExcelUtil excelUtil = new ExcelUtil();
			Vector<Vector<String>> data = null;//excelUtil.getData(files,1);
			data.remove(0);
			//Collections.reverse(data);
			List<Task> insertData = new ArrayList<>();
			String parentUid = "1";
			String rootParent = "root";
			String codeUid = "1";
			int parentLenght=0;
			String root = null;
			Map<String,NodeInfo> nodeCodeMap = new HashMap<>();
			for(int i = 0; i < data.size(); i++ ) {
				Vector<String> task = data.get(i);
				if ("".equals(task.get(1))){
					continue;
				}
				Task t = new Task();
				t.setSort(i);
				t.setId(CommonUtil.getUUID());
				t.setCreateUser(SessionUtil.getUserUid());
				t.setCompany(SessionUtil.getCompanyUid());
				t.setCreateTime(new Date());
				String code = task.get(0);
				String[] coddingArrr = code.split("\\.");
				String nodeType = task.get(10);
				t.setCode(t.getId());
				// 判断是否是最顶层根节点
				if(coddingArrr.length == 1 && i == 0 ){
					t.setParent("0"); // 父节点没有，设置为空
					root = t.getCode();
					t.setSort(1);
					nodeCodeMap.put(root,NodeInfo.builder().code(root).count(0).build());
				}else if(coddingArrr.length == 1){
					// 判断是第二层节点
					NodeInfo parentNode = nodeCodeMap.get(root);
					//t.setParent(parentNode.getCode());
					t.setParent("0");
					parentNode.increment();
					nodeCodeMap.put(code,NodeInfo.builder().code(t.getId()).count(0).build());
					t.setSort(parentNode.getCount());
				}else{
					NodeInfo parentNode = nodeCodeMap.get(code.substring(0,code.lastIndexOf(".")));
					t.setParent(parentNode.getCode());
					parentNode.increment();
					nodeCodeMap.put(code,NodeInfo.builder().code(t.getId()).count(0).build());
					t.setSort(parentNode.getCount());
				}


				//t.setCode(parentUid);
				t.setCoding(task.get(0));
				t.setText(task.get(1));
				t.setStartDate(task.get(2));
				t.setDuration(task.get(3));
				t.setEndDate(task.get(4));
				if (!"".equals(task.get(5))){
					t.setActStartDate(task.get(5));
				}
				if (!"".equals(task.get(6))){
					t.setActDuration(task.get(6));
				}
				if (!"".equals(task.get(7))){
					t.setActEndDate(task.get(7));
				}
				if (!"".equals(task.get(8))){
					t.setProgress(task.get(8));
				}
				t.setTaskStandard(task.get(9));
				t.setTaskType(task.get(10));
				t.setTaskWeight(task.get(11));

				/*parentUid = CommonUtil.getUUID();	//上级id
				
				if(task.get(0).length()==1) {
					t.setParent("0");
				}else {
					t.setParent(parentUid);
				}*/
				
				t.setProjUid(projUid);
				t.setScheduleUid(scheduleUid);
				// 保存
				insertData.add(t);
//				save(t);
			}
		dao.insertTaskByBatch(insertData);
		
	}


	@Override
	public void importXmlTasks(String fileId, String scheduleUid, String projUid) {
		String files = AttachmentUtil.TMP_PATH + File.separator + fileId;
		
		File file = new File(AttachmentUtil.TMP_PATH + File.separator + fileId);
		
		File f = new File(files);   

		
		
	}


	@Override
	public List<Task> getTaskFilterData(String id, String parent, String companyUid) {
		return dao.getTaskFilterData(id, parent, companyUid);
	}


	@Override
	public List<Task> getTaskFilterData2(String id, String parent, String companyUid) {
		return dao.getTaskFilterData2(id, parent, companyUid);
	}


	@Override
	public Task searchTaskSuperiorName(String id, String parent, String companyUid) {
		return dao.searchTaskSuperiorName(id, parent, companyUid);
	}

	@Override
	public List<TaskDTO> getChildTasks(String projUid, String parentUid) {
		return dao.getChildTasks(projUid,parentUid);
	}

	@Override
	public void batchUpdateTask(String ids, String assignee, String approver,String startDate,String endDate,String assigneeName,String approverName) {
		String[] idList = ids.split(",");
		dao.batchUpdateTask(idList,assignee, approver,startDate,endDate,assigneeName,approverName);
	}

	@Override
	public List<Map<String, Object>> getTemplates(String parentUid) {
		return dao.getTemplates(parentUid);
	}

	@Override
	public void insertProgressTemplate(List<Task> list, String parentId,String scheduleUid) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		String randStr =CommonUtil.getUUID().substring(0,6);
		Map<String, Object> tempSchedule = dao.getTempSchedule();
		if(tempSchedule==null){
			throw new EngineException("未找到启用的模板，请先启动模板!",500101);
		}else{
			list = dao.getTemplatesList(String.valueOf(tempSchedule.get("id")));

			for (Task task : list) {
				if("root".equals(task.getParent())){
					task.setParent("0");
				}else{
					task.setParent(task.getParent()+randStr);
				}
				task.setCoding(task.getCode());
				task.setScheduleUid(scheduleUid);
				task.setStartDate(dateString);
				task.setEndDate(dateString);
			/*task.setActStartDate(dateString);
			task.setActEndDate(dateString);*/
				task.setCode(task.getId()+randStr);
				task.setId(CommonUtil.getUUID());
//			save(task);
			}
			dao.insertTaskByBatch(list);
		}

	}

	@Override
	public List<Map<String, Object>> getRemindList() {
		return dao.getRemindList();
	}

	@Override
	public void updateParentTask(String parent) {
		List<Task> dataList = dao.getChildProgressTaskList(parent);
		Double totalWeight = 0.0;
		Double totalProgress = 0.0;
		for (Task task :  dataList) {
			Double taskWeight = task.getTaskWeight()==null||"".equals(task.getTaskWeight())? 0:Double.valueOf(task.getTaskWeight());
			Double taskProgress = task.getProgress()==null||"".equals(task.getProgress())? 0:Double.valueOf(task.getProgress());
			// 默认权重是100
			Double weight = task.getTaskWeight()==null||"".equals(task.getTaskWeight()) ? 100 : taskWeight;
			totalWeight += weight;
			// 默认进度是0
			Double progress = task.getProgress()==null||"".equals(task.getProgress()) ? 0 : taskProgress;
			totalProgress += progress*weight;
		}
		Double progress = (totalWeight == 0.0 ? totalProgress/100 : totalProgress/totalWeight);
		Task task = new Task();
		task.setCode(parent);
		task.setProgress(String.valueOf(progress));
		dao.updateParentTask(task);
		//task.progress = progress === '0.00' ? 0.01: progress
		//dao.batchUpdateParentTask(taskCode);
	}

	@Override
	public List<Task> getProgressTaskForApp(String scheduleUid, String userUid,int pageSize, int pageIndex, Integer type,String dataType,String search) {
		int start = (pageIndex - 1) * pageSize;
		return dao.getProgressTaskForApp(scheduleUid,userUid,start,pageSize,type,dataType,search);
	}

	@Override
	public ReturnPageJson getReportForLease(ScheduleQuery query) {
		ReturnPageJson result = new ReturnPageJson();

		List<LeaseReport> data = dao.getReportForLease(query);
		int totalCount = dao.getReportForLeaseTotalCount(query);
		result.setData(data);
		result.setTotalCount(totalCount);
		return result;
	}

	/**
	 * 获取租赁项目进度汇总表
	 * @param query
	 * @return
	 */
	@Override
	public ReturnPageJson getReportForLeaseCollect(ScheduleQuery query) {
		ReturnPageJson result = new ReturnPageJson();

		List<Map<String,Object>> data = dao.getReportForLeaseCollect(query);
		int totalCount = dao.getLeaseCollectReportForTotalCount(query);
		result.setData(data);
		result.setTotalCount(totalCount);
		return result;
	}

	/**
	 * 更新任务进度状态
	 */
	@Override
	public void updateTaskStatus() {
		dao.updateTaskStatus();
	}
}
