package tcb.shms.core.dao;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import tcb.shms.core.entity.GenericEntity;

/**
 * GenericHibernateDao
 * @author Mark Huang
 * @version 2014/3/7
 */
public abstract class GenericHibernateDao<T extends GenericEntity> extends GenericDao<T> {

	@Autowired
	private SessionFactory sessionFactory;
	
	private Class<T> entityClass;
	
	@SuppressWarnings("unchecked")
	public GenericHibernateDao() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type t = c.getGenericSuperclass();
		if(t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<T>) p[0];
		}
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public T findById(Long id) throws Exception {
		Assert.notNull(id, "id不得為空");
		return (T) getSession().byId(entityClass).load(id);
	}

	@Override
	public T save(T entity) throws Exception {
		Assert.notNull(entity,"不得為null");
		Serializable id = getSession().save(entity);
		try {
			BeanUtils.setProperty(entity, "id", id);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return entity;
	}

	@Override
	public void update(T entity) throws Exception {
		Assert.notNull(entity,"entity不得為null");
		getSession().update(entity);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		Assert.notNull(id,"id不得為null");
		T t = findById(id);
		getSession().delete(t);
	}

	@Override
	public void delete(T entity) throws Exception {
		Assert.notNull(entity,"entity不得為null");
		getSession().delete(entity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> createSQLQuery(String sql) throws Exception{
		List<Object[]> queryList = getSession().createNativeQuery(sql).list();
		return queryList;
	}
	
	/**
	 * query list
	 * @param id
	 * @throws Exception
	 */
	public List<T> findList(T entity) throws Exception{
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> root = query.from(entityClass);
		query.select(root);
		Query<T> q = getSession().createQuery(query);
		List<T> list = q.getResultList();
		return list;		
	}
	
}
