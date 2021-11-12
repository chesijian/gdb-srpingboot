/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jrsoft.engine.interceptor;

import com.fasterxml.jackson.core.JsonParseException;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.exception.EngineException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局拦截器
 *
 * @author Blueeyedboy
 * @version 2014-8-19
 */
public class BaseInterceptor implements HandlerInterceptor {

    //AuthorizationUtil authorizationUtil= new AuthorizationUtil();



    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //System.out.println("请求方法:"+request.getRequestURI());
		if(request.getMethod().toUpperCase().equals("OPTIONS")){
			return true;
		}
        //LogInterceptor.threadRequestLocal.set(request);

        if (logger.isDebugEnabled()) {
			logger.info("请求地址: {},isWeChat:{}", request.getRequestURI(), SessionUtil.isWechat());
			long beginTime = System.currentTimeMillis();//1、开始时间
            //LogInterceptor.startTimeThreadLocal.set(beginTime);        //线程绑定变量（该数据只有当前请求的线程可见）
            logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
                    .format(beginTime), request.getRequestURI());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        boolean flag = false;
        try {

			//过滤参数中特殊字符
			/*authorizationUtil.checkIllCharacters(request);
            flag = authorizationUtil.isValid(request,response);*/
            flag=true;

        }catch (EngineException e) {
            // TODO Auto-generated catch block
            result.put("status", e.getErrcode());
            result.put("errorMsg", e.getMessage());
            //e.printStackTrace();

        }catch (MalformedJwtException e) {
            // TODO Auto-generated catch block
            result.put("status", EngineException.ERRCODE_JWT_MALFORMMED);
            result.put("errorMsg", "cant not parse token");

        } catch (ExpiredJwtException e) {
            // TODO Auto-generated catch block
            result.put("status", EngineException.ERRCODE_JWT_EXPIRED);
            result.put("errorMsg", e.getMessage());

        }  catch (Exception e) {
            // TODO Auto-generated catch block
            result.put("status", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.put("errorMsg", e.getMessage());
            e.printStackTrace();

        } finally {
            if (flag) {
                return true;
            } else {
                clear(request);
                response.getWriter().write(CommonUtil.objToJson(result));
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        // 保存日志
        //LogUtils.saveLog(request, handler, ex, null);
        if(ex != null){
        	/*if( ex instanceof EngineForbiddenException){
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("status", ((EngineForbiddenException) ex).errcode);
				result.put("errorMsg", ex.getMessage());
				//重置response
				response.reset();
				//设置编码格式
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=UTF-8");

				PrintWriter pw = response.getWriter();
				pw.write(CommonUtil.objToJson(result));
				pw.flush();
				pw.close();
			}else{
				ex.printStackTrace();
			}*/
        }
		if(!request.getMethod().toUpperCase().equals("OPTIONS")){
			clear(request);
		}


    }

    public void clear(HttpServletRequest request){
        //LogInterceptor.TokenRedisThreadLocal.remove();
        // 打印JVM信息。
        if (logger.isDebugEnabled()) {

            //long beginTime = LogInterceptor.startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
            long endTime = System.currentTimeMillis();    //2、结束时间
            logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
                    new SimpleDateFormat("hh:mm:ss.SSS").format(endTime),
                    request.getRequestURI(), Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
                    (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
            //删除线程变量中的数据，防止内存泄漏
            //LogInterceptor.startTimeThreadLocal.remove();
        }
        //从线程中删除
        //LogInterceptor.threadRequestLocal.remove();
    }


}
