package com.jrsoft.business.modules.construction.service;


import java.util.List;
import java.util.Map;

public interface MessageService {





	/**
	 * 获取消息列表数据数据
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> getMessageList(String companyUid, String userUid, String projUid,
                                                    int start, int end, boolean isCompanyAdmin, String statisDate, String dbType);

	/**
	 * 获取评论列表数据
	 * @param businessKey
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> getCommentsList(String businessKey, int start, int end);

	/**
	 * 获取回复数据
	 * @param replyUid
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> getReplyDetails(String replyUid, int start, int end);
	/**
	 * 获取评论总数
	 * @param businessKey
	 * @return
	 */
	public int getCommentTotalCount(String businessKey);

	public void insertLookUser(String id, String userUid);

	/**
	 * 获取已读点赞列表数据
	 * @param businessKey
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> getLookAndLikeList(String businessKey, String ifLike, int start, int end);
	/**
	 * 获取已读、点赞总数
	 * @param businessKey
	 * @return
	 */
	public int getLookAndLikeTotalCount(String businessKey, String ifLike);

	/**
	 * 点赞、取消点赞
	 * @param businessKey
	 * @param userUid
	 * @param likeValue
	 */
	public void updateLikeValue(String businessKey, String userUid, Integer likeValue);
	/**
	 * 获取已选中的人
	 * @param ids
	 * @param companyUid
	 * @return
	 */
	public List<Map<String, Object>> getSelectedUsers(String[] ids, String companyUid);


	int getIfLike(String businessKey, String userUid);
}
