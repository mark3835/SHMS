package tcb.shms.module.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import tcb.shms.module.entity.Menu;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.AuthorizastionService;
import tcb.shms.module.service.MenuService;

/**
 * @author MARK3835
 *
 */
@Controller
public class MenuAction extends GenericAction<Menu> {

	@Autowired
	MenuService menuService;

	@Autowired
	AuthorizastionService authorizastionService;

	@RequestMapping(value = "/menu/getMenu", method = RequestMethod.GET)
	public @ResponseBody String getMenu() {
		String jsonInString = null;
		try {
			//@TODO 撈取使用者權限
			User user = super.getSessionUser();
			List<String> authMenuList =  authorizastionService.getByAuth(authorizastionService.getAuthByUser(user));
			List<Menu> menuList = menuService.getList(new Menu());
			//loop MENU 判斷是否有權限 沒有則移除
			Iterator<Menu> it = menuList.iterator();
			while (it.hasNext()) {
				Menu menuObj = it.next();
				boolean exist = false;
				for(String authMenu:authMenuList) {					
					if (menuObj.getId().equals(Long.parseLong(authMenu))) {
						exist = true;
						break;
					}
				}	
				if(!exist) {
					it.remove();
				}
			}
			// List 自訂排序  MENU依照order值排序
			Collections.sort(menuList, new Comparator<Menu>() {
				public int compare(Menu m1, Menu m2) {
					// 回傳值: -1 前者比後者小, 0 前者與後者相同, 1 前者比後者大
					return m1.getMenuOrder().compareTo(m2.getMenuOrder());
				}
			});
			jsonInString = new Gson().toJson(menuList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/menu/api/getMenu", method = RequestMethod.GET)
	public @ResponseBody String getAnnouncement() {
		String jsonInString = null;
		try {
			List<Menu> menuList = menuService.getList(new Menu());
			jsonInString = new Gson().toJson(menuList);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	@RequestMapping(value = "/menu/api/addMenu", method = RequestMethod.POST)
	public @ResponseBody String addMenu(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Menu menu = new Menu();
			menu.setMenuName(MapUtils.getString(map, "menuName"));
			menu.setMenuUrl(MapUtils.getString(map, "menuUrl"));
			menu.setMenuTierTwo(MapUtils.getLong(map, "menuTierTwo"));
			menu.setMenuOrder(MapUtils.getInteger(map, "menuOrder"));
			menu.setMenuApiUrl(MapUtils.getString(map, "menuApiUrl"));
			menu = menuService.save(menu);
			resultMap.put("result", "success");
			resultMap.put("id", menu.getId().toString());					
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/menu/api/updateMenu", method = RequestMethod.POST)
	public @ResponseBody String updateMenu(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Menu menu = menuService.getById(MapUtils.getLong(map, "id"));
			menu.setMenuName(MapUtils.getString(map, "menuName"));
			menu.setMenuUrl(MapUtils.getString(map, "menuUrl"));
			menu.setMenuTierTwo(MapUtils.getLong(map, "menuTierTwo"));
			menu.setMenuOrder(MapUtils.getInteger(map, "menuOrder"));
			menu.setMenuApiUrl(MapUtils.getString(map, "menuApiUrl"));
			menuService.update(menu);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/menu/api/deleteMenu", method = RequestMethod.POST)
	public @ResponseBody String deleteConfig(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			menuService.deleteById(MapUtils.getLong(map, "ID"));
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
