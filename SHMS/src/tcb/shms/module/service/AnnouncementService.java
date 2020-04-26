package tcb.shms.module.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.dao.AnnouncementDao;
import tcb.shms.module.entity.Announcement;


/**
 * @author Mark Huang
 * @date 2020/3/13
 **/
@Transactional
@Service
public class AnnouncementService extends GenericService<Announcement>{

	@Autowired
	AnnouncementDao announcementDao;
	
	@Override
	protected AnnouncementDao getDao() {
		return announcementDao;
	}

	public List<Announcement> getAnnouncementListBeforeToday(Announcement announcement) throws Exception {
		return announcementDao.getAnnouncementListBeforeToday(announcement);
	}
}
