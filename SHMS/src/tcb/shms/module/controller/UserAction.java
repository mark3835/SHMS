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
import tcb.shms.module.entity.User;
import tcb.shms.module.service.UserService;

/**
 *  @author MARK3835
 *
 */
@Controller
public class UserAction extends GenericAction<User>{
	
	@Autowired
	UserService userService;
				
	@RequestMapping(value = "/user/api/getUser", method = RequestMethod.GET)
	public @ResponseBody String getUser() {
		String jsonInString = null;
		try {
			List<User> userList = userService.getList(new User());
			jsonInString = new Gson().toJson(userList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
    
	@RequestMapping(value = "/user/api/addUser", method = RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			User user = new User();
			user.setRocId(MapUtils.getString(map, "rocId"));
			user.setName(MapUtils.getString(map, "name"));
			user.setAccount(MapUtils.getString(map, "account"));
			user.setUnitId(MapUtils.getString(map, "unitId"));
			user.setJobName(MapUtils.getString(map, "jobName"));
			user.setJobLevel(MapUtils.getInteger(map, "jobLevel"));
			user.setPhone(MapUtils.getString(map, "phone"));
			user.setEmail(MapUtils.getString(map, "email"));
			user.setBirthday(MapUtils.getString(map, "birthday"));
			user.setIsLeave(MapUtils.getInteger(map, "isLeave"));
			user = userService.save(user);
			resultMap.put("result", "success");
			resultMap.put("id", user.getId().toString());					
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/user/api/updateUser", method = RequestMethod.POST)
	public @ResponseBody String updateUser(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			User user = userService.getById(MapUtils.getLong(map, "id"));
			user.setRocId(MapUtils.getString(map, "rocId"));
			user.setName(MapUtils.getString(map, "name"));
			user.setAccount(MapUtils.getString(map, "account"));
			user.setUnitId(MapUtils.getString(map, "unitId"));
			user.setJobName(MapUtils.getString(map, "jobName"));
			user.setJobLevel(MapUtils.getInteger(map, "jobLevel"));
			user.setPhone(MapUtils.getString(map, "phone"));
			user.setEmail(MapUtils.getString(map, "email"));
			user.setBirthday(MapUtils.getString(map, "birthday"));
			user.setIsLeave(MapUtils.getInteger(map, "isLeave"));
			userService.update(user);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/user/api/deleteUser", method = RequestMethod.POST)
	public @ResponseBody String deleteUser(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			userService.deleteById(MapUtils.getLong(map, "ID"));
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
