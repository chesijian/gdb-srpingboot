package com.jrsoft.business.modules.construction.service.impl;


import com.jrsoft.business.modules.construction.dao.BusinessDao;
import com.jrsoft.business.modules.construction.domain.FileCatalog;
import com.jrsoft.business.modules.construction.service.BusinessService;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;



/**
 * 课本类处理
 *
 * @author Blueeyedboy
 * @create 2018-10-13 16:00
 **/

@Service
@Transactional
public class BusinessServiceImpl implements BusinessService {

	@Autowired
	private BusinessDao businessDao;

	@Override
	public List<Map<String, Object>> getPurchaseList(String projUid) {
		return businessDao.getPurchaseList(projUid);
	}

	@Override
	public List<Map<String, Object>> getPurchaseDet(String orderCode,String search,int start,int end) {
		return businessDao.getPurchaseDet(orderCode,search,start,end);
	}

	@Override
	public void importCatalogTemplate(List<FileCatalog> list, String parentId) {

	}

	/**
	 * 导入文档目录模板
	 * @param list
	 * @param parentId
	 */
	/*@Override
	public void importCatalogTemplate(List<FileCatalog> list, String parentId) {
		String randStr = CommonUtil.getUUID().substring(0,5);
		List<FileCatalog> result = new ArrayList<>();
		for (FileCatalog item : list) {
			List<FileCatalog> oldData = businessDao.getHistoryFileCatalog(item);
			if(oldData==null||oldData.size()==0){
				item.setId(item.getTempUid()+randStr);
				item.setCreateUser(SessionUtil.getUserUid());
				item.setCompany(SessionUtil.getCompanyUid());
				item.setCreateTime(new Date());
				if("root".equals(item.getParentUid())){
					item.setParentUid(parentId);
				}else{
					FileCatalog parent = businessDao.getParentFileCatalog(item);
					if(parent!=null){
						item.setParentUid(parent.getId());
					}else{
						item.setParentUid(item.getParentUid()+randStr);
					}

				}
				result.add(item);
			}

		}
		if(result.size()>0){
			businessDao.batchInsertCatalog(result);
		}


	}*/

	public String getRandomString(int length){
		//定义一个字符串（A-Z，a-z，0-9）即62位；
		String str="zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
		//由Random生成随机数
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		//长度为几就循环几次
		for(int i=0; i<length; ++i){
			//产生0-61的数字
			int number=random.nextInt(62);
			//将产生的数字通过length次承载到sb中
			sb.append(str.charAt(number));
		}
		//将承载的字符转换成字符串
		return sb.toString();
	}
}
