package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.LoginLogDao;
import tcb.shms.module.entity.LoginLog;


/**
 * @author Mark Huang
 * @date 2020/3/23
 **/
@Transactional
@Service
public class LoginLogService extends GenericService<LoginLog>{

	@Autowired
	LoginLogDao loginLogDao;
	
	@Override
	protected LoginLogDao getDao() {
		return loginLogDao;
	}

	
}
