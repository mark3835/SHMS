package tcb.shms.module.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import tcb.shms.module.entity.Config;
import tcb.shms.module.service.ConfigService;
import tcb.shms.module.service.ErrorLogService;

/**
 * @author MARK3835
 *
 */
@Controller
public class ConfigAction extends GenericAction<Config> {

	@Autowired
	ConfigService configService;

	@Autowired
	ErrorLogService  errorLogService;

	@Autowired
	HttpServletRequest request;

	@RequestMapping(value = "/config/api/getConfig", method = RequestMethod.GET)
	public @ResponseBody String getConfig() {
		String jsonInString = null;
		try {
			Config config = new Config();
			List<Config> configList = configService.getList(config);
			jsonInString = new Gson().toJson(configList);
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/config/api/addConfig", method = RequestMethod.POST)
	public @ResponseBody String addConfig(@RequestBody String data) {
		String jsonInString = null;
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Config config = new Config();
			config.setCfgName(MapUtils.getString(map, "cfgName"));
			config.setCfgKey(MapUtils.getString(map, "cfgKey"));
			config.setCfgType(MapUtils.getString(map, "cfgType"));
			config.setCfgValue(MapUtils.getString(map, "cfgValue"));
			config.setCfgMemo(MapUtils.getString(map, "cfgMemo"));
			request.getSession().getAttribute(SystemConfig.SESSION_KEY.LOGIN);
			config.setCreateId(user);
			config.setCreateTime(new Timestamp(System.currentTimeMillis()));
			List<Config> configList = configService.getList(config);
			jsonInString = new Gson().toJson(configList);
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/config/api/editConfig", method = RequestMethod.POST)
	public @ResponseBody String editConfig(@RequestBody String data) {
		String jsonInString = null;
		try {
			Config config = new Config();
			List<Config> configList = configService.getList(config);
			jsonInString = new Gson().toJson(configList);
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/config/api/deleteConfig", method = RequestMethod.POST)
	public @ResponseBody String deleteConfig(@RequestBody String data) {
		String jsonInString = null;
		try {
			Config config = new Config();
			List<Config> configList = configService.getList(config);
			jsonInString = new Gson().toJson(configList);
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
