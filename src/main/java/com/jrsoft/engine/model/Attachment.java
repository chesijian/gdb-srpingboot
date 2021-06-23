package com.jrsoft.engine.model;


/**
 * ${DESCRIPTION}
 *
 * @author Blueeyedboy
 * @create 2017-06-21 16:02
 **/
public interface Attachment extends BaseModel {
    public String getFileName();

    public void setFileName(String fileName);

    public String getSuffix();

    public void setSuffix(String suffix);

    public String getXtype();

    public void setXtype(String xtype);

    public String getSize();

    public void setSize(String size);

    public String getParentId();

    public void setParentId(String parentId);

	public String getProjectId();

	public void setProjectId(String projectId) ;

	public String getFormKey() ;

	public void setFormKey(String formKey) ;

	public String getFormId() ;

	public void setFormId(String formId) ;

	public String getFormInfoId() ;

	public void setFormInfoId(String formInfoId) ;

	public String getFileId() ;

	public void setFileId(String fileId) ;

	public String getAttr1();

	public void setAttr1(String attr1) ;

	public String getAttr2() ;

	public void setAttr2(String attr2) ;

	public String getAttr3() ;

	public void setAttr3(String attr3);

	public String getAttr4() ;

	public void setAttr4(String attr4) ;

	public String getAttr5() ;

	public void setAttr5(String attr5) ;
//	public boolean isThumb() ;
//
//	public void setThumb(boolean thumb) ;
}
