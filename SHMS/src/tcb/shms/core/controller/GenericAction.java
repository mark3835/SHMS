package tcb.shms.core.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tcb.shms.core.entity.GenericEntity;

/**
 * GenericAction
 * @author Mark Huang
 * @version 2020/3/4
 */
public abstract class GenericAction<T extends GenericEntity> implements Action<T> {

	protected final transient Logger log = LogManager.getLogger(getClass());
	
	
}
