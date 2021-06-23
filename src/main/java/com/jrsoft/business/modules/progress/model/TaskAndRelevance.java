package com.jrsoft.business.modules.progress.model;

import java.util.ArrayList;
import java.util.List;

public class TaskAndRelevance {
	
	List<Task> insertTasks = new ArrayList<Task>();
	List<Task> updateTasks = new ArrayList<Task>();
	List<Task> deleteTasks = new ArrayList<Task>();
	List<Iink> insertLinks = new ArrayList<Iink>();
	List<Iink> deleteLinks = new ArrayList<Iink>();
	
	public TaskAndRelevance() {
	}

	public List<Task> getInsertTasks() {
		return insertTasks;
	}

	public void setInsertTasks(List<Task> insertTasks) {
		this.insertTasks = insertTasks;
	}

	public List<Task> getUpdateTasks() {
		return updateTasks;
	}

	public void setUpdateTasks(List<Task> updateTasks) {
		this.updateTasks = updateTasks;
	}

	public List<Task> getDeleteTasks() {
		return deleteTasks;
	}

	public void setDeleteTasks(List<Task> deleteTasks) {
		this.deleteTasks = deleteTasks;
	}

	public List<Iink> getInsertLinks() {
		return insertLinks;
	}

	public void setInsertLinks(List<Iink> insertLinks) {
		this.insertLinks = insertLinks;
	}

	public List<Iink> getDeleteLinks() {
		return deleteLinks;
	}

	public void setDeleteLinks(List<Iink> deleteLinks) {
		this.deleteLinks = deleteLinks;
	}

	
	
	
}
