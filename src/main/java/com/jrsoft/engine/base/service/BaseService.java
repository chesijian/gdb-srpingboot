package com.jrsoft.engine.base.service;

import com.jrsoft.engine.base.domain.BaseEntity;

import java.util.List;

/**
 * service的基类
 *
 * @author Blueeyedboy
 * @create 2018-10-13 15:46
 **/
public interface BaseService <T extends BaseEntity>{
	//HqlQuery<T> createHqlQuery();
	T get(String id);
	long count();
	List<T> findAll();
	List<T> findList(T entity);
	void save(T entity);
	void update(T entity);
	void delete(T entity);
	void deleteByPrimaryKey(String id);
}
