package com.jrsoft.business.modules.meeting.service;

import java.util.List;
import java.util.Map;

public interface MeetingServiceI {
	
	
	/**
	 * 根据项目id查询会议室列表
	 * @param projUid
	 * @param start
	 * @param end
	 * @param search
	 * @param isCompanyAdmin
	 * @param userUid
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchMeetingRoomtList(String projUid, int start, int end, String search,
			boolean isCompanyAdmin, String userUid, String companyUid);
	
	
	/**
	 * 查询会议室占用时间记录
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> searchMeetingRoomtTimeDetails(String id, String date);

	
	/**
	 * 根据id编辑会议室
	 * @param id
	 * @return
	 */
	Map<String, Object> searchMeetingRoomtById(String id);

	
	/**
	 * 删除会议室
	 * @param id
	 */
	void delete(String id);

	
	/**
	 * 根据项目查询会议
	 * @param projUid
	 * @param start
	 * @param end
	 * @param type
	 * @param search
	 * @param isCompanyAdmin
	 * @param userUid
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchMeetingList(String projUid, int start, int end, String type, String search,
			boolean isCompanyAdmin, String userUid, String companyUid);

	/**
	 * 根据项目id查询会议任务列表
	 * @param projUid
	 * @param start
	 * @param end
	 * @param type
	 * @param search
	 * @param isCompanyAdmin
	 * @param userUid
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> getMeetingTasks(String projUid, int start, int end, String type, String search,
												boolean isCompanyAdmin, String userUid, String companyUid);
	/**
	 * 编辑会议
	 * @param id
	 * @return
	 */
	Map<String, Object> searchMeetingById(String id, String userUid);

	
	/**
	 * 删除会议
	 * @param id
	 */
	void deleteMeeting(String id);

	
	/**
	 * 查询会议室占用时间
	 * @param id
	 * @return
	 */
	Integer checkMeetingOccupationTime(String id, String startDate, String endDate);

	
	/**
	 * 更新当前人签到状态
	 * @param id
	 * @param userId
	 */
	void updateMeetingUserStatus(String id, String userId, int status);

	
	/**
	 * 查询会议任务参与人
	 * @param copyUserUid
	 * @return
	 */
	List<Map<String, Object>> searchMeetingcopyUser(String copyUserUid);

	/**
	 * 根据id查询会议任务反详情
	 * @param parentUid
	 * @return
	 */
	Map<String, Object> getOrderDetail(String parentUid);

	

}
