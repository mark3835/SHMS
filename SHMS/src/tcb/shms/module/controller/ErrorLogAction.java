package tcb.shms.module.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.ErrorLog;

/**
 * @author MARK3835
 *
 */
@Controller
public class ErrorLogAction extends GenericAction<ErrorLog> {

	@RequestMapping(value = "/errorLog/api/getErrorLog", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			List<ErrorLog> errorLogList = errorLogService.getList(new ErrorLog());
			jsonInString = new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE_STRING).create().toJson(errorLogList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
