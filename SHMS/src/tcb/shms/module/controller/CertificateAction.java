package tcb.shms.module.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.GsonBuilder;

import tcb.shms.core.controller.GenericAction;
import tcb.shms.core.service.CsvService;
import tcb.shms.module.entity.Certificate;
import tcb.shms.module.entity.Unit;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.CertificateService;
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
					String gotFee = csvData.get(8).trim().replaceAll(",", "");
					String gotTrainUnit = csvData.get(9).trim();
					String reviewName = csvData.get(10).trim();
					String saveManager = csvData.get(11).trim();
					String helper = csvData.get(12).trim();
					String fireHelper = csvData.get(13).trim();
					
					Certificate ceritficate = new Certificate();					
					User user = userService.getByAccount(getAccount(email));
					ceritficate.setRocId(user.getRocId());
					ceritficate.setCertificateName(certificateName);
					ceritficate.setCertificateUnit(certificateUnit);
					ceritficate.setCertificateType(certificateType);
					ceritficate.setGotFee(Integer.parseInt(gotFee));
					ceritficate.setGotDate(parseDate(gotDate));
					ceritficate.setGotTrainUnit(getUnitIdByName(gotTrainUnit));
					ceritficate.setIsResponsible(1);
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
						if(StringUtils.isNotEmpty(saveManager)){
							unit.setSaveManager(user.getRocId());
						}
						if(StringUtils.isNotEmpty(helper)){
							unit.setHelper(user.getRocId());
						}
						if(StringUtils.isNotEmpty(fireHelper)){
							unit.setFireHelper(user.getRocId());
						}		
						unitService.update(unit);
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
				Map<String,Unit> unitMap = new HashMap<String, Unit>();
				for(Unit unit:unitList) {
					unitMap.put(unit.getUnitName(), unit);
				}
				return unitMap.get(unitName).getUnitId();
			} catch (Exception e) {
				log.error("",e);
				errorLogService.addErrorLog(this.getClass().getName(), e);
			}			
		}
		return unitName;
	}
	
	private String getAccount(String email) {
		String[] stringArray = email.split("@");
		return stringArray[0];
	}
	
	private static Date parseDate(String strDate) {
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
	
	@RequestMapping(value = "/certificate/api/getCertificate", method = RequestMethod.GET)
	public @ResponseBody String getCertificate() {
		String jsonInString = null;
		try {
			Map resultMap = new HashMap();
			jsonInString = new GsonBuilder().create().toJson(resultMap);
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}

		return jsonInString;
	}

}
