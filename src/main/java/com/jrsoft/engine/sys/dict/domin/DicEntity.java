package com.jrsoft.engine.sys.dict.domin;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="pf_dic" )
public class DicEntity extends BaseEntity {

    @Column(name = "DIC_ID_")
    private String dicId;

    @Column(name = "DIC_NAME_")
    private String dicName;

    @Column(name = "TYPE_")
    private Integer type;

    @Column(name = "DESCRIPTION_")
    private String description;

    @Column(name = "ENABLE_")
    private Integer enable;

    @Column(name = "PARENT_ID_")
    private String parentId;

    @Transient
    private List<DicItemEntity> children;
}
