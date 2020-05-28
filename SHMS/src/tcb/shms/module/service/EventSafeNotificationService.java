package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.EventSafeNotificationDao;
import tcb.shms.module.entity.EventSafeNotification;


/**
 * @author Mark Huang
 * @date 2020/5/27
 **/
@Transactional
@Service
public class EventSafeNotificationService extends GenericService<EventSafeNotification>{

	@Autowired
	EventSafeNotificationDao eventSafeNotificationDao;
	
	@Override
	protected EventSafeNotificationDao getDao() {
		return eventSafeNotificationDao;
	}

	
}
