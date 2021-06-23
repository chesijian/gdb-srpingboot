package com.jrsoft.business.modules.fileManage.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jrsoft.engine.model.org.OrgRole;
import org.apache.ibatis.annotations.Param;

import com.jrsoft.business.modules.fileManage.model.FileBean;
import com.jrsoft.engine.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface FileManageDao {

	/**
	 * 根据项目id查询文件文件夹
	 * @param parentUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @return
	 */
	List<Map<String, Object>> searchBypRrojectId(@Param("projUid")String projUid, @Param("parentUid") String parentUid, 
			@Param("userUid") String userUid, @Param("companyUid") String companyUid, @Param("isCompanyAdmin")boolean isCompanyAdmin, 
			@Param("search")String search, @Param("dbType") String dbType);
	
	
	/**
	 * Xu
	 * 移动文件夹
	 * @return
	 */
	Boolean moveFolder(@Param("checkedId") String checkedId, @Param("moveId") String moveId);
	
	
	/**
	 * Xu
	 * 查询下级文件目录
	 * @return
	 */
	List<Map<String, Object>> subordinateFiles(@Param("id_") String id_, @Param("userUid") String userUid, @Param("isCompanyAdmin") boolean isCompanyAdmin );
	
	
	/**
	 * Xu
	 * 删除文件夹和下级
	 * @return
	 */
	Boolean deleteTop(@Param("arrId") String arrId);
	
	
	/**
	 * Xu
	 * 重命名文件夹
	 * @param name
	 * @param id
	 */
	Map<String, Object> updateFileName(@Param("name") String name, @Param("id") String id);
	
	
	/**
	 * 检验当前目录文件夹是否重复
	 * @param parentUid
	 * @param companyUid
	 * @param fileName
	 * @return
	 */
	int checkFileName(@Param("parentUid") String parentUid, @Param("companyUid") String companyUid, @Param("fileName") String fileName, @Param("projUid") String projUid);

	
	/**
	 * 检验移动功能不能移动到自己的下级
	 * @param parentUid 上级id
	 * @param id 需要移动的id
	 * @return
	 */
	List<Map<String, Object>> checkMoveNoOneself(@Param("parentUid")String parentUid, @Param("id")String id);

	
	/**
	 * 编辑文件
	 * @param id
	 * @return
	 */
	Map<String, Object> updateFile(@Param("id")String id);


	void moveFile(@Param("ids")String[] ids, @Param("moveId")String moveId);


	void removeFile(@Param("ids")String[] ids);

	/**
	 * 根据项目id查询文件夹
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchFolderList(@Param("projUid") String projUid, @Param("parentUid")String parentUid, @Param("userUid")String userUid, @Param("companyUid") String companyUid,
											   @Param("isCompanyAdmin")boolean isCompanyAdmin,@Param("roleList")List<OrgRole> roleList);


	List<Map<String, Object>> searchBelow(@Param("id")String id, @Param("userUid")String userUid, @Param("companyUid")String companyUid,
										  @Param("isCompanyAdmin") boolean isCompanyAdmin,@Param("roleList")List<OrgRole> roleList);

	

	
	/**
	 * 查询文件
	 * @param projUid
	 * @param parentUid
	 * @param userUid
	 * @param companyUid
	 * @param isCompanyAdmin
	 * @param search
	 * @return
	 */
	List<Map<String, Object>> searchFileBypRrojectId(@Param("projUid")String projUid, @Param("parentUid") String parentUid, 
			@Param("userUid") String userUid, @Param("companyUid") String companyUid, 
			@Param("isCompanyAdmin")boolean isCompanyAdmin, @Param("search")String search, @Param("dbType") String dbType);

	
	/**
	 * 编辑文件夹
	 * @param id
	 * @return
	 */
	Map<String, Object> updateFolder(@Param("id")String id, @Param("dbType") String dbType);

	
	/**
	 * 根据id查询附件
	 * @param id
	 * @return
	 */
	List<FileBean> getFileList(@Param("id") String id, @Param("dbType") String dbType);

	
	/**
	 * 通过id查询文件与项目信息
	 * @param projUid
	 * @param id
	 * @return
	 */
	Map<String, Object> getFileLists(@Param("projUid") String projUid, @Param("id") String id);

	/**
	 * 获取文件总数
	 * @param id
	 * @param search
	 * @return
	 */
	int searchAccessoryTotalCount(@Param("projUid")String projUid, @Param("id") String id,  @Param("search") String search, @Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid,
								  @Param("companyUid") String companyUid,@Param("roleList")List<OrgRole> roleList);


	List<Map<String, Object>> searchAccessoryByFolderIdMySql(@Param("projUid")String projUid, @Param("id") String id, @Param("start") int start, @Param("pageSize") int pageSize,
			@Param("search") String search, @Param("isCompanyAdmin") boolean isCompanyAdmin, @Param("userUid") String userUid,
			@Param("companyUid") String companyUid,@Param("roleList")List<OrgRole> roleList);

	
	Map<String, Object> searchSelectCatalogue(@Param("catalogueId") String catalogueId);


	void insertSelectEntity(@Param("uid") String uid, @Param("projUid")String projUid, @Param("selectCreateUser")String selectCreateUser, @Param("selectCreateTime")Date selectCreateTime, @Param("selectUpdateUser")String selectUpdateUser,
			@Param("selectUpdateTime")Date selectUpdateTime, @Param("selectName")String selectName, @Param("selectCompany")String selectCompany, @Param("selectSubCompany") String selectSubCompany, @Param("selectLb")Integer selectLb);


	void insertEntity(@Param("keyId") String keyId,@Param("uid")String uid, @Param("createUser") String createUser, @Param("createTime")Date createTime, @Param("updateUser")String updateUser, @Param("updateTime")Date updateTime,
			@Param("name")String name, @Param("company")String company, @Param("subCompany")String subCompany, @Param("lb")Integer lb);


	List<Map<String, Object>> searchNextCatalogue(@Param("id") String id);


	List<Map<String, Object>> searchFirstCatalogue(@Param("catalogueId") String catalogueId);


	List<Map<String, Object>> searchDirectoryList(@Param("parentUid") String parentUid, @Param("companyUid")String companyUid);

	
	/**
	 * 删除目录
	 * @param idss
	 */
	void removeCatalogue(@Param("idss")  String[] idss);

	
	/**
	 * 编辑目录
	 * @param id
	 * @return
	 */
	Map<String, Object> searchCatalogueById(@Param("id") String id);
	
	

}
