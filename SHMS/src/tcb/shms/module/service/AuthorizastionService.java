package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.AuthorizastionDao;
import tcb.shms.module.entity.Authorizastion;


/**
 * @author Mark Huang
 * @date 2020/3/13
 **/
@Transactional
@Service
public class AuthorizastionService extends GenericService<Authorizastion>{

	@Autowired
	AuthorizastionDao authorizastionDao;
	
	@Override
	protected AuthorizastionDao getDao() {
		return authorizastionDao;
	}


}
