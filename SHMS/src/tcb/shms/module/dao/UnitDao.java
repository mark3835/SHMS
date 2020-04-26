package tcb.shms.module.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import tcb.shms.core.dao.GenericHibernateDao;
import tcb.shms.module.entity.Unit;

@Repository
public class UnitDao extends GenericHibernateDao<Unit>{

	public Unit findByUnitId(String unitId) throws Exception {
		Assert.notNull(unitId, "unitId不得為空");
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Unit> cr = cb.createQuery(Unit.class);
		Root<Unit> root = cr.from(Unit.class);
		cr.select(root);
		cr.where(cb.equal(root.get("unitId"), unitId));
		Query<Unit> query = getSession().createQuery(cr);
		List<Unit> results = query.getResultList();
		
		return results.get(0);
	}
}
