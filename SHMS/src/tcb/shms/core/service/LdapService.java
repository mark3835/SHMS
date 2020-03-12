package tcb.shms.core.service;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import tcb.shms.module.config.SystemConfig;

@Service
public class LdapService {

	@SuppressWarnings("unused")
	private final Logger log = LogManager.getLogger(getClass());
	
	static String returnedAtts[] = { 
			"cn", 
			"sn", 
			"distinguishedName", 
			"displayname", 
			"userAccountControl", 
			"name", 
			"memberOf",
			"rocid" 
	};
		
	public boolean checkADAccount(String account, String password){
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
	        System.out.println("login verification begins...");
	        trustSelfSignedSSL();
	        context = new InitialDirContext(tbl);
//	        String searchBase = "DC=tcb,DC=com";
//	        String searchFilter = "((objectClass=user)&(cn=" + account + "))";
//			SearchControls ctrl = new SearchControls();
//			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
//			ctrl.setReturningAttributes(returnedAtts);
//			NamingEnumeration answer = context.search(searchBase, searchFilter, ctrl);
//
//			int count = 0;
//			while (answer.hasMore()) {
//				SearchResult result = (SearchResult) answer.next();
//				System.out.println(result);
//				count++;
//			}
//			answer.close();
	        System.out.println("login successfully.");
	        
//	        System.out.println("查詢筆數:" + count);
	    } catch (Exception ex) {
	        System.out.println("login failed.");
	        System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
	    } finally {
	        try {
	            if (context != null) {
	                context.close();
	                context = null;
	            }
	            tbl.clear();
	        } catch (Exception e) {
	            System.out.println("exception happened.");	           
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
	            ex.printStackTrace();
	        }
	}
	
	
}
