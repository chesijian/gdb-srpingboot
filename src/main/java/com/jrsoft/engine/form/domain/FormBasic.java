package com.jrsoft.engine.form.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import com.jrsoft.engine.common.utils.CommonUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 * 表单配置实体
 *
 * @author Blueeyedboy
 * @create 2018-10-12 17:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SYS_FORM_BASIC")
public class FormBasic extends BaseEntity {

    @ApiModelProperty(value="formKey",name="formKey",dataType="String")
    @Column(name = "FORM_KEY_", length = 50)
    private String formKey;

    @ApiModelProperty(value="联动字段",name="linkColumnName",dataType="String")
    @Column(name = "LINK_COLUMN_NAME_", length = 50)
    private String linkColumnName;

    @ApiModelProperty(value="标题",name="title",dataType="String")
    @Column(name = "TITLE_", length = 50)
    private String title;

    @ApiModelProperty(value="app标题",name="appTitle",dataType="String")
    @Column(name = "APP_TITLE_", length = 50)
    private String appTitle;

    @ApiModelProperty(value="弹窗宽度",name="width",dataType="String")
    @Column(name = "WIDTH_", length = 10)
    private String width;

    @ApiModelProperty(value="弹窗高度",name="height",dataType="String")
    @Column(name = "HEIGHT_", length = 10)
    private String height;

    @ApiModelProperty(value="是否启用",name="enable",dataType="Integer")
    @Column(name = "ENABLE_", length = 1)
    private Integer enable;

    @ApiModelProperty(value="是否启用",name="ifHasProcess",dataType="Integer")
    @Column(name = "IF_HAS_PROCESS_", length = 1)
    private Integer ifHasProcess;

    @ApiModelProperty(value="表单类型",name="type",dataType="Integer")
    @Column(name = "TYPE_", length = 1)
    private Integer type;

    @ApiModelProperty(value="布局结构",name="type",dataType="Integer")
    @Column(name = "LAYOUT_", length = 1)
    private Integer layout;

    @ApiModelProperty(value="回填规则",name="backFillRule",dataType="String")
    @Column(name = "BACK_FILL_RULE_", length = 500)
    private String backFillRule;

    @ApiModelProperty(value="备注",name="remark",dataType="String")
    @Column(name = "REMARK_", length = 500)
    private String remark;

    @ApiModelProperty(value="所属分类",name="category",dataType="String")
    @Column(name = "CATEGORY_", length = 500)
    private String category;

    @Transient
    private List<FormBasic> children;

    /*@Transient
    private List<SysFormEventEntity> events;

    @Transient
    private List<FormBasicInfo> forms;*/

    @Transient
    Map<String, Map<String, Integer>> wfFormAuth; // 流程过程中节点权限

    /*@Transient
    private FormCode formCode;*/

    @Transient
    private int btnAdd = 1;

    @Transient
    private int btnLook = 1;

    @Transient
    private int btnDelete = 1;

    @Transient
    private int btnEdit = 1;

    @Transient
    private int btnExport = 1;

    @Transient
    private Map<String, String> backFillMap;


    public Map<String, String> getBackFillMap(){
        Map<String, String> map = null;
        if(CommonUtil.ifNotEmpty(backFillRule)){
            map = CommonUtil.jsonToMapStr(backFillRule);
        }
        this.backFillMap = map;
        return backFillMap;
    }
}

