package com.jrsoft.business.modules.extInterface.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jrsoft.engine.common.utils.CommonUtil;

public class FuShanApiTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
//        uploadProj();
        getDic();
//        uploadProj();
    }

    public static void getDic() throws UnsupportedEncodingException {
        String url = "http://157.122.146.230:8728/UploadSmz/GetBaseDataDictionary";
        String api_key = "B5388B32B1C14CF8A621359B74D4DCF9";
        String api_version = "1.0";
        String timestamp = CommonUtil.getDateTimeStr();



        Map<String,Object> params = new HashMap<>();
        params.put("type",11);
        String body = CommonUtil.objToJson(params);

        Map<String,String> urlParams = new HashMap<>();
        urlParams.put("api_key",api_key);
        urlParams.put("api_version",api_version);
        urlParams.put("timestamp",timestamp);
        urlParams.put("body",body);

        String[] array = new String[]{"api_key", "api_version", "body", "timestamp"};
        StringBuffer sb = new StringBuffer();
        sb.append(api_key);
        Arrays.sort(array);

        for(int i = 0; i < 4; ++i) {
            sb.append(array[i]).append(urlParams.get(array[i]));
        }


        sb.append(api_key);


        String str = sb.toString();
        System.out.println("-----"+str);

        String signature = MD5(str);
        System.out.println("----signature-"+signature);

        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("body",CommonUtil.objToJson(params));

        String requestUrl =  "api_key="+ URLEncoder.encode(api_key, "UTF-8") +"&api_version="+URLEncoder.encode(api_version, "UTF-8")+"&timestamp="+URLEncoder.encode(timestamp, "UTF-8")+"&signature="+URLEncoder.encode(signature, "UTF-8");
        System.out.println("requestUrl="+requestUrl);
        requestUrl = url+"?"+requestUrl;
//        System.out.printf("???????????????"+requestUrl);
        System.out.println("???????????????"+body);


//        String result =
String result = request(requestUrl,body);
        System.out.println("result----"+result);
    }


    public static void uploadProj() throws UnsupportedEncodingException {
        String url = "http://157.122.146.230:8728/UploadSmz/UploadItemInfo";
        String api_key = "B5388B32B1C14CF8A621359B74D4DCF9";
        String api_version = "1.0";
        String timestamp = CommonUtil.getDateTimeStr();



        Map<String,Object> params = new HashMap<>();
//        params.put("corpCode","123456");
//        params.put("corpName","Test");
//        params.put("areaCode","678");
//        params.put("corpType","1");
//        params.put("licenseNum","678");
//        params.put("address","678");
//        params.put("zipCode","678");
//        params.put("registerDate",CommonUtil.getDateStr());

        String projectCode = "12121";	//??????????????????
        String contractorCorpCode = "91440608666511288F"; // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        String contractorCorpName = "Test???????????????";	//?????????????????????
        String name = "Test";	//????????????
        String description = "wdsfd";	//????????????
        Integer category = 72;	//????????????
        String buildCorpName = "test??????????????????"; //??????????????????
        String buildCorpCode = "91440600668165913J";	//??????????????????????????????????????????????????????????????????????????????????????????????????????
        List<Map<String, Object>> builderLicenses = new ArrayList<Map<String, Object>>();	//???????????????
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("prjName", "11");
        map2.put("builderLicenseNum", "3424323212");
        Map<String, Object> map3 = new HashMap<String, Object>();
        map3.put("prjName", "11");
        map3.put("builderLicenseNum", "3424323212");
        builderLicenses.add(map2);
        builderLicenses.add(map3);
        String buildPlanNum = "123";	//?????????????????????????????????
        String prjPlanNum = "000";	//?????????????????????????????????
        String areaCode = "??????";	//???????????????
        BigDecimal invest = new BigDecimal(121.12).setScale(4, BigDecimal.ROUND_HALF_DOWN);	//?????????
        BigDecimal buildingArea = new BigDecimal(1343.12).setScale(4, BigDecimal.ROUND_HALF_DOWN);	//?????????
        BigDecimal buildingLength = new BigDecimal(1343.12).setScale(4, BigDecimal.ROUND_HALF_DOWN);	//?????????
        String startDate = "2019-05-21";	//????????????
        String completeDate = "2019-05-24";	//????????????
        String linkMan = "??????";	//???????????????
        String linkPhone = "1394587322";	//?????????????????????
        Integer prjStatus = 85;	//????????????
        BigDecimal lat = new BigDecimal(1343.12).setScale(4, BigDecimal.ROUND_HALF_DOWN);	//WGS84??????
        BigDecimal lng = new BigDecimal(1343.12).setScale(4, BigDecimal.ROUND_HALF_DOWN);	//WGS84??????
        String address = "??????";	//????????????
        String approvalNum = "432423232";	//????????????
        Integer approvalLevelNum = 90;	//????????????
        Integer prjSize = 96;	//????????????
        Integer propertyNum = 99;	//????????????
        Integer functionNum = 106;	//????????????
        String thirdPartyProjectCode = "4754634476423243543"; //???????????????????????????????????????????????????????????????????????????


        params.put("projectCode", projectCode);
        params.put("contractorCorpCode", contractorCorpCode);
        params.put("contractorCorpName", contractorCorpName);
        params.put("name", name);
        params.put("description", description);

        params.put("category", category);
        params.put("buildCorpName", buildCorpName);
        params.put("buildCorpCode", buildCorpCode);
        params.put("builderLicenses", builderLicenses);
        params.put("buildPlanNum", buildPlanNum);
        params.put("prjPlanNum", prjPlanNum);
        params.put("areaCode", areaCode);
        params.put("invest", invest);
        params.put("buildingArea", buildingArea);
        params.put("buildingLength", buildingLength);
        params.put("startDate", startDate);
        params.put("completeDate", completeDate);
        params.put("linkMan", linkMan);
        params.put("linkPhone", linkPhone);
        params.put("prjStatus", prjStatus);
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("address", address);
        params.put("approvalNum", approvalNum);
        params.put("approvalLevelNum", approvalLevelNum);
        params.put("prjSize", prjSize);
        params.put("propertyNum", propertyNum);
        params.put("functionNum", functionNum);
        params.put("thirdPartyProjectCode", thirdPartyProjectCode);

        String body = CommonUtil.objToJson(params);

        Map<String,String> urlParams = new HashMap<>();
        urlParams.put("api_key",api_key);
        urlParams.put("api_version",api_version);
        urlParams.put("body",body);
        urlParams.put("timestamp",timestamp);

        String[] array = new String[]{"api_key", "api_version", "body", "timestamp"};
        StringBuffer sb = new StringBuffer();
        sb.append(api_key);
        Arrays.sort(array);

        for(int i = 0; i < 4; ++i) {
            sb.append(array[i]).append(urlParams.get(array[i]));
        }


        sb.append(api_key);


        String str = sb.toString();
        System.out.println("-----"+str);

        String signature = MD5(str);
        System.out.println("----signature-"+signature);

        Map<String,Object> requestParams = new HashMap<>();
        requestParams.put("body",CommonUtil.objToJson(params));

        String requestUrl =  "api_key="+ URLEncoder.encode(api_key, "UTF-8") +"&api_version="+URLEncoder.encode(api_version, "UTF-8")+"&timestamp="+URLEncoder.encode(timestamp, "UTF-8")+"&signature="+URLEncoder.encode(signature, "UTF-8");
        System.out.println("requestUrl="+requestUrl);
        requestUrl = url+"?"+requestUrl;
//        System.out.printf("???????????????"+requestUrl);
        System.out.println("???????????????"+body);
//        System.out.println("????????????2???"+CommonUtil.objToJson(requestParams));


        String result = request(requestUrl,body);
        System.out.println("result----"+result);
    }

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

    /**
     * 32???MD5????????????????????????
     *
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] btInput = s.getBytes();
            // ??????MD5??????????????? MessageDigest ??????
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ?????????????????????????????????
            mdInst.update(btInput);
            // ????????????
            byte[] md = mdInst.digest();
            // ????????????????????????????????????????????????
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
