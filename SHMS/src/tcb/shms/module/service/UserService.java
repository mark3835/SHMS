package tcb.shms.module.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.dao.UserDao;
import tcb.shms.module.entity.User;


/**
 * @author Mark Huang
 * @date 2020/3/3
 **/
@Transactional
@Service
public class UserService extends GenericService<User>{

	@Autowired
	UserDao userDao;
	
	@Autowired
	AuthorizastionService authorizastionService;
	
	@Override
	protected UserDao getDao() {
		return userDao;
	}

	public User getByRocid(String rocId) throws Exception {
		Assert.notNull(rocId, "rocId不能為null");		
		User entity = getDao().findByRocId(rocId);		
		return entity;
	}
	
	public User getByAccount(String account) throws Exception {
		Assert.notNull(account, "account不能為null");		
		User entity = getDao().findByAccount(account);	
		return entity;
	}
	
	/**
	 * 取得單位襄理以上主管 排除自己 取審核人用
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public List<User> getManagers(User user) throws Exception{
		User queryUser = new User();
		queryUser.setIsLeave(SystemConfig.USER.IS_LEAVE_FALSE);
		queryUser.setUnitId(user.getUnitId());
		List<User> thisUserUnitList = getList(queryUser);
		List<User> resultList = new ArrayList<User>();
		for(User unitUser:thisUserUnitList) {
			if(unitUser.getRocId().equals(user.getRocId())) {
				continue;
			}
			List<Integer> authList = authorizastionService.getAuthByUser(unitUser);
			if(authList.contains(SystemConfig.AUTH_LV.MANAGER)) {
				resultList.add(unitUser);
				continue;
			}
			if(authList.contains(SystemConfig.AUTH_LV.JUNIOR_MANAGER)) {
				resultList.add(unitUser);
				continue;
			}
		}
		return resultList;
	}

}
