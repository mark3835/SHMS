package tcb.shms.module.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.entity.EventSafeNotificationReturn;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.EventSafeNotificationReturnService;

/**
 * @author MARK3835
 *
 */
@Controller
public class EventSafeNotificationReturnAction extends GenericAction<EventSafeNotificationReturn> {

	@Autowired
	EventSafeNotificationReturnService eventSafeNotificationReturnService;

	@RequestMapping(value = "/eventSafeNotificationReturn/api/getEventSafeNotificationReturn", method = RequestMethod.GET)
	public @ResponseBody String getEventSafeNotificationReturn() {
		String jsonInString = null;
		try {
			EventSafeNotificationReturn eventSafeNotificationReturn = new EventSafeNotificationReturn();
			List<EventSafeNotificationReturn> eventSafeNotificationReturnList = eventSafeNotificationReturnService.getList(eventSafeNotificationReturn);
			jsonInString = new Gson().toJson(eventSafeNotificationReturnList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/eventSafeNotification/api/addEventSafeNotificationReturn", method = RequestMethod.POST)
	public @ResponseBody String addEventSafeNotificationReturn(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			EventSafeNotificationReturn eventSafeNotificationReturn = new EventSafeNotificationReturn();
			User user = getSessionUser();
			eventSafeNotificationReturn.setCreateId(user.getRocId());
			eventSafeNotificationReturn.setCreateTime(new Timestamp(System.currentTimeMillis()));
			eventSafeNotificationReturn = eventSafeNotificationReturnService.save(eventSafeNotificationReturn);
			resultMap.put("result", "success");
			resultMap.put("id", eventSafeNotificationReturn.getId().toString());					
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");		
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/eventSafeNotification/api/updateEventSafeNotificationReturn", method = RequestMethod.POST)
	public @ResponseBody String updateEventSafeNotification(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			EventSafeNotificationReturn eventSafeNotificationReturn = eventSafeNotificationReturnService.getById(MapUtils.getLong(map, "id"));
			User user = getSessionUser();
			eventSafeNotificationReturn.setEditId(user.getRocId());
			eventSafeNotificationReturn.setEditTime(new Timestamp(System.currentTimeMillis()));
			eventSafeNotificationReturnService.update(eventSafeNotificationReturn);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/eventSafeNotification/api/deleteEventSafeNotificationReturn", method = RequestMethod.POST)
	public @ResponseBody String deleteEventSafeNotification(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			eventSafeNotificationReturnService.deleteById(MapUtils.getLong(map, "ID"));
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
