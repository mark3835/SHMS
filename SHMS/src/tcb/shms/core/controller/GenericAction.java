package tcb.shms.core.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import tcb.shms.core.entity.GenericEntity;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.ErrorLogService;

/**
 * GenericAction
 * @author Mark Huang
 * @version 2020/3/4
 */
public abstract class GenericAction<T extends GenericEntity> implements Action<T> {

	protected final transient Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	protected HttpServletRequest request;
	
	@Autowired
	protected ErrorLogService  errorLogService;
	
	public User getSessionUser() {
		return (User) request.getSession().getAttribute(SystemConfig.SESSION_KEY.LOGIN);	
	}
	
	
}
