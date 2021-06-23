package com.jrsoft.business.modules.progress.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lean
 * @description 任务传输对象
 * @create 2019-10-21 16:39
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
    private String id;
    private String text;
    private String code;
    private boolean leaf;
    private Integer duration;
}
