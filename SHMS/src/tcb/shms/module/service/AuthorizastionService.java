package tcb.shms.module.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tcb.shms.core.service.GenericService;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.dao.AuthorizastionDao;
import tcb.shms.module.entity.Authorizastion;
import tcb.shms.module.entity.Config;
import tcb.shms.module.entity.Unit;
import tcb.shms.module.entity.User;


/**
 * @author Mark Huang
 * @date 2020/3/13
 **/
@Transactional
@Service
public class AuthorizastionService extends GenericService<Authorizastion>{

	@Autowired
	AuthorizastionDao authorizastionDao;
	
	@Autowired
	ConfigService configService;
	
	@Autowired
	UnitService unitService;
	
	@Override
	protected AuthorizastionDao getDao() {
		return authorizastionDao;
	}

	public void saveOrUpdate(Authorizastion authorizastion) throws Exception {
		Assert.notNull(authorizastion,"authorizastion不能為null");				
		getDao().saveOrUpdate(authorizastion);
	}
	
	public void deleteAll() throws Exception {			
		getDao().executeSql(" DELETE FROM AUTHORIZASTION ");
	}
	
	/**
	 * 用權限腳色 查找有權限的menu id list
	 * @param authList
	 * @return
	 * @throws Exception
	 */
	public List<String> getByAuth(List<Integer> authList) throws Exception{
		String sql = "SELECT distinct MENU_ID FROM AUTHORIZASTION WHERE  1=1  and ( 1!=1 ";
		for(Integer auth:authList) {
			sql += " OR  AUTH_LV = " + auth;
		}
		sql += ")";
		List<Map> authMenuMapList = this.getListBySQLQuery(sql);
		List<String> resultList = new ArrayList<String>();
		for(Map authMenuMap:authMenuMapList) {
			resultList.add(MapUtils.getString(authMenuMap, "MENU_ID"));
		}
		return resultList;
	}
	
	/**
	 * 取得這個user的權限腳色
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public List<Integer> getAuthByUser(User user) throws Exception {   
		List<Integer> resultList = new ArrayList<Integer>();
		Config config = new Config();
		config.setCfgType(SystemConfig.CFG_TYPE.SYSTEM_ADMIN);
		config.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
		List<Config> systemAdminList = configService.getList(config);
		for(Config systemAdmin:systemAdminList) {
			if(systemAdmin.getCfgValue().equals(user.getAccount())) {
				resultList.add(SystemConfig.AUTH_LV.SYSTEM_ADMIN);
				break;
			}   
		}
		//襄理
		if(user.getJobLevel() >=10) {
			resultList.add(SystemConfig.AUTH_LV.JUNIOR_MANAGER);
		}
		if(StringUtils.isNotBlank(user.getUnitId())) {
			Unit unit = unitService.getByUnitId(user.getUnitId());
			if(unit != null) {
				if(user.getRocId().equals(unit.getSaveManager())) {
					resultList.add(SystemConfig.AUTH_LV.SAFE_MANAGER);
				}
				if(user.getRocId().equals(unit.getFireHelper())) {
					resultList.add(SystemConfig.AUTH_LV.FIRE_HELPER);
				}
				if(user.getRocId().equals(unit.getHelper())) {
					resultList.add(SystemConfig.AUTH_LV.HELPER);
				}
				if(user.getRocId().equals(unit.getAffairs())) {
					resultList.add(SystemConfig.AUTH_LV.AFFAIRS);
				}
				if(user.getRocId().equals(unit.getManager())) {
					resultList.add(SystemConfig.AUTH_LV.MANAGER);
				}
			}
				
		}
			
		return resultList;
	}
}
