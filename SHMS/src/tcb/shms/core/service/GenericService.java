package tcb.shms.core.service;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tcb.shms.core.dao.GenericDao;
import tcb.shms.core.entity.GenericEntity;

/**
 * GenericService
 * @author Mark Huang
 * @version 2020/3/13
 */
@Transactional
public abstract class GenericService<T extends GenericEntity> implements Service<T> {

	protected final transient Logger log = LogManager.getLogger(getClass());
	
	protected abstract GenericDao<T> getDao();
		
	@Override
	public T save(T entity) throws Exception {
		Assert.notNull(entity,"entity不能為null");
				
		T dbEntity = getDao().save(entity);
		
		return dbEntity;
	}

	@Override
	public T getById(Long id) throws Exception {
		Assert.notNull(id, "id不能為null");
		
		T entity = getDao().findById(id);
		
		return entity;
	}

	@Override
	public T update(T entity) throws Exception {
		Assert.notNull(entity, "entity不能為null");
				
		T dbEntity = getDao().findById(entity.getId());
		
		BeanUtils.copyProperties(entity, dbEntity);
		
		getDao().update(dbEntity);
		
		return dbEntity;
	}
	

	@Override
	public void deleteById(Long id) throws Exception {
		Assert.notNull(id, "id不能為null");		
		getDao().deleteById(id);
	}
	
	@Override
	public List<T> getList(T entity) throws Exception{
		return getDao().findList(entity);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getListBySQLQuery(String sql) throws Exception{
		return getDao().createSQLQuery(sql);
	}
	
}
