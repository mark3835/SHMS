package tcb.shms.module.config;

public class SystemConfig {
	
	public static final String SYSTEM_NAME = "SHMS";
	
	public class SESSION_KEY{
		public static final String LOGIN = "SESSION_KEY_LOGIN";
	}

	public class LDAP{
		public static final String URL = "LDAPS://ITDC01.TCB.COM:3269";
	}
	
	public class CFG_TYPE{
		public static final String DEFAULT_AUTH_URL = "DEFAULT_AUTH_URL";
		public static final String SYSTEM_ADMIN = "SYSTEM_ADMIN";
	}
	
	public class ANNOUNCEMENT{
		public static final String FILE_ROOT_PATH = "announcementFile/";
		public static final String FILE_TYPE_PDF = "application/pdf";
	}
	
	public class AUTH_LV{
		public static final int AFFAIRS = 1;
		public static final int SAFE_MANAGER = 2;
		public static final int FIRE_HELPER = 3;
		public static final int HELPER = 4;
		public static final int MANAGER = 5;
		public static final int JUNIOR_MANAGER = 6;
		public static final int SYSTEM_ADMIN = 7;
	}
}
