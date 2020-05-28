package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.EventSafeNotificationReturnDao;
import tcb.shms.module.entity.EventSafeNotificationReturn;


/**
 * @author Mark Huang
 * @date 2020/5/27
 **/
@Transactional
@Service
public class EventSafeNotificationReturnService extends GenericService<EventSafeNotificationReturn>{

	@Autowired
	EventSafeNotificationReturnDao eventSafeNotificationReturnDao;
	
	@Override
	protected EventSafeNotificationReturnDao getDao() {
		return eventSafeNotificationReturnDao;
	}

	
}
