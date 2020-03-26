package tcb.shms.core.dao;

import java.util.List;
import java.util.Map;

import tcb.shms.core.entity.Entity;

/**
 * Dao
 * @author Mark Huang
 * @version 2020/3/3
 */
public interface Dao<T extends Entity> {
	
	/**
	 * Find entity by the primary key
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T findById(Long id) throws Exception;
	
	/**
	 * Save the entity to database
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public T save(T entity) throws Exception;
	
	/**
	 * Update the entity from database
	 * @param entity
	 * @throws Exception
	 */
	public void update(T entity) throws Exception;
	
	/**
	 * Delete the entity from database
	 * @param entity
	 * @throws Exception
	 */
	public void delete(T entity) throws Exception;
	
	/**
	 * Delete the entity by primary id
	 * @param id
	 * @throws Exception
	 */
	public void deleteById(Long id) throws Exception;
	
	/**
	 * query BY SQL
	 * @param id
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> createSQLQuery(String sql) throws Exception;
	
	
	/**
	 * query list
	 * @param id
	 * @throws Exception
	 */
	public List<T> findList(T entity) throws Exception;
	

}
