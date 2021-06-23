package com.jrsoft.business.modules.fileManage.service;

import java.util.List;
import java.util.Map;

import com.jrsoft.business.modules.fileManage.model.FileBean;
import com.jrsoft.engine.base.model.ReturnPageJson;

public interface FileManageServiceI {
	
	
	/**
	 * 根据项目id查询文件文件夹
	 * @param parentUid
	 * @param userUid
	 * @param companyUid
	 * @param search
	 * @return
	 */
	List<Map<String, Object>> searchBypRrojectId(String projUid, String parentUid, String userUid, String companyUid, boolean isCompanyAdmin, String search);
	
	
	/**
	 * Xu
	 * 移动文件夹
	 * @return
	 */
	Boolean moveFolder(String checkedId, String moveId);
	
	
	/**
	 * Xu
	 * 查询下级文件目录
	 * @return
	 */
	List<Map<String, Object>> subordinateFiles(String id_, String userUid, boolean isCompanyAdmin);
	
	
	/**
	 * Xu
	 * 删除文件夹和下级
	 * @return
	 */
	Boolean deleteTop(String arrId);
	
	
	/**
	 * Xu
	 * 重命名文件夹
	 * @param name
	 * @param id
	 */
	Map<String, Object> updateFileName(String name, String id);
	
	
	
	/**
	 * 检验当前目录文件夹是否重复
	 * @param parentUid
	 * @param companyUid
	 * @param fileName
	 * @return
	 */
	int checkFileName(String parentUid, String companyUid, String fileName, String projUid);

	
	/**
	 * 检验移动功能不能移动到自己的下级
	 * @param parentUid 上级id
	 * @param id 需要移动的id
	 * @return
	 */
	List<Map<String, Object>> checkMoveNoOneself(String parentUid, String id);

	
	/**
	 * 编辑文件
	 * @param id
	 * @return
	 */
	Map<String, Object> updateFile(String id);

	
	/**
	 * 移动文件
	 * @param checkedId
	 * @param moveId
	 */
	void moveFile(String checkedId, String moveId);

	
	/**
	 * 删除文件
	 * @param id
	 */
	void removeFile(String[] ids);

	
	/**
	 * PC遍历递归文件夹
	 * @param projUid
	 * @return
	 */
	List<Map<String, Object>> searchFolderRecursion(String projUid, String parentUid, String userUid, String companyUid, boolean isCompanyAdmin);

	
	/**
	 * 根据文件夹id查询文件
	 * @param id
	 * @param search
	 * @return
	 */
	ReturnPageJson searchAccessoryByFolderId(String projUid, String id, int pageIndex, int pageSize, String search,
											 boolean isCompanyAdmin, String userUid, String companyUid);

	
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
	List<Map<String, Object>> searchFileBypRrojectId(String projUid, String parentUid, String userUid,
			String companyUid, boolean isCompanyAdmin, String search);

	
	/**
	 * 编辑文件夹目录
	 * @param id
	 * @return
	 */
	Map<String, Object> updateFolder(String id, String dbType);

	
	/**
	 * 根据id查询附件
	 * @param id
	 * @return
	 */
	List<FileBean> getFileList(String id, String dbType);

	
	/**
	 * 通过id查询文件与项目信息
	 * @param projUid
	 * @param id
	 * @return
	 */
	Map<String, Object> getFileLists(String projUid, String id);

	
	/**
	 * 导入文档模板
	 * @param catalogueId
	 * @param projUid
	 * @param parentUid
	 */
	void searchByCatalogue(String catalogueId, String projUid, String parentUid);

	
	/**
	 * 查询目录模板
	 * @param parentUid
	 * @param companyUid
	 * @return
	 */
	List<Map<String, Object>> searchDirectoryList(String parentUid, String companyUid);

	
	
	/**
	 * 删除目录
	 * @param idss
	 */
	void removeCatalogue(String[] idss);

	
	/**
	 * 编辑目录
	 * @param id
	 * @return
	 */
	Map<String, Object> searchCatalogueById(String id);


    List<Map<String,Object>> getDocumentDirectoryTemplate(String parentUid);
}
