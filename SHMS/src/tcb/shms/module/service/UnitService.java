package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.UnitDao;
import tcb.shms.module.entity.Unit;


/**
 * @author Mark Huang
 * @date 2020/3/23
 **/
@Transactional
@Service
public class UnitService extends GenericService<Unit>{

	@Autowired
	UnitDao unitDao;
	
	@Override
	protected UnitDao getDao() {
		return unitDao;
	}

	
}
