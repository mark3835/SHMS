package tcb.shms.module.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.entity.Announcement;
import tcb.shms.module.entity.Menu;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.AnnouncementService;
import tcb.shms.module.service.ErrorLogService;

/**
 * @author MARK3835
 *
 */
@Controller
public class AnnouncementAction extends GenericAction<Menu> {

	@Autowired
	AnnouncementService announcementService;

	@RequestMapping(value = "/announcement/getAnnouncement", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/announcement/api/addAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String addAnnouncement(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Announcement announcement = new Announcement();
			announcement.setAnnouncementDate(new DateUtils().parseDate((MapUtils.getString(map, "announcementDate")));
			announcement.setAnnouncementName(MapUtils.getString(map, "announcementName"));
			user.setAccount(MapUtils.getString(map, "account"));
			user.setUnitId(MapUtils.getString(map, "unitId"));
			user.setJobName(MapUtils.getString(map, "jobName"));
			user.setJobLevel(MapUtils.getInteger(map, "JobLevel"));
			user = announcementService.save(user);
			resultMap.put("result", "success");
			resultMap.put("id", user.getId().toString());					
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");		
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/announcement/api/updateAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String updateAnnouncement(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			User user = announcementService.getById(MapUtils.getLong(map, "id"));
			user.setRocId(MapUtils.getString(map, "rocId"));
			user.setName(MapUtils.getString(map, "name"));
			user.setAccount(MapUtils.getString(map, "account"));
			user.setUnitId(MapUtils.getString(map, "unitId"));
			user.setJobName(MapUtils.getString(map, "jobName"));
			user.setJobLevel(MapUtils.getInteger(map, "jobLevel"));
			user.setPhone(MapUtils.getString(map, "phone"));
			user.setEmail(MapUtils.getString(map, "email"));
			user.setBirthday(MapUtils.getString(map, "birthday"));
			userService.update(user);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");	
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/announcement/api/deleteAnnouncement", method = RequestMethod.POST)
	public @ResponseBody String deleteAnnouncement(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			announcementService.deleteById(MapUtils.getLong(map, "ID"));
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");	
		}

		return new Gson().toJson(resultMap);
	}

}
