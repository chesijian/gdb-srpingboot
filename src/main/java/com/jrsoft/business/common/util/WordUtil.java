package com.jrsoft.business.common.util;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

import com.jrsoft.engine.common.utils.CommonUtil;
import com.jrsoft.engine.impl.util.AttachmentUtil;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;


public class WordUtil {

	private static final int TITLE_FONT_SIZE = 18;

	private static final String TITLE_FONT_FAMILLY = "黑体";
	private static final int SUBTITLE_FONT_SIZE = 20;
	private static final String CONTENT_FONT_FAMILLY = "仿宋";
	private static final int CONTENT_FONT_SIZE = 13;

	//签名图片长度
	public static final int SIGN_PIC_WIDTH = 100;
	//签名图片高度
	public static final int SIGN_PIC_HEIGHT = 50;


	/**
	 * 生成周报临时文件2
	 * @author 车斯剑
	 * @date 2016年10月20日下午8:53:27
	 */
	public static void exportWeekReportByWeekData(String tempPath, String fileName,Map<String,Object> dataObj) {
		FileInputStream is = null;
		POIFSFileSystem pfs = null;
		OutputStream os = null;
		try {
			XWPFDocument doc = new XWPFDocument();
			// 创建一个段落
			XWPFParagraph para = doc.createParagraph();
			para.setAlignment(ParagraphAlignment.CENTER);
			// 一个XWPFRun代表具有相同属性的一个区域
			// 设置标题
			XWPFRun run = para.createRun();
			run.setBold(true);
			run.setFontFamily(TITLE_FONT_FAMILLY);
			run.setFontSize(TITLE_FONT_SIZE);
			run.setText(dataObj.get("checkType")+"问题隐患通知单");

			//插入表格
			para = doc.createParagraph();
			run = para.createRun();
			//run.setBold(true);
			run.setFontFamily(CONTENT_FONT_FAMILLY);
			run.setFontSize(CONTENT_FONT_SIZE);
			run.setText("编号:"+dataObj.get("sn"));
			run.setText(" 							");

			run = para.createRun();
			run.setFontFamily(CONTENT_FONT_FAMILLY);
			run.setFontSize(CONTENT_FONT_SIZE);
			run.setText("时间:"+String.valueOf(dataObj.get("createDate")));
			//insertNoBorderTable(doc,para,dataObj);
			insertTable(doc,para,dataObj);
			//insertMergeTable(doc,para,dataObj);
			//insertMergeTable2(doc,para,"整改措施：","编制人：","时间：");
			//insertMergeTable2(doc,para,null,"签发人：","接收人：");

	        String tempFileNamePath = tempPath + File.separator + fileName;
	        String _tempFileNamePath = tempPath + File.separator +"_"+ fileName;
			File file = new File(_tempFileNamePath);
			FileOutputStream fos = new FileOutputStream(file);
			doc.write(fos);
			fos.flush();
			doc.close();

			/** 打开word2007的文件设置图片 */
			OPCPackage opc = POIXMLDocument.openPackage(_tempFileNamePath);
			CustomXWPFDocument document = new CustomXWPFDocument(opc);

			//设置图片
			//获取表格对象集合
			List<XWPFTable> tables = document.getTables();
			//循环所有需要进行替换的文本，进行替换
			for (int i = 0; i < tables.size(); i++) {
				XWPFTable table = tables.get(i);
				List<XWPFTableRow> rows = table.getRows();
				//遍历表格,并替换模板
				eachTable(document,rows, dataObj);
			}

			FileOutputStream fopts = new FileOutputStream(tempFileNamePath);
			document.write(fopts);
			fopts.flush();
			fopts.close();
			document.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (pfs != null) {
					pfs.close();
				}
				if (os != null) {
					os.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	//列合并  ，有点问题，用不了
	/*public static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol) {
		for(int colIndex = fromCol; colIndex <= toCol; colIndex++){
			CTHMerge hmerge = CTHMerge.Factory.newInstance();
			if(colIndex == fromCol){
				hmerge.setVal(STMerge.RESTART);
			} else {
				hmerge.setVal(STMerge.CONTINUE);
			}
			XWPFTableCell cell = table.getRow(row).getCell(colIndex);
			CTTcPr tcPr = cell.getCTTc().getTcPr();
			if (tcPr != null) {
				tcPr.setHMerge(hmerge);
			} else {
				tcPr = CTTcPr.Factory.newInstance();
				tcPr.setHMerge(hmerge);
				cell.getCTTc().setTcPr(tcPr);
			}
		}
	}*/
	/**
	 * 插入表格
	 * @author 车斯剑
	 * @date 2016年10月17日下午10:13:54
	 * @param doc
	 * @param para
	 * @param saleData
	 */
	public static void insertTable(XWPFDocument doc, XWPFParagraph para, Map<String,Object> saleData){
		/*CTPPr pr = para.getCTP().getPPr() != null ? para.getCTP().getPPr() : para.getCTP().addNewPPr();
		CTSpacing spacing =pr.addNewSpacing();
		spacing.setLine(new BigInteger("160"));
		spacing.setLineRule(STLineSpacingRule.AUTO);
		para.setAlignment(ParagraphAlignment.CENTER);
		XWPFTable xTable = doc.createTable(4, 4);
		CTTbl ttbl = xTable.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		//tblWidth.setW(new BigInteger("6400"));
		tblWidth.setType(STTblWidth.DXA);
		xTable.getRow(0).setHeight(80);

		for(int m =0;m<4;m++){
			XWPFTableCell cell = xTable.getRow(0).getCell(m);
			XWPFParagraph xp = cell.addParagraph();
			xp.setVerticalAlignment(TextAlignment.CENTER);
			xp.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun temp = xp.createRun();
			if(m==0){
				cell.getCTTc().addNewTcPr().addNewTcW().setW(new BigInteger("1300"));
				temp.setText("工程名称");

			}else if(m==1){
				cell.getCTTc().addNewTcPr().addNewTcW().setW(new BigInteger("3100"));
				temp.setText(String.valueOf(saleData.get("projName")));
			}else if(m==2){
				cell.getCTTc().addNewTcPr().addNewTcW().setW(new BigInteger("900"));
				temp.setText("部位");
			}else{
				cell.getCTTc().addNewTcPr().addNewTcW().setW(new BigInteger("3100"));
				temp.setText(String.valueOf(saleData.get("title")));
			}
			temp.setFontFamily(CONTENT_FONT_FAMILLY);
			temp.setFontSize(CONTENT_FONT_SIZE);

		}
		mergeCellHorizontally(xTable,1,0,3);
		mergeCellHorizontally(xTable,2,0,3);
		mergeCellHorizontally(xTable,3,0,3);

		XWPFTableCell cell = xTable.getRow(1).getCell(0);
		cell.setText("检查记录：");

		XWPFParagraph xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun temp = xp.createRun();

		temp.setText("	"+String.valueOf(saleData.get("DESC_")));
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);

		if(saleData.get("imgs")!=null){
			List<Map<String,Object>> imgsList =(List<Map<String,Object>>)saleData.get("imgs");
			xp = cell.addParagraph();
			xp.setVerticalAlignment(TextAlignment.CENTER);
			xp.setAlignment(ParagraphAlignment.LEFT);
			for (int i = 0; i < imgsList.size(); i++) {
				temp = xp.createRun();
				temp.setText("${channelImage"+i+"}");
			}
		}

		xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.RIGHT);
		temp = xp.createRun();
		temp.setText("检查人:"+String.valueOf(saleData.get("createUserName")));
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);
		temp.setText(" 				");
		temp.setText("时间:"+String.valueOf(saleData.get("createTime")));
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);

		insertMergeTable3(xTable,2,"整改措施：","编制人：","时间：");
		insertMergeTable3(xTable,3,null,"签发人：","接收人：");*/


	}

	public static void insertMergeTable3(XWPFTable xTable, int rowNum, String title,String foot1,String foot2){


		xTable.getRow(rowNum).setHeight(2880);

		XWPFTableCell cell = xTable.getRow(rowNum).getCell(0);
		if(title!=null){
			cell.setText(title);
		}
		XWPFParagraph xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun temp = xp.createRun();
		temp.setText("");

		temp.addBreak();
		temp.addBreak();
		temp.addBreak();
		temp.addBreak();
		temp.addBreak();
		//para.setSpacingBefore(1000);

		xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.RIGHT);
		temp = xp.createRun();
		temp.setText(foot1);
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);
		temp.setText(" 				");
		temp.setText(foot2+"	");
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);

	}


	public static void insertMergeTable(XWPFDocument doc, XWPFParagraph para, Map<String,Object> saleData){
		/*CTPPr pr = para.getCTP().getPPr() != null ? para.getCTP().getPPr() : para.getCTP().addNewPPr();
		CTSpacing spacing =pr.addNewSpacing();
		spacing.setLine(new BigInteger("360"));
		spacing.setLineRule(STLineSpacingRule.AUTO);
		para.setAlignment(ParagraphAlignment.CENTER);
		XWPFTable xTable = doc.createTable(1, 1);
		CTTbl ttbl = xTable.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		tblWidth.setW(new BigInteger("8400"));
		tblWidth.setType(STTblWidth.DXA);

		//xTable.getRow(0).setHeight(2880);

		XWPFTableCell cell = xTable.getRow(0).getCell(0);
		cell.setText("检查记录：");

		XWPFParagraph xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun temp = xp.createRun();

		temp.setText("	"+String.valueOf(saleData.get("DESC_")));
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);

		if(saleData.get("imgs")!=null){
			List<Map<String,Object>> imgsList =(List<Map<String,Object>>)saleData.get("imgs");
			xp = cell.addParagraph();
			xp.setVerticalAlignment(TextAlignment.CENTER);
			xp.setAlignment(ParagraphAlignment.LEFT);
			for (int i = 0; i < imgsList.size(); i++) {
				temp = xp.createRun();
				temp.setText("${channelImage"+i+"}");
			}
		}

		xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.RIGHT);
		temp = xp.createRun();
		temp.setText("检查人:"+String.valueOf(saleData.get("createUserName")));
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);
		temp.setText(" 				");
		temp.setText("时间:"+String.valueOf(saleData.get("createTime")));
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);*/

	}


	public static void insertMergeTable2(XWPFDocument doc, XWPFParagraph para, String title,String foot1,String foot2){
		/*CTPPr pr = para.getCTP().getPPr() != null ? para.getCTP().getPPr() : para.getCTP().addNewPPr();
		CTSpacing spacing =pr.addNewSpacing();
		spacing.setLine(new BigInteger("360"));
		spacing.setLineRule(STLineSpacingRule.AUTO);
		para.setAlignment(ParagraphAlignment.CENTER);
		XWPFTable xTable = doc.createTable(1, 1);
		CTTbl ttbl = xTable.getCTTbl();
		CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
		CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
		tblWidth.setW(new BigInteger("8400"));
		tblWidth.setType(STTblWidth.DXA);

		xTable.getRow(0).setHeight(2880);

		XWPFTableCell cell = xTable.getRow(0).getCell(0);
		if(title!=null){
			cell.setText(title);
		}
		XWPFParagraph xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun temp = xp.createRun();
		temp.setText("");

		temp.addBreak();
		temp.addBreak();
		temp.addBreak();
		temp.addBreak();
		temp.addBreak();
		//para.setSpacingBefore(1000);

		xp = cell.addParagraph();
		xp.setVerticalAlignment(TextAlignment.CENTER);
		xp.setAlignment(ParagraphAlignment.RIGHT);
		temp = xp.createRun();
		temp.setText(foot1);
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);
		temp.setText(" 				");
		temp.setText(foot2+"	");
		temp.setFontFamily(CONTENT_FONT_FAMILLY);
		temp.setFontSize(CONTENT_FONT_SIZE);*/

	}
	/**
	 * 遍历表格
	 * @param rows 表格行对象
	 * @param dataMap 需要替换的信息集合
	 */
	public static void eachTable(CustomXWPFDocument document,List<XWPFTableRow> rows ,Map<String, Object> dataMap){
		/*for (XWPFTableRow row : rows) {
			List<XWPFTableCell> cells = row.getTableCells();
			List<XWPFParagraph> result = new ArrayList<XWPFParagraph>();
			for (XWPFTableCell cell : cells) {
				//判断单元格是否需要替换
				String cellText = cell.getText();
				if(checkText(cellText)){
					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					for (XWPFParagraph paragraph : paragraphs) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun run : runs) {
							String text = run.getText(0);
							if (text != null) {
								if (text.indexOf("${channelImage") > -1) {
									run.setText("",0);
									//channelParagraph=paragraph;
									result.add(paragraph);

								}
							}

						}
					}
					insertPicture(result,document,dataMap);

				}
			}

		}*/
	}

	/**
	 * 往表格中插入图片
	 * @param paragList
	 * @throws IOException
	 * @throws InvalidFormatException
	 */
	public static void insertPicture(List<XWPFParagraph> paragList,CustomXWPFDocument document,Map<String, Object> dataMap) {
		try {
			List<Map<String,Object>> imgsList =(List<Map<String,Object>>)dataMap.get("imgs");
			for (int i=0;i<paragList.size();i++) {
				Map<String,Object> imgObj = imgsList.get(i);
				String desPath = AttachmentUtil.UPLOAD_PATH + File.separator + imgObj.get("company") + File.separator + CommonUtil.getDateStr((Date)imgObj.get("createTime"));
				String fileName = imgObj.get("id")+"."+imgObj.get("suffix");
				File pictureF = new File(desPath + File.separator + fileName);
				if(pictureF.exists()) {
					XWPFParagraph channelParagraph = paragList.get(i);
					int picType = document.PICTURE_TYPE_PNG;
					FileInputStream imageIns = new FileInputStream(pictureF);
					document.addPictureData(imageIns, picType);
					document.createPicture(channelParagraph, document.getAllPictures().size() - 1, 100, 100, "");
					if (imageIns != null) {
						imageIns.close();
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 判断文本中时候包含$
	 * @param text 文本
	 * @return 包含返回true,不包含返回false
	 */
	public static boolean checkText(String text){
		boolean check  =  false;
		if(text.indexOf("$")!= -1){
			check = true;
		}
		return check;
	}


}
