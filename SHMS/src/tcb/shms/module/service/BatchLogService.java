package tcb.shms.module.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.BatchLogDao;
import tcb.shms.module.entity.BatchLog;


/**
 * @author Mark Huang
 * @date 2020/8/10
 **/
@Transactional
@Service
public class BatchLogService extends GenericService<BatchLog>{

	@Autowired
	BatchLogDao batchLogDao;
	
	@Override
	protected BatchLogDao getDao() {
		return batchLogDao;
	}


}
