package tcb.shms.module.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.core.service.CsvService;
import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.Certificate;
import tcb.shms.module.entity.Config;
import tcb.shms.module.entity.Unit;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.AuthorizastionService;
import tcb.shms.module.service.CertificateService;
import tcb.shms.module.service.ConfigService;
import tcb.shms.module.service.UnitService;
import tcb.shms.module.service.UserService;

/**
 * @author MARK3835
 *
 */
@Controller
public class CertificateAction extends GenericAction<Certificate> {

	@Autowired
	CertificateService certificateService;
	
	@Autowired
	CsvService csvService;
	
	@Autowired
	UnitService unitService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthorizastionService authorizastionService;
	
	@Autowired
	ConfigService configService;
	
	Map<String,Unit> unitMap = null;
	
	@RequestMapping(value = "/certificate/api/importCertificate", method = RequestMethod.GET)
	public @ResponseBody String importCertificate(@RequestParam(value="fileName", required=false) String fileName) {
		String jsonInString = null;
		try {	
			Map resultMap = new HashMap();
			List<List<String>> csvDataList = csvService.readCsvFile(fileName);
			
			boolean firstLine = true;
			Iterator<List<String>> csvDataListIterator = csvDataList.iterator();			
			while(csvDataListIterator.hasNext()){
				List<String> csvData = csvDataListIterator.next();
				if(firstLine) {
					firstLine = false;
					continue;
				}				
				if(csvData.size() < 14){
					int count = csvData.size();
					for(;count<14;count++) {
						csvData.add("");
					}
				}				
				
				try {
					String unitName = csvData.get(0).trim();
					String name = csvData.get(1).trim();
					String jobName = csvData.get(2).trim();
					String email = csvData.get(3).trim();
					String certificateType = csvData.get(4).trim();
					String certificateName = csvData.get(5).trim();
					String certificateUnit = csvData.get(6).trim();
					String gotDate = csvData.get(7).trim();
					String gotFee = null;
					String gotTrainUnit = null;
					String reviewName = null;
					String saveManager = null;
					String helper = null;
					String fireHelper = null;
					if(csvData.size() == 14){
						gotFee = csvData.get(8).trim();
						gotFee = gotFee.replaceAll("\"", "");
						gotTrainUnit = csvData.get(9).trim();
						reviewName = csvData.get(10).trim();
						saveManager = csvData.get(11).trim();
						helper = csvData.get(12).trim();
						fireHelper = csvData.get(13).trim();
					}else if(csvData.size() >= 15){
						gotFee = csvData.get(8).trim() +  csvData.get(9).trim() ;
						gotFee = gotFee.replaceAll(",", "");
						gotFee = gotFee.replaceAll("\"", "");
						gotTrainUnit = csvData.get(10).trim();
						reviewName = csvData.get(11).trim();
						saveManager = csvData.get(12).trim();
						helper = csvData.get(13).trim();
						fireHelper = csvData.get(14).trim();
					}							
					
					Certificate ceritficate = new Certificate();					
					User user = userService.getByAccount(getAccount(email));
					ceritficate.setRocId(user.getRocId());
					ceritficate.setCertificateName(certificateName);
					ceritficate.setCertificateUnit(certificateUnit);
					ceritficate.setCertificateType(certificateType);
					ceritficate.setGotFee(Integer.parseInt(gotFee));
					ceritficate.setGotDate(parseDate(gotDate));
					ceritficate.setGotTrainUnit(getUnitIdByName(gotTrainUnit));
					if(StringUtils.isNotBlank(saveManager) || StringUtils.isNotBlank(helper) || StringUtils.isNotBlank(fireHelper)) {
						ceritficate.setIsResponsible(1);
					}else {
						ceritficate.setIsResponsible(0);
					}
					ceritficate.setCreateId("system");
					ceritficate.setCreateTime(new Timestamp(System.currentTimeMillis()));
					User findUser = new User();
					if(reviewName != null && StringUtils.isNotBlank(reviewName)) {
						findUser.setName(reviewName);				
						List<User> userList = userService.getList(findUser);
						if(userList != null && userList.size() != 0) {
							if(userList.size() == 1) {
								ceritficate.setReviewId(userList.get(0).getRocId());
							}else {
								for(User reviewUser:userList) {
									if(reviewUser.getUnitId().equals(user.getUnitId())) {
										ceritficate.setReviewId(reviewUser.getRocId());
										break;
									}
								}
							}
						}
					}					
					if(ceritficate.getReviewId() == null) {
						ceritficate.setReviewId(reviewName);
					}					
					
					if(StringUtils.isNotBlank(saveManager) || StringUtils.isNotBlank(helper) || StringUtils.isNotBlank(fireHelper)) {
						Unit unit = unitService.getByUnitId(user.getUnitId());
						boolean isChanged = false;
						if(StringUtils.isNotEmpty(saveManager)){
							unit.setSaveManager(user.getRocId());
							isChanged = true;
						}
						if(StringUtils.isNotEmpty(helper)){
							unit.setHelper(user.getRocId());
							isChanged = true;
						}
						if(StringUtils.isNotEmpty(fireHelper)){
							unit.setFireHelper(user.getRocId());
							isChanged = true;
						}		
						if(isChanged) {
							unitService.update(unit);
						}						
					}
					
					certificateService.save(ceritficate);
				} catch (Exception e) {
					log.error("",e);
					errorLogService.addErrorLog(this.getClass().getName(), e);
				}
			}
			
			jsonInString = new GsonBuilder().create().toJson(resultMap);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	private String getUnitIdByName(String unitName) {
		if(unitMap == null) {
			try {
				List<Unit> unitList = unitService.getList(new Unit());
				unitMap = new HashMap<String, Unit>();
				for(Unit unit:unitList) {
					unitMap.put(unit.getUnitName(), unit);
				}
				return unitMap.get(unitName).getUnitId();
			} catch (Exception e) {
				log.error("",e);
				return unitName;
			}			
		}else {
			return unitMap.get(unitName).getUnitId();
		}
	}
	
	private String getAccount(String email) {
		String[] stringArray = email.split("@");
		return stringArray[0];
	}
		
	private static Date parseDate(String strDate) {
		if(StringUtils.isBlank(strDate)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String year = null;
		String monthDay = null;
		if(strDate.length() == 7) {
			year = strDate.substring(0, 3);
			monthDay = strDate.substring(3);
		}else if(strDate.length() == 6){
			year = strDate.substring(0, 2);
			monthDay = strDate.substring(2);
		}
		year = Integer.toString(Integer.parseInt(year) + 1911);
		try {
			return sdf.parse(year+monthDay);
		} catch (ParseException e) {
			return null;
		}
	}
	
	@RequestMapping(value = "/certificateSetting/api/getCertificate", method = RequestMethod.GET)
	public @ResponseBody String getCertificate() {
		String jsonInString = null;
		try {
			String sql = " SELECT c.*,user.name,unit.unit_name  , trainUnit .unit_name  as trainUnitName  " + 
					" FROM CERTIFICATE as c,User as user, unit as unit , unit as trainUnit " + 
					" WHERE c.roc_id = user.roc_id and user.unit_id = unit.unit_id  and c.GET_TRAIN_UNIT = trainUnit.unit_id   ";
			List<Map> dataResult = certificateService.getListBySQLQuery(sql);
			jsonInString = new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE_STRING).create().toJson(dataResult);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
	
	
	
	@RequestMapping(value = "/certificateSetting/api/addCertificate", method = RequestMethod.POST)
	public @ResponseBody String addUser(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {			
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Certificate certificate = new Certificate();
			certificate.setRocId(MapUtils.getString(map, "rocId"));
			certificate.setCertificateName(MapUtils.getString(map, "certificateName"));
			certificate.setCertificateType(MapUtils.getString(map, "certificateType"));
			certificate.setCertificateUnit(MapUtils.getString(map, "certificateUnit"));
			certificate.setGotDate(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.parse(MapUtils.getString(map, "gotDate")));
			certificate.setGotFee(MapUtils.getIntValue(map, "gotFee"));
			certificate.setGotTrainUnit(MapUtils.getString(map, "gotTrainUnit"));
			certificate.setReviewId(MapUtils.getString(map, "reviewId"));
			certificate.setReviewTime(new Timestamp(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.parse(MapUtils.getString(map, "reviewTime")).getTime()));
			certificate.setIsResponsible(MapUtils.getIntValue(map, "isResponsible"));
			User user = getSessionUser();
			certificate.setCreateId(user.getRocId());
			certificate.setCreateTime(new Timestamp(System.currentTimeMillis()));
			certificate = certificateService.save(certificate);
			resultMap.put("result", "success");
			resultMap.put("id", certificate.getId().toString());					
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/certificateSetting/api/updateCertificate", method = RequestMethod.POST)
	public @ResponseBody String updateUser(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);			
			Certificate certificate = certificateService.getById(MapUtils.getLong(map, "id"));
			certificate.setRocId(MapUtils.getString(map, "rocId"));
			certificate.setCertificateName(MapUtils.getString(map, "certificateName"));
			certificate.setCertificateType(MapUtils.getString(map, "certificateType"));
			certificate.setCertificateUnit(MapUtils.getString(map, "certificateUnit"));
			certificate.setGotDate(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.parse(MapUtils.getString(map, "gotDate")));
			certificate.setGotFee(MapUtils.getIntValue(map, "gotFee"));
			certificate.setGotTrainUnit(MapUtils.getString(map, "gotTrainUnit"));
			certificate.setReviewId(MapUtils.getString(map, "reviewId"));
			certificate.setReviewTime(new Timestamp(SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.parse(MapUtils.getString(map, "reviewTime")).getTime()));
			certificate.setIsResponsible(MapUtils.getIntValue(map, "isResponsible"));
			User user = getSessionUser();
			certificate.setEditId(user.getRocId());
			certificate.setEditTime(new Timestamp(System.currentTimeMillis()));
			certificateService.update(certificate);
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/certificateSetting/api/deleteCertificate", method = RequestMethod.POST)
	public @ResponseBody String deleteUser(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> map = new Gson().fromJson(data, HashMap.class);	
			certificateService.deleteById(MapUtils.getLong(map, "ID"));
			resultMap.put("result", "success");
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
			resultMap.put("result", "error");
			resultMap.put("errorMsg", e.getMessage());
		}

		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value = "/certificate/api/getDefaultData", method = RequestMethod.GET)
	public @ResponseBody String getUnitByUser() {
		String jsonInString = null;
		try {
			Map result = new HashMap();
			User loginUser = this.getSessionUser();
			result.put("loginUser", loginUser);
			Unit unit = unitService.getByUnitId(loginUser.getUnitId());
			result.put("unit", unit);
			//作業人員
			String workMan = "";
			List<Integer> authList = authorizastionService.getAuthByUser(loginUser);
			if(authList.contains(SystemConfig.AUTH_LV.AFFAIRS)) {
				workMan = "總務";
			}else if(authList.contains(SystemConfig.AUTH_LV.MANAGER)) {
				workMan = "單位主管";
			}else if(authList.contains(SystemConfig.AUTH_LV.JUNIOR_MANAGER)) {
				workMan = "襄理";
			}
			result.put("workMan", workMan);
			
			//取得證書種類選項
			Config certificateType = new Config();
			certificateType.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
			certificateType.setCfgType(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_SAVEMANAGER);
			List<Config> certificateTypeList = configService.getList(certificateType);
			certificateType.setCfgType(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_FIREHELPER);
			certificateTypeList.addAll(configService.getList(certificateType));
			certificateType.setCfgType(SystemConfig.CFG_TYPE.CERTIFICATE_TYPE_HELPER);
			certificateTypeList.addAll(configService.getList(certificateType));
			result.put("certificateTypeList", certificateTypeList);
			//取得核發單位選項
			Config certificateUnit = new Config();
			certificateUnit.setCfgInUse(SystemConfig.CFG_IN_USE.CFG_IN_USE_TRUE);
			certificateUnit.setCfgType(SystemConfig.CFG_TYPE.CERTIFICATE_UNIT);
			List<Config> certificateUnitList = configService.getList(certificateUnit);
			result.put("certificateUnitList", certificateUnitList);
			
			//取得派訓單位列表
			List<Unit> unitList = unitService.getList(new Unit());
			result.put("unitList", unitList);
			
			//取得單位人員選項
			//取得單位證照表單資料
			User queryUser = new User();
			queryUser.setIsLeave(SystemConfig.USER.IS_LEAVE_FALSE);
			queryUser.setUnitId(loginUser.getUnitId());
			List<User> userList = userService.getList(queryUser);
			result.put("userList", userList);
			List<String> userIdList = new ArrayList<String>();
			for(User user:userList) {
				userIdList.add(user.getRocId());
			}
			//取得單位證照
			List<Certificate> certificateList = certificateService.getByRocIds(userIdList);
			result.put("certificateList", certificateList);
			
			//取得審核人
			List<User> managerList = userService.getManagers(loginUser);
			result.put("managerList", managerList);
						
			jsonInString =  new GsonBuilder().setDateFormat(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE_STRING).create().toJson(result);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}
		
	@RequestMapping(value = "/certificate/api/saveData", method = RequestMethod.POST)
	public @ResponseBody String saveData(@RequestBody String data) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		try {
			HashMap<String,Object> requestMap = new Gson().fromJson(data, HashMap.class);	
			User loginUser = this.getSessionUser();
			
			Certificate certificate = new Certificate();
			certificate.setRocId(MapUtils.getString(requestMap, "rocId"));
			certificate.setCertificateName(MapUtils.getString(requestMap, "certificateName"));
			certificate.setCertificateType(MapUtils.getString(requestMap, "certificateType"));
			certificate.setCertificateUnit(MapUtils.getString(requestMap, "certificateUnit"));
			certificate.setGotFee(MapUtils.getIntValue(requestMap, "gotFee"));
			certificate.setGotDate(SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.parse(MapUtils.getString(requestMap, "gotDate")));
			certificate.setGotTrainUnit(MapUtils.getString(requestMap, "gotTrainUnit"));
			certificate.setReviewId(MapUtils.getString(requestMap, "reviewer"));
			certificate.setMemo(MapUtils.getString(requestMap, "memo"));
			certificate.setIsResponsible(MapUtils.getIntValue(requestMap, "isResponsible"));
			certificate.setCreateId(loginUser.getRocId());
			certificate.setCreateTime(new Timestamp(System.currentTimeMillis()));
			certificateService.save(certificate);
			
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
