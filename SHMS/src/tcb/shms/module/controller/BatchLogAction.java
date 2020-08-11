package tcb.shms.module.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.Announcement;
import tcb.shms.module.entity.BatchLog;
import tcb.shms.module.entity.ErrorLog;
import tcb.shms.module.service.AnnouncementService;
import tcb.shms.module.service.BatchLogService;

/**
 * @author MARK3835
 *
 */
@Controller
public class BatchLogAction extends GenericAction<BatchLog> {
	
	@Autowired
	BatchLogService batchLogService;

	@RequestMapping(value = "/batchLog/api/getBatchLog", method = RequestMethod.GET)
	public @ResponseBody String getBatchLog() {
		String jsonInString = null;
		try {
			List<BatchLog> batchLogList = batchLogService.getList(new BatchLog());
			jsonInString = new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE_STRING).create().toJson(batchLogList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
