package com.jrsoft.business.modules.meeting.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface MeetingDao {
	
	
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
	List<Map<String, Object>> searchMeetingRoomtList(@Param("projUid")String projUid, @Param("start") int start, @Param("end") int end, @Param("search") String search,
			@Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("dbType") String dbType);
	
	
	/**
	 * 查询会议室占用时间记录
	 * @param id
	 * @param date
	 * @return
	 */
	List<Map<String, Object>> searchMeetingRoomtTimeDetails(@Param("id") String id, @Param("date") String date, @Param("dbType") String dbType);

	
	/**
	 * 根据id编辑会议室
	 * @param id
	 * @return
	 */
	Map<String, Object> searchMeetingRoomtById(@Param("id") String id);

	
	/**
	 * 删除会议室时间记录
	 * @param id
	 */
	void deleteMeetingRoomtTime(@Param("id") String id);

	
	/**
	 * 删除会议室
	 * @param id
	 */
	void deleteMeetingRoomt(@Param("id") String id);

	
	/**
	 * 根据项目id查询会议列表
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
	List<Map<String, Object>> searchMeetingList(@Param("projUid") String projUid, @Param("start") int start, @Param("end") int end, @Param("type") String type, @Param("search") String search,
			@Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("dbType") String dbType);

	List<Map<String, Object>> getMeetingTasks(@Param("projUid") String projUid, @Param("start") int start, @Param("end") int end, @Param("type") String type, @Param("search") String search,
												@Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("dbType") String dbType);

	/**
	 * 编辑会议
	 * @param id
	 * @return
	 */
	Map<String, Object> searchMeetingById(@Param("id") String id, @Param("userUid") String userUid, @Param("dbType") String dbType);

	
	/**
	 * 删除人员
	 * @param id
	 */
	void deletePersonnel(@Param("id") String id);

	
	/**
	 * 删除会议
	 * @param id
	 */
	void deleteMeeting(@Param("id") String id);

	
	/**
	 * 查询会议室占用时间
	 * @param id
	 * @return
	 */
	Integer checkMeetingOccupationTime(@Param("id") String id, @Param("startDate") String startDate, 
			@Param("endDate") String endDate, @Param("dbType") String dbType);

	
	/**
	 * 更新当前人签到状态
	 * @param id
	 * @param userId
	 */
	void updateMeetingUserStatus(@Param("id") String id, @Param("userId") String userId, @Param("status") int status);

	
	/**
	 * 查询会议任务参与人
	 * @param copyUser
	 * @return
	 */
	List<Map<String, Object>> searchMeetingcopyUser(@Param("copyUser") String[] copyUser);

	/**
	 * 根据id查询会议任务
	 * @param id
	 * @return
	 */
	Map<String, Object> getOrderDetail(@Param("id") String id);

	

}
