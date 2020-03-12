package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.UserDao;
import tcb.shms.module.entity.User;


/**
 * @author Mark Huang
 * @date 2020/3/3
 **/
@Service
public class UserService extends GenericService<User>{

	@Autowired
	UserDao userDao;
	
	@Override
	protected UserDao getDao() {
		return userDao;
	}

	@Transactional
	public User getByRocid(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不能為null");
		
		User entity = getDao().findByRocId(rocId);
		
		return entity;
	}

}
