package com.jrsoft.business.modules.progress.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Lean
 * @description 任务传输对象
 * @create 2019-10-21 16:39
 **/
@Data
public class LeaseReport {
    private String id;
    private String projName;
    private String projCode;
    private String projStatus;//项目状态
    private List<Task> taskList;
}
