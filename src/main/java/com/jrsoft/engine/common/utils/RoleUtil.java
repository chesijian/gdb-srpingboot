package com.jrsoft.engine.common.utils;

import com.jrsoft.engine.model.org.OrgRole;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2017-08-12 16:05
 **/
public class RoleUtil {
    /**
     * 判断是否是公司管理员
     * @author 大雄
     * @Title isCompanyAdmin
     * @Date 2015-5-11
     * @Description TODO
     * @Params @return
     * @Return boolean
     */
    public static boolean isCompanyAdmin(){
        List<OrgRole> roleList = SessionUtil.getRoleSet();
        if(!CommonUtil.ifNotEmptyList(roleList))
            return false;
        for(OrgRole role : roleList){
			if(role.getRoleId() != null) {
				if ("ROLE_COMPANY_ADMIN".equals(role.getRoleId())) {
					return true;
				}
			}
				if ("ROLE_COMPANY_ADMIN".equals(role.getId())) {
					return true;
				}
        }
        return false;
    }

	/**
	 * 判断是否是公司管理员
	 * @author 大雄
	 * @Title isCompanyAdmin
	 * @Date 2015-5-11
	 * @Description TODO
	 * @Params @return
	 * @Return boolean
	 */
	public static boolean isSubAdmin(){
		List<OrgRole> roleList = SessionUtil.getRoleSet();
		if(!CommonUtil.ifNotEmptyList(roleList))
			return false;
		for(OrgRole role : roleList){
			if(role.getRoleId() != null) {
				if ("ROLE_SUB_ADMIN".equals(role.getRoleId())) {
					return true;
				}
			}
			if ("ROLE_SUB_ADMIN".equals(role.getId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是所有数据权限
	 * @author 大雄
	 * @Title isCompanyAdmin
	 * @Date 2015-5-11
	 * @Description TODO
	 * @Params @return
	 * @Return boolean
	 */
	public static boolean isDataAdmin(){
		List<OrgRole> roleList = SessionUtil.getRoleSet();
		if(!CommonUtil.ifNotEmptyList(roleList))
			return false;
		for(OrgRole role : roleList){
			if(role.getRoleId() != null) {
				if ("ROLE_DATA_ADMIN".equals(role.getRoleId())) {
					return true;
				}
			}
			if ("ROLE_DATA_ADMIN".equals(role.getId())) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 判断是否是超级管理员
	 * @author 大雄
	 * @Title isCompanyAdmin
	 * @Date 2015-5-11
	 * @Description TODO
	 * @Params @return
	 * @Return boolean
	 */
	public static boolean isAdmin(){
		List<OrgRole> roleList = SessionUtil.getRoleSet();
		if(!CommonUtil.ifNotEmptyList(roleList))
			return false;
		for(OrgRole role : roleList){
			if(role.getRoleId() != null){
				if("ROLE_ADMIN".equals(role.getRoleId())){
					return true;
				}
			}
			if ("ROLE_ADMIN".equals(role.getId())) {
				return true;
			}

		}
		return false;
	}

	/**
	* 判断用户是否拥有该角色
	* @param
	* @return 
	* @author Blueeyedboy
	* @create 11/9/2018 10:38 AM
	**/
	public static boolean isAllow(String roleUid){
		List<OrgRole> roleList = SessionUtil.getRoleSet();
		if(!CommonUtil.ifNotEmptyList(roleList))
			return false;
		for(OrgRole role : roleList){
			if (roleUid.equals(role.getId())) {
				return true;
			}

		}
		return false;
	}
}
