package tcb.shms.module.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.Config;
import tcb.shms.module.entity.EventSafeNotification;
import tcb.shms.module.entity.EventSafeNotificationReturn;
import tcb.shms.module.entity.Unit;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.AuthorizastionService;
import tcb.shms.module.service.ConfigService;
import tcb.shms.module.service.EventSafeNotificationReturnService;
import tcb.shms.module.service.EventSafeNotificationService;
import tcb.shms.module.service.UnitService;
import tcb.shms.module.service.UserService;

/**
 * @author MARK3835
 *
 */
@Controller
public class EventSafeNotificationReturnAction extends GenericAction<EventSafeNotificationReturn> {

	@Autowired
	EventSafeNotificationReturnService eventSafeNotificationReturnService;
	
	@Autowired
	EventSafeNotificationService eventSafeNotificationService;
	
	@Autowired
	UnitService unitService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthorizastionService authorizastionService;	
	
	@Autowired
	ConfigService configService;

	@RequestMapping(value = "/eventSafeNotification/api/getEventSafeNotificationReturn", method = RequestMethod.POST)
	public @ResponseBody String getEventSafeNotificationReturn(@RequestBody String data) {
		String jsonInString = null;
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Map result = new HashMap();
			Long eventId = MapUtils.getLong(map, "eventId");
			EventSafeNotification eventSafeNotification= eventSafeNotificationService.getById(eventId);
			result.put("eventSafeNotification", eventSafeNotification);
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
			if(StringUtils.isNotBlank(unit.getManager())) {
				User manager = userService.getByRocid(unit.getManager());
				result.put("manager", manager);
			}
			if(StringUtils.isNotBlank(unit.getSaveManager())) {
				User saveManager = userService.getByRocid(unit.getSaveManager());
				result.put("saveManager", saveManager);
			}
			if(StringUtils.isNotBlank(unit.getHelper())) {
				User helper = userService.getByRocid(unit.getHelper());
				result.put("helper", helper);
			}
			if(StringUtils.isNotBlank(unit.getFireHelper())) {
				User fireHelper = userService.getByRocid(unit.getFireHelper());
				result.put("fireHelper", fireHelper);
			}
			if(StringUtils.isNotBlank(unit.getAffairs())) {
				User affairs = userService.getByRocid(unit.getAffairs());
				result.put("affairs", affairs);
			}
			result.put("nowTime", SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.format(new Timestamp(System.currentTimeMillis())));
			//取得核發單位選項
			Config eventEffect = new Config();
			eventEffect.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
			eventEffect.setCfgType(SystemConfig.CFG_TYPE.EVENT_EFFECT_TYPE);
			List<Config> eventEffectList = configService.getList(eventEffect);
			result.put("eventEffectList", eventEffectList);
			
			EventSafeNotificationReturn eventSafeNotificationReturn = new EventSafeNotificationReturn();
			eventSafeNotificationReturn.setUnitId(loginUser.getUnitId());
			eventSafeNotificationReturn.setEventId(eventId);
			List<EventSafeNotificationReturn> eventSafeNotificationReturnList = eventSafeNotificationReturnService.getList(eventSafeNotificationReturn);			
			if(eventSafeNotificationReturnList != null && eventSafeNotificationReturnList.size() > 0) {
				result.put("eventSafeNotificationReturn", eventSafeNotificationReturnList.get(1));
			}
			
			jsonInString = new Gson().toJson(result);
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
			eventSafeNotificationReturn.setEffectType(MapUtils.getString(map, "effectType"));
			eventSafeNotificationReturn.setIsSafe(MapUtils.getInteger(map, "isSafe"));
			eventSafeNotificationReturn.setMemo(MapUtils.getString(map, "memo"));
			eventSafeNotificationReturn.setEventId(MapUtils.getLong(map, "eventId"));			
			eventSafeNotificationReturn.setUnitId(user.getUnitId());
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
