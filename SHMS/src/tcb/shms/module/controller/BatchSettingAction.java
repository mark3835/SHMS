package tcb.shms.module.controller;

import java.sql.Timestamp;
import java.util.Date;
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
import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.BatchSetting;
import tcb.shms.module.service.BatchSettingService;

/**
 * @author MARK3835
 *
 */
@Controller
public class BatchSettingAction extends GenericAction<BatchSetting> {

	@Autowired
	BatchSettingService batchSettingService;
	
	@RequestMapping(value = "/batchSetting/api/getBatchSetting", method = RequestMethod.GET)
	public @ResponseBody String getBatchSetting() {
		String jsonInString = null;
		try {
			List<BatchSetting> batchSettingList = batchSettingService.getList(new BatchSetting());
			jsonInString = new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE_STRING).create().toJson(batchSettingList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	    
	@RequestMapping(value = "/batchSetting/api/addBatchSetting", method = RequestMethod.POST)
	public @ResponseBody String addBatchSetting(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			BatchSetting batchSetting = new BatchSetting();
			batchSetting.setBatchClass(MapUtils.getString(map, "batchClass"));
			batchSetting.setBatchName(MapUtils.getString(map, "batchName"));
			int batchType = MapUtils.getIntValue(map, "batchType");
			batchSetting.setBatchType(batchType);
			switch(batchType) { 
				case 1 :
					String batchType1Date = MapUtils.getString(map, "batchType1Date");
					Date batchType1DateDate = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.parse(batchType1Date);
					batchSetting.setBatchType1Date(new Timestamp(batchType1DateDate.getTime()));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;
				case 2 :
					batchSetting.setBatchType2EveryMin(MapUtils.getIntValue(map, "batchType2EveryMin"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;
				case 3 :
					batchSetting.setBatchType3EveryHour(MapUtils.getIntValue(map, "batchType3EveryHour"));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					
					break;
				case 4 :
					batchSetting.setBatchType4EveryWeek(MapUtils.getIntValue(map, "batchType4EveryWeek"));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;
				case 5 :
					batchSetting.setBatchType5EveryMonth(MapUtils.getIntValue(map, "batchType5EveryMonth"));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;			
			};
			batchSetting = batchSettingService.save(batchSetting);
			resultMap.put("result", "success");
			resultMap.put("id", batchSetting.getId().toString());					
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/batchSetting/api/updateBatchSetting", method = RequestMethod.POST)
	public @ResponseBody String updateBatchSetting(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			BatchSetting batchSetting = batchSettingService.getById(MapUtils.getLong(map, "id"));
			batchSetting.setBatchClass(MapUtils.getString(map, "batchClass"));
			batchSetting.setBatchName(MapUtils.getString(map, "batchName"));
			int batchType = MapUtils.getIntValue(map, "batchType");
			batchSetting.setBatchType(batchType);
			switch(batchType) { 
				case 1 :
					String batchType1Date = MapUtils.getString(map, "batchType1Date");
					Date batchType1DateDate = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.parse(batchType1Date);
					batchSetting.setBatchType1Date(new Timestamp(batchType1DateDate.getTime()));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;
				case 2 :
					batchSetting.setBatchType2EveryMin(MapUtils.getIntValue(map, "batchType2EveryMin"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;
				case 3 :
					batchSetting.setBatchType3EveryHour(MapUtils.getIntValue(map, "batchType3EveryHour"));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					
					break;
				case 4 :
					batchSetting.setBatchType4EveryWeek(MapUtils.getIntValue(map, "batchType4EveryWeek"));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;
				case 5 :
					batchSetting.setBatchType5EveryMonth(MapUtils.getIntValue(map, "batchType5EveryMonth"));
					batchSetting.setBatchHour(MapUtils.getIntValue(map, "batchHour"));
					batchSetting.setBatchMinute(MapUtils.getIntValue(map, "batchMinute"));
					break;			
			};
			batchSettingService.update(batchSetting);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/batchSetting/api/deleteBatchSetting", method = RequestMethod.POST)
	public @ResponseBody String deleteBatchSetting(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			batchSettingService.deleteById(MapUtils.getLong(map, "ID"));
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
