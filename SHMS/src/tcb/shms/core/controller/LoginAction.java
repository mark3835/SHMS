package tcb.shms.core.controller;

import java.sql.Timestamp;
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
import tcb.shms.module.entity.LoginLog;
import tcb.shms.module.service.ErrorLogService;
import tcb.shms.module.service.LoginLogService;

/**
 *  @author MARK3835
 *
 */
@Controller
public class LoginAction{
	
	private final Logger log = LogManager.getLogger(getClass());
	
	@Autowired
	LdapService ldapService;
	
	@Autowired
	LoginLogService  loginLogService;
	
	@Autowired
	ErrorLogService  errorLogService;
	
	@Autowired 
	HttpServletRequest request;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
    public @ResponseBody String login(@RequestBody String data) {     
		String jsonInString = null;
		boolean result = true;
        try {
        	
        	@SuppressWarnings("unchecked")
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);
//        	result = ldapService.checkADAccount(MapUtils.getString(map, "account"), MapUtils.getString(map, "pasw"));        
        	
        	request.getSession().setAttribute(SystemConfig.SESSION_KEY.LOGIN, MapUtils.getString(map, "account"));
			jsonInString = new Gson().toJson(result);
			
			if(result){
				LoginLog loginLog = new LoginLog();
				loginLog.setLoginTime(new Timestamp(System.currentTimeMillis()));
				loginLog.setAccount(MapUtils.getString(map, "account"));
				loginLogService.save(loginLog);				
			}
			
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

               
        return jsonInString;
    }
	
	@RequestMapping(value="/login/loginOut", method=RequestMethod.GET)
    public @ResponseBody String loginOut() {     
		String result = "success";
        try {
        	request.getSession().removeAttribute(SystemConfig.SESSION_KEY.LOGIN);			
		} catch (Exception e) {
			result = "error";
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}
        return result;
    }
    
	
	@RequestMapping(value="/login/api/addUserAndUnitTest", method=RequestMethod.GET)
    public @ResponseBody String addUserAndUnitTest() {     
		String jsonInString = "error";
        try {
        	
        	ldapService.getADUserAndUnitToDb("mark3835", "5tgb^YHN");
        	
        	jsonInString = "success";
			
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

               
        return jsonInString;
    }

}
