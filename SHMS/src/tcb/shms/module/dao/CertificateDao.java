package tcb.shms.module.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import tcb.shms.core.dao.GenericHibernateDao;
import tcb.shms.module.entity.Certificate;

@Repository
public class CertificateDao extends GenericHibernateDao<Certificate>{

	public List<Certificate> findByRocIds(List<String> rocIds) throws Exception {
		Assert.notNull(rocIds, "rocIds不得為空");
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<Certificate> cr = cb.createQuery(Certificate.class);
		Root<Certificate> root = cr.from(Certificate.class);
		cr.select(root);
		for(String rocId:rocIds) {
			cr.where(cb.equal(root.get("rocId"), rocId));
		}
		Query<Certificate> query = getSession().createQuery(cr);
		List<Certificate> results = query.getResultList();
		
		return results;
	}
}
