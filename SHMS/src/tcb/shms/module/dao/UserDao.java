package tcb.shms.module.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import tcb.shms.core.dao.GenericHibernateDao;
import tcb.shms.module.entity.User;

@Repository
public class UserDao extends GenericHibernateDao<User>{

	
	public User findByRocId(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不得為空");
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root);
		cr.where(cb.equal(root.get("rocId"), rocId));
		Query<User> query = getSession().createQuery(cr);
		List<User> results = query.getResultList();
		
		return results.get(0);
	}
	
	public User findByAccount(String account) throws Exception {
		Assert.notNull(account, "account不得為空");
		
		CriteriaBuilder cb = getSession().getCriteriaBuilder();
		CriteriaQuery<User> cr = cb.createQuery(User.class);
		Root<User> root = cr.from(User.class);
		cr.select(root);
		cr.where(cb.equal(root.get("account"), account));
		Query<User> query = getSession().createQuery(cr);
		
		List<User> results = query.getResultList();
		
		return results.get(0);
	}
	

}
