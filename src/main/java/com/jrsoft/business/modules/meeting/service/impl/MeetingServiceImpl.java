package com.jrsoft.business.modules.meeting.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrsoft.business.modules.meeting.dao.MeetingDao;
import com.jrsoft.business.modules.meeting.service.MeetingServiceI;
import com.jrsoft.engine.common.utils.CommonUtil;

@Service
@Transactional()
public class MeetingServiceImpl implements MeetingServiceI{
	
	@Autowired
	private MeetingDao meetingDao;

	
	@Override
	public List<Map<String, Object>> searchMeetingRoomtList(String projUid, int start, int end, String search,
			boolean isCompanyAdmin, String userUid, String companyUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return meetingDao.searchMeetingRoomtList(projUid, start, end, search, isCompanyAdmin, userUid, companyUid, dbType);
	}


	@Override
	public List<Map<String, Object>> searchMeetingRoomtTimeDetails(String id, String date) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return meetingDao.searchMeetingRoomtTimeDetails(id, date, dbType);
	}


	@Override
	public Map<String, Object> searchMeetingRoomtById(String id) {
		return meetingDao.searchMeetingRoomtById(id);
	}


	@Override
	public void delete(String id) {
		//删除会议室时间记录
		//meetingDao.deleteMeetingRoomtTime(id);
		//删除会议室
		meetingDao.deleteMeetingRoomt(id);
	}


	@Override
	public List<Map<String, Object>> searchMeetingList(String projUid, int start, int end, String type, String search,
			boolean isCompanyAdmin, String userUid, String companyUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start = start - 1;
		}
		return meetingDao.searchMeetingList(projUid, start, end, type, search, isCompanyAdmin, userUid, companyUid, dbType);
	}

	@Override
	public List<Map<String, Object>> getMeetingTasks(String projUid, int start, int end, String type, String search, boolean isCompanyAdmin, String userUid, String companyUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		if(dbType.equals("mysql")) {
			start --;
		}
		return meetingDao.getMeetingTasks(projUid, start, end, type, search, isCompanyAdmin, userUid, companyUid, dbType);
	}

	@Override
	public Map<String, Object> searchMeetingById(String id, String userUid) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return meetingDao.searchMeetingById(id, userUid, dbType);
	}


	@Override
	public void deleteMeeting(String id) {
		//删除人员
		meetingDao.deletePersonnel(id);
		//删除会议
		meetingDao.deleteMeeting(id);
	}


	@Override
	public Integer checkMeetingOccupationTime(String id, String startDate, String endDate) {
		String dbType = CommonUtil.getDatabaseType();	//获取数据库类型
		return meetingDao.checkMeetingOccupationTime(id, startDate, endDate, dbType);
	}


	@Override
	public void updateMeetingUserStatus(String id, String userId, int status) {
		meetingDao.updateMeetingUserStatus(id, userId, status);
	}


	@Override
	public List<Map<String, Object>> searchMeetingcopyUser(String copyUserUid) {
		String[] copyUser = copyUserUid.split(",");
		if(copyUser != null) {
			return meetingDao.searchMeetingcopyUser(copyUser);
		}
		return null;
	}

	@Override
	public Map<String, Object> getOrderDetail(String id) {
		return meetingDao.getOrderDetail(id);
	}
}
