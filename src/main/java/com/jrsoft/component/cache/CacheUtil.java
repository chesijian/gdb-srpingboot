package com.jrsoft.component.cache;

import com.alibaba.fastjson.JSON;
import com.jrsoft.component.redis.RedisUtil;

import com.jrsoft.engine.base.domain.sys.PfTokenRedis;
import com.jrsoft.engine.common.utils.CommonUtil;

import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.model.org.OrgDepart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import java.util.*;

/**
 * redis缓存操作类
 *
 * @author Blueeyedboy
 * @create 2018-10-16 16:58
 **/
public class CacheUtil {
	public static int EXPIRE_IN_ONE_DAY = 24*60*60;
	// 默认公有云license有效期1天
	public static int LICENSE_EXPIRE_IN = 24*60*60;

	// 默认token每次可以延期30分钟
	public static int EXPIRE_DELAY_SECONDS = 1800;

	public static int EXPIRE_IN = 7200;//默认超时时间2个小时
	public static final String AUTH_ROLE_KEY = "AUTH_ROLE";
	public static final String USER_KEY = "USER";
	public static final String ORG_KEY = "ORG";
	public static final String VCODE_KEY = "SYS:VCODE:";
	public static final String AUTH_LICENSE_KEY = "AUTH_LICENSE";

	public static AntPathMatcher antPathMatcher = new AntPathMatcher();



	/**
	* 设置redis中存储token
	* @param tokenPo PfTokenRedis
	* @return
	* @author Blueeyedboy
	* @create 2018/10/16 17:11
	**/
	public static void putToken(PfTokenRedis tokenPo){
		//精简
		tokenPo.getUser().setCreateUser(null);
		tokenPo.getUser().setCreateTime(null);
		tokenPo.getUser().setUpdateUser(null);
		tokenPo.getUser().setUpdateTime(null);
		RedisUtil.getInstance().set(USER_KEY+":"+ tokenPo.getCompany().getId()+":"+tokenPo.getUser().getId(), tokenPo, 2*60*60);
	}

	/**
	* 从redis中获取tokenRedis
	* @param
	* @return
	* @author Blueeyedboy
	* @create 2018/10/16 17:12
	**/
	public static PfTokenRedis  getToken(PfTokenRedis tokenPo){
		Object obj = RedisUtil.getInstance().get(USER_KEY+":"+ tokenPo.getCompany().getId()+":"+tokenPo.getUser().getId());
		if(obj != null){
			return (PfTokenRedis)obj;
		}
		return null;
	}

	/**
	 * 退出登陆
	 * @param
	 * @return
	 * @author Blueeyedboy
	 * @create 6/29/2019 11:48 AM
	 **/
	public static void deleteToken(PfTokenRedis tokenPo){
		if( tokenPo != null)
			RedisUtil.getInstance().del(USER_KEY+":"+ tokenPo.getCompany().getId()+":"+tokenPo.getLoginType()+":"+tokenPo.getUser().getId());
	}

	/**
	 * 根据key 获取过期时间
	 * @param tokenPo 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public static long getTokenExpire(PfTokenRedis tokenPo){
		String key = USER_KEY+":"+ tokenPo.getCompany().getId()+":"+tokenPo.getLoginType()+":"+tokenPo.getUser().getId();
		return getExpire(key);
	}


	public static void put(String key,Object obj){
		RedisUtil.getInstance().set(key,obj);
	}

	public static void put(String key,String obj,long expire){
		RedisUtil.getInstance().set(key,obj,expire);
	}

	public static Object get(String key){
		try {
			return RedisUtil.getInstance().get(key);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public static long getExpire(String key){
		return RedisUtil.getInstance().getExpire(key);
	}
	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean setExpire(String key,long time){
		return RedisUtil.getInstance().expire(key,time);
	}

	/**
	 * 指定缓存失效时间
	 * @param tokenPo 键
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean setTokenExpire(PfTokenRedis tokenPo,long time){
		String key = USER_KEY+":"+ tokenPo.getCompany().getId()+":"+tokenPo.getLoginType()+":"+tokenPo.getUser().getId();
		return setExpire(key,time);
	}

	public static String getString(String key){
		Object result = get(key);
		if(result != null){
			return String.valueOf(result);
		}
		return null;
	}

	public static void del(String key){
		RedisUtil.getInstance().del(key);
	}
	/*public static int size(String pattern){
		Set<String> keys = RedisUtil.getInstance().getRedisTemplate().keys(pattern);
		return keys.size();
	}*/

	/*public static void batchDel(String pattern){
		Set<String> keys = getKeys(pattern);

		RedisUtil.getInstance().getRedisTemplate().delete(keys);
	}*/

	/*public static Set<String> getKeys(String pattern){
		Set<String> keys = RedisUtil.getInstance().getRedisTemplate().keys(pattern);

		return keys;
	}*/

	/**
	 * 根据请求方法删除公司中某个资源及角色
	 * @param companyUid String
	 * @return
	 * @author Blueeyedboy
	 * @create 2018/10/16 17:13
	 **/
	/*public static void deleteAuthsAndRoles(String companyUid){
		if(!CommonUtil.ifNotEmpty(companyUid)){
			companyUid = SessionUtil.getCompanyUid();
		}
		String key = AUTH_ROLE_KEY+":"+companyUid+":*";
		Set<String> keys = RedisUtil.getInstance().getRedisTemplate().keys(key);
		RedisUtil.getInstance().getRedisTemplate().delete(keys);
	}*/


	/**
	 * 删除公有云模式获取企业license信息
	 * @param
	 * @return
	 * @author Blueeyedboy
	 * @create 12/9/2019 4:39 PM
	 **/
	public static void delCorpLicense(String orgUid){
		CacheUtil.del(AUTH_LICENSE_KEY+":"+orgUid);
	}



	/*public static void putForSet(String key,String value){
		RedisUtil.getInstance().getRedisTemplate().opsForSet().add(key,value);
	}

	public static Set<Object> getForSet(String key){
		return RedisUtil.getInstance().getRedisTemplate().opsForSet().members(key);
	}

	public static void removeForSet(String key,String value){
		Set<Object> members = RedisUtil.getInstance().getRedisTemplate().opsForSet().members(key);
		RedisUtil.getInstance().getRedisTemplate().opsForSet().remove(key,members);
	}*/

}
