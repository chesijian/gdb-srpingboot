package com.jrsoft.business.modules.extInterface.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.jrsoft.engine.common.utils.CommonUtil;

/**
 * 
 * @author Xu
 *
 */
public class SendPost {
	
	public static final String api_key = "B5388B32B1C14CF8A621359B74D4DCF9";
	public static final String api_version = "1.0";
	public static final String url = "http://157.122.146.230:8728/";
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param postURL
	 *            发送请求的 URL
	 * @param params
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String request(String postURL,  String params) {
        String res = null;
        HttpURLConnection urlConn = null;

        try {
            URL url = new URL(postURL);
            urlConn = (HttpURLConnection)url.openConnection();
            urlConn.setDoOutput(true);
            urlConn.setDoInput(true);
            urlConn.setConnectTimeout(30000);
            urlConn.setReadTimeout(40000);
            urlConn.setRequestMethod("POST");
            urlConn.setUseCaches(false);
            urlConn.setRequestProperty("content-Type", "text/html;charset=utf-8");

            urlConn.connect();
            if (params != null) {
                OutputStreamWriter out = new OutputStreamWriter(urlConn.getOutputStream(), "UTF-8");
                out.write(params);
                out.flush();
                out.close();
            }

            StringBuffer strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));
            String line = null;

            while((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }

            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            if (urlConn != null) {
                urlConn.disconnect();
            }

            return res;
        }
    }
	

	public static String assemblyUtils(String body, String path, Map<String, Object> params) throws Exception {
		
		Map<String,String> urlParams = new HashMap<>();
        urlParams.put("api_key",SendPost.api_key);
        urlParams.put("api_version",SendPost.api_version);
        urlParams.put("body",body);

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String dateString = formatter.format(CommonUtil.getDate());

        String timestamp = CommonUtil.getDateTimeStr();
//        timestamp = "2019-06-10 21:09:56";

//        System.out.println("----timestamp----"+timestamp);

//        System.out.println("-------"+CommonUtil.getDateTimeStr().length());
        urlParams.put("timestamp", timestamp);
//        urlParams.put("timestamp", "2019-06-10 20:34:03");
//        urlParams.put("timestamp", dateString);
		
        String[] array = new String[]{"api_key", "api_version", "body", "timestamp"};
        StringBuffer sb = new StringBuffer();
        sb.append(SendPost.api_key);
        Arrays.sort(array);

        for(int i = 0; i < 4; ++i) {
//            System.out.println(array[i] + "----" + urlParams.get(array[i]));
            sb.append(array[i]).append(urlParams.get(array[i]));
        }
        sb.append(SendPost.api_key);

        String str = sb.toString();
        System.out.println("-----"+str);
        String signature = Md5Util.encodeByMd5(str).toUpperCase();
//        System.out.println("----signature-"+signature);
        
//        Map<String,Object> requestParams = new HashMap<>();
//        requestParams.put("body",CommonUtil.objToJson(params));
//        requestParams.put("body",body);

//        Thread.sleep(1000);

        String requestUrl =  path+"?api_key="+ URLEncoder.encode(SendPost.api_key, "UTF-8") +"&api_version="+URLEncoder.encode(SendPost.api_version, "UTF-8")+"&timestamp="+URLEncoder.encode(timestamp, "UTF-8")+"&signature="+URLEncoder.encode(signature, "UTF-8");
        System.out.println("requestUrl="+requestUrl);
        requestUrl = SendPost.url+requestUrl;
        System.out.println("请求参数："+body);

        String result = request(requestUrl,body);
		return result;
	}
	
}
