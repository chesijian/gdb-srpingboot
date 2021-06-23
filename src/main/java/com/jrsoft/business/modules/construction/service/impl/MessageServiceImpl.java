package com.jrsoft.business.modules.construction.service.impl;


import com.jrsoft.business.modules.construction.dao.MessageDao;
import com.jrsoft.business.modules.construction.service.MessageService;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional()
public class MessageServiceImpl implements MessageService {
	@Autowired
	private MessageDao messageDao;



	/**
	 * 获取消息列表数据
	 * @param companyUid
	 * @param userUid
	 * @param projUid
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getMessageList(String companyUid, String userUid, String projUid, 
			int start, int end,boolean isCompanyAdmin,String statisDate, String dbType){
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return messageDao.getMessageList(companyUid,userUid,projUid,start,end,isCompanyAdmin,statisDate,dbType);
	}
	/**
	 * 获取消息列表数据
	 * @param businessKey
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCommentsList(String businessKey,int start, int end){
		String dbType = CommonUtil.getDatabaseType();
		if("mysql".equals(dbType)){
			start--;
		}
		return messageDao.getCommentsList(businessKey,start,end,dbType);
	}

	@Override
	public int getCommentTotalCount(String businessKey) {
		return messageDao.getCommentTotalCount(businessKey);
	}

	@Override
	public List<Map<String, Object>> getReplyDetails(String replyUid, int start, int end) {
		String dbType = CommonUtil.getDatabaseType();
		if("mysql".equals(dbType)){
			start--;
		}
		return messageDao.getReplyDetails(replyUid,start,end,dbType);
	}

	/**
	 * 插入查看人记录
	 ** @param id
	 * @param userUid
	 * @return
	 */
	@Override
	public void insertLookUser(String id,String userUid) {
		Map<String, Object> data = new HashMap<>();
		int lookCount = messageDao.findUserLookRecord(id,userUid);
		if(lookCount==0){
			data.put("createUser",SessionUtil.getUserUid());
			data.put("company",SessionUtil.getCompanyUid());
			//data.put("subCompany",SessionUtil.getSubCompanyId());
			data.put("createTime",new Date());
			data.put("id",CommonUtil.getUUID());

			data.put("userUid",userUid);
			data.put("businessKey",id);
			data.put("isLike",0);

			messageDao.insertLookUser(data);
		}

	}

	@Override
	public List<Map<String, Object>> getLookAndLikeList(String businessKey, String ifLike, int start, int end) {
		String dbType = CommonUtil.getDatabaseType();
		if("mysql".equals(dbType)){
			start--;
		}
		return messageDao.getLookAndLikeList(businessKey,ifLike,start,end,dbType);
	}

	@Override
	public int getLookAndLikeTotalCount(String businessKey, String ifLike) {
		return messageDao.getLookAndLikeTotalCount(businessKey,ifLike);
	}

	@Override
	public void updateLikeValue(String businessKey, String userUid, Integer likeValue) {
		messageDao.updateLikeValue(businessKey,userUid,likeValue);
	}

	@Override
	public List<Map<String, Object>> getSelectedUsers(String[] ids, String companyUid) {
		return messageDao.getSelectedUsers(ids,companyUid);
	}

	@Override
	public int getIfLike(String businessKey, String userUid) {
		return messageDao.getIfLike(businessKey,userUid);
	}
}
