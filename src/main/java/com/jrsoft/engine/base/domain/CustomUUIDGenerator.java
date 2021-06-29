package com.jrsoft.engine.base.domain;

/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2018-12-01 12:45 PM
 **/

import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.ReflectUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.UUIDGenerator;

import java.io.Serializable;

/**
 * 自定义UUID生成器
 * @author sevenlin
 */
public class CustomUUIDGenerator extends UUIDGenerator {
	public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
		Object id = null;
		try {
			id = ReflectUtil.getFieldValue(object,"id");
			if(CommonUtil.ifNotEmpty(id)){
				//System.out.println("getFieldValue=========="+ id);
				return id.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(object+"====CustomUUIDGenerator===="+ CommonUtil.objToJson(object));

		return CommonUtil.getUUID();
	}
}
