package tcb.shms.module.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.CertificateDao;
import tcb.shms.module.entity.Certificate;


/**
 * @author Mark Huang
 * @date 2020/3/23
 **/
@Transactional
@Service
public class CertificateService extends GenericService<Certificate>{

	@Autowired
	CertificateDao certificateDao;
	
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
	
}
