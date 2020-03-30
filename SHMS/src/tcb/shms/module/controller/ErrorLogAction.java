package tcb.shms.module.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.entity.ErrorLog;
import tcb.shms.module.service.ErrorLogService;

/**
 * @author MARK3835
 *
 */
@Controller
public class ErrorLogAction extends GenericAction<ErrorLog> {

	@Autowired
	ErrorLogService  errorLogService;

	@RequestMapping(value = "/errorLog/getErrorLog", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			List<ErrorLog> errorLogList = errorLogService.getList(new ErrorLog());
			jsonInString = new Gson().toJson(errorLogList);
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
