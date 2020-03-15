package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.MenuDao;
import tcb.shms.module.entity.Menu;


/**
 * @author Mark Huang
 * @date 2020/3/13
 **/
@Transactional
@Service
public class MenuService extends GenericService<Menu>{

	@Autowired
	MenuDao menuDao;
	
	@Override
	protected MenuDao getDao() {
		return menuDao;
	}


}
