package tcb.shms.module.controller;

import java.util.ArrayList;
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
import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.entity.Authorizastion;
import tcb.shms.module.service.AuthorizastionService;

/**
 * @author MARK3835
 *
 */
@Controller
public class AuthorizastionAction extends GenericAction<Authorizastion> {

	@Autowired
	AuthorizastionService authorizastionService;	
	
	@RequestMapping(value = "/auth/api/getAuthorizastion", method = RequestMethod.GET)
	public @ResponseBody String getAuthorizastion() {
		String jsonInString = null;
		try {
			Map resultMap = new HashMap();
			String selectAuthSql = "SELECT MENU_ID, AUTH_LV FROM AUTHORIZASTION order by AUTH_LV";
			List<Map> menuAuthList = authorizastionService.getListBySQLQuery(selectAuthSql);
			
			String selectMenuSql = "SELECT ID, MENU_NAME FROM MENU";
			List<Map> menuList = authorizastionService.getListBySQLQuery(selectMenuSql);
			
			String selectAuthLvSql = "SELECT distinct AUTH_LV FROM AUTHORIZASTION";
			List<Map> authLvList = authorizastionService.getListBySQLQuery(selectAuthLvSql);
			Map<String,Map> authKeyMap = new HashMap<String, Map>();
			for(Map menuAuthMap:menuAuthList) {
				String menuId = MapUtils.getString(menuAuthMap, "MENU_ID");
				String authLv = MapUtils.getString(menuAuthMap, "AUTH_LV");
				authKeyMap.put(menuId + "_" + authLv, menuAuthMap);
			}
			
			resultMap.put("authKeyMap", authKeyMap);
			resultMap.put("menuList", menuList);
			resultMap.put("authLvList", authLvList);
			jsonInString = new GsonBuilder().create().toJson(resultMap);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	
	@RequestMapping(value = "/auth/api/updateAuthorizastion", method = RequestMethod.POST)
	public @ResponseBody String updateUnit(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			ArrayList<String> checkboxValueList = new Gson().fromJson(data, ArrayList.class);	
			authorizastionService.deleteAll();
			for(String checkboxValue:checkboxValueList) {				
				String[] stringArray = checkboxValue.split("_");
				Authorizastion authorizastion = new Authorizastion();
				authorizastion.setMenuId(Long.parseLong(stringArray[0]));
				authorizastion.setAuthLv(Integer.parseInt(stringArray[1]));
				authorizastionService.save(authorizastion);
			}
			
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
