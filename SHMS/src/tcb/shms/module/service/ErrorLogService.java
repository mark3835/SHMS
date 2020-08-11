package tcb.shms.module.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.ErrorLogDao;
import tcb.shms.module.entity.ErrorLog;


/**
 * @author Mark Huang
 * @date 2020/3/23
 **/
@Transactional
@Service
public class ErrorLogService extends GenericService<ErrorLog>{

	@Autowired
	ErrorLogDao errorLogDao;
	
	@Override
	protected ErrorLogDao getDao() {
		return errorLogDao;
	}
	
	public void addErrorLog(String className, Exception e) {
		try {
			ErrorLog errorLog = new ErrorLog();
			errorLog.setErrorClass(className);
			errorLog.setErrorMsg(cretaeErrorMsg(className, e));
			errorLog.setErrorTime(new Timestamp(System.currentTimeMillis()));
			errorLogDao.save(errorLog);
		} catch (Exception e1) {
			log.error(e1);
		}
	}
	
	private String cretaeErrorMsg(String className, Exception e) {
		String result = "";
		int count = 0;
		for(StackTraceElement stackTrace:e.getStackTrace()) {
			if(count == 0) {
				result = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "." + 
						stackTrace.getLineNumber() + System.getProperty("line.separator"); 
				
			}else {			
				if(className.contains(stackTrace.getClassName())){
					result = result + stackTrace.getClassName() + "." + stackTrace.getMethodName() + "." + 
								stackTrace.getLineNumber() + System.getProperty("line.separator") + e.getMessage(); 	
				}
			}
			count++;
		}
		return result;
	}
	
	
}
