package com.jrsoft.business.modules.process.model;

import java.util.ArrayList;
import java.util.List;

public class ProcessTaskList {
	
	
	List<ProcessTask> task = new ArrayList<ProcessTask>();

	public List<ProcessTask> getTask() {
		return task;
	}

	public void setTask(List<ProcessTask> task) {
		this.task = task;
	}
	
}
