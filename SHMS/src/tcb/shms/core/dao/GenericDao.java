package tcb.shms.core.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tcb.shms.core.entity.GenericEntity;

/**
 * GenericDao
 * @author Mark Huang
 * @version 2020/3/3
 */
public abstract class GenericDao<T extends GenericEntity> implements Dao<T> {
	
	protected final transient Logger log = LogManager.getLogger(getClass());

}