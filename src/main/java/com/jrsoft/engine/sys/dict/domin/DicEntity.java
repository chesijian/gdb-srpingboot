package com.jrsoft.engine.sys.dict.domin;

import com.jrsoft.engine.base.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table( name ="pf_dic_test" )
public class DicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value="主键",name="id",hidden = true)
    @Column(name = "ID_", length = 36, nullable = false)
    private Integer id;

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
