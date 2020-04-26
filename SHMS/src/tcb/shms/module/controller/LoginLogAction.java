package tcb.shms.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.entity.LoginLog;
import tcb.shms.module.service.LoginLogService;

/**
 * @author MARK3835
 *
 */
@Controller
public class LoginLogAction extends GenericAction<LoginLog> {

	@Autowired
	LoginLogService loginLogService;
	
	@RequestMapping(value = "/loginLog/api/getLoginLog", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			List<LoginLog> logingLogList = loginLogService.getList(new LoginLog());
			jsonInString = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").create().toJson(logingLogList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
