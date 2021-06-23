package com.jrsoft.engine.impl.util;


import com.jrsoft.engine.base.model.ReturnJson;

import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.common.utils.SessionUtil;

import com.jrsoft.engine.model.Attachment;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;


/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2017-06-21 17:08
 **/
public class AttachmentUtil {
    public static String TMP_PATH;
    public static String  UPLOAD_PATH;
	public static String  CACHE_PATH;//临时文件缓存区
    //临时文件存放位置
    //正式文件存放位置

	public static String DOT = ".";


	public static String ACCEPT_TYPE = "*"; // 设置允许接受的任何文件后缀

	public static String OFFICE_TYPE = "doc,docx,ppt,pptx,xls,xlsx";

	private static String OFFICE_WORD_TYPE = "doc,docx";
	private static String OFFICE_EXCEL_TYPE = "xls,xlsx";
	private static String CAD_TYPE = "dwg,dxf,dwf";

	public static boolean OPEN_OFFICE_ENABLE = false;
	/**
	* 判断是否打开微软office
	**/
	public static boolean OPEN_WEB_OFFICE = true;

	/**
	 * 判断是否打开cad预览
	 **/
	public static boolean OPEN_CAD_VIEWER = true;

	/**
	 * 判断是否打开cad预览
	 **/
	public static String CAD_VIEWER_URL = "http://cad.zhuzhu.biz:3000/cad/viewer.html";
	/**
	 * oss或者cos下载是否走内部流量
	 **/
	public static boolean REDIRECT = true;

	static{
    	try {
    		//临时文件存放位置


		}catch(Exception e){
    		//默认是存在绝对路径
    		e.printStackTrace();
		}
    }

    public static boolean isWordType(String type){
		if(CommonUtil.isNotNull(type))
			return OFFICE_WORD_TYPE.contains(type);
		return false;
	}
	public static boolean isExcelType(String type){
		if(CommonUtil.isNotNull(type))
			return OFFICE_EXCEL_TYPE.contains(type);
		return false;
	}
	public static boolean isCadType(String type){
		if(CommonUtil.isNotNull(type))
			return CAD_TYPE.contains(type);
		return false;
	}
    /**
    * 判断是否符合上传文件类型
    * @param 
    * @return 
    * @author Blueeyedboy
    * @create 10/29/2019 9:24 AM
    **/
    public static boolean isAcceptType(String type){
		if(ACCEPT_TYPE.equals("*")){
			return true;
		}
		if(ACCEPT_TYPE.indexOf(type)>-1){
			return true;
		}
		return false;
	}


    /**
     * 上传到临时文件夹
     * @param fileName
     * @return boolean
     * @author Blueeyedboy
     * @create 2017/5/23 9:34
     **/
    public static boolean uploadTmp(String fileName, InputStream in) throws Exception {
        File srcDir = new File(TMP_PATH);
        if(!srcDir.exists()){
            srcDir.mkdirs();
        }
        String filePath = TMP_PATH+File.separator+fileName;
        OutputStream out = new FileOutputStream(filePath);
        //copyFile(in,out);
        return true;
    }


	/**
	* 删除缓存文件
	* @param 
	* @return 
	* @author Blueeyedboy
	* @create 1/11/2019 6:28 PM
	**/
	public static void deleteCache(String fileName){
		String filePath = CACHE_PATH+File.separator+fileName;
		File f = new File(filePath);
		if(f.exists()){
			f.delete();
		}
	}

	/**
	 * 上传到临时文件夹
	 * @param attachment
	 * @return boolean
	 * @author Blueeyedboy
	 * @create 2017/5/23 9:34
	 **/
	public static boolean uploadTmp(Attachment attachment, InputStream in) throws Exception {
		if(attachment.getSize() == null){
			long length = Long.parseLong(String.valueOf(in.available()));
			//((PfAttachment)attachment).setLength(length);
			attachment.setSize(AttachmentUtil.FormetFileSize(length));
		}
		//把文件保存到临时文件夹
		uploadTmp( attachment.getId()+"."+attachment.getSuffix(),  in);

		//生成文件缩略图


		// 判断是否是pdf，并且是否需要转换图片
		return true;
	}
	/**
	* 判断是否pdf
	* @param suffix 后缀
	* @return boolean
	* @author Blueeyedboy
	* @date 6/23/2020 10:36 AM
	**/
	public static boolean isPdf(String suffix){
		if(suffix.toLowerCase().endsWith("pdf")){
			return true;
		}
		return false;
	}
	/**
	 * 判断是否cad
	 * @param suffix 后缀
	 * @return boolean
	 * @author Blueeyedboy
	 * @date 6/23/2020 10:36 AM
	 **/
	public static boolean isCad(String suffix){
		if(suffix.toLowerCase().endsWith("dwg")){
			return true;
		}
		return false;
	}




	public static InputStream getRelativeTmpInputStream(Attachment attachment,final String id,boolean isThumb) throws FileNotFoundException {
		File srcDir = new File(TMP_PATH);
		if(!srcDir.exists()){
			return null;
		}
		FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.startsWith(id)){
					return true;
				}
				return false;
			}
		};
		File[] files = srcDir.listFiles(filter);
		File file = null;
		if(files != null && files.length>0){
			for(File f : files){
				//如果获取压缩的图片
				/*if(isThumb && f.getName().startsWith(id+ImageUtil.DEFAULT_POSTFIX)){
					file = f;
					break;
				}*/
				//如果获取未压缩的图片
				/*if(!isThumb  && f.getName().startsWith(id+ImageUtil.DEFAULT_POSTFIX)){
					continue;
				}*/
			}
			//如果没找到就默认一个文件
			if(isThumb && file ==null){
				file = files[0];
			}
			if(file == null){
				file = files[0];
			}
			if(attachment !=null){
				if(!CommonUtil.ifNotEmpty(attachment.getFileName())){
					attachment.setFileName(file.getName());

				}
				if(!CommonUtil.ifNotEmpty(attachment.getSuffix())){
					String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
					attachment.setSuffix(suffix);
				}
			}
			return new FileInputStream(file);
		}
		return null;
	}







    /**
     * 获取文件大小
     * @param fileS
     * @return
     * @author Blueeyedboy
     * @create 2017/5/23 10:11
     **/
    public static String FormetFileSize(long fileS) {// 转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 获取文件后缀名
     * @param fileName
     * @return java.lang.String
     * @author Blueeyedboy
     * @create 2017/5/23 10:21
     **/
    public static String getSuffix(String fileName){
        //return FileUtil.getSuffix(fileName);
		return null;
    }

    /**
     * 判断临时文件是否存在
     * @param
     * @return
     * @author Blueeyedboy
     * @create 2017/5/23 14:31
     **/
    public static boolean isExistTmp(String fileName){
        fileName = TMP_PATH + File.separator + fileName;
        File srcDir = new File(fileName);
        if(srcDir.exists()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 判断正式文件是否存在
     * @param
     * @return
     * @author Blueeyedboy
     * @create 2017/5/23 14:31
     **/
    public static boolean isExist(String fileName,String dateStr){
        fileName = TMP_PATH + File.separator + dateStr + File.separator + fileName;
        File srcDir = new File(fileName);
        if(srcDir.exists()){
            return true;
        }else{
            return false;
        }
    }


    
    /**
    *
	 * @param preDirPath 绝对路径存储时前一天的路径
	 * @param dirPath    附件保存到数据库的日期路径
	 * @param fileName   附件名称
    * @return 
    * @author Blueeyedboy
    * @create 2018/10/31 16:01
    **/
	private static void delete(String preDirPath,String dirPath ,String fileName){
		File desDir = new File(dirPath+File.separator + fileName);
		if (!desDir.exists()) {
			//如果不存在则文件夹推迟上一天，兼容文件上传和数据上传不在同一天的情况
			desDir = new File(preDirPath+File.separator + fileName);
			if (desDir.exists()) {
				desDir.delete();
			}

		}else{
			desDir.delete();
		}
	}


	/**
	* 判断是否是office
	* @param 
	* @return 
	* @author Blueeyedboy
	* @create 11/15/2019 11:56 AM
	**/
	public static boolean isOffice(String suffix){
		if(CommonUtil.ifNotEmpty(suffix) && OFFICE_TYPE.indexOf(suffix)>-1){
			return true;
		}
		return false;
	}


}
