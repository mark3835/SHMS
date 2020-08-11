package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.BatchSettingDao;
import tcb.shms.module.entity.BatchSetting;


/**
 * @author Mark Huang
 * @date 2020/8/10
 **/
@Transactional
@Service
public class BatchSettingService extends GenericService<BatchSetting>{

	@Autowired
	BatchSettingDao batchSettingDao;
	
	@Override
	protected BatchSettingDao getDao() {
		return batchSettingDao;
	}


}
