package tcb.shms.core.service;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.Unit;
import tcb.shms.module.entity.User;
import tcb.shms.module.service.ErrorLogService;
import tcb.shms.module.service.UnitService;
import tcb.shms.module.service.UserService;

@Service
public class LdapService {

	private final static Logger log = LogManager.getLogger(LdapService.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	ErrorLogService  errorLogService;
	
	static String returnedAtts[] = { 
			"cn", 
			"department", 
			"tcbDeptID", 
			"displayname", 
			"mail", 
			"tcbJobLevel", 
			"title",
			"rocid",
			"info",
			"ou",
			"birthday",
			"memberOf",
			"telephoneNumber",
			"pager"
	};
		
	public boolean checkADAccount(String account, String password){
		Hashtable<String, String> tbl = new Hashtable<String, String>(4);
	    String LDAP_URL = SystemConfig.LDAP.URL;
	    tbl.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    tbl.put(Context.PROVIDER_URL, LDAP_URL);
	    tbl.put(Context.SECURITY_AUTHENTICATION, "simple");
	    tbl.put(Context.SECURITY_PRINCIPAL, account + "@tcb.com");
	    tbl.put(Context.SECURITY_CREDENTIALS, password);
	    DirContext context = null;
	    try {
	    	log.info("login verification begins...");
	        trustSelfSignedSSL();
	        context = new InitialDirContext(tbl);
	        log.info("login successfully.");
	        
	    } catch (Exception ex) {
	    	log.error(ex);
			errorLogService.addErrorLog(this.getClass().getName(), ex);
            return false;
	    } finally {
	        try {
	            if (context != null) {
	                context.close();
	                context = null;
	            }
	            tbl.clear();
	        } catch (Exception e) {
	        	log.info(" finally exception happened.");	           
	        }
	    }
		return true;
	  
	}
	
	/**
	 * ldaps TLS放行
	 */
	public static void trustSelfSignedSSL() {
	    try {
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        X509TrustManager tm = new X509TrustManager() {

	            @Override
	            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            @Override
	            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            }

	            @Override
	            public X509Certificate[] getAcceptedIssuers() {
	                return null;
	            }
	        };
	        ctx.init(null, new TrustManager[] { tm }, null);
	        SSLContext.setDefault(ctx);
	        } catch (Exception ex) {
	        	log.error(ex);
	        }
	}
	
	
	public static void main(String args[]) {
		new LdapService().getADUserTest("mark3835", "5tgb^YHN");
	}
	
	/**
	   *   撈取 AD user資料跟單位 新增或更新
	 * @param account
	 * @param password
	 * @return
	 */
	public void getADUserAndUnitToDb(String account, String password){
		Hashtable<String, String> tbl = new Hashtable<String, String>(4);
	    String LDAP_URL = SystemConfig.LDAP.URL;
	    tbl.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    tbl.put(Context.PROVIDER_URL, LDAP_URL);
	    tbl.put(Context.SECURITY_AUTHENTICATION, "simple");
	    tbl.put(Context.SECURITY_PRINCIPAL, account + "@tcb.com");
	    tbl.put(Context.SECURITY_CREDENTIALS, password);
	    DirContext context = null;
	    try {
	    	log.info("login verification begins...");
	        trustSelfSignedSSL();
	        context = new InitialDirContext(tbl);
	        String searchFilter = "(&(objectClass=user)(rocid=*)(ou=*))";
	        String searchBase = "DC=tcb,DC=com";
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctrl.setReturningAttributes(returnedAtts);
			NamingEnumeration answer = context.search(searchBase, searchFilter, ctrl);

			int count = 0;
			List<User> userList = new ArrayList<User>();
			Map<String, Unit> unitMap = new HashedMap<String, Unit>();
			while (answer.hasMore()) {
				SearchResult result = (SearchResult) answer.next();
				Attributes  attrs= result.getAttributes();
				
				//有帳號沒有tcbJobLevel  董事長也沒 讓他過
				if(attrs.get("tcbJobLevel") == null && attrs.get("title") != null  && !String.valueOf(attrs.get("title").get()).equals("董事長")) {
					continue;
				}
				
				User user = new User();
				user.setRocId(String.valueOf(attrs.get("rocid").get()));
				
				//用ROCID查詢是否已存在
				List<User> queryList = userService.getList(user);
				if(attrs.get("displayname") != null && attrs.get("displayname").get() != null) {
					user.setName(String.valueOf(attrs.get("displayname").get()));
				}
				if(attrs.get("ou") != null && attrs.get("ou").get() != null) {
					user.setUnitId(String.valueOf(attrs.get("ou").get()));
				}
				if(attrs.get("title") != null && attrs.get("title").get() != null) {
					user.setJobName(String.valueOf(attrs.get("title").get()));
				}
				//董事長 沒 tcbJobLevel				
				if(attrs.get("tcbJobLevel") != null && attrs.get("tcbJobLevel").get() != null) {
					user.setJobLevel(Integer.valueOf(attrs.get("tcbJobLevel").get().toString()));
				}else if(String.valueOf(attrs.get("title").get()).equals("董事長")) {
					//總經理15 顧問16 董事長給個20等好了
					user.setJobLevel(20);
				}
				if(attrs.get("cn") != null && attrs.get("cn").get() != null) {
					user.setAccount(String.valueOf(attrs.get("cn").get()));
				}
				if(attrs.get("mail") != null && attrs.get("mail").get() != null) {
					user.setEmail(String.valueOf(attrs.get("mail").get()));
				}
				if(attrs.get("birthday") != null && attrs.get("birthday").get() != null) {
					user.setBirthday(String.valueOf(attrs.get("birthday").get()));
				}
				if(attrs.get("pager") != null && attrs.get("pager").get() != null) {
					user.setPager(String.valueOf(attrs.get("pager").get()));
				}
				if(attrs.get("telephoneNumber") != null && attrs.get("telephoneNumber").get() != null) {
					user.setTel(String.valueOf(attrs.get("telephoneNumber").get()));
				}
				user.setIsLeave(0);
				
				//已存在 比對資料
				if(queryList.size() > 0) {
					User oldUser = queryList.get(0);
					boolean isChange = false;
					user.setId(oldUser.getId());
					//比對資料是否有異動
					if(oldUser.getJobLevel() != user.getJobLevel()) {
						isChange = true;
					}else if(oldUser.getJobName() != user.getJobName()) {
						isChange = true;
					}else if(oldUser.getUnitId() != user.getUnitId()) {
						isChange = true;
					}else if(oldUser.getEmail() != user.getEmail()) {
						isChange = true;
					}else if(oldUser.getAccount() != user.getAccount()) {
						isChange = true;
					}
					if(isChange) {
						userService.update(user);
					}
				}else {
					userService.save(user);
				}
				
				
				String unitId = String.valueOf(attrs.get("ou").get());
				if(!unitMap.containsKey(unitId)) {
					Unit unit = new Unit();
					unit.setUnitId(unitId);
					List<Unit> unitList = unitService.getList(unit);
					if(unitList.size() == 0) {
						unit.setUnitName(String.valueOf(attrs.get("department").get()));
						unitService.save(unit);
					}	
					unitMap.put(unitId, unit);
				}
				
				
				count++;
			}
			answer.close();
	        log.info("login successfully.");
	        
	        log.info("查詢筆數:" + count);
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    	log.error(ex);
			errorLogService.addErrorLog(this.getClass().getName(), ex);
	    } finally {
	        try {
	            if (context != null) {
	                context.close();
	                context = null;
	            }
	            tbl.clear();
	        } catch (Exception e) {
	        	log.info("finally exception happened.");	           
	        }
	    }
	  
	}
	
	
	
	/**
	   *   撈取 AD user資料跟單位 新增或更新
	 * @param account
	 * @param password
	 * @return
	 */
	public void getADUserTest(String account, String password){
		Hashtable<String, String> tbl = new Hashtable<String, String>(4);
	    String LDAP_URL = SystemConfig.LDAP.URL;
	    tbl.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    tbl.put(Context.PROVIDER_URL, LDAP_URL);
	    tbl.put(Context.SECURITY_AUTHENTICATION, "simple");
	    tbl.put(Context.SECURITY_PRINCIPAL, account + "@tcb.com");
	    tbl.put(Context.SECURITY_CREDENTIALS, password);
	    System.out.println("env setting");
	    DirContext context = null;
	    try {
	    	log.info("login verification begins...");
	        trustSelfSignedSSL();
	        context = new InitialDirContext(tbl);
	        String searchFilter = "(&(objectClass=user)(rocid=*)(ou=*))";
	        String searchBase = "DC=tcb,DC=com";
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctrl.setReturningAttributes(returnedAtts);
			NamingEnumeration answer = context.search(searchBase, searchFilter, ctrl);

			int count = 0;
			while (answer.hasMore()) {
				SearchResult result = (SearchResult) answer.next();
				Attributes  attrs= result.getAttributes();
				
				//用ROCID查詢是否已存在
				try {
					String aaa = attrs.get("cn").get() + " ; " + attrs.get("displayname").get() + " ; " + attrs.get("ou").get();
				} catch (Exception e) {
					log.info(attrs.get("cn").get());
				}
				
				
				count++;
			}
			answer.close();
	        log.info("login successfully.");
	        
	        log.info("查詢筆數:" + count);
	    } catch (Exception ex) {
	    	log.error(ex);
			errorLogService.addErrorLog(this.getClass().getName(), ex);
	    } finally {
	        try {
	            if (context != null) {
	                context.close();
	                context = null;
	            }
	            tbl.clear();
	        } catch (Exception e) {
	        	log.info("finally exception happened.");	           
	        }
	    }
	  
	}
	
	
}
