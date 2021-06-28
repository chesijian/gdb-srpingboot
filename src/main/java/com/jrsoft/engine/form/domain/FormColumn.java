package com.jrsoft.engine.form.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import com.jrsoft.engine.sys.dict.domin.DicEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 表单字段实体
 *
 * @author Blueeyedboy
 * @create 2018-10-12 17:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SYS_FORM_COLUMN")
public class FormColumn extends BaseEntity {

    @ApiModelProperty(value="标题",name="columnName",dataType="String")
    @Column(name = "COLUMN_NAME_", length = 50)
    private String columnName;

    @ApiModelProperty(value="字段类型",name="javaType",dataType="String")
    @Column(name = "JAVA_TYPE_", length = 50)
    private String javaType;

    @ApiModelProperty(value="宽度",name="javaType",dataType="String")
    @Column(name = "WIDTH_", length = 10)
    private String width;

    @ApiModelProperty(value="是否只读",name="javaType",dataType="Integer")
    @Column(name = "READ_ONLY_", length = 1)
    private Integer readOnly;

    @ApiModelProperty(value="标题",name="title",dataType="String")
    @Column(name = "TITLE_", length = 50)
    private String title;

    @ApiModelProperty(value="父表单关联Id",name="subFormForeignKey",dataType="String")
    @Column(name = "SUB_FORM_FOREIGN_KEY_", length = 36)
    private String subFormForeignKey;

    @ApiModelProperty(value="表单名称",name="tableName",dataType="String")
    @Column(name = "TABLE_NAME_", length = 50)
    private String tableName;

    @ApiModelProperty(value="字段类型",name="widgetType",dataType="String")
    @Column(name = "WIDGET_TYPE_", length = 50)
    private String widgetType;

    @ApiModelProperty(value="是否合计",name="summary",dataType="Integer")
    @Column(name = "SUMMARY_", length = 1)
    private Integer summary;

    @ApiModelProperty(value="合计公式",name="expression",dataType="String")
    @Column(name = "EXPRESSION_", length = 200)
    private String expression;

    @ApiModelProperty(value="是否必填",name="require",dataType="Integer")
    @Column(name = "REQUIRE_", length = 1)
    private Integer require;

    @ApiModelProperty(value="行号",name="row",dataType="Integer")
    @Column(name = "ROW_", length = 10)
    private Integer row;

    @ApiModelProperty(value="列号",name="col",dataType="Integer")
    @Column(name = "COL_", length = 10)
    private Integer col;

    @ApiModelProperty(value="校验类型",name="validType",dataType="String")
    @Column(name = "VALID_TYPE_", length = 50)
    private String validType;

    @ApiModelProperty(value="验证规则",name="validRule",dataType="String")
    @Column(name = "VALID_RULE_", length = 50)
    private String validRule;

    @ApiModelProperty(value="验证信息",name="validMessage",dataType="String")
    @Column(name = "VALID_MESSAGE_", length = 50)
    private String validMessage;

    @ApiModelProperty(value="绑定参数",name="attr",dataType="String")
    @Column(name = "ATTR_", length = 50)
    private String attr;

    @ApiModelProperty(value="图标样式",name="icon",dataType="String")
    @Column(name = "ICON_", length = 50)
    private String icon;

    @ApiModelProperty(value="关联Id",name="parentId",dataType="String")
    @Column(name = "PARENT_ID_", length = 50)
    private String parentId;

    @ApiModelProperty(value="匹配类型",name="matchType",dataType="String")
    @Column(name = "MATCH_TYPE_", length = 50)
    private String matchType;

    @ApiModelProperty(value="是否查询",name="ifSearchForm",dataType="Integer")
    @Column(name = "IF_SEARCH_FORM_", length = 1)
    private Integer ifSearchForm;

    @ApiModelProperty(value="排序",name="sort",dataType="Integer")
    @Column(name = "SORT_", length = 10)
    private Integer sort;

    @ApiModelProperty(value="是否隐藏",name="hidden",dataType="Integer")
    @Column(name = "HIDDEN_", length = 1)
    private Integer hidden;

    @ApiModelProperty(value="字段类型",name="type",dataType="Integer")
    @Column(name = "TYPE_", length = 1)
    private Integer type;

    @ApiModelProperty(value="是否为静默字段",name="silence",dataType="Integer")
    @Column(name = "SILENCE_", length = 1)
    private Integer silence;

	@ApiModelProperty(value="路由打开方式,1表示模态框，2表示tab",name="openType",dataType="Integer")
	@Column(name = "OPEN_TYPE_", length = 100)
	private Integer openType;

    @ApiModelProperty(value="唯一规则",name="onlyRule",dataType="Integer")
    @Column(name = "ONLY_RULE_", length = 100)
    private Integer onlyRule;

    @ApiModelProperty(value="点击路由",name="openUrl",dataType="String")
    @Column(name = "OPEN_URL_", length = 100)
    private String openUrl;

    @ApiModelProperty(value="弹框宽度",name="openWidth",dataType="String")
    @Column(name = "OPEN_WIDTH_", length = 10)
    private String openWidth;

    @ApiModelProperty(value="弹框高度",name="openHeight",dataType="String")
    @Column(name = "OPEN_HEIGHT_", length = 10)
    private String openHeight;

    @ApiModelProperty(value="点击参数",name="openParams",dataType="String")
    @Column(name = "OPEN_PARAMS_", length = 500)
    private String openParams;

    @ApiModelProperty(value="数据来源类型",name="sourceType",dataType="String")
    @Column(name = "SOURCE_TYPE_", length = 50)
    private String sourceType;

    @ApiModelProperty(value="数据来源方式",name="sourceAttr",dataType="String")
    @Column(name = "SOURCE_ATTR_", length = 500)
    private String sourceAttr;

    //    @ApiModelProperty(value="表单关联ID",name="formKey",dataType="String")
    //    @Column(name = "FORM_KEY_", length = 36)
    //    private String formKey;

    @ApiModelProperty(value="是否过滤权限",name="filterAuth",dataType="Integer")
    @Column(name = "FILTER_AUTH_", length = 1)
    private Integer filterAuth;

    @ApiModelProperty(value="表单关联ID",name="formId",dataType="String")
    @Column(name = "FORM_ID_", length = 36)
    private String formId;

    @ApiModelProperty(value="数据过滤参数",name="initMatchValue",dataType="String")
    @Column(name = "INIT_MATCH_VALUE_", length = 100)
    private String initMatchValue;

    @ApiModelProperty(value="排序规则",name="sortRule",dataType="String")
    @Column(name = "SORT_RULE_", length = 10)
    private String sortRule;

    @ApiModelProperty(value="执行脚本",name="events",dataType="String")
    @Column(name = "EVENTS_", length = 500)
    private String events;

    @ApiModelProperty(value="标题位置",name="titleLocation",dataType="String")
    @Column(name = "TITLE_ALIGN_", length = 500)
    private String titleAlign;

    @ApiModelProperty(value="数据位置",name="dataLocation",dataType="String")
    @Column(name = "DATA_ALIGN_", length = 500)
    private String dataAlign;

    @ApiModelProperty(value="日期格式",name="dateType",dataType="String")
    @Column(name = "DATE_TYPE_", length = 500)
    private String dateType;

    @ApiModelProperty(value="是否不关联",name="ifNotRelated",dataType="Integer")
    @Column(name = "IF_NOT_RELATED_", length = 100)
    private Integer ifNotRelated;

    @ApiModelProperty(value="是否多选",name="multiple",dataType="Integer")
    @Column(name = "MULTIPLE_", length = 100)
    private Integer multiple;


    @Transient
    private DicEntity dicEntity;


    @Transient
    private FormBasicInfo config;

    /*@Transient
    private List<SysMessageEntity> apiConfig;*/

    @Transient
    private String key;

    @Transient
    private String prefix;

    @Transient
    private int btnLook = 1;

    @Transient
    private int btnEdit = 1;

    @Transient
    @ApiModelProperty(value="字段状态",name="status",dataType="Integer")
    private Integer status;
}

