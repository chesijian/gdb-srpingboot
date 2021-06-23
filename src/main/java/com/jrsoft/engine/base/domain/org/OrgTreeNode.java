package com.jrsoft.engine.base.domain.org;/**
 * Created by chesijian on 2021/6/16.
 */

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName OrgTreeNode
 * @Description TODO
 * @Author chesijian
 * @Date 2021/6/16 13:56
 * @Version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrgTreeNode implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String nodeId;
    private String label;
    private String text;
    private String userId;
    private String userName;
    private int isResp;
    private int gender;
    private boolean leaf;
    private String enCode;
    private String parentId;
    private String parentPosition;
    private String mobile;
    private String type;
    private Double sort;
    private String roleUid;
    private String positionUid;
    private String departUid;
    private int checked;
    private String icon;
    private String picture;
    private String wxId;

    private String positionName;


    private List<OrgTreeNode> children;
}
