package tcb.shms.module.config;

import java.text.SimpleDateFormat;

/**
 * 常數設定
 * @author MARK3835
 *
 */
public class SystemConfig {
	
	public static final String SYSTEM_NAME = "SHMS";
	
	public static class SESSION_KEY{
		public static final String LOGIN = "SESSION_KEY_LOGIN";
	}

	public static class LDAP{
		public static final String URL = "LDAPS://ITDC01.TCB.COM:3269";
	}
	
	public static class CFG_TYPE{
		public static final String DEFAULT_AUTH_URL = "DEFAULT_AUTH_URL";
		public static final String SYSTEM_ADMIN = "SYSTEM_ADMIN";
		public static final String CERTIFICATE_TYPE_SAVEMANAGER = "CERTIFICATE_TYPE_SAVEMANAGER";
		public static final String CERTIFICATE_TYPE_FIREHELPER = "CERTIFICATE_TYPE_FIREHELPER";
		public static final String CERTIFICATE_TYPE_HELPER = "CERTIFICATE_TYPE_HELPER";
		public static final String CERTIFICATE_UNIT = "CERTIFICATE_UNIT";
	}
	
	public static class CFG_IN_USE{
		public static final Integer CFG_IN_USE_TRUE = 1;
		public static final Integer CFG_IN_USE_FALSE = 0;
	}
	
	public static class ANNOUNCEMENT{
		public static final String FILE_ROOT_PATH = "announcementFile/";
		public static final String FILE_TYPE_PDF = "application/pdf";
	}
	
	public static class AUTH_LV{
		public static final int AFFAIRS = 1;
		public static final int SAFE_MANAGER = 2;
		public static final int FIRE_HELPER = 3;
		public static final int HELPER = 4;
		public static final int MANAGER = 5;
		public static final int JUNIOR_MANAGER = 6;
		public static final int SYSTEM_ADMIN = 7;
	}
	
	public static class DATE_FORMAT{
		public static final String BASIC_DATE_FORMATE_STRING = "yyyy/MM/dd";
		public static final String BASIC_DATETIME_FORMATE_STRING = "yyyy/MM/dd HH:mm:ss";
		public static final SimpleDateFormat BASIC_DATE_FORMATE = new SimpleDateFormat(BASIC_DATE_FORMATE_STRING);
		public static final SimpleDateFormat BASIC_DATETIME_FORMATE = new SimpleDateFormat(BASIC_DATETIME_FORMATE_STRING);
	}
	
	public static class EXCEL_PATH{
		public static final String DEFAULT_CREATE_PATH = "D:/excelFileCreate/";
	}
	
	public static class USER{
		public static final int IS_LEAVE_TRUE = 1;
		public static final int IS_LEAVE_FALSE = 0;
	}
	
	public static class CERTIFICATE{
		public static final int IS_RESPONSIBLE_TRUE = 1;
		public static final int IS_RESPONSIBLE_FALSE = 0;
	}
}
