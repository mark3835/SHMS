package tcb.shms.module.controller;

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
import tcb.shms.module.entity.Menu;
import tcb.shms.module.service.MenuService;

/**
 *  @author MARK3835
 *
 */
@Controller
public class MenuAction extends GenericAction<Menu>{
	
	@Autowired
	MenuService menuService;
	
	@Autowired 
	HttpServletRequest request;
	
	@RequestMapping(value="/menu/getMenu", method=RequestMethod.GET)
    public @ResponseBody String getStockPrice() {     
		String jsonInString = null;
        try {
        	String account = ObjectUtils.identityToString(request.getSession().getAttribute(SystemConfig.SESSION_KEY.LOGIN));
        	List<Menu> menuList = menuService.getList(new Menu());
			jsonInString = new Gson().toJson(menuList);
		} catch (Exception e) {
			e.printStackTrace();
		}

               
        return jsonInString;
    }
    

}
