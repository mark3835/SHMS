package tcb.shms.module.controller;

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
import tcb.shms.module.entity.Unit;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.UnitService;
import tcb.shms.module.service.UserService;

/**
 * @author MARK3835
 *
 */
@Controller
public class UnitAction extends GenericAction<Unit> {

	@Autowired
	UnitService unitService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/unitSetting/api/getUnit", method = RequestMethod.GET)
	public @ResponseBody String getUnit() {
		String jsonInString = null;
		try {
			List<Unit> unitList = unitService.getList(new Unit());
			jsonInString = new Gson().toJson(unitList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	    
	@RequestMapping(value = "/unitSetting/api/addUnit", method = RequestMethod.POST)
	public @ResponseBody String addUnit(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Unit unit = new Unit();
			unit.setUnitId(MapUtils.getString(map, "unitId"));
			unit.setUnitName(MapUtils.getString(map, "unitName"));
			unit.setManager(MapUtils.getString(map, "manager"));
			unit.setSaveManager(MapUtils.getString(map, "saveManager"));
			unit.setFireHelper(MapUtils.getString(map, "fireHelper"));
			unit.setHelper(MapUtils.getString(map, "helper"));
			unit.setAffairs(MapUtils.getString(map, "affairs"));
			unit.setTel(MapUtils.getString(map, "tel"));
			unit = unitService.save(unit);
			resultMap.put("result", "success");
			resultMap.put("id", unit.getId().toString());					
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/unitSetting/api/updateUnit", method = RequestMethod.POST)
	public @ResponseBody String updateUnit(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Unit unit = unitService.getById(MapUtils.getLong(map, "id"));
			unit.setUnitId(MapUtils.getString(map, "unitId"));
			unit.setUnitName(MapUtils.getString(map, "unitName"));
			unit.setManager(MapUtils.getString(map, "manager"));
			unit.setSaveManager(MapUtils.getString(map, "saveManager"));
			unit.setFireHelper(MapUtils.getString(map, "fireHelper"));
			unit.setHelper(MapUtils.getString(map, "helper"));
			unit.setAffairs(MapUtils.getString(map, "affairs"));
			unit.setTel(MapUtils.getString(map, "tel"));
			unitService.update(unit);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/unitSetting/api/deleteUnit", method = RequestMethod.POST)
	public @ResponseBody String deleteUnit(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			unitService.deleteById(MapUtils.getLong(map, "ID"));
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/unitData/api/getUnitByUser", method = RequestMethod.GET)
	public @ResponseBody String getUnitByUser() {
		String jsonInString = null;
		try {
			Map result = new HashMap();
			User loginUser = this.getSessionUser();
			Unit unit = unitService.getByUnitId(loginUser.getUnitId());
			result.put("unit", unit);
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
			jsonInString = new Gson().toJson(result);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/unitData/api/getUnitUserList", method = RequestMethod.GET)
	public @ResponseBody String getUnitUserList() {
		String jsonInString = null;
		try {
			User loginUser = this.getSessionUser();
			User selectUser = new User();
			selectUser.setUnitId(loginUser.getUnitId());
			selectUser.setIsLeave(SystemConfig.USER.IS_LEAVE_FALSE);
			List<User> userList= userService.getList(selectUser);		
			jsonInString = new Gson().toJson(userList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/unitData/api/saveData", method = RequestMethod.POST)
	public @ResponseBody String saveData(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> requestMap = new Gson().fromJson(data, HashMap.class);	
			User loginUser = this.getSessionUser();
			Unit unit = unitService.getByUnitId(loginUser.getUnitId());			
			unit.setAffairs(MapUtils.getString(requestMap, "affairs"));
			unitService.update(unit);
			
			Map dataMap = new HashMap();
			Map whereMap = new HashMap();			
			dataMap.put("phone", MapUtils.getString(requestMap, "affairsPhone"));
			dataMap.put("email", MapUtils.getString(requestMap, "affairsEmail"));
			whereMap.put("rocId", MapUtils.getString(requestMap, "affairs"));			
			userService.updateWithColumn(dataMap, whereMap);
			
			dataMap.clear();
			whereMap.clear();	
			dataMap.put("phone", MapUtils.getString(requestMap, "managerPhone"));
			dataMap.put("email", MapUtils.getString(requestMap, "managerEmail"));
			whereMap.put("rocId", unit.getManager());			
			userService.updateWithColumn(dataMap, whereMap);
			
			dataMap.clear();
			whereMap.clear();		
			dataMap.put("phone", MapUtils.getString(requestMap, "saveManagerPhone"));
			dataMap.put("email", MapUtils.getString(requestMap, "saveManagerEmail"));
			whereMap.put("rocId", unit.getSaveManager());			
			userService.updateWithColumn(dataMap, whereMap);
			
			dataMap.clear();
			whereMap.clear();	
			dataMap.put("phone", MapUtils.getString(requestMap, "fireHelperPhone"));
			dataMap.put("email", MapUtils.getString(requestMap, "fireHelperEmail"));
			whereMap.put("rocId", unit.getFireHelper());			
			userService.updateWithColumn(dataMap, whereMap);
			
			dataMap.clear();
			whereMap.clear();		
			dataMap.put("phone", MapUtils.getString(requestMap, "helperPhone"));
			dataMap.put("email", MapUtils.getString(requestMap, "helperEmail"));
			whereMap.put("rocId", unit.getHelper());			
			userService.updateWithColumn(dataMap, whereMap);
			
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
