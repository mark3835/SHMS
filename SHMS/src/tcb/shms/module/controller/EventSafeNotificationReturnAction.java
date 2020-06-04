package tcb.shms.module.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.h2.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.core.service.ExcelService;
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
	
	@Autowired
	ExcelService excelService;

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
			//取得影響類別選項
			Config eventEffectType = new Config();
			eventEffectType.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
			eventEffectType.setCfgType(SystemConfig.CFG_TYPE.EVENT_EFFECT_TYPE);
			List<Config> eventEffectTypeList = configService.getList(eventEffectType);
			result.put("eventEffectTypeList", eventEffectTypeList);
			
//			EventSafeNotificationReturn eventSafeNotificationReturn = new EventSafeNotificationReturn();
//			eventSafeNotificationReturn.setUnitId(loginUser.getUnitId());
//			eventSafeNotificationReturn.setEventId(eventId);
//			List<EventSafeNotificationReturn> eventSafeNotificationReturnList = eventSafeNotificationReturnService.getList(eventSafeNotificationReturn);			
//			if(eventSafeNotificationReturnList != null && eventSafeNotificationReturnList.size() > 0) {
//				eventSafeNotificationReturn = eventSafeNotificationReturnList.get(0);
//				if(eventSafeNotificationReturn.getReviewId() != null) {
//					User reviewer = userService.getByRocid(eventSafeNotificationReturn.getReviewId());
//					result.put("reviewer", reviewer);
//				}
//				if(eventSafeNotificationReturn.getCreateId() != null) {
//					User creater = userService.getByRocid(eventSafeNotificationReturn.getCreateId());
//					result.put("creater", creater);
//				}
//				result.put("eventSafeNotificationReturn", eventSafeNotificationReturn);
//			}
			
			jsonInString = new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE_STRING).create().toJson(result);
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
			User loginUser = getSessionUser();
			EventSafeNotificationReturn eventSafeNotificationReturn = new EventSafeNotificationReturn();
			eventSafeNotificationReturn.setUnitId(loginUser.getUnitId());
			eventSafeNotificationReturn.setEventId(MapUtils.getLong(map, "eventId"));	
			eventSafeNotificationReturn.setEffectType(MapUtils.getString(map, "effectType"));
			eventSafeNotificationReturn.setIsSafe(MapUtils.getInteger(map, "isSafe"));
			eventSafeNotificationReturn.setMemo(MapUtils.getString(map, "memo"));
			eventSafeNotificationReturn.setEventId(MapUtils.getLong(map, "eventId"));			
			eventSafeNotificationReturn.setUnitId(loginUser.getUnitId());
			eventSafeNotificationReturn.setReturnTime(new Timestamp(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.parse(MapUtils.getString(map, "returnTime")).getTime()));
			eventSafeNotificationReturn.setCreateId(loginUser.getRocId());
			eventSafeNotificationReturn.setCreateTime(new Timestamp(System.currentTimeMillis()));
			eventSafeNotificationReturn.setEditId(loginUser.getRocId());
			eventSafeNotificationReturn.setEditTime(new Timestamp(System.currentTimeMillis()));
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
	
	@RequestMapping(value = "/eventSafeNotificationReview/api/getReviewData", method = RequestMethod.GET)
	public @ResponseBody String getReviewData() {
		Map result = new HashMap();
		try {
			User loginUser = this.getSessionUser();
			
			//取得待審未核證照			
			if(getReviewAuth()) {
				List<EventSafeNotificationReturn> eventSafeNotificationReturnReviewerList = eventSafeNotificationReturnService.getNotReviewReviewerByRocId(loginUser.getRocId());
				result.put("eventSafeNotificationReturnReviewerList", eventSafeNotificationReturnReviewerList);				
			}else {
				result.put("eventSafeNotificationReturnReviewerList", null);
			}
			
			//取得送出待審證照
			List<EventSafeNotificationReturn> eventSafeNotificationReturnCreateIdList = eventSafeNotificationReturnService.getNotReviewCreteIdByRocId(loginUser.getRocId());
			result.put("eventSafeNotificationReturnCreateIdList", eventSafeNotificationReturnCreateIdList);
			
			//取得單位人員資料 為了顯示名字 證照資料只存rocid
			User queryUser = new User();
			queryUser.setIsLeave(SystemConfig.USER.IS_LEAVE_FALSE);
			queryUser.setUnitId(loginUser.getUnitId());
			List<User> userList = userService.getList(queryUser);
			result.put("userList", userList);
			
			//取得平安通報名稱
			List<EventSafeNotification> eventSafeNotificationList = eventSafeNotificationService.getList(new EventSafeNotification());
			result.put("eventSafeNotificationList", eventSafeNotificationList);
			
			//取得單位名稱
			Unit unit = unitService.getByUnitId(loginUser.getUnitId());
			result.put("unit", unit);
			
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE_STRING).create().toJson(result);
	}
	
	//單位最高主管或襄理
	private boolean getReviewAuth() throws Exception {
		User loginUser = this.getSessionUser();
		List<Integer> authList = authorizastionService.getAuthByUser(loginUser);
		if(authList.contains(SystemConfig.AUTH_LV.MANAGER) || authList.contains(SystemConfig.AUTH_LV.JUNIOR_MANAGER)) {
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/eventSafeNotificationReview/api/getReviewDataCount", method = RequestMethod.GET)
	public @ResponseBody String getReviewDataCount() {
		Map result = new HashMap();
		try {
			User loginUser = this.getSessionUser();
			
			//取得待審未核證照
			if(getReviewAuth()) {
				List<EventSafeNotificationReturn> eventSafeNotificationReturnReviewerList = eventSafeNotificationReturnService.getNotReviewReviewerByRocId(loginUser.getRocId());
				result.put("eventSafeNotificationReturnReviewerListCount", eventSafeNotificationReturnReviewerList.size());
				
			}else {
				result.put("eventSafeNotificationReturnReviewerListCount", 0);
			}
			
			//取得送出待審證照
			List<EventSafeNotificationReturn> eventSafeNotificationReturnCreateIdList = eventSafeNotificationReturnService.getNotReviewCreteIdByRocId(loginUser.getRocId());
			result.put("eventSafeNotificationReturnCreateIdListCount", eventSafeNotificationReturnCreateIdList.size());
			
			
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return new Gson().toJson(result);
	}
	
	@RequestMapping(value = "/eventSafeNotificationReview/api/submitReview", method = RequestMethod.POST)
	public @ResponseBody String submitReview(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> requestMap = new Gson().fromJson(data, HashMap.class);	
			User loginUser = this.getSessionUser();
			
			Map dataMap = new HashMap();
			Map whereMap = new HashMap();			
			dataMap.put("reviewTime", new Timestamp(System.currentTimeMillis()));
			dataMap.put("reviewId", loginUser.getRocId());
			whereMap.put("id", MapUtils.getLong(requestMap, "ID"));			
			eventSafeNotificationReturnService.updateWithColumn(dataMap, whereMap);
			
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
		
	@RequestMapping(value = "/eventSafeNotificationReview/api/cancelReview", method = RequestMethod.POST)
	public @ResponseBody String cancelReview(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> requestMap = new Gson().fromJson(data, HashMap.class);	
			eventSafeNotificationReturnService.deleteById(MapUtils.getLong(requestMap, "ID"));
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}

	@RequestMapping(value = "/eventSafeNotificationReturnData/api/getReturnData", method = RequestMethod.GET)
	public @ResponseBody String getReturnData() {
		Map result = new HashMap();
		try {
			User loginUser = this.getSessionUser();
			
			List<EventSafeNotificationReturn> eventSafeNotificationReturnList = eventSafeNotificationReturnService.getList(new EventSafeNotificationReturn());
			result.put("eventSafeNotificationReturnList", eventSafeNotificationReturnList);
			
			//取得單位人員資料 為了顯示名字 證照資料只存rocid
			User queryUser = new User();
			queryUser.setIsLeave(SystemConfig.USER.IS_LEAVE_FALSE);
			queryUser.setUnitId(loginUser.getUnitId());
			List<User> userList = userService.getList(queryUser);
			result.put("userList", userList);
			
			//取得平安通報名稱
			List<EventSafeNotification> eventSafeNotificationList = eventSafeNotificationService.getList(new EventSafeNotification());
			result.put("eventSafeNotificationList", eventSafeNotificationList);
			
			//取得所有單位名稱
			List<Unit> unitList = unitService.getList(new Unit());
			result.put("unitList", unitList);
			
			//取得影響類別選項
			Config eventEffectType = new Config();
			eventEffectType.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
			eventEffectType.setCfgType(SystemConfig.CFG_TYPE.EVENT_EFFECT_TYPE);
			List<Config> eventEffectTypeList = configService.getList(eventEffectType);
			result.put("eventEffectTypeList", eventEffectTypeList);
			
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE_STRING).create().toJson(result);
	}
	
	
	@RequestMapping(value = "/eventSafeNotificationReturnData/api/searchReturnData", method = RequestMethod.POST)
	public @ResponseBody String searchReturnData(@RequestBody String data) {
		HashMap result = new HashMap();
		try {
			HashMap<String,Object> requestMap = new Gson().fromJson(data, HashMap.class);	
			
			EventSafeNotificationReturn eventSafeNotificationReturn = new EventSafeNotificationReturn();
			Long eventId = MapUtils.getLong(requestMap, "eventId");
			if(eventId != null) {
				eventSafeNotificationReturn.setEventId(eventId);
			}
			Integer isSafe = MapUtils.getInteger(requestMap, "isSafe");
			if(isSafe != null) {
				eventSafeNotificationReturn.setIsSafe(isSafe);
			}
			String effectType = MapUtils.getString(requestMap, "effectType");
			if(StringUtils.isNotBlank(effectType)) {
				eventSafeNotificationReturn.setEffectType(effectType);
			}
			String unitId = MapUtils.getString(requestMap, "unitId");
			if(StringUtils.isNotBlank(unitId)) {
				eventSafeNotificationReturn.setUnitId(unitId);
			}
			String createId = MapUtils.getString(requestMap, "createId");
			if(StringUtils.isNotBlank(createId)) {
				eventSafeNotificationReturn.setCreateId(createId);
			}
			String isReview = MapUtils.getString(requestMap, "isReview");
			if(StringUtils.isNotBlank(isReview)) {
				eventSafeNotificationReturn.setReviewId(isReview);
			}
			List<EventSafeNotificationReturn> eventSafeNotificationReturnList = eventSafeNotificationReturnService.getList(eventSafeNotificationReturn);
			result.put("eventSafeNotificationReturnList", eventSafeNotificationReturnList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			result.put("result", "error");
			result.put("errorMsg", e.getMessage());
		}

		return new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE_STRING).create().toJson(result);
	}
	
	@RequestMapping(value = "/eventSafeNotificationReturnData/api/downloadExcel", method = RequestMethod.POST)
	public void downloadExcel(@RequestParam Map<String, String> data, HttpServletResponse response, HttpServletRequest request) throws Exception {
	    try {
	    	User loginUser = this.getSessionUser();
	    	
	    	String fileName = "平安通報回報資料查詢.xls";
			
			EventSafeNotificationReturn eventSafeNotificationReturn = new EventSafeNotificationReturn();
			Long eventId = MapUtils.getLong(data, "eventId");
			if(eventId != null) {
				eventSafeNotificationReturn.setEventId(eventId);
			}
			Integer isSafe = MapUtils.getInteger(data, "isSafe");
			if(isSafe != null) {
				eventSafeNotificationReturn.setIsSafe(isSafe);
			}	
			String effectType = MapUtils.getString(data, "effectType");
			if(StringUtils.isNotBlank(effectType)) {
				eventSafeNotificationReturn.setEffectType(effectType);
			}
			String unitId = MapUtils.getString(data, "unitId");
			if(StringUtils.isNotBlank(unitId)) {
				eventSafeNotificationReturn.setUnitId(unitId);
			}
			String createId = MapUtils.getString(data, "createId");
			if(StringUtils.isNotBlank(createId)) {
				eventSafeNotificationReturn.setCreateId(createId);
			}
			String isReview = MapUtils.getString(data, "isReview");
			if(StringUtils.isNotBlank(isReview)) {
				eventSafeNotificationReturn.setReviewId(isReview);
			}
			
			//取得單位人員資料 為了顯示名字 證照資料只存rocid
			User queryUser = new User();
			queryUser.setIsLeave(SystemConfig.USER.IS_LEAVE_FALSE);
			queryUser.setUnitId(loginUser.getUnitId());
			List<User> userList = userService.getList(queryUser);
			
			//取得平安通報名稱
			List<EventSafeNotification> eventSafeNotificationList = eventSafeNotificationService.getList(new EventSafeNotification());
			
			//取得所有單位名稱
			List<Unit> unitList = unitService.getList(new Unit());
			
			List<EventSafeNotificationReturn> eventSafeNotificationReturnList = eventSafeNotificationReturnService.getList(eventSafeNotificationReturn);
			List<String> titleList = new ArrayList<String>();			
			titleList.add("事件編號");
			titleList.add("事件名稱");
			titleList.add("單位名稱");
			titleList.add("開始通報時間");
			titleList.add("是否平安");
			titleList.add("影響類別");
			titleList.add("簡要說明重要案情");
			titleList.add("建立者");
			titleList.add("是否審核");
			List<List<String>> dataList = new ArrayList<List<String>>();
			for(EventSafeNotificationReturn item:eventSafeNotificationReturnList){
				List<String> list = new ArrayList<String>();
				String eventKey = "";
				String eventName = "";
				for(EventSafeNotification eventSafeNotification:eventSafeNotificationList) {
					if(eventSafeNotification.getId().equals(item.getEventId())) {		
						eventKey = eventSafeNotification.getEventKey();
						eventName = eventSafeNotification.getEventName();
						break;
					}
				}
				list.add(eventKey);
				list.add(eventName);
				String unitName = "";
				for(Unit unit:unitList) {
					if(unit.getUnitId().equals(item.getUnitId())) {
						unitName =  unit.getUnitName();
						break;
					}
				}
				list.add(unitName);
				list.add(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.format(item.getReturnTime()));
				if(SystemConfig.EVENT_SAFE_NOTIFICATION_RETURN.IS_SAFE_TRUE == item.getIsSafe()) {
					list.add("是");
				}else {
					list.add("否");
				}
				list.add(item.getEffectType());
				list.add(item.getMemo());
				String createName = "";
				for(User user:userList) {
					if(user.getRocId().equals(item.getCreateId())) {
						createName = user.getName();						
						break;
					}
				}
				list.add(createName);
				if(item.getReviewId() == null) {
					list.add("未審核");
				}else {
					list.add("已審核");
				}
				dataList.add(list);
			}
			
			File excelFile = excelService.createExcel("平安通報回報資料查詢", "平安通報回報資料查詢", titleList, dataList);

	        //檔名亂碼問題處理
	        // 获得请求头中的User-Agent
			String agent = request.getHeader("User-Agent");
			// 根据不同的客户端进行不同的编码
			String filenameEncoder = "";
			if (agent.contains("MSIE")) {
				// IE浏览器
				filenameEncoder = URLEncoder.encode(fileName, "utf-8");
				filenameEncoder = filenameEncoder.replace("+", " ");
			} else if (agent.contains("Firefox")) {
				// 火狐浏览器
				org.apache.tomcat.util.codec.binary.Base64 base64Encoder = new org.apache.tomcat.util.codec.binary.Base64();
				filenameEncoder = "=?utf-8?B?" + base64Encoder.encode(fileName.getBytes("utf-8")) + "?=";
			} else {
				// 其它浏览器
				filenameEncoder = URLEncoder.encode(fileName, "utf-8");
			}
			InputStream inputStream = new FileInputStream(excelFile);
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename=" + filenameEncoder); 
	        IOUtils.copy(inputStream, response.getOutputStream());
	        response.flushBuffer();
	        inputStream.close();
	    } catch (Exception e){
	        e.printStackTrace();
	    }
	}
	
	

}
