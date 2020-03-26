package tcb.shms.core.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import tcb.shms.module.config.SystemConfig;
import tcb.shms.module.entity.Authorizastion;
import tcb.shms.module.entity.Config;
import tcb.shms.module.service.AuthorizastionService;
import tcb.shms.module.service.ConfigService;
import tcb.shms.module.service.ErrorLogService;
import tcb.shms.module.service.MenuService;

/**
   * 驗證是否登入
 * @author MARK3835
 *
 */
public class LoginSessionFilter implements Filter{
	
	private final Logger log = LogManager.getLogger(getClass());
	
	private static List<String> passUrls = new ArrayList<>();
	private static String ctxPath = null;
	private static String redirectUrl = "";
	private static String passH2URL = null;
	
	MenuService menuService;

	AuthorizastionService authorizastionService;
	
	ErrorLogService  errorLogService;
	
	ConfigService configService;
	
	List<Config> defaultAuthList;
	
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {

		// 获取web.xml中的初始化参数
        String ignoreURL = filterConfig.getInitParameter("passURL");
        redirectUrl = filterConfig.getInitParameter("redirectURL");

        // 保存不拦截的url
        String[] ignoreURLArray = ignoreURL.split(",");
        for (String url : ignoreURLArray) {
            passUrls.add(url.trim());
        }
        ctxPath = filterConfig.getServletContext().getContextPath();
        passH2URL = filterConfig.getInitParameter("passH2URL");
        log.info("ctx = " + ctxPath);
        log.info("不拦截的URL包括:");        
        for (String url : passUrls) {
        	log.info(url);
        }
        log.info("passH2URL = " + passH2URL);
        
        //spring autowired在filter是無效的
        //手動塞
        ServletContext sc = filterConfig.getServletContext(); 
        XmlWebApplicationContext cxt = (XmlWebApplicationContext)WebApplicationContextUtils.getWebApplicationContext(sc);        
        if(cxt != null && cxt.getBean("authorizastionService") != null && authorizastionService == null) {
        	authorizastionService  = (AuthorizastionService) cxt.getBean("authorizastionService");  
        }           
        if(cxt != null && cxt.getBean("errorLogService") != null && errorLogService == null) {
        	errorLogService  = (ErrorLogService) cxt.getBean("errorLogService");  
        }           
        if(cxt != null && cxt.getBean("menuService") != null && menuService == null) {
        	menuService  = (MenuService) cxt.getBean("menuService");  
        } 
        if(cxt != null && cxt.getBean("configService") != null && configService == null) {
        	configService  = (ConfigService) cxt.getBean("configService");  
        	Config config = new Config();
			config.setCfgType(SystemConfig.CFG_TYPE.DEFAULT_AUTH_URL);
			config.setCfgInUse(1);
			try {
				defaultAuthList = configService.getList(config);
			} catch (Exception e) {
				log.error(e);
				errorLogService.addErrorLog(this.getClass().getName(), e);
			}
        } 
    }

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 請求url
        String url = request.getRequestURI();        
        // 相對路徑
        String subUrl = url.substring(ctxPath.length() + 1);
        
        log.info("url:" + url);
        log.info("subUrl:" + subUrl);
        
        // h2 後台管理 pass
        if (url.startsWith(ctxPath + passH2URL)) {
        	log.info("subUrl.startsWith(" + ctxPath + passH2URL + "):" + url + "__H2後台管理放行");
            filterChain.doFilter(request, response);
            return;
        }
        
        //不用登入就能取得的URL資源
        for (String urlStr : passUrls) {
            if (subUrl.indexOf(urlStr) > -1) {
            	log.info("subUrl.indexOf(urlStr):" + subUrl.indexOf(urlStr) + "__不用登入放行");
                filterChain.doFilter(request, response);
                return;
            }
        }
        
        // 获得session
        HttpSession session = request.getSession();
        // 从session中获取SessionKey对应值,若值不存在,则重定向到redirectUrl
        Object user = session.getAttribute(SystemConfig.SESSION_KEY.LOGIN);
        log.info("user:" + user);
        if (user != null) {
        	//公告跟MENU只要登入就能看到 預設放行
        	if(this.checkUrlDefaultAuth(subUrl)) {
        		log.info("user:" + user);
        		filterChain.doFilter(request, response);
        		return;
        	};
        	//登入後還需驗證權限
        	if(!this.checkUrlHaveAuth(user, subUrl)) {
        		log.info("user:" + user);
        		response.getWriter().write("<script>alert('no rule')</script>");
        		return;
        	};
        	
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(ctxPath + "/" + redirectUrl);
        }
	}
	
	/**
	 * 判斷進來的URL是否有權限存取
	 * @param user
	 * @param subUrl
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private boolean checkUrlHaveAuth(Object user, String subUrl){
		try {
			//@TODO 撈取使用者權限
			String account = ObjectUtils.identityToString(user);
			int accountAuthLv = 0;
			Authorizastion authorizastion = new Authorizastion();
			authorizastion.setAuthLv(accountAuthLv);
			List<Authorizastion> authorizastionList = authorizastionService.getList(authorizastion);
			String sql = "SELECT MENU_API_URL FROM MENU WHERE MENU_API_URL IS NOT NULL AND ( 1=1 ";
			for(Authorizastion auth:authorizastionList) {
				sql += " OR id = " + auth.getMenuId() ;
			}
			sql += ")";
			List<Map> authList = menuService.getListBySQLQuery(sql);
			for(Map map:authList) {
				if(subUrl.startsWith(MapUtils.getString(map, "MENU_API_URL"))) {
					log.info("subUrl.startsWith(MapUtils.getString(map, \"MENU_API_URL\"):" + MapUtils.getString(map, "MENU_API_URL") + "__擁有此使用者權限放行");
					return true;
				}
			}
		} catch (Exception e) {
			log.error(e);
			errorLogService.addErrorLog(this.getClass().getName(), e);
		}
		return false;
	}
	
	/**
	 * 判斷進來的URL是否為預設放行
	 * @param user
	 * @param subUrl
	 * @return
	 * @throws Exception
	 */
	private boolean checkUrlDefaultAuth(String subUrl){
		for(Config conf: defaultAuthList) {
			if(subUrl.startsWith(conf.getCfgValue())) {
				log.info("subUrl.startsWith(conf.getCfgValue()):" + conf.getCfgValue() + "__預設放行");
				return true;
			}
		}
		return false;
	}

}
