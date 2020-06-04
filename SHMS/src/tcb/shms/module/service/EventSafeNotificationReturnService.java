package tcb.shms.module.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
	
	/**
	 * 取得已審核的通報
	 * @param rocId
	 * @return
	 * @throws Exception
	 */
	public List<EventSafeNotificationReturn> getReviewReturnByEventId(Long eventId) throws Exception {
		Assert.notNull(eventId, "eventId不能為null");		
		List<EventSafeNotificationReturn> entitys = getDao().getReviewReturnByEventId(eventId);		
		return entitys;
	}

	/**
	 * 取得審核人為登入者的待簽核證照(審核時間為空)
	 * @param rocId
	 * @return
	 * @throws Exception
	 */
	public List<EventSafeNotificationReturn> getNotReviewReviewerByRocId(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不能為null");		
		List<EventSafeNotificationReturn> entitys = getDao().getNotReviewReviewerByRocId(rocId);		
		return entitys;
	}
	
	/**
	 * 取得建立者為登入者的待簽核證照(審核時間為空)
	 * @param rocId
	 * @return
	 * @throws Exception
	 */
	public List<EventSafeNotificationReturn> getNotReviewCreteIdByRocId(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不能為null");		
		List<EventSafeNotificationReturn> entitys = getDao().getNotReviewCreteIdByRocId(rocId);		
		return entitys;
	}
}
