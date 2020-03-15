package tcb.shms.module.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		
//	@RequestMapping(value="/user/test", method=RequestMethod.GET)
//    public @ResponseBody String getStockPrice(@RequestBody Map<String, Object> params) {     
//		String jsonInString = null;
//        try {
//        	 Map<String, String> maps = new HashMap<String, String>();
//             String rocId = (String) params.get("RocId");
//             
//			 User user = userService.getByRocid(rocId);
//			 jsonInString = new Gson().toJson(maps);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//               
//        return jsonInString;
//    }
	@RequestMapping(value="/user/test", method=RequestMethod.GET)
    public @ResponseBody String getStockPrice() {     
		String jsonInString = null;
        try {
			 User user = userService.getByRocid("123");
			 jsonInString = new Gson().toJson(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

               
        return jsonInString;
    }
    

}
