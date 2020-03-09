package tcb.shms.core.controller;

import tcb.shms.core.entity.Entity;

/**
 * Action
 * @author Mark Huang
 * @version 2020/3/4
 */
public interface Action<T extends Entity> {

	public final static String INDEX = "index";

	public final static String SECTION = "section";
	
	public final static String EDIT = "edit";
	
	public final static String DELETE = "delete";
	
	public final static String LIST = "list";

}
