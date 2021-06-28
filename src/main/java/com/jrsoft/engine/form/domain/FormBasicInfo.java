package com.jrsoft.engine.form.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 表单实体
 *
 * @author Blueeyedboy
 * @create 2018-10-12 17:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SYS_FORM_BASIC_INFO")
public class FormBasicInfo extends BaseEntity {

    @ApiModelProperty(value="标题",name="title",dataType="String")
    @Column(name = "TITLE_", length = 50)
    private String title;

    @ApiModelProperty(value="实体名称",name="entity",dataType="String")
    @Column(name = "ENTITY_", length = 50)
    private String entity;

    @ApiModelProperty(value="表单名称",name="tableName",dataType="String")
    @Column(name = "TABLE_NAME_", length = 50)
    private String tableName;

    @ApiModelProperty(value="表单类型",name="type",dataType="Integer")
    @Column(name = "TYPE_", length = 1)
    private Integer type;

    @ApiModelProperty(value="是否默认展开",name="expandAll",dataType="Integer")
    @Column(name = "EXPAND_ALL_", length = 1)
    private Integer expandAll;

    @ApiModelProperty(value="表单宽度",name="formWidth",dataType="String")
    @Column(name = "FORM_WIDTH_", length = 10)
    private String formWidth;

    @ApiModelProperty(value="表单高度",name="formHeight",dataType="String")
    @Column(name = "FORM_HEIGHT_", length = 10)
    private String formHeight;

    @ApiModelProperty(value="列表宽度",name="gridWidth",dataType="String")
    @Column(name = "GRID_WIDTH_", length = 10)
    private String gridWidth;

    @ApiModelProperty(value="列表高度",name="gridHeight",dataType="String")
    @Column(name = "GRID_HEIGHT_", length = 10)
    private String gridHeight;

    @ApiModelProperty(value="总列数",name="colNum",dataType="Integer")
    @Column(name = "COL_NUM_", length = 10)
    private Integer colNum;

    @ApiModelProperty(value="跟节点ID",name="rootId",dataType="String")
    @Column(name = "ROOT_ID_", length = 50)
    private String rootId;

    @ApiModelProperty(value="跟节点名称",name="rootText",dataType="String")
    @Column(name = "ROOT_TEXT_", length = 50)
    private String rootText;

    @ApiModelProperty(value="树节点字段名",name="treeColumn",dataType="String")
    @Column(name = "TREE_COLUMN_", length = 50)
    private String treeColumn;

    @ApiModelProperty(value="父节点字段名",name="parentColumn",dataType="String")
    @Column(name = "PARENT_COLUMN_", length = 50)
    private String parentColumn;

    @ApiModelProperty(value="父表单关联Id",name="subFormForeignKey",dataType="String")
    @Column(name = "SUB_FORM_FOREIGN_KEY_", length = 36)
    private String subFormForeignKey;

    @ApiModelProperty(value="是否展示跟节点",name="showRoot",dataType="Integer")
    @Column(name = "SHOW_ROOT_", length = 1)
    private Integer showRoot;

    @ApiModelProperty(value="是否展示标题",name="showTitle",dataType="Integer")
    @Column(name = "SHOW_TITLE_", length = 1)
    private Integer showTitle;

    @ApiModelProperty(value="父节点ID",name="parentId",dataType="String")
    @Column(name = "PARENT_ID_", length = 36)
    private String parentId;

//    @ApiModelProperty(value="表单关联ID",name="formKey",dataType="String")
//    @Column(name = "FORM_KEY_", length = 36)
//    private String formKey;

    @ApiModelProperty(value="表单关联ID",name="formId",dataType="String")
    @Column(name = "FORM_ID_", length = 36)
    private String formId;

    @ApiModelProperty(value="页面数量",name="pageSize",dataType="Integer")
    @Column(name = "PAGE_SIZE_", length = 100)
    private Integer pageSize;

    @ApiModelProperty(value="api数据接口",name="api",dataType="String")
    @Column(name = "API_", length = 100)
    private String api;


    @ApiModelProperty(value="api数据接口",name="apiTitle",dataType="String")
    @Transient
    private String apiTitle;

    @ApiModelProperty(value="摘要",name="abstracts",dataType="String")
    @Column(name = "ABSTRACTS_")
    private String abstracts;

    @ApiModelProperty(value="移动端路由",name="mobilePath",dataType="String")
    @Column(name = "MOBILE_PATH_")
    private String mobilePath;

    @ApiModelProperty(value="排序号",name="sort",dataType="int")
    @Column(name = "SORT_")
    private Integer sort;

    @ApiModelProperty(value="树表是否懒加载",name="lazy",dataType="int")
    @Column(name = "LAZY_")
    private Integer lazy;

    @ApiModelProperty(value="是否启用流程，主要用于左右结构等多表单情况，默认只能有一个开启",name="enableProcess",dataType="int")
    @Column(name = "ENABLE_PROCESS_")
    private Integer enableProcess;

    /*@Transient
    private SysMessageEntity apiConfig;*/

    @Transient
    private List<FormColumn> queryColumns;

    @Transient
    private List<FormColumn> formColumns;

    @Transient
    private List<FormColumn> columns;

    @Transient
    private List<FormBasicInfo> children;


    /*@Transient
    private List<SysFormEventEntity> events;

    @Transient
    private List<FormExcel> formExcelList;*/

}

