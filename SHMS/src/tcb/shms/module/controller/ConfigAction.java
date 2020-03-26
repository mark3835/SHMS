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
import tcb.shms.module.entity.Config;
import tcb.shms.module.service.ConfigService;
import tcb.shms.module.service.ErrorLogService;

/**
 * @author MARK3835
 *
 */
@Controller
public class ConfigAction extends GenericAction<Config> {

	@Autowired
	ConfigService configService;

	@Autowired
	ErrorLogService  errorLogService;

	@Autowired
	HttpServletRequest request;

	@RequestMapping(value = "/config/api/getConfig", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			Config config = new Config();
			List<Config> configList = configService.getList(config);
			jsonInString = new Gson().toJson(configList);
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
