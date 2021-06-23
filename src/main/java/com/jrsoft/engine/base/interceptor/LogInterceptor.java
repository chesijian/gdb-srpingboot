/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.jrsoft.engine.base.interceptor;

import com.jrsoft.engine.base.domain.sys.PfTokenRedis;
import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;
import com.jrsoft.engine.model.org.OrgUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志拦截器
 * @author Blueeyedboy
 * @version 2014-8-19
 */
public class LogInterceptor implements HandlerInterceptor {

	public static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<>("ThreadLocal StartTime");
	//token
	public static  ThreadLocal<PfTokenRedis> TokenRedisThreadLocal  =
			new NamedThreadLocal<>("ThreadLocal PfTokenRedis");
	//创建线程
	public static ThreadLocal<HttpServletRequest> threadRequestLocal = new ThreadLocal<>();
	//日志实体

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
		//System.out.println("请求方法:"+request.getRequestURI());
		threadRequestLocal.set(request);
		if(true){
			//return true;
		}
		logger.info("请求方法: {},isWeChat:{}",request.getRequestURI(), SessionUtil.isWechat());
		if (logger.isDebugEnabled()){
			//System.out.println("------------");
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
	        logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS")
	        	.format(beginTime), request.getRequestURI());
		}

		//对移动端处理

		try {
			return true;
		}catch (Exception e) {
			clear(request);
				Map<String,Object> data = new HashMap<String,Object>();
				data.put("status",403);
				data.put("errorMsg", CommonUtil.ifNotEmpty(e.getMessage())?e.getMessage():"Sorry, you don't have permission!");
				response.getWriter().write(CommonUtil.objToJson(data));
				e.printStackTrace();
				return false;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

		/*if (modelAndView != null){
			//System.out.println(modelAndView.getViewName()+"------=======-----"+request.getRequestURI());
			//logger.info("ViewName:{} 请求方法: {}",modelAndView.getViewName(),request.getRequestURI());
		}*/
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
		//System.out.println("------afterCompletion------");
		// 保存日志
		//LogUtils.saveLog(request, handler, ex, null);
		clear(request);

	}

	public void clear(HttpServletRequest request){
		LogInterceptor.TokenRedisThreadLocal.remove();
		// 打印JVM信息。
		if (logger.isDebugEnabled()) {
			long beginTime = LogInterceptor.startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）
			long endTime = System.currentTimeMillis();    //2、结束时间
			logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
					new SimpleDateFormat("hh:mm:ss.SSS").format(endTime),
					request.getRequestURI(), Runtime.getRuntime().maxMemory() / 1024 / 1024, Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
					(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1024 / 1024);
			//删除线程变量中的数据，防止内存泄漏
			LogInterceptor.startTimeThreadLocal.remove();
		}
		//从线程中删除
		LogInterceptor.threadRequestLocal.remove();
	}


}
