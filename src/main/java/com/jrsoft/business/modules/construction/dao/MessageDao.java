package com.jrsoft.business.modules.construction.dao;

import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface MessageDao {

	/**
	 * 获取消息列表数据
	 * @author 车斯剑
	 * @date 2018年10月20日下午2:27:18
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> getMessageList(@Param("companyUid") String companyUid, @Param("userUid") String userUid, @Param("projUid") String projUid,
                                                    @Param("start") int start, @Param("end") int end, @Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("statisDate") String statisDate, @Param("dbType") String dbType);

	/**
	 * 获取消息列表数据
	 * @author 车斯剑
	 * @date 2018年10月20日下午2:27:18
	 * @param businessKey
	 * @return
	 */
	public List<Map<String, Object>> getCommentsList(@Param("businessKey") String businessKey, @Param("start") int start, @Param("end") int end, @Param("dbType") String dbType);

	/**
	 * 获取回复数据
	 * @param parentUid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> getReplyDetails(@Param("parentUid") String parentUid, @Param("start") int start, @Param("end") int end, @Param("dbType") String dbType);

	/**
	 * 获取评论总数
	 * @param businessKey
	 * @return
	 */
	public int getCommentTotalCount(@Param("businessKey") String businessKey);
	/**
	 * 插入查看人记录
	 * @param obj
	 * @return
	 */
	public int insertLookUser(Map<String, Object> obj);


	/**
	 * 获取当前人是否已有查看记录
	 * @author 车斯剑
	 * @date 2018年10月18日下午7:02:20
	 * @return
	 */
	public int findUserLookRecord(@Param("id") String id, @Param("userUid") String userUid);


	/**
	 * 获取已读点赞列表数据
	 * @author 车斯剑
	 * @date 2018年10月20日下午2:27:18
	 * @param businessKey
	 * @return
	 */
	public List<Map<String, Object>> getLookAndLikeList(@Param("businessKey") String businessKey, @Param("ifLike") String ifLike, @Param("start") int start, @Param("end") int end, @Param("dbType") String dbType);

	/**
	 * 获取已读、点赞总数
	 * @param businessKey
	 * @return
	 */
	public int getLookAndLikeTotalCount(@Param("businessKey") String businessKey, @Param("ifLike") String ifLike);

	/**
	 * 点赞、取消点赞
	 * @param businessKey
	 * @param userUid
	 * @param likeValue
	 */
	public void updateLikeValue(@Param("businessKey") String businessKey, @Param("userUid") String userUid, @Param("likeValue") Integer likeValue);


	public List<Map<String, Object>> getSelectedUsers(@Param("ids") String[] ids, @Param("companyUid") String companyUid);

	public void deleteByBusinessKey(@Param("businessKey") String businessKey);

	int getIfLike(@Param("businessKey") String businessKey, @Param("userUid") String userUid);

}
