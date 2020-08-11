package tcb.shms.core.batch;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.BatchSetting;
import tcb.shms.module.service.BatchSettingService;
import tcb.shms.module.service.ErrorLogService;

/**
 * 
 * @author MARK3835
 * @date 2020/8/11
 *
 */
public class QuartzJob {
	
	private final Logger log = LogManager.getLogger(getClass());
	
	BatchSettingService batchSettingService = null;
	
	ErrorLogService  errorLogService;
	
	ApplicationContext springContext = null;
	
	public static void main(String args[]) throws ParseException, ClassNotFoundException {
//		String batchClass = "syncAdUserUnitBatch.hi";
//		String className = batchClass.substring(0,batchClass.lastIndexOf(".") );
//		String methodName = batchClass.substring(batchClass.lastIndexOf(".") + 1);
//		System.out.println(className);
//		System.out.println(methodName);		
//		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		Method  mh = ReflectionUtils.findMethod(springContext.getBean(className).getClass(), methodName);
//        Object obj = ReflectionUtils.invokeMethod(mh,  springContext.getBean(className));
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		System.out.println(cal);
		System.out.println(cal.get(Calendar.DAY_OF_MONTH) );

	}
		
	public void execute() {

		springContext = WebApplicationContextUtils.getWebApplicationContext(ContextLoaderListener.getCurrentWebApplicationContext().getServletContext());
		if(springContext != null && springContext.getBean("errorLogService") != null && errorLogService == null) {
			errorLogService  = (ErrorLogService) springContext.getBean("errorLogService");  
        } 
		if(springContext != null && springContext.getBean("batchSettingService") != null && batchSettingService == null) {
			batchSettingService  = (BatchSettingService) springContext.getBean("batchSettingService");  
        } 
		
		try {
			List<BatchSetting> batchSettingList = batchSettingService.getList(new BatchSetting());
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			for(BatchSetting batchSetting:batchSettingList) {
				try {
					int batchType = batchSetting.getBatchType();
					
					switch(batchType) { 
						case 1 :
							if(batchSetting.getLastStartTime() != null) {
								Date runDate = batchSetting.getBatchType1Date();
								String runDateString = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.format(runDate);
								Integer runHour = batchSetting.getBatchHour();
								Integer runMinute = batchSetting.getBatchMinute();								
								runDateString = runDateString + " " + Integer.toString(runHour) + ":" + Integer.toString(runMinute) + ":00"; 
								Date runTime = SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.parse(runDateString);
								//判斷是否執行
								if(checkTimeBiggerNow(now, runTime)) {
									//先更新執行時間
									batchSetting.setLastStartTime(new Timestamp(System.currentTimeMillis()));
									batchSettingService.update(batchSetting);
									//執行批次  這邊不處理回傳  批次程式要自己回寫LOG
									runBatch(batchSetting.getBatchClass());
								}
							}
							break;
						case 2 :
							if(batchSetting.getLastStartTime() == null) {	
								//先更新執行時間
								batchSetting.setLastStartTime(new Timestamp(System.currentTimeMillis()));
								batchSettingService.update(batchSetting);
								//執行批次  這邊不處理回傳  批次程式要自己回寫LOG
								runBatch(batchSetting.getBatchClass());
							}else {
								Date d = batchSetting.getLastStartTime();
								Date addMinDate = new Date(d.getTime() + (batchSetting.getBatchMinute()*60));
								//判斷是否執行
								if(checkTimeBiggerNow(now, addMinDate)) {
									//先更新執行時間
									batchSetting.setLastStartTime(new Timestamp(System.currentTimeMillis()));
									batchSettingService.update(batchSetting);
									//執行批次  這邊不處理回傳  批次程式要自己回寫LOG
									runBatch(batchSetting.getBatchClass());
								}
							}							
							break;
						case 3 :
							if(batchSetting.getLastStartTime() == null) {	
								//先更新執行時間
								batchSetting.setLastStartTime(new Timestamp(System.currentTimeMillis()));
								batchSettingService.update(batchSetting);
								//執行批次  這邊不處理回傳  批次程式要自己回寫LOG
								runBatch(batchSetting.getBatchClass());
							}else {
								Date d = batchSetting.getLastStartTime();
								Date addMinDate = new Date(d.getTime() + (batchSetting.getBatchHour()*60*60));
								//判斷是否執行
								if(checkTimeBiggerNow(now, addMinDate)) {
									//先更新執行時間
									batchSetting.setLastStartTime(new Timestamp(System.currentTimeMillis()));
									batchSettingService.update(batchSetting);
									//執行批次  這邊不處理回傳  批次程式要自己回寫LOG
									runBatch(batchSetting.getBatchClass());
								}
							}
							break;
						case 4 :
							
							//有上次執行時間
							if(batchSetting.getLastStartTime() != null) {
								String nowDate = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.format(now);
								String lastStartDate = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.format(batchSetting.getLastStartTime());
								//上次執行時間為今天  今天已執行過
								if(nowDate.equals(lastStartDate)) {
									break;
								}
							}
							
							int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
							//calendar星期日是0  但是我設定這邊算7
							if(w == 0) {
								w = 7;
							}
							if(w == batchSetting.getBatchType4EveryWeek()) {
								String runDateString = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.format(now);
								Integer runHour = batchSetting.getBatchHour();
								Integer runMinute = batchSetting.getBatchMinute();								
								runDateString = runDateString + " " + Integer.toString(runHour) + ":" + Integer.toString(runMinute) + ":00"; 
								Date runTime = SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.parse(runDateString);
								//判斷是否執行
								if(checkTimeBiggerNow(now, runTime)) {
									//先更新執行時間
									batchSetting.setLastStartTime(new Timestamp(System.currentTimeMillis()));
									batchSettingService.update(batchSetting);
									//執行批次  這邊不處理回傳  批次程式要自己回寫LOG
									runBatch(batchSetting.getBatchClass());
								}
							}
							
							break;
						case 5 :
							//有上次執行時間
							if(batchSetting.getLastStartTime() != null) {
								String nowDate = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.format(now);
								String lastStartDate = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.format(batchSetting.getLastStartTime());
								//上次執行時間為今天  今天已執行過
								if(nowDate.equals(lastStartDate)) {
									break;
								}
							}
							
							if(cal.get(Calendar.DAY_OF_MONTH)  == batchSetting.getBatchType5EveryMonth()){
								String runDateString = SystemConfig.DATE_FORMAT.BASIC_DATE_FORMATE.format(now);
								Integer runHour = batchSetting.getBatchHour();
								Integer runMinute = batchSetting.getBatchMinute();								
								runDateString = runDateString + " " + Integer.toString(runHour) + ":" + Integer.toString(runMinute) + ":00"; 
								Date runTime = SystemConfig.DATE_FORMAT.BASIC_DATETIME_FORMATE.parse(runDateString);
								//判斷是否執行
								if(checkTimeBiggerNow(now, runTime)) {
									//先更新執行時間
									batchSetting.setLastStartTime(new Timestamp(System.currentTimeMillis()));
									batchSettingService.update(batchSetting);
									//執行批次  這邊不處理回傳  批次程式要自己回寫LOG
									runBatch(batchSetting.getBatchClass());
								}
							}
														
							break;			
					};
				} catch (Exception e) {
					log.error("",e);
					errorLogService.addErrorLog(this.getClass().getName(), e);
				}
			}
		} catch (Exception e) {
			log.error("",e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}
		
	}
	

	private boolean checkTimeBiggerNow(Date now, Date runTime) {
		long diff = runTime.getTime() - now.getTime();
		if(diff >= 0) {
			return true;
		}else {
			return false;
		}
	}
	
	private void runBatch(String batchName) {
		String className = batchName.substring(0,batchName.lastIndexOf(".") );
		String methodName = batchName.substring(batchName.lastIndexOf(".") + 1);
		
		Method  mh = ReflectionUtils.findMethod(springContext.getBean(className).getClass(), methodName);
		//這邊不處理回傳  批次程式要自己回寫LOG
        ReflectionUtils.invokeMethod(mh,  springContext.getBean(className));
	}

}
