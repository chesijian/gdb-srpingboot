package com.jrsoft.engine.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jrsoft.engine.exception.EngineIllegalArgumentException;


import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* 通用处理类
* @author Blueeyedboy
* @create 2017/5/22 16:45
**/
public class CommonUtil {

    //public static Gson GSON = new Gson();
    //public static ChineseCharToEn CHINESETOEN = new ChineseCharToEn();

    public static synchronized String getUUID(){
        //用hibernate的UUID生成器
        //return UUID.randomUUID().toString();
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
    * 获取系统当前时间
    * @param
    * @return
    * @author Blueeyedboy
    * @create 2017/5/23 14:18
    **/
    public static Date getDate(){
        return new Date();
    }

    public static String getDateTime (){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(Date dateDate) {
        if(!CommonUtil.ifNotEmpty(dateDate)){
            return "";
        }
        DateFormat mediumDateFormat =
                DateFormat.getDateTimeInstance(
                        DateFormat.MEDIUM,
                        DateFormat.MEDIUM);
        //System.out.println(dateDate.toGMTString());;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
    * 获取当前日期字符串2017-12-12
    * @param
    * @return
    * @author Blueeyedboy
    * @create 2017/5/23 14:25
    **/
    public static String getDateStr(Date currentTime){
        if(currentTime == null){
            currentTime = getDate();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前日期字符串2017-12-12
     * @param
     * @return
     * @author Blueeyedboy
     * @create 2017/5/23 14:25
     **/
    public static String getDateStr(){

        return getDateStr(getDate());
    }



    /**
     * 获取当前日期时间字符串2017-12-12 12:12:12
     * @param
     * @return
     * @author Blueeyedboy
     * @create 2017/5/23 14:26
     **/
    public static String getDateTimeStr(Date currentTime){
        if(currentTime == null){
            //currentTime = getDate();
			return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 获取当前日期时间字符串2017-12-12 12:12:12
     * @param
     * @return
     * @author Blueeyedboy
     * @create 2017/5/23 14:26
     **/
    public static String getDateTimeStr(){

        Date  currentTime = getDate();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * 获取当前日期时间字符串毫秒级别2017-12-12 12:12:12:111
     * @param
     * @return
     * @author Blueeyedboy
     * @create 2017/5/23 14:26
     **/
    public static String getDateTimeLongStr(){

        Date  currentTime = getDate();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取指定日期格式当前日期的字符型日期
     *
     * @param p_format
     *            日期格式 格式1:"yyyy-MM-dd" 格式2:"yyyy-MM-dd hh:mm:ss EE" 格式3:"yyyy年MM月dd日 hh:mm:ss EE" 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
     * @return String 当前时间字符串
     * @author zhuqx
     * @Date: 2006-10-31
     */
    public static String getDateTimeByFormat(String p_format) {
        Date d = getDate();
        SimpleDateFormat sdf = new SimpleDateFormat(p_format);
        String dateStr = sdf.format(d);
        return dateStr;
    }

    /**
     *
     * @author 大雄
     * @Title millisToDay
     * @Date 2014-3-12
     * @Description 计算毫秒为多少天多少小时多少分
     * @Params @param millis
     * @Params @return
     * @Return String
     */
    public static String millisToDay(Long millis){
        if(millis != null){
            StringBuilder str = new StringBuilder();
            Long day = millis/86400000;
            Long temp = millis%86400000;
            Long hour = (temp)/3600000;
            temp = temp%3600000;
            Long minute = temp/60000;
            temp = temp%60000;
            Long second = temp/1000;
            if(day >0){
                str.append(day+"天");
            }
            if(hour >0){
                str.append(hour+"小时");
            }
            if(minute >0){
                str.append(minute+"分");
            }
            if(second >0){
                str.append(second+"秒");
            }else{
                str.append("1秒");
            }
            return str.toString();
        }else{
            return "";
        }
    }

    public static String getString(Object o){
    	if (o == null){
    		return null;
		}
    	return o.toString();
	}

	public static int getInt(Object o){
		if (o == null){
			return 0;
		}
		return Integer.parseInt(o.toString());
	}
	public static Integer getInteger(Object o){
		if (o == null){
			return null;
		}
		return Integer.parseInt(o.toString());
	}









    /**
     * 转化为两位数字比如 9-->09
     * @author 大雄
     * @Title decimal
     * @Date 2015-1-6
     * @Description 转化为两位数字比如 9-->09
     * @Params @param offset 几位数字
     * @Params @param shu
     * @Params @return
     * @Return String
     */
    public static String decimal(int offset,Object shu){
        DecimalFormat df = new DecimalFormat();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<offset;i++){
            sb.append("0");
        }
        df.applyPattern(sb.toString());
        return df.format(shu);
    }

    /**
     * 判断一个对象是否为空
     * @param o
     * @return
     */
    public static boolean ifNotEmpty(Object o){
        if(o == null || o.toString().trim().equals("") || o.toString().length() == 0){
            return false;
        }
        else{
            return true;
        }
    }

	public static boolean isNotNull(Object o){
		if(o == null || o.toString().trim().equals("") || o.toString().length() == 0){
			return false;
		}
		else{
			return true;
		}
	}

	public static boolean isNull(Object o){
		return !isNotNull(o);
	}

    public static boolean ifNotEmptyList(List o){
        if(o != null && o.size() > 0){
            return true;
        }
        return false;
    }

    public static boolean ifNotEmptyMap(Map o){
        if(o != null && o.size() > 0){
            return true;
        }
        return false;
    }

    public static String join(String[] strArr,String suffix){
        if(strArr != null && strArr.length>0){
            StringBuilder sb = new StringBuilder();
            for(String str : strArr){
                sb.append(suffix).append(str);
            }
            sb.delete(0,suffix.length());
            return sb.toString();
        }
        return null;
    }

	public static String join(Set<String> set,String suffix){
    	if(set != null){
			String[] strArr = new String[set.size()];
			set.toArray(strArr);
			return join(strArr, suffix);
		}
		return null;
	}

    public static String join(List<String> strArr,String suffix){
        if(strArr != null && strArr.size()>0){
            StringBuilder sb = new StringBuilder();
            for(String str : strArr){
                sb.append(suffix).append(str);
            }
            sb.delete(0,suffix.length());
            return sb.toString();
        }
        return null;
    }

    /**
    * json转换成对象
    * @param json
     * @param requiredType
    * @return
    * @author Blueeyedboy
    * @create 2017/7/5 14:41
    **/
    public static <T> T parseJson(String json,Class<T> requiredType) {
        //return JSON.parseObject(json,requiredType);
        return JSON.parseObject(json,requiredType);
    }

    /**
     * parseJson
     * @author Blueeyedboy
     * @Title parseJson
     * @Date 2015-3-30
     * @Description TODO
     * @Params @param o
     * @Params @return
     * @Return Object
     */
    public static Object parseJson(String json){
        return JSON.parse(json);
    }

    /**
     * objToJson
     * @author Blueeyedboy
     * @Title objToJson
     * @Date 2015-3-30
     * @Description TODO
     * @Params @param o
     * @Params @return
     * @Return String
     */
    public static String objToJson(Object o){
        return JSON.toJSONString(o);
    }

    /**
     * 转换json为Map<String,Object>
     * @author Blueeyedboy
     * @Title jsonToMap
     * @Date 2015-3-30
     * @Description TODO
     * @Params @param json
     * @Params @return
     * @Return Map<String,Object>
     */
    public static Map<String,Object> jsonToMapObj(String json){
        if(CommonUtil.ifNotEmpty(json)){
            return JSON.parseObject(json, new TypeReference<Map<String, Object>>(){});
        }else{
            return new HashMap<String, Object>();
        }
    }
    /**
     * 转换json为List<Map<String,Object>>
     * @author Blueeyedboy
     * @Title jsonToListMapObj
     * @Date 2015-3-30
     * @Description TODO
     * @Params @param json
     * @Params @return
     * @Return List<Map<String,Object>>
     */
    public static List<Map<String,Object>> jsonToListMapObj(String json){
        if(CommonUtil.ifNotEmpty(json)){
            return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>(){});
        }else{
            return new ArrayList<Map<String,Object>>();
        }
    }

    /**
     * 转换json为List<Map<String,String>>
     * @author Blueeyedboy
     * @Title jsonToListMapStr
     * @Date 2015-3-30
     * @Description TODO
     * @Params @param json
     * @Params @return
     * @Return List<Map<String,String>>
     */
    public static List<Map<String,String>> jsonToListMapStr(String json){
        if(CommonUtil.ifNotEmpty(json)){
            return JSON.parseObject(json, new TypeReference<List<Map<String, String>>>(){});
        }else{
            return new ArrayList<Map<String,String>>();
        }
    }

    /**
     * 转换json为Map<String,String>
     * @author Blueeyedboy
     * @Title jsonToMapString
     * @Date 2015-3-30
     * @Description TODO
     * @Params @param json
     * @Params @return
     * @Return Map<String,String>
     */
    public static Map<String,String> jsonToMapStr(String json){
        if(CommonUtil.ifNotEmpty(json)){
            return JSON.parseObject(json, new TypeReference<Map<String, String>>(){});
        }else{
            return new HashMap<String, String>();
        }
    }
    /**
     * json转换
     * @description
     * @author Blueeyedboy
     * @date 2016年10月17日上午10:35:34
     * @param json
     * @return
     */
    public static Map<String,Integer> jsonToMapInteger(String json){
        if(CommonUtil.ifNotEmpty(json)){
            return JSON.parseObject(json, new TypeReference<Map<String, Integer>>(){});
        }else{
            return new HashMap<String, Integer>();
        }
    }

    /**
     *
     * @author 大雄
     * @Title replaceStr
     * @Date 2015-1-6
     * @Description 将{variable}变量表达式替换为实际值
     * @Params @param content
     * @Params @param map
     * @Params @return
     * @Return String
     */
    public static String replaceStr(String content,@SuppressWarnings("rawtypes") Map map){
        if(!ifNotEmpty(content)){
            return "";
        }
        if(map == null || map.isEmpty()){
            return content;
        }
        Pattern pa = Pattern.compile("\\{(.*?)\\}");
        Matcher m = pa.matcher(content);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            //System.out.println(m.group(1)+"=====replaceStr======"+m.group());
            m.appendReplacement(sb, map.get(m.group(1)) == null ? "" : map.get(m.group(1)).toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

	/**
	 *
	 * @author 大雄
	 * @Title replaceStr
	 * @Date 2015-1-6
	 * @Description 将{variable}变量表达式替换为实际值
	 * @Params @param content
	 * @Params @param map
	 * @Params @return
	 * @Return String
	 */
	public static String replaceStrNoBlank(String content,@SuppressWarnings("rawtypes") Map map){
		if(!ifNotEmpty(content)){
			return "";
		}
		if(map == null || map.isEmpty()){
			throw new EngineIllegalArgumentException("替换参数不能为空!");
		}
		Pattern pa = Pattern.compile("\\{(.*?)\\}");
		Matcher m = pa.matcher(content);
		StringBuffer sb = new StringBuffer();

		while (m.find()) {
			Object o = map.get(m.group(1));
			if(CommonUtil.isNull(o)){
				o = map.get(m.group(1).toLowerCase());
				if(CommonUtil.isNull(o)){
					throw new EngineIllegalArgumentException("替换参数不能为空!");
				}else{
					m.appendReplacement(sb, o.toString());
				}

			}else{
				m.appendReplacement(sb, o.toString());
			}
		}
		m.appendTail(sb);
		return sb.toString();
	}

    /**
     * 判断是否是linxu
     * @return boolean
     * @author Blueeyedboy
     * @create 2017/5/23 9:23
     **/
    public static boolean isLinux() {

        String os = getOsName();
        // System.out.println("======os======="+os);
        if (os.startsWith("Linux")) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 获取当期的操作系统
     *
     * @author Blueeyedboy
     * @Title getOsName
     * @Date 2015-9-6
     * @Description TODO
     * @return
     * @Return String
     */
    public static String getOsName() {
        String os = "";
        os = System.getProperty("os.name");
        return os;
    }




    /**
     *
     * @author 大雄
     * @Title getRandomNum
     * @Date 2015-1-27
     * @Description 获取随机数
     * @Params @param offset 获取随机数的位数
     * @Params @return
     * @Return int
     */
    public static int getRandomNum(int offset){
        return (int)((Math.random()*9+1)*Math.pow(10, (offset-1)));
    }

    /**
    * list转换为数组
    * @param  list
    * @return
    * @author Blueeyedboy
    * @create 2017/8/25 15:24
    **/
    public static String[] listToStrArr(List<String> list){
        String[] strings = new String[list.size()];

        list.toArray(strings);
        return strings;
    }

	/**
	 * list转换为数组
	 * @param  array 字符串数组
	 * @return List<String>
	 * @author Blueeyedboy
	 * @create 2017/8/25 15:24
	 **/
	public static List<String> strArrToList(String[] array){
		return Arrays.asList(array);
	}

    public static void main(String[] args) {
        List<String> idArr = new ArrayList<String>();
        for(int i=0;i<10000;i++){
            String id = CommonUtil.getUUID();

            idArr.add(id);
        }
        Map<String,String> ids = new HashMap<String,String>();
        for(int i=0;i<idArr.size();i++){
            String id = idArr.get(i);
            if(ids.containsKey(id)){
                throw new RuntimeException("运行时异常");
            }
            ids.put(id,id);
        }
        System.out.println("----------");
    }

    public static String getDatabaseType() {
	    return "mysql";
    }
}
