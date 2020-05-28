package tcb.shms.module.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.EventSafeNotification;
import tcb.shms.module.entity.Unit;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.AuthorizastionService;
import tcb.shms.module.service.EventSafeNotificationService;
import tcb.shms.module.service.UnitService;

/**
 * @author MARK3835
 *
 */
@Controller
public class EventSafeNotificationAction extends GenericAction<EventSafeNotification> {

	@Autowired
	EventSafeNotificationService eventSafeNotificationService;
	
	@Autowired
	AuthorizastionService authorizastionService;
	
	@Autowired
	UnitService unitService;

	@RequestMapping(value = "/eventSafeNotification/api/getEventSafeNotification", method = RequestMethod.GET)
	public @ResponseBody String getEventSafeNotification() {
		String jsonInString = null;
		try {
			Map result = new HashMap();
			User loginUser = this.getSessionUser();
			result.put("loginUser", loginUser);
			Unit unit = unitService.getByUnitId(loginUser.getUnitId());
			result.put("unit", unit);
			//作業人員
			String workMan = "";
			List<Integer> authList = authorizastionService.getAuthByUser(loginUser);
			if(authList.contains(SystemConfig.AUTH_LV.AFFAIRS)) {
				workMan = "總務";
			}else if(authList.contains(SystemConfig.AUTH_LV.FIRE_HELPER)) {
				workMan = "防火管理人";
			}else if(authList.contains(SystemConfig.AUTH_LV.HELPER)) {
				workMan = "急救人員";
			}else if(authList.contains(SystemConfig.AUTH_LV.MANAGER)) {
				workMan = "單位主管";
			}else if(authList.contains(SystemConfig.AUTH_LV.JUNIOR_MANAGER)) {
				workMan = "襄理";
			}
			result.put("workMan", workMan);
			EventSafeNotification eventSafeNotification = new EventSafeNotification();
			List<EventSafeNotification> eventSafeNotificationList = eventSafeNotificationService.getList(eventSafeNotification);
			result.put("eventSafeNotificationList", eventSafeNotificationList);
			jsonInString = new Gson().toJson(result);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/eventSafeNotificationSetting/api/getEventKey", method = RequestMethod.GET)
	public @ResponseBody String getEventKey() {
		String eventKey = null;
		try {			
			SimpleDateFormat timeFormat = new SimpleDateFormat("MMdd");
			String monthDate = timeFormat.format(new Date());
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			int year = c.get(Calendar.YEAR)-1911;
			eventKey = Integer.toString(year) + monthDate;
			List<Map> eventSafeNotificationList = eventSafeNotificationService.getListBySQLQuery(" select * from  EVENT_SAFE_NOTIFICATION where EVENT_KEY LIKE '%" + eventKey + "%' ");
			int count = eventSafeNotificationList.size() + 1;
			eventKey = eventKey + String.format("%02d", count);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return eventKey;
	}
	
	@RequestMapping(value = "/eventSafeNotificationSetting/api/addEventSafeNotification", method = RequestMethod.POST)
	public @ResponseBody String addEventSafeNotification(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			User user = getSessionUser();
			EventSafeNotification eventSafeNotification = new EventSafeNotification();
			eventSafeNotification.setEventKey(MapUtils.getString(map, "eventKey"));		
			List<EventSafeNotification> eventSafeNotificationList = eventSafeNotificationService.getList(eventSafeNotification);
			if(eventSafeNotificationList != null && eventSafeNotificationList.size() > 0) {
				throw new Exception("eventKey重複");
			}
			eventSafeNotification.setEventName(MapUtils.getString(map, "eventName"));
			eventSafeNotification.setCreateId(user.getRocId());
			eventSafeNotification.setCreateTime(new Timestamp(System.currentTimeMillis()));
			eventSafeNotification = eventSafeNotificationService.save(eventSafeNotification);
			resultMap.put("result", "success");
			resultMap.put("id", eventSafeNotification.getId().toString());					
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");		
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/eventSafeNotificationSetting/api/updateEventSafeNotification", method = RequestMethod.POST)
	public @ResponseBody String updateEventSafeNotification(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			EventSafeNotification eventSafeNotification = eventSafeNotificationService.getById(MapUtils.getLong(map, "id"));
			User user = getSessionUser();
			eventSafeNotification.setEventKey(MapUtils.getString(map, "eventKey"));
			eventSafeNotification.setEventName(MapUtils.getString(map, "eventName"));
			eventSafeNotification.setEditId(user.getRocId());
			eventSafeNotification.setEditTime(new Timestamp(System.currentTimeMillis()));
			eventSafeNotificationService.update(eventSafeNotification);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/eventSafeNotificationSetting/api/deleteEventSafeNotification", method = RequestMethod.POST)
	public @ResponseBody String deleteEventSafeNotification(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			eventSafeNotificationService.deleteById(MapUtils.getLong(map, "ID"));
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");	
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}

}
