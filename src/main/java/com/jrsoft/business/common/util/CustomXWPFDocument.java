package com.jrsoft.business.common.util;
import java.io.IOException;  
import java.io.InputStream;  
  
import org.apache.poi.openxml4j.opc.OPCPackage;  
import org.apache.poi.xwpf.usermodel.XWPFDocument;  
import org.apache.poi.xwpf.usermodel.XWPFParagraph;  
/*import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;  */
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;  
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;  
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;  
    
/**  
 * @author POI 导出图片bug修复 
 *   
 */    
public class CustomXWPFDocument extends XWPFDocument {    
    public CustomXWPFDocument(InputStream in) throws IOException {    
        super(in);    
    }    
    
    /**  
     *   
     */    
    public CustomXWPFDocument() {    
        super();    
        // TODO Auto-generated constructor stub     
    }    
    
    /**  
     * @param pkg  
     * @throws IOException  
     */    
    public CustomXWPFDocument(OPCPackage pkg) throws IOException {    
        super(pkg);    
        // TODO Auto-generated constructor stub     
    }  // picAttch 图片后面追加的字符串 可以是空格  
    public void createPicture(XWPFParagraph paragraph,int id, int width, int height,String picAttch) {    

    }

    /**
     * @param id
     * @param width 宽
     * @param height 高
     * @param paragraph  段落
     */
    public void createPicture(int id, int width, int height,XWPFParagraph paragraph) {

    }
}