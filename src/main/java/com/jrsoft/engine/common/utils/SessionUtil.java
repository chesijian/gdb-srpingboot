package com.jrsoft.engine.common.utils;

import com.jrsoft.engine.base.domain.sys.PfTokenRedis;
import com.jrsoft.engine.base.interceptor.LogInterceptor;
import com.jrsoft.engine.model.org.OrgDepart;
import com.jrsoft.engine.model.org.OrgRole;
import com.jrsoft.engine.model.org.OrgUser;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * session的处理类
 *
 * @author Blueeyedboy
 * @create 2017-06-07 14:51
 **/
public class SessionUtil {


    public static final int LOGIN_TYPE_PC_WEB = 0;
    public static final int LOGIN_TYPE_MOBILE_WECHAT = 1;
    public static final int LOGIN_TYPE_MOBILE_ANDROID = 2;
    public static final int LOGIN_TYPE_MOBILE_IOS = 3;
    public static final int LOGIN_TYPE_MOBILE_ANDROID_WEB = 4;
    public static final int LOGIN_TYPE_MOBILE_IOS_WEB = 5;
    public static final String is_open_api = "is_open_api";
    public static final String WX_CORP = "wx_corp";
    /**
    * 外链表单传参是否外链
    * @author Blueeyedboy
    * @create 8/3/2020 11:16 PM
    **/
    public static final String HEADER_IS_EXTERNAL_FORM = "is_external_form";
	/**
	 * 外链表单请求url必须携带short_key
	 * @author Blueeyedboy
	 * @create 8/3/2020 11:16 PM
	 **/
    public static final String HEADER_SHORT_KEY = "short_key";



    public static PfTokenRedis getAccessTokenPo(){
//        PfTokenRedis tokenRedis = LogInterceptor.TokenRedisThreadLocal.get();
        PfTokenRedis tokenRedis =(PfTokenRedis) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(tokenRedis != null){
            return tokenRedis;
        }
        //tokenRedis = TokenCache.get(userUid);
        return tokenRedis;
    }


    public static String getUserUid() {
        PfTokenRedis hr =getAccessTokenPo();
        OrgUser user = hr.getUser();
        return user.getId();
    }

    public static String getCompanyUid() {
        PfTokenRedis tokenRedis =getAccessTokenPo();
        OrgDepart company = tokenRedis.getCompany();
        return company.getId();
    }

    public static List<OrgRole> getRoleSet() {
        try {
            PfTokenRedis tokenRedis = getAccessTokenPo();
            if(tokenRedis != null){
                return tokenRedis.getRoleList();
            }
            /*HttpServletRequest request = getCurrentRequest();
            synchronized(request){
                if (request == null || request.getSession() == null) {
                    return null;
                }
                Object o = request.getSession().getAttribute(Enums_SessionType.RoleSet.getValue());
                if (o == null) {
                    return null;
                } else {
                    return (List<OrgRole>) o;
                }
            }*/
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String getSubCompanyUid() {
        return null;
    }

    public static String getUserName() {
        return null;
    }
    /**
     * 从当前线程获取请求
     * @return javax.servlet.http.HttpServletRequest
     * @author Blueeyedboy
     * @create 2017/6/7 15:21
     **/
    public static HttpServletRequest getCurrentRequest() {

        //return LogInterceptor.threadRequestLocal.get();
        return null;
    }

    public static String getCurrentRequestURI(){
		HttpServletRequest request = getCurrentRequest();
		if(request != null){
			return request.getServletPath();
		}
		return null;
	}

	/**
	 * 获取header中配置的是否额外表单
	 * @return String
	 * @author Blueeyedboy
	 * @create 8/3/2020 11:21 PM
	 **/
	public static boolean isExternalForm(){
		HttpServletRequest request = getCurrentRequest();
		if(request != null){
			String isExternalForm = request.getHeader(HEADER_IS_EXTERNAL_FORM);
			String shortKey = request.getHeader(HEADER_SHORT_KEY);
			if(CommonUtil.isNotNull(isExternalForm) && CommonUtil.isNotNull(shortKey) && isExternalForm.equals("true")){
				return true;
			}
		}
		return false;
	}

	/**
	* 获取header中配置的短连接key
	* @return String
	* @author Blueeyedboy
	* @create 8/3/2020 11:21 PM
	**/
	public static String getShortKey(){
		HttpServletRequest request = getCurrentRequest();
		if(request != null){
			String shortKey = request.getHeader(HEADER_SHORT_KEY);
			return shortKey;
		}
		return null;
	}

    /**
     * 获取session
     * @description
     * @author 大雄
     * @date 2016年8月17日下午4:10:02
     * @return
     */
    public static HttpSession getSession(){
        if(getCurrentRequest() == null){
            return null;
        }else{
            return getCurrentRequest().getSession();
        }
    }

    /**
     * 获取会话id
     * @description
     * @author 大雄
     * @date 2016年8月17日下午4:12:15
     * @return
     */
    public static String getSessionId(){
        if(getSession() == null){
            return null;
        }else{
            return getSession().getId();
        }
    }

    /**
     * @author 大雄
     * @Title getAttribute
     * @Date 2014-8-19
     * @Description 获取session中存储的值
     * @Params  key
     * @Params
     * @Return Object
     */
    public static Object getAttribute(String key) {
        if(getSession() == null){
            return null;
        }
        return getSession().getAttribute(key);

    }

    /**
    * 获取session的值
    * @return
    * @author Blueeyedboy
    * @create 2017/6/28 16:01
    **/
    public static Map<String,Object> getSessionData(){

        Map<String,Object> data = new HashMap<String,Object>();
            if(getSession() == null){
                return null;
            }
            HttpSession session = getSession();
            Enumeration enumeration =getSession().getAttributeNames();//获取session中所有的键值对
            while(enumeration.hasMoreElements()){
                String key=enumeration.nextElement().toString();//获取session中的键值
                //根据键值取出session中的值
                data.put(key,session.getAttribute(key));
                //String FileName= (String)session.getAttribute("AddFileName");
            }


        return data;
    }


    /**
     *
     * @author 大雄
     * @Title addAttribute
     * @Date 2014-8-19
     * @Description 设置session中的值
     * @Params @param key
     * @Params @param value
     * @Return void
     */
    public static boolean setAttribute(String key, Object value) {
        if(getSession() == null){
            return false;
        }
        getSession().setAttribute(key,value);
        return true;
    }

    /**
     * 获取客户端IP
     * @return
     * @author Blueeyedboy
     * @create 2017/6/7 15:21
     */
    public static String getClientIpAddr() {
        HttpServletRequest request = getCurrentRequest();
        return getClientIpAddr(request);
    }

	public static String getClientIpAddr(HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

    /**
     *
     * @author 大雄
     * @Title getBasePath
     * @Date 2015-2-5
     * @Description 获取当前系统URL路径
     * @Params @return
     * @Return String
     */

	/**
	 * 获取当前系统URL路径
	 * @author 大雄
	 * @create 2015-2-5
	 * @Description 获取当前系统URL路径
	 * @return String 结果
	 */


    public static String getBasePathEscape() {
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return "";
        }
        String strBackUrl = "http%3a%2f%2f" + request.getServerName(); //服务器地址
        String path = request.getContextPath();
        if(path.startsWith("/")){
            path =  path.substring(1);
        }
        //System.out.println(path+"=====request.getContextPath()----------"+request.getContextPath());

        int serverPort = request.getServerPort(); //端口号
        if (serverPort == 80) {
            strBackUrl += "%2f"+path+"%2f";      //项目名称
        } else {
            strBackUrl = strBackUrl + "%3a" + serverPort +"%2f"+ path+"%2f";
        }
        return strBackUrl;
    }



    /**
    *
    * 判断请求是否是微信
    * @author Blueeyedboy
    * @create 2017/7/4 17:53
    **/
    public static boolean isWechat(){
        //System.out.println(getCurrentRequest().getHeader("user-agent").toLowerCase());
        //因东莞二期使用需要，暂时全部改为true
        /**/
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return false;
        }

        String userAgent = request.getHeader("user-agent").toLowerCase();

        if(userAgent.indexOf("micromessenger")>-1){//微信客户端
            return true;
        }else{
            return false;
        }
    }

	/**
	 *
	 * 判断请求是否是微信
	 * @author Blueeyedboy
	 * @create 2017/7/4 17:53
	 **/
	public static boolean isIos(){
		//System.out.println(getCurrentRequest().getHeader("user-agent").toLowerCase());
		//因东莞二期使用需要，暂时全部改为true
        /**/
		HttpServletRequest request = getCurrentRequest();
		if (request == null) {
			return false;
		}

		String userAgent = request.getHeader("user-agent").toLowerCase();
//		System.out.println("------isIos-----userAgent------------"+userAgent);
		if(userAgent.indexOf("apple")>-1 && userAgent.indexOf("chrome")<0){//微信客户端
			return true;
		}else{
			return false;
		}
	}

    /**
    * 是否是接口访问
    * @param
    * @return
    * @author Blueeyedboy
    * @create 2017/10/31 17:10
    **/
    public static boolean isOpenApi(){
        HttpServletRequest request = getCurrentRequest();
        if (request == null) {
            return false;
        }
        Object str = request.getAttribute(is_open_api);
        if(str != null){
            if(Boolean.parseBoolean(str.toString())){
                return true;
            }
        }
        return false;
    }

    /**
    * 获取当前企业号
    * @return
    * @author Blueeyedboy
    * @create 2017/10/31 17:54
    **/


    /**
    *
    * 获取登陆类型
    * @author Blueeyedboy
    * @create 2017/7/4 17:53
    **/
    public static int getLoginType(){
        if(isWechat()){
            return LOGIN_TYPE_MOBILE_WECHAT;
        }
        return LOGIN_TYPE_PC_WEB;
    }

    /**
    * 获取当前登陆人登陆方式
    * @param 
    * @return 
    * @author Blueeyedboy
    * @create 6/29/2019 12:41 PM
    **/


	/**
	* 获取当前线程绑定的日志对象
	* @param 
	* @return 
	* @author Blueeyedboy
	* @create 7/3/2019 5:17 PM
	**/

    public static synchronized OrgUser getUser() {
        try {
			/*PfTokenRedis tokenRedis = getAccessTokenPo();
			if(tokenRedis != null){
				return tokenRedis.getUser();
			}*/
            HttpServletRequest request = getCurrentRequest();
            synchronized(request){
                if (request == null || request.getSession() == null) {
                    return null;
                }
//                Object o = request.getSession().getAttribute(Enums_SessionType.User.getValue());
                Object o = request.getSession().getAttribute("user");
                if (o == null) {
                    return null;
                } else {
                    return (OrgUser) o;
                }
            }
        } catch (Exception e) {
            return null;
        }
    }



    /**
    * 获取请求中的用户唯一id，当移动端访问的时候
    * @return
    * @author Blueeyedboy
    * @create 2017/7/5 18:29
    **/
    public static String getAccessSecurt(){
        return getAccessSecurt(getCurrentRequest());

    }

    /**
     * 获取请求中的用户唯一id，当移动端访问的时候
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/
    public static String getAccessSecurt(HttpServletRequest request){
        if(request  == null){
            return null;
        }else{
            return request.getParameter("access_securt");
        }

    }

    public static String getAccessCorp(HttpServletRequest request){
        if(request  == null){
            return null;
        }else{
            return request.getParameter("corp_id");
        }

    }
    public static String getAccessAuthCode(HttpServletRequest request){
        if(request  == null){
            return null;
        }else{
            return request.getParameter("auth_code");
        }
    }

    public static String getAccessSuite(HttpServletRequest request){
        if(request  == null){
            return null;
        }else{
            return request.getParameter("suite_id");
        }

    }

    public static String getAccessSuite(){
        if(getCurrentRequest()  == null){
            return null;
        }else{
            return getCurrentRequest().getParameter("suite_id");
        }

    }

	public static String getAccessAgentId(){
		if(getCurrentRequest()  == null){
			return null;
		}else{
			return getCurrentRequest().getParameter("agent_id");
		}

	}
	/**
	* 返回系统配置的所属应用id
	* @param 
	* @return 
	* @author Blueeyedboy
	* @create 6/24/2019 8:03 PM
	**/
	public static String getAccessAppId(){
		if(getCurrentRequest()  == null){
			return null;
		}else{
			return getCurrentRequest().getParameter("app_id");
		}

	}
	/**
	* 返回请求的菜单对应app
	* @param 
	* @return 
	* @author Blueeyedboy
	* @create 4/28/2020 8:49 PM
	**/
	public static String getAccessApp(){
		if(getCurrentRequest()  == null){
			return null;
		}else{
			return getCurrentRequest().getParameter("app");
		}

	}

    /**
     * 获取请求中的微信用户的随机code有效期5分钟
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/
    public static String getAccessCode(HttpServletRequest request){
        if(request  == null){
            return null;
        }else{
            return request.getParameter("access_code");
        }

    }



    /**
     * 当移动端访问的时候获取请求中的用户的令牌，
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/

    /**
     * 当移动端访问的时候获取请求中的用户的令牌，
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/


    /**
     * 当移动端访问的时候获取请求中的用户获取令牌的ak，
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/
    public static String getAccessAk(){
        return getAccessAk(getCurrentRequest());

    }

    /**
     * 当移动端访问的时候获取请求中的用户获取令牌的ak，
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/
    public static String getAccessAk(HttpServletRequest request){
        if(request  == null){
            return null;
        }else{
            return request.getParameter("access_ak");
        }

    }

    /**
     * 当移动端访问的时候获取请求中的用户获取令牌的移动端密码，
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/
    public static String getAccessPwd(){
        return getAccessPwd(getCurrentRequest());

    }

    /**
     * 当移动端访问的时候获取请求中的用户获取令牌的移动端密码，
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/
    public static String getAccessPwd(HttpServletRequest request){
        if(request  == null){
            return null;
        }else{
            return request.getParameter("access_pwd");
        }

    }

    /**
     *   返回前台数据
     * @param response
     * @param object
     * @return
     * @author Blueeyedboy
     * @create 2017/7/5 18:29
     **/
    public static void write(HttpServletResponse response, Object object){
        try {
        response.setHeader("pragma","no-cache" );
        response.setHeader("cache-control","no-cache" );
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(object.toString());
        response.getWriter().flush();
        response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
    *   返回前台数据
     * @param response
     * @param object
    * @return
    * @author Blueeyedboy
    * @create 2017/7/5 18:29
    **/
    public static void writeJson(HttpServletResponse response, Object object){
        try {
            String json = CommonUtil.objToJson(object);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}