package tcb.shms.core.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import tcb.shms.core.entity.GenericEntity;
import tcb.shms.module.config.SystemConfig;

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
	public void saveOrUpdate(T entity) throws Exception {
		Assert.notNull(entity, "不得為null");
		getSession().saveOrUpdate(entity);
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

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Override
	public List<Map> createSQLQuery(String sql) throws Exception {
		List<Map> queryList = getSession().createNativeQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		return queryList;
	}
	
	@Override
	public void executeSql(String sql) throws Exception {
		getSession().createNativeQuery(sql).executeUpdate();
	}

	/**
	 * query list
	 * 目前只支援 ENTITY 裡面 STRING INT LONG DATE TIME型態where 的等於條件幫找
	 * 
	 * @param id
	 * @throws Exception
	 */
	@Override
	public List<T> findList(T entity) throws Exception {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(entityClass);
		Root<T> root = query.from(entityClass);
		query.select(root);		
		//用反射取entity值塞WHERE條件
		Class<?> c = entityClass;
		Field[] fields = c.getDeclaredFields();
		List<Predicate> predicateList = new ArrayList<Predicate>();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if(field.getName().toString().equals("serialVersionUID")) {
					continue;
				}
				if(ObjectUtils.isNotEmpty(field.get(entity))) {
					if (field.getType().getCanonicalName().equals("java.lang.String")) {
						//如果值等於 not null 
						if( SystemConfig.COLUMN.NOT_NULL.equals(field.get(entity))) {
							predicateList.add(builder.isNotNull(root.get(field.getName().toString())));
						}else if( SystemConfig.COLUMN.NULL.equals(field.get(entity))) {
							predicateList.add(builder.isNull(root.get(field.getName().toString())));
						}else {
							predicateList.add(builder.equal(root.get(field.getName().toString()), field.get(entity)));
						}
					} else if (field.getType().getCanonicalName().equals("java.lang.Integer") || field.getType().getCanonicalName().equals("int")) {
						predicateList.add(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					} else if (field.getType().getCanonicalName().equals("java.lang.Long") || field.getType().getCanonicalName().equals("long")) {
						predicateList.add(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					}else if (field.getType().getCanonicalName().equals("java.util.Date")) {
						predicateList.add(builder.equal(root.<Date>get(field.getName().toString()), (Date)field.get(entity)));
					}else if (field.getType().getCanonicalName().equals("java.sql.Time")) {
						predicateList.add(builder.equal(root.<Time>get(field.getName().toString()), (Time)field.get(entity)));
					}
				}
				
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		query.where(predicateList.toArray(new Predicate[predicateList.size()]));
		Query<T> q = getSession().createQuery(query);
		List<T> list = q.getResultList();
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void updateWithColumn(Map dataMap, Map whereMap) throws Exception {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaUpdate<T> criteria = builder.createCriteriaUpdate(entityClass);
		Root<T> root = criteria.from(entityClass);
		for (Object key : dataMap.keySet()) {
		    criteria.set(root.get(key.toString()), dataMap.get(key));
		}
		for (Object key : whereMap.keySet()) {
		    criteria.where(builder.equal(root.get(key.toString()), whereMap.get(key)));
		}
		getSession().createQuery(criteria).executeUpdate();
	}
}
