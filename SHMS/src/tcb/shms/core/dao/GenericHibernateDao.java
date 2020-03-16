package tcb.shms.core.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import tcb.shms.core.entity.GenericEntity;

/**
 * GenericHibernateDao
 * 
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
		if (t instanceof ParameterizedType) {
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
		Assert.notNull(entity, "不得為null");
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
		Assert.notNull(entity, "entity不得為null");
		getSession().update(entity);
	}

	@Override
	public void deleteById(Long id) throws Exception {
		Assert.notNull(id, "id不得為null");
		T t = findById(id);
		getSession().delete(t);
	}

	@Override
	public void delete(T entity) throws Exception {
		Assert.notNull(entity, "entity不得為null");
		getSession().delete(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> createSQLQuery(String sql) throws Exception {
		List<Object[]> queryList = getSession().createNativeQuery(sql).list();
		return queryList;
	}

	/**
	 * query list
	 * 目前只支援 ENTITY 裡面 STRING INT LONG型態where條件幫找
	 * 
	 * @param id
	 * @throws Exception
	 */
	public List<T> findList(T entity) throws Exception {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> root = query.from(entityClass);
		query.select(root);		
		//用反射塞WHERE條件
		Class<?> c = entityClass;
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if(field.getName().toString().equals("serialVersionUID")) {
					continue;
				}
				if(ObjectUtils.isNotEmpty(field.get(entity))) {
					if (field.getType().getCanonicalName().equals("java.lang.String")) {
						query.where(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					} else if (field.getType().getCanonicalName().equals("java.lang.Integer") || field.getType().getCanonicalName().equals("int")) {
						query.where(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					} else if (field.getType().getCanonicalName().equals("java.lang.Long") || field.getType().getCanonicalName().equals("long")) {
						query.where(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					}
				}
				
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		Query<T> q = getSession().createQuery(query);
		List<T> list = q.getResultList();
		return list;
	}

}
