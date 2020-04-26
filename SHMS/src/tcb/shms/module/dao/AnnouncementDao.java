package tcb.shms.module.dao;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import tcb.shms.core.dao.GenericHibernateDao;
import tcb.shms.module.entity.Announcement;

@Repository
public class AnnouncementDao extends GenericHibernateDao<Announcement>{
	
	/**
	 * query list
	 * 目前只支援 ENTITY 裡面 STRING INT LONG型態where條件幫找
	 * 
	 * @param id
	 * @throws Exception
	 */
	public List<Announcement> getAnnouncementListBeforeToday(Announcement entity) throws Exception {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Announcement> query = builder.createQuery(Announcement.class);
		Root<Announcement> root = query.from(Announcement.class);
		query.select(root);		
		//用反射取entity值塞WHERE條件
		Class<?> c = Announcement.class;
		Field[] fields = c.getDeclaredFields();
		List<Predicate> predicateList = new ArrayList<Predicate>();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if(field.getName().toString().equals("serialVersionUID")) {
					continue;
				}
				if(ObjectUtils.isNotEmpty(field.get(entity))) {
					if (field.getType().getCanonicalName().equals("java.lang.String")) {
						predicateList.add(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					} else if (field.getType().getCanonicalName().equals("java.lang.Integer") || field.getType().getCanonicalName().equals("int")) {
						predicateList.add(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					} else if (field.getType().getCanonicalName().equals("java.lang.Long") || field.getType().getCanonicalName().equals("long")) {
						predicateList.add(builder.equal(root.get(field.getName().toString()), field.get(entity)));
					}else if (field.getType().getCanonicalName().equals("java.util.Date")) {
						predicateList.add(builder.lessThanOrEqualTo(root.<Date>get(field.getName().toString()), (Date)field.get(entity)));
					}else if (field.getType().getCanonicalName().equals("java.sql.Time")) {
						predicateList.add(builder.lessThanOrEqualTo(root.<Time>get(field.getName().toString()), (Time)field.get(entity)));
					}
				}
				
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
		query.where(predicateList.toArray(new Predicate[predicateList.size()]));
		Query<Announcement> q = getSession().createQuery(query);
		List<Announcement> list = q.getResultList();
		return list;
	}
	
}
