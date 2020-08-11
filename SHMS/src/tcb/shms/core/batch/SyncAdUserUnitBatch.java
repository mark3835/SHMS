package tcb.shms.core.batch;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tcb.shms.core.service.LdapService;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.BatchLog;
import tcb.shms.module.entity.Config;
import tcb.shms.module.service.BatchLogService;
import tcb.shms.module.service.ConfigService;
import tcb.shms.module.service.ErrorLogService;
import tcb.shms.module.service.LoginLogService;

/**
 * 同步AD人員單位批次
 * @author mark
 * create date 2020/08/11
 *
 */
@Component
public class SyncAdUserUnitBatch {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	ErrorLogService  errorLogService;
	
	@Autowired
	BatchLogService batchLogService;
	
	@Autowired
	LdapService ldapService;
	
	@Autowired
	ConfigService configService;
	
	public void runSync() {
		String result = "";
		Date beginDate = new Date();
		try {
        	Config cfg = new Config();
        	cfg.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
        	cfg.setCfgKey(SystemConfig.CFG_KEY.LDAP_ACC_KEY);
			List<Config> cfgList = configService.getList(cfg);
			String ldapAccKey = cfgList.get(0).getCfgValue();
			cfg.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
			cfg.setCfgKey(SystemConfig.CFG_KEY.LDAP_PSD_KEY);
			cfgList = configService.getList(cfg);
			String ldapPsdKey = cfgList.get(0).getCfgValue();
			
        	ldapService.getADUserAndUnitToDb(ldapAccKey, ldapPsdKey);        	
        	result = "success";
        	
        	BatchLog batchLog = new BatchLog();
        	batchLog.setBatchBeginTime(beginDate);
			
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}
	}

	public void hi() {
		System.out.println("hi~~~~~~~~~~~~~~~~~~~~~~");
	}
}
