package tcb.shms.module.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import tcb.shms.core.dao.GenericHibernateDao;
import tcb.shms.module.entity.EventSafeNotificationReturn;

@Repository
public class EventSafeNotificationReturnDao extends GenericHibernateDao<EventSafeNotificationReturn>{
	
	public List<EventSafeNotificationReturn> getReviewReturnByEventId(Long eventId) throws Exception {
		Assert.notNull(eventId, "eventId不得為空");
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<EventSafeNotificationReturn> cr = cb.createQuery(EventSafeNotificationReturn.class);
		Root<EventSafeNotificationReturn> root = cr.from(EventSafeNotificationReturn.class);
		cr.select(root);
		cr.where(cb.equal(root.get("eventId"), eventId),cb.isNotNull(root.get("reviewTime")));
		Query<EventSafeNotificationReturn> query = getSession().createQuery(cr);
		List<EventSafeNotificationReturn> results = query.getResultList();
		
		return results;
	}

	public List<EventSafeNotificationReturn> getNotReviewReviewerByRocId(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不得為空");
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<EventSafeNotificationReturn> cr = cb.createQuery(EventSafeNotificationReturn.class);
		Root<EventSafeNotificationReturn> root = cr.from(EventSafeNotificationReturn.class);
		cr.select(root);
		cr.where(cb.notEqual(root.get("createId"), rocId),cb.isNull(root.get("reviewTime")));
		Query<EventSafeNotificationReturn> query = getSession().createQuery(cr);
		List<EventSafeNotificationReturn> results = query.getResultList();
		
		return results;
	}
	
	public List<EventSafeNotificationReturn> getNotReviewCreteIdByRocId(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不得為空");
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<EventSafeNotificationReturn> cr = cb.createQuery(EventSafeNotificationReturn.class);
		Root<EventSafeNotificationReturn> root = cr.from(EventSafeNotificationReturn.class);
		cr.select(root);
		cr.where(cb.equal(root.get("createId"), rocId), cb.isNull(root.get("reviewTime")));
		Query<EventSafeNotificationReturn> query = getSession().createQuery(cr);
		List<EventSafeNotificationReturn> results = query.getResultList();
		
		return results;
	}
}
