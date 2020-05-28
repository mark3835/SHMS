package tcb.shms.module.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.dao.CertificateDao;
import tcb.shms.module.entity.Certificate;
import tcb.shms.module.entity.Config;


/**
 * @author Mark Huang
 * @date 2020/3/23
 **/
@Transactional
@Service
public class CertificateService extends GenericService<Certificate>{

	@Autowired
	CertificateDao certificateDao;
	
	@Autowired
	ConfigService configService;
	
	@Override
	protected CertificateDao getDao() {
		return certificateDao;
	}
	
	public List<Certificate> getByRocIds(List<String> rocIds) throws Exception {
		Assert.notNull(rocIds, "rocIds不能為null");		
		List<Certificate> entitys = getDao().findByRocIds(rocIds);		
		return entitys;
	}

	/**
	 * 取得審核人為登入者的待簽核證照(審核時間為空)
	 * @param rocId
	 * @return
	 * @throws Exception
	 */
	public List<Certificate> getNotReviewReviewerByRocId(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不能為null");		
		List<Certificate> entitys = getDao().getNotReviewReviewerByRocId(rocId);		
		return entitys;
	}
	
	/**
	 * 取得建立者為登入者的待簽核證照(審核時間為空)
	 * @param rocId
	 * @return
	 * @throws Exception
	 */
	public List<Certificate> getNotReviewCreteIdByRocId(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不能為null");		
		List<Certificate> entitys = getDao().getNotReviewCreteIdByRocId(rocId);		
		return entitys;
	}
	
	static List<Config> certificateTypeList;
	
	/**
	 * 傳入證照名稱 判斷類別屬於三類哪一類
	 * @param certificateType
	 * @return
	 * @throws Exception 
	 */
	public String checkCertificateType(String certificateType) throws Exception {
		//先撈取所有證書種類
		Config certificateTypeQuery = new Config();
		certificateTypeQuery.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
		certificateTypeQuery.setCfgType(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_SAVEMANAGER);
		if(certificateTypeList == null) {
			certificateTypeList = configService.getList(certificateTypeQuery);
			certificateTypeQuery.setCfgType(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_FIREHELPER);
			certificateTypeList.addAll(configService.getList(certificateTypeQuery));
			certificateTypeQuery.setCfgType(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_HELPER);
			certificateTypeList.addAll(configService.getList(certificateTypeQuery));
		}		
		for(Config certificateTypeConfig:certificateTypeList) {
			//證照裡存的是證照類別中文名稱
			if(certificateTypeConfig.getCfgValue().equals(certificateType)) {						
				if(certificateTypeConfig.getCfgType().equals(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_SAVEMANAGER)) {
					return SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_SAVEMANAGER;
				}else if(certificateTypeConfig.getCfgType().equals(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_FIREHELPER)) {
					return SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_FIREHELPER;
				}else if(certificateTypeConfig.getCfgType().equals(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_HELPER)) {
					return SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_HELPER;
				}
			}
		}
		return null;		
	}
	
}
