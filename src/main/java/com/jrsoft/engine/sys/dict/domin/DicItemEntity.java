package com.jrsoft.engine.sys.dict.domin;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="pf_dic_item" )
public class DicItemEntity extends BaseEntity {

    @Column(name = "NAME_")
    private String name;

    @Column(name = "VALUE_")
    private String value;

    @Column(name = "PARENT_ID_")
    private String parentId;

    @Column(name = "DESCRIPTION_")
    private String description;

    @Column(name = "ENABLE_")
    private Integer enable;

    @Column(name = "GS_")
    private String gs;

    @Column(name = "BLZ_")
    private String blz;

    @Column(name = "TYPE_")
    private String type;

    @Column(name = "ISCHECK_")
    private Integer isCheck;

    @Column(name = "ATTRIBUTE_")
    private String attribute;
}
