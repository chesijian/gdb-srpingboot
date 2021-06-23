package com.jrsoft.engine.base.service;

import com.jrsoft.engine.base.domain.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


/**
 * 所有service实现类的基类
 *
 * @author Blueeyedboy
 * @create 2018-10-13 15:54
 **/
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T > {


	/*@Autowired
	@PersistenceContext(name = "entityManagerFactory")
	protected EntityManager entityManager;

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Autowired
	protected R repository;*/

	@Override
	public T get(String id) {
		//return (T)repository.findOne(id);
		return null;
	}

	public List<T> findList(T entity) {
		/*if(entity != null && ReflectUtil.hasField(entity.getClass(),"sort")){
			Sort sort = new Sort(Sort.Direction.ASC,"sort");
			return repository.findAll(sort);
		}
		return repository.findAll();*/
		return null;
	}

	public List<T> findAll() {
//		return repository.findAll();
		return null;
	}
	public long count() {
		//return repository.count();
		return 1;
	}

	@Transactional
	public void save(T entity) {
		entity.doBeforeInsert();
		//Object id = repository.save(entity);
	}

	@Transactional
	public void update(T entity) {

		T model = this.get(entity.getId());
		if(model == null){
			save(entity);
		}else{
			if(entity.getCompany() == null){
				entity.setCompany(model.getCompany());
			}
			if(entity.getSubCompany() == null){
				entity.setSubCompany(model.getSubCompany());
			}

			BeanUtils.copyProperties(entity,model,new String[]{"id","createTime","createUser"});
			model.doBeforeUpdate();
			//repository.saveAndFlush(model);
		}
		//System.out.println("model=========="+model);

	}

	@Transactional
	@Override
	public void delete(T entity) {
		//repository.delete(entity);
	}

	@Transactional
	@Override
	public void deleteByPrimaryKey(String id) {
		//repository.delete(id);
	}

	private Class<T> getTClass()
	{
		Type type = getClass().getGenericSuperclass();
		ParameterizedType ParameterizedType = (ParameterizedType)type;
		Class<T> tClass = (Class<T>)ParameterizedType.getActualTypeArguments()[0];
		return tClass;
	}
}
