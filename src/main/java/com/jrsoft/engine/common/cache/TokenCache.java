package com.jrsoft.engine.common.cache;


import com.jrsoft.component.redis.RedisUtil;
import com.jrsoft.engine.base.domain.sys.PfTokenRedis;

/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2017-07-05 16:30
 **/
public class TokenCache {
	/**
	* PC的有效期默认2个小时
	* @author Blueeyedboy
	* @create 2/1/2021 5:01 PM
	**/
	public static int TOKEN_0_EXPIRE = 2000*60*60;

	/**
	 * token:1 微信的有效期默认2个小时
	 * @author Blueeyedboy
	 * @create 2/1/2021 5:01 PM
	 **/
	private static int TOKEN_1_EXPIRE = 2*60*60;
	/**
	 * token:2 android的有效期默认2个小时
	 * @author Blueeyedboy
	 * @create 2/1/2021 5:01 PM
	 **/
	private static int TOKEN_2_EXPIRE = 2*60*60;
	/**
	 * token:3 ios的有效期默认2个小时
	 * @author Blueeyedboy
	 * @create 2/1/2021 5:01 PM
	 **/
	private static int TOKEN_3_EXPIRE = 2*60*60;
	/**
	 * openApi的有效期默认2个小时
	 * @author Blueeyedboy
	 * @create 2/1/2021 5:01 PM
	 **/
	public static int TOKEN_OPEN_EXPIRE = 2*60*60;



	/*static{
		String token0 = PfSysConfCache.getSysConfVal("sys.login.type0.expire_in");
		if(CommonUtil.ifNotEmpty(token0)){
			TOKEN_0_EXPIRE = Integer.parseInt(token0);
		}
		String token1 = PfSysConfCache.getSysConfVal("sys.login.type1.expire_in");
		if(CommonUtil.ifNotEmpty(token1)){
			TOKEN_1_EXPIRE = Integer.parseInt(token1);
		}
		String token2 = PfSysConfCache.getSysConfVal("sys.login.type2.expire_in");
		if(CommonUtil.ifNotEmpty(token2)){
			TOKEN_2_EXPIRE = Integer.parseInt(token2);
		}
		String token3 = PfSysConfCache.getSysConfVal("sys.login.type3.expire_in");
		if(CommonUtil.ifNotEmpty(token3)){
			TOKEN_3_EXPIRE = Integer.parseInt(token3);
		}
		String tokenOpen = PfSysConfCache.getSysConfVal("sys.open.token.expire_in");
		if(CommonUtil.ifNotEmpty(tokenOpen)){
			TOKEN_OPEN_EXPIRE = Integer.parseInt(tokenOpen);
		}
	}*/

	public static int  getExpireIn(int loginType){
		switch (loginType){
			case 0:
				return TOKEN_0_EXPIRE;
			case 1:
				return TOKEN_1_EXPIRE;
			case 2:
				return TOKEN_2_EXPIRE;
			case 3:
				return TOKEN_3_EXPIRE;
		}
		return TOKEN_0_EXPIRE;
	}


}
