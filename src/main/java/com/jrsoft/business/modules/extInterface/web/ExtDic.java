package com.jrsoft.business.modules.extInterface.web;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jrsoft.engine.common.utils.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "extDict", description = "施工宝检查接口")
@RestController
@RequestMapping(value = "/api_v1/extDict")
public class ExtDic {

	
	@ApiOperation(value = "查询字典", notes = "查询字典")
    @ApiResponses({@ApiResponse(code = HttpServletResponse.SC_OK, message = "操作成功")})
    @ApiImplicitParams({})
    @RequestMapping(value = "getDict", method = RequestMethod.GET)
	public static String getDic(int type) throws Exception {
		String url = "http://157.122.146.230:8728/UploadSmz/GetBaseDataDictionary";
		String api_key = "B5388B32B1C14CF8A621359B74D4DCF9";
		String api_version = "1.0";
		String timestamp = CommonUtil.getDateTimeStr();

		Map<String, Object> params = new HashMap<>();
		params.put("type", type);
		String body = CommonUtil.objToJson(params);

		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("api_key", api_key);
		urlParams.put("api_version", api_version);
		urlParams.put("timestamp", timestamp);
		urlParams.put("body", body);

		String[] array = new String[] { "api_key", "api_version", "body", "timestamp" };
		StringBuffer sb = new StringBuffer();
		sb.append(api_key);
		Arrays.sort(array);

		for (int i = 0; i < 4; ++i) {
			sb.append(array[i]).append(urlParams.get(array[i]));
		}

		sb.append(api_key);

		String str = sb.toString();
		System.out.println("-----" + str);

		String signature = Md5Util.encodeByMd5(str).toUpperCase();
		System.out.println("----signature-" + signature);

		Map<String, Object> requestParams = new HashMap<>();
		requestParams.put("body", CommonUtil.objToJson(params));

		String requestUrl = "api_key=" + URLEncoder.encode(api_key, "UTF-8") + "&api_version="
				+ URLEncoder.encode(api_version, "UTF-8") + "&timestamp=" + URLEncoder.encode(timestamp, "UTF-8")
				+ "&signature=" + URLEncoder.encode(signature, "UTF-8");
		System.out.println("requestUrl=" + requestUrl);
		requestUrl = url + "?" + requestUrl;

		String result = SendPost.request(requestUrl, body);
		System.out.println("result----" + result);
		
		return result;
	}

	public static void uploadPorject(){
		String requestUrl = "http://157.122.146.230:8728/UploadSmz/UploadItemInfo?api_key=B5388B32B1C14CF8A621359B74D4DCF9&api_version=1.0&timestamp=2019-06-10+20%3A34%3A03&signature=21621E08A3B041C8531F4D44B5D0D5E3";

		String result = SendPost.request(requestUrl, null);
	}

}
