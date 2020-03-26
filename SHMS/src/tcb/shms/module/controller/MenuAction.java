package tcb.shms.module.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.Authorizastion;
import tcb.shms.module.entity.Menu;
import tcb.shms.module.service.AuthorizastionService;
import tcb.shms.module.service.ErrorLogService;
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
	
	@Autowired
	ErrorLogService  errorLogService;

	@Autowired
	HttpServletRequest request;

	@RequestMapping(value = "/menu/getMenu", method = RequestMethod.GET)
	public @ResponseBody String getMenu() {
		String jsonInString = null;
		try {
			//@TODO 撈取使用者權限
			String account = ObjectUtils
					.identityToString(request.getSession().getAttribute(SystemConfig.SESSION_KEY.LOGIN));
			int accountAuthLv = 0;
			Authorizastion authorizastion = new Authorizastion();
			authorizastion.setAuthLv(accountAuthLv);
			List<Authorizastion> authorizastionList = authorizastionService.getList(authorizastion);
			List<Menu> menuList = menuService.getList(new Menu());
			//loop MENU 判斷是否有權限 沒有則移除
			Iterator<Menu> it = menuList.iterator();
			while (it.hasNext()) {
				Menu menuObj = it.next();
				boolean exist = false;
				for(Authorizastion authObj:authorizastionList) {					
					if (menuObj.getId().equals(authObj.getId())) {
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
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
