package com.jrsoft.business.modules.construction.domain;

import com.jrsoft.engine.base.domain.BaseEntity;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.List;

/**
 * Created by chesijian on 2018/10/27.
 */
@Data
@Alias("MessagePo")
public class MessagePo extends BaseEntity {
    String title;
    String sendMsgUsers;
    String description;
    String businessKey;
    String state;
    String formKey;
    String url;



}
