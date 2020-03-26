package tcb.shms.module.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.entity.Announcement;
import tcb.shms.module.entity.Menu;
import tcb.shms.module.service.AnnouncementService;
import tcb.shms.module.service.ErrorLogService;

/**
 * @author MARK3835
 *
 */
@Controller
public class UnitAction extends GenericAction<Menu> {

	@Autowired
	AnnouncementService announcementService;
	
	@Autowired
	ErrorLogService  errorLogService;

	@Autowired
	HttpServletRequest request;

	@RequestMapping(value = "/unit/getUnit", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			List<Announcement> announcementList = announcementService.getList(new Announcement());
			jsonInString = new Gson().toJson(announcementList);
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
