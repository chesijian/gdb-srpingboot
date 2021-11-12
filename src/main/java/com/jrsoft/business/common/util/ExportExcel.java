package com.jrsoft.business.common.util;

import com.jrsoft.business.common.domain.ExcelTitle;
import com.jrsoft.business.modules.progress.model.Task;
import com.jrsoft.business.modules.statistics.domain.SheetData;
import com.jrsoft.engine.common.utils.CommonUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExportExcel {

	/**
	 * 导出集团报表excel
	 ** @param out
	 * @param dataLists
	 */
	public void exportListExcel(OutputStream out, List<SheetData> dataLists) throws Exception {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 标题样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置样式
		/*style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);*/
		//标题字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.GREEN.index);
		//font.setFontHeightInPoints((short) 12);
		//font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		//headerStyle.setFont(font);
		HSSFFont redFont = workbook.createFont();
		redFont.setColor(HSSFColor.RED.index);
		HSSFFont yellowFont = workbook.createFont();
		yellowFont.setColor(HSSFColor.YELLOW.index);

		//数据样式
		HSSFCellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		dateCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		//正常状态样式
		HSSFCellStyle normalStyle = workbook.createCellStyle();
		normalStyle.setFont(font);
		//可控状态样式
		HSSFCellStyle controllStyle = workbook.createCellStyle();
		controllStyle.setFont(yellowFont);
		//延误状态样式
		HSSFCellStyle delayStyle = workbook.createCellStyle();
		delayStyle.setFont(redFont);

		/*style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);*/
		/*style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);*/
		/*style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);*/
		//数据字体
		/*HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 11);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);*/
		//创建单元格格式
		HSSFCellStyle styleBorder = workbook.createCellStyle();
		styleBorder = setStyle(styleBorder,HSSFCellStyle.BORDER_THIN);

		for(SheetData sheetData : dataLists){
			// 生成一个表格
			HSSFSheet sheet = workbook.createSheet(sheetData.getSheetName());
			// 设置表格默认列宽度为15个字节
			sheet.setDefaultColumnWidth((short) 11);
			//产生标题
			HSSFRow row = null;
			int index = 0;
			row = sheet.createRow(index);
			HSSFCell cell = row.createCell(0);
			int dataIndex = 1;
			//如果是进度明细
			if("leaseReport-bak".equals(sheetData.getDataType())){
				dataIndex=2;
				cell.setCellStyle(headerStyle);
				cell.setCellValue("项目编号");
				cell = row.createCell(1);
				cell.setCellStyle(headerStyle);
				cell.setCellValue("项目名称");
			}else{
				cell.setCellStyle(headerStyle);
				sheet.setColumnWidth(0, 5*256);
				cell.setCellValue("序号");
			}
			List<ExcelTitle> titles =sheetData.getTitles();
			for (int i = 0; i < titles.size(); i++) {
				ExcelTitle entry =titles.get(i);
				HSSFCell cell1 = row.createCell(i+dataIndex);
				if(CommonUtil.ifNotEmpty(entry.getWidth())){
					sheet.setColumnWidth(i+dataIndex, entry.getWidth());
				}
				cell1.setCellStyle(headerStyle);
				cell1.setCellValue(entry.getTitle());
			}
			//产生数据
			if("leaseReport".equals(sheetData.getDataType())){
				List<Task> taskList = sheetData.getDatas();
				for (Task task : taskList){
					index++;
					row = sheet.createRow(index);
					HSSFRichTextString indexString = new HSSFRichTextString(String.valueOf(index));
					HSSFCell areaCell = row.createCell(0);
					areaCell.setCellStyle(dateCellStyle);
					areaCell.setCellValue(indexString);
					for (int i=0; i<titles.size(); i++) {
						ExcelTitle titleObj =titles.get(i);
						areaCell = row.createCell(i+dataIndex);
						Object ob = getGetMethod(task, titleObj.getField());
						if("progressStatus".equals(titleObj.getField())){
							if("正常".equals(ob)){
								areaCell.setCellStyle(normalStyle);
							}else if("可控".equals(ob)){
								areaCell.setCellStyle(controllStyle);
							}else if("延误".equals(ob)){
								areaCell.setCellStyle(delayStyle);
							}
						}else{
							areaCell.setCellStyle(dateCellStyle);
						}
						areaCell.setCellValue(ob==null? "":String.valueOf(ob));
						areaCell.setCellType(HSSFCell.CELL_TYPE_STRING);
					}

				}
			}else{
				List<Map<String,Object>> datas = sheetData.getDataList();
				for (Map<String,Object> data : datas) {
					index++;
					row = sheet.createRow(index);
					HSSFRichTextString indexString = new HSSFRichTextString(String.valueOf(index));
					HSSFCell indexCell = row.createCell(0);
					indexCell.setCellStyle(dateCellStyle);
					indexCell.setCellValue(indexString);
					for (int i=0; i<titles.size(); i++) {
						ExcelTitle titleObj =titles.get(i);
						HSSFCell areaCell = row.createCell(i+1);
						areaCell.setCellStyle(dateCellStyle);
						String value = data.get(titleObj.getField())==null? "":String.valueOf(data.get(titleObj.getField()));
						areaCell.setCellValue(value);
						areaCell.setCellType(HSSFCell.CELL_TYPE_STRING);
					}

				}
			}

		}

		
		try {
			//workbook = setBorder(workbook,styleBorder,0,2,(short)0,(short)(mainTitle.size()+1));
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 根据属性，获取get方法
	 * @param ob 对象
	 * @param name 属性名
	 * @return
	 * @throws Exception
	 */
	public static Object getGetMethod(Object ob , String name)throws Exception{
		Method[] m = ob.getClass().getMethods();
		for(int i = 0;i < m.length;i++){
			if(("get"+name).toLowerCase().equals(m[i].getName().toLowerCase())){
				return m[i].invoke(ob);
			}
		}
		return null;
	}

	/**
	 * 导出excel,标题和数据列、图片
	 ** @param out
	 * @param mainTitle
	 * @param dataLists
	 * @param companyName
	 * @param title
	 */
	public void exportExcel(OutputStream out, List<ExcelTitle> mainTitle, List<Map<String, Object>> dataLists, String companyName, String title) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet("sheet 1");
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 11);

		//公司标题样式
		HSSFCellStyle companyStyle = workbook.createCellStyle();
		companyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//对齐方式
		companyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//公司名称字体
		HSSFFont companyFont = workbook.createFont();
		companyFont.setColor(HSSFColor.BLACK.index);
		companyFont.setFontHeightInPoints((short) 14);
		companyFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		companyStyle.setFont(companyFont);

		// 明细标题样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置样式
		//style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//明细字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);


		//明细数据样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//明细数据字体
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setFontHeightInPoints((short) 11);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);
		//创建单元格格式
		HSSFCellStyle styleBorder = workbook.createCellStyle();
		styleBorder = setStyle(styleBorder,HSSFCellStyle.BORDER_THIN);


		//标题
		HSSFRow titleRow = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1,0,mainTitle.size()));
		HSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellStyle(companyStyle);
		titleCell.setCellValue(new HSSFRichTextString(title));


		try {
			//产生标题
			int index = 2;
			HSSFRow row4 = sheet.createRow(index);
			HSSFCell cell = row4.createCell(0);
			cell.setCellStyle(style);
			HSSFRichTextString indexText = new HSSFRichTextString("序号");
			cell.setCellValue(indexText);
			for (int i = 0; i < mainTitle.size(); i++) {
				ExcelTitle entry =mainTitle.get(i);
				HSSFCell cell1 = row4.createCell(i+1);
				if(CommonUtil.ifNotEmpty(entry.getWidth())){
					sheet.setColumnWidth(i+1, entry.getWidth());
				}
				cell1.setCellStyle(style);
				HSSFRichTextString headerText1 = new HSSFRichTextString(entry.getTitle());
				cell1.setCellValue(headerText1);
			}

			HSSFCell cell1 = row4.createCell(mainTitle.size()+1);
			cell1.setCellStyle(style);
			HSSFRichTextString headerText1 = new HSSFRichTextString("现场图片");
			cell1.setCellValue(headerText1);

			//产生数据
			HSSFRow row = null;
			Map<Integer,Double> totalMap = new HashMap<Integer, Double>();
			List<Integer> numbers =new ArrayList<Integer>();
//			Integer[] numbers = new Integer[];
			for (Map<String,Object> data : dataLists) {
				index++;
				row = sheet.createRow(index);
				HSSFRichTextString indexString = new HSSFRichTextString(String.valueOf(index-2));
				HSSFCell indexCell = row.createCell(0);
				indexCell.setCellStyle(style2);
				indexCell.setCellValue(indexString);
				for (int i=0; i<mainTitle.size(); i++) {
					ExcelTitle titleObj =mainTitle.get(i);
					HSSFCell areaCell = row.createCell(i+1);
					if(titleObj.isIfTotal()){
						Double totalVal = data.get(titleObj.getField())==null? 0.0:Double.valueOf(String.valueOf(data.get(titleObj.getField())));
						if(totalMap.containsKey(titleObj.getLine())){
							Double aa =totalMap.get(titleObj.getLine())+totalVal;
							totalMap.put(titleObj.getLine(), aa);
						}else{
							totalMap.put(titleObj.getLine(), totalVal);
						}
					}
					areaCell.setCellStyle(style2);
					String value = data.get(titleObj.getField())==null? "":String.valueOf(data.get(titleObj.getField()));
					HSSFRichTextString areaString = new HSSFRichTextString(value);
					areaCell.setCellValue(areaString);
					areaCell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
				//处理图片
				BufferedImage bufferImg = null;
				ByteArrayOutputStream byteArrayOut =null;
				// 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
				HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				row.setHeight((short)1500);
				if(data.get("imgs")!=null){
					List<Map<String,Object>> imgsList =(List<Map<String,Object>>)data.get("imgs");
					//List<Attachment> imgsList =(List<Attachment>)data.get("imgs");
					numbers.add(imgsList.size());
					for (int i = 0; i < imgsList.size(); i++) {
						Map<String,Object> imgObj = imgsList.get(i);
						String desPath = "";//AttachmentUtil.UPLOAD_PATH + File.separator + imgObj.get("company") + File.separator + CommonUtil.getDateStr((Date)imgObj.get("createTime"));
						String fileName = imgObj.get("id")+"."+imgObj.get("suffix");
						byteArrayOut = new ByteArrayOutputStream();
						File desDir = new File(desPath + File.separator + fileName);
						if(desDir.exists()) {
							bufferImg = ImageIO.read(desDir);
							ImageIO.write(bufferImg, "jpg", byteArrayOut);
							// anchor主要用于设置图片的属性
							int colNum =mainTitle.size()+1+i;
							sheet.setColumnWidth(colNum, 256*13);
							HSSFClientAnchor anchor = new HSSFClientAnchor(25, 25, 1023, 225, (short) colNum, index, (short) colNum, index);
							// 插入图片
							patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
						}
						//InputStream inputStream =AttachmentUtil.getInputStream(imgObj);
						//bufferImg = ImageIO.read(inputStream);
					}
				}


			}
			int max = Collections.max(numbers);
			if(max>1){
				for (int i = 2; i <= index; i++) {
					sheet.addMergedRegion(new CellRangeAddress(i, i,mainTitle.size()+1,mainTitle.size()+max));
				}
			}

			//设置边框
			workbook = setBorder(workbook,styleBorder,0,2,(short)0,(short)(mainTitle.size()+max+1));
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 设置上下左右边框
	 * @param style 需要设置边框的HSSFCellStyle对象
	 * @param type 边框类型 HSSFCellStyle.BORDER_THIN
	 * @return 返回设置边框后的HSSFCellStyle 
	 */
	public static  HSSFCellStyle setStyle(HSSFCellStyle style,short type){
	       style.setBorderBottom(type); //下边框 
	       style.setBorderLeft(type);//左边框 
	       style.setBorderTop(type);//上边框 
	       style.setBorderRight(type);//右边框
	       return style;
	}
	
	/**
	 * 数据处理完之后对单元格合并以及没有创建单元格的情况处理边框问题
	 * @param style 需要设置边框的HSSFCellStyle对象
	 * @param workbook 工作簿
	 * @param rowStart 开始行
	 * @param columStart	开始列
	 * @param sheetAt	工作表
	 * @param maxCellNum	最大列
	 * @return 返回设置边框后的HSSFWorkbook 
	 */
	public static  HSSFWorkbook  setBorder(HSSFWorkbook  workbook,HSSFCellStyle style,int sheetAt,int rowStart,short columStart,short maxCellNum){
		HSSFSheet st = workbook.getSheetAt(sheetAt);
		for(int i=rowStart;i<=st.getLastRowNum();i++ ){
	       		HSSFRow row = st.getRow(i);
	       		if(row==null){//空行不处理
	       			continue ;
	       		}
	       		for(short j=columStart;j<maxCellNum;j++){
	       			HSSFCell cell = row.getCell(j);
	       			if(cell==null){//处理在创建数据单元格过程中没有创建单元格的情况
	       				cell = row.createCell(j);
	       				cell.setCellStyle(style);
	       			}else if(getCellFormatValue(cell)==null || "".equals(getCellFormatValue(cell))){//处理合并单元格的情况
	       				cell.setCellStyle(style);
	       			}
	       		}
	       	}
	       	return workbook;
	}
	/**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

	/**
	 * 生成
	 * @param sheet
	 * @param titleStyle
	 * @param dataStyle
	 * @param headers
	 * @param dataList
	 */
	private void createWorkSheet(HSSFSheet sheet,HSSFCellStyle titleStyle,HSSFCellStyle dataStyle,List<String> headers,List<Map<String, String>> dataList) {
		HSSFRow row = null;
		HSSFCell dataCell =null;

		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i);
			Map<String, String> dataItem = dataList.get(i);
			for (int j = 0; j < headers.size(); j++) {
				dataCell = row.createCell(j);
				if(i==0){
					dataCell.setCellStyle(titleStyle);
				}else{
					dataCell.setCellStyle(dataStyle);
				}

				HSSFRichTextString titleStr = new HSSFRichTextString(String.valueOf(dataItem.get(headers.get(j))));
				dataCell.setCellValue(titleStr);
			}

		}

	}

	/**
	 * 生成
	 * @param sheet
	 * @param titleStyle
	 * @param dataStyle
	 * @param headers
	 * @param dataList
	 */
	private void createWorkSheet(HSSFSheet sheet,HSSFCellStyle titleStyle,HSSFCellStyle dataStyle,List<String> headers,List<String> headersVal,List<Map<String, String>> dataList) {
		HSSFRow row = sheet.createRow(0);
		HSSFCell titleCell =null;
		for (int i = 0; i < headers.size(); i++) {
			titleCell = row.createCell(i);
			titleCell.setCellStyle(titleStyle);
			HSSFRichTextString titleStr = new HSSFRichTextString(headers.get(i));
			titleCell.setCellValue(titleStr);
		}

		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i+1);
			HSSFCell dataCell =null;
			Map<String, String> dataItem = dataList.get(i);
			for (int j = 0; j < headersVal.size(); j++) {
				dataCell = row.createCell(j);
				dataCell.setCellStyle(dataStyle);
				HSSFRichTextString titleStr = new HSSFRichTextString(String.valueOf(dataItem.get(headersVal.get(j))));
				dataCell.setCellValue(titleStr);
			}

		}

	}

	/**
	 * 是否有外勤记录
	 * @param userUid
	 * @param exportDate
	 * @param day
	 * @param outWorkDatas
	 * @return
	 */
	private List<Map<String, String>> getOutWorkRecord(String userUid,String exportDate,int day,List<Map<String, String>> outWorkDatas) {
		String dayStr =day<10? "-0"+day:"-"+day;
		String createTime =exportDate+dayStr;
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (int i=0;i<outWorkDatas.size(); i++) {
			if(outWorkDatas.get(i).get("createDate").equals(createTime)&&outWorkDatas.get(i).get("createUserUid").equals(userUid)){
				result.add(outWorkDatas.get(i));
			}
		}
		return result;
	}

}
