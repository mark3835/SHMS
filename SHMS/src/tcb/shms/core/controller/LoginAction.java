package tcb.shms.core.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import tcb.shms.core.service.LdapService;
import tcb.shms.module.config.SystemConfig;

/**
 *  @author MARK3835
 *
 */
@Controller
public class LoginAction{
	
	@SuppressWarnings("unused")
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	LdapService ldapService;
	
	@Autowired 
	HttpServletRequest request;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody String login(@RequestBody String data) {     
		String jsonInString = null;
		boolean result;
        try {
        	
        	@SuppressWarnings("unchecked")
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);
        	result = ldapService.checkADAccount(MapUtils.getString(map, "account"), MapUtils.getString(map, "pasw"));        
        	
        	request.getSession().setAttribute(SystemConfig.SESSION_KEY.LOGIN, MapUtils.getString(map, "account"));;
			jsonInString = new Gson().toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

               
        return jsonInString;
    }
    

}
