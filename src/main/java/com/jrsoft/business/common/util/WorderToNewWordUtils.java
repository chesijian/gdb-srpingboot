package com.jrsoft.business.common.util;

import com.jrsoft.engine.common.utils.AttachmentUtil;
import com.jrsoft.engine.common.utils.CommonUtil;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTVMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.*;
import java.util.*;

/**
 * Created by chesijian on 2019/3/14.
 */
public class WorderToNewWordUtils {
    /**
     * 根据模板生成word文档
     * @param inputUrl 模板路径
     * @param textMap 需要替换的文本内容
     * @param mapList 需要动态生成的内容
     * @return
     */
    public static CustomXWPFDocument changWord(String inputUrl, Map<String, Object> textMap, Map<String, Object> mapList) {
        CustomXWPFDocument document = null;
        try {
            //获取docx解析对象
            document = new CustomXWPFDocument(POIXMLDocument.openPackage(inputUrl));

            //解析替换文本段落对象
            WorderToNewWordUtils.changeText(document, textMap);

            //解析替换表格对象
            WorderToNewWordUtils.changeTable(document, textMap, mapList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * 替换表格对象方法
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     * @param mapList 需要动态生成的内容
     */
    public static void changeTable(CustomXWPFDocument document, Map<String, Object> textMap,Map<String, Object> mapList){
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();

        //循环所有需要进行替换的文本，进行替换
        for (int i = 0; i < tables.size(); i++) {
            XWPFTable table = tables.get(i);
//            if(checkText(table.getText())){
                List<XWPFTableRow> rows = table.getRows();
                //遍历表格,并替换模板
                eachTable(document,rows, textMap);
//            }
        }


    }




    /**
     * 遍历表格
     * @param rows 表格行对象
     * @param textMap 需要替换的信息集合
     */
    public static void eachTable(CustomXWPFDocument document,List<XWPFTableRow> rows ,Map<String, Object> textMap){
        XWPFParagraph channelParagraph = null;
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                List<XWPFParagraph> paragraphs = cell.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    List<XWPFRun> runs = paragraph.getRuns();
                    for (XWPFRun run : runs) {
                        String text = run.getText(0);
                        if (text != null) {
                            if (text.indexOf("localhostPicture") > -1) {
                                run.setText("",0);
                                channelParagraph=paragraph;
                            }
                        }
                        changeValue(run, text, textMap);
                    }
                }
            }
        }
        insertPicture(channelParagraph,document,textMap);

    }
    /**
     * 往表格中插入图片
     * @param channelParagraph
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void insertPicture(XWPFParagraph channelParagraph,CustomXWPFDocument document,Map<String, Object> dataMap) {
        try {
            List<Map<String,Object>> imgsList =(List<Map<String,Object>>)dataMap.get("imgs");
            for (int i=0;i<imgsList.size();i++) {
                Map<String,Object> imgObj = imgsList.get(i);
                String desPath = AttachmentUtil.UPLOAD_PATH + File.separator + imgObj.get("company") + File.separator + CommonUtil.getDateStr((Date)imgObj.get("createTime"));
                String fileName = imgObj.get("id")+"."+imgObj.get("suffix");
                File pictureF = new File(desPath + File.separator + fileName);
                if(pictureF.exists()) {
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
     * 匹配传入信息集合与模板
     * @param value 模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static Object changeValue(XWPFRun run, String value, Map<String, Object> textMap){
        Set<Map.Entry<String, Object>> textSets = textMap.entrySet();
        Object valu = "";
        for (Map.Entry<String, Object> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = textSet.getKey();
            if(value.equals(key)){
                valu = textSet.getValue();
                run.setText((String)valu,0);
            }
        }
        return valu;
    }


    /**
     * 替换段落文本
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     */
    public static void changeText(CustomXWPFDocument document, Map<String, Object> textMap){
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            System.out.println(text);
//            if(checkText(text)){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    String textVal = run.getText(0);

                    Object ob = changeValue(run,textVal, textMap);
                    /*if (ob instanceof String){
                        run.setText((String)ob,0);
                    }*/
                }
//            }
        }
    }


    /**
     * 获取需要合并单元格的下标
     * @return
     */
    public static List<Integer[]> startEnd(List<String[]> daList){
        List<Integer[]> indexList = new ArrayList<Integer[]>();

        List<String> list = new ArrayList<String>();
        for (int i=0;i<daList.size();i++){
            list.add(daList.get(i)[0]);
        }
        Map<Object, Integer> tm = new HashMap<Object, Integer>();
        for (int i=0;i<daList.size();i++){
            if (!tm.containsKey(daList.get(i)[0])) {
                tm.put(daList.get(i)[0], 1);
            } else {
                int count = tm.get(daList.get(i)[0]) + 1;
                tm.put(daList.get(i)[0], count);
            }
        }
        for (Map.Entry<Object, Integer> entry : tm.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            if (list.indexOf(key) != (-1)){
                Integer[] index = new Integer[2];
                index[0] = list.indexOf(key);
                index[1] = list.lastIndexOf(key);
                indexList.add(index);
            }
        }
        return indexList;
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

    /**
     * 合并行
     * @param table
     * @param col 需要合并的列
     * @param fromRow 开始行
     * @param toRow 结束行
     */
    /*public static void mergeCellVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for(int rowIndex = fromRow; rowIndex <= toRow; rowIndex++){
            CTVMerge vmerge = CTVMerge.Factory.newInstance();
            if(rowIndex == fromRow){
                vmerge.setVal(STMerge.RESTART);
            } else {
                vmerge.setVal(STMerge.CONTINUE);
            }
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            CTTcPr tcPr = cell.getCTTc().getTcPr();
            if (tcPr != null) {
                tcPr.setVMerge(vmerge);
            } else {
                tcPr = CTTcPr.Factory.newInstance();
                tcPr.setVMerge(vmerge);
                cell.getCTTc().setTcPr(tcPr);
            }
        }
    }*/

    //列合并  ，有点问题，用不了
    public static void mergeCellHorizontally(XWPFTable table, int row, int fromCol, int toCol) {
        /*for(int colIndex = fromCol; colIndex <= toCol; colIndex++){
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
        }*/
    }
    /**
     * 往表格中的一个单元格中插入图片
     *
     * @param xwpfTableCell
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void insertPictureIntoTableCell(XWPFTableCell xwpfTableCell, int width,int height) throws IOException, InvalidFormatException {
        XWPFParagraph newPara = xwpfTableCell.getParagraphArray(0);
        XWPFRun run = newPara.createRun();
        String iconPath = "D://temp//temp//W_4522l204091.jpg";
        run.addPicture(new FileInputStream(iconPath), XWPFDocument.PICTURE_TYPE_JPEG, "W_4522l204091.jpg", Units.toEMU(width), Units.toEMU(height));

    }


    /**
     * 根据图片类型，取得对应的图片类型代码
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType){
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if(picType != null){
            if(picType.equalsIgnoreCase("png")){
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            }else if(picType.equalsIgnoreCase("dib")){
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            }else if(picType.equalsIgnoreCase("emf")){
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            }else if(picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")){
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            }else if(picType.equalsIgnoreCase("wmf")){
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 为表格插入数据，行数不够添加新行
     * @param table 需要插入数据的表格
     * @param tableList 第四个表格的插入数据
     * @param daList 第二个表格的插入数据
     * @param type 表格类型：1-第一个表格 2-第二个表格 3-第三个表格 4-第四个表格
     */
    public static void insertTable(XWPFTable table, List<String> tableList,List<String[]> daList,Integer type){
        if (2 == type){
            //创建行和创建需要的列
            for(int i = 1; i < daList.size(); i++){
                XWPFTableRow row = table.insertNewTableRow(1);//添加一个新行
                row.createCell();//添加第一个列
                row.createCell();//添加第二个列
            }
            //创建行,根据需要插入的数据添加新行，不处理表头
            for(int i = 0; i < daList.size(); i++){
                List<XWPFTableCell> cells = table.getRow(i+1).getTableCells();
                for(int j = 0; j < cells.size(); j++){
                    XWPFTableCell cell02 = cells.get(j);
                    cell02.setText(daList.get(i)[j]);
                }
            }
        }else if (4 == type){
            //插入表头下面第一行的数据
            for(int i = 0; i < tableList.size(); i++){
                XWPFTableRow row = table.createRow();
                List<XWPFTableCell> cells = row.getTableCells();
                cells.get(0).setText(tableList.get(i));
            }
        }
    }






    /**
     * 将输入流中的数据写入字节数组
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose){
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(isClose){
                try {
                    in.close();
                } catch (Exception e2) {
                    System.out.println("关闭流失败");
                }
            }
        }
        return byteArray;
    }

}

