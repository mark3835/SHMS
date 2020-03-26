package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.ConfigDao;
import tcb.shms.module.entity.Config;


/**
 * @author Mark Huang
 * @date 2020/3/23
 **/
@Transactional
@Service
public class ConfigService extends GenericService<Config>{

	@Autowired
	ConfigDao configDao;
	
	@Override
	protected ConfigDao getDao() {
		return configDao;
	}

	
}
