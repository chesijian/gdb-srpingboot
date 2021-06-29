package com.jrsoft.engine.common.utils;



import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
* 反射处理
* @param
* @return 
* @author Blueeyedboy
* @create 12/1/2018 11:54 AM
**/
public class ReflectUtil {
	/**
	* 判断类中是否含有该属性
	* @param
	* @return 
	* @author Blueeyedboy
	* @create 12/1/2018 11:56 AM
	**/
	public static boolean hasField(Class clazz,String fieldName){
		Field[] fields=clazz.getDeclaredFields();
		boolean b=false;
		for(int i=0;i<fields.length;i++){
			if(fields[i].getName().equals(fieldName)){
				return true;
			}
		}
		return false;
	}

	/**
	* 获取特定属性字段
	* @param 
	* @return 
	* @author Blueeyedboy
	* @create 12/1/2018 12:51 PM
	**/
	public static Field getField (Object object , String fieldName){
		// 获取该对象的Class
		Class objClass = object.getClass();
		// 获取所有的属性数组getFields
		Field[] fields = objClass.getDeclaredFields();
		//Field[] fields = objClass.getFields();
		for (Field field:fields) {
			// 属性名称
			String currentFieldName = field.getName();
			if(currentFieldName.equals(fieldName)){
				return field; // 通过反射拿到该属性在此对象中的值(也可能是个对象)
			}
		}
		Class superClass = objClass.getSuperclass();
		do{
//			if(superClass.equals(BaseEntity.class)){
//				break;
//			}
			Field[] superFields = superClass.getDeclaredFields();
			//Field[] fields = objClass.getFields();
			for (Field field : superFields) {
				// 属性名称
				String currentFieldName = field.getName();
				if(currentFieldName.equals(fieldName)){
					//抑制Java对其的检查
					field.setAccessible(true) ;
					return field; // 通过反射拿到该属性在此对象中的值(也可能是个对象)
				}
			}
			superClass = superClass.getSuperclass();
		}while(superClass != null);


		return null;
	}


	/**
	* 获取特定属性值
	* @param
	* @return 
	* @author Blueeyedboy
	* @create 12/1/2018 12:51 PM
	**/
	public static Object getFieldValue (Object object , String fieldName) throws Exception {
		Field field = getField(object,fieldName);
		if(field != null){
			return field.get(object);
		}
		return null;
	}



	/*
	 * 获取泛型类Class对象，不是泛型类则返回null
	 */
	public static Class<?> getActualTypeArgument(Class<?> clazz) {
		Class<?> entitiClass = null;
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType) {
			Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass)
					.getActualTypeArguments();
			if (actualTypeArguments != null && actualTypeArguments.length > 0) {
				entitiClass = (Class<?>) actualTypeArguments[0];
			}
		}

		return entitiClass;
	}

	/**
	* 根据类定义创建对象
	* @param 
	* @return 
	* @author Blueeyedboy
	* @create 1/16/2019 4:31 PM
	**/
	public static Object newInstance(Class<?> c){
		// 为创建java对象
		try {
			Object newObject = c.newInstance();
			return newObject;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* 通过反射设置对象中字段的值
	* 如果设置成功，则返回设置的字段值
	* @param
	* @return 
	* @author Blueeyedboy
	* @create 1/16/2019 4:33 PM
	**/
	public static Object setFieldValue(Object object, String fieldName, Object val) throws RuntimeException {
		Object result = null;
		try {
			Field fu = getField(object, fieldName); // 获取对象的属性域
			//System.out.println(fieldName+"------------"+fu);
			// 设置对象属性域的访问属性
			fu.setAccessible(true);
			// 设置对象属性域的属性值
			fu.set(object, val);
			// 获取对象属性域的属性值
			result = fu.get(object);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("反射设置字段值报错:"+e.getMessage());
		}
		return result;
	}
}
