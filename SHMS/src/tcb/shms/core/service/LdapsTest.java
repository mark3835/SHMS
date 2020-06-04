package tcb.shms.core.service;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Hashtable;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LdapsTest {

	private final static Logger log = LogManager.getLogger(LdapsTest.class);
		
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
		
	public static void checkADAccount(String account, String password){
		Hashtable<String, String> tbl = new Hashtable<String, String>(4);
	    String LDAP_URL = "LDAPS://ITDC01.TCB.COM:3269";
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
				
				try {
					String aaa = attrs.get("cn").get() + " ; " + attrs.get("displayname").get() + " ; " + attrs.get("ou").get();
					log.info("data:" + aaa);
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
		checkADAccount("account", "password");
	}
	
}
