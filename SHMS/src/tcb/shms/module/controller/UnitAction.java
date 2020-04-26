package tcb.shms.module.controller;

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
import tcb.shms.module.entity.Unit;
import tcb.shms.module.service.UnitService;

/**
 * @author MARK3835
 *
 */
@Controller
public class UnitAction extends GenericAction<Unit> {

	@Autowired
	UnitService unitService;
	
	@RequestMapping(value = "/unit/api/getUnit", method = RequestMethod.GET)
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
	    
	@RequestMapping(value = "/unit/api/addUnit", method = RequestMethod.POST)
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
	
	@RequestMapping(value = "/unit/api/updateUnit", method = RequestMethod.POST)
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
			unit = unitService.save(unit);
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
	
	@RequestMapping(value = "/unit/api/deleteUnit", method = RequestMethod.POST)
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

}
