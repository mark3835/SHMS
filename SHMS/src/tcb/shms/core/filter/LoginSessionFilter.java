package tcb.shms.core.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tcb.shms.module.config.SystemConfig;

/**
   * 驗證是否登入
 * @author MARK3835
 *
 */
public class LoginSessionFilter implements Filter{
	
	private final Logger log = LogManager.getLogger(getClass());
	
	private static List<String> passUrls = new ArrayList<>();
	private String ctxPath = null;
	private static String redirectUrl = "";
	
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
        log.info("ctx = " + ctxPath);
        log.info("不拦截的URL包括:");
        for (String url : passUrls) {
        	log.info(url);
        }
    }

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 请求的url
        String url = request.getRequestURI();        
        // 相对路径
        String subUrl = url.substring(ctxPath.length() + 1);
        
        log.info("url:" + url);
        log.info("subUrl:" + subUrl);
        
        for (String urlStr : passUrls) {
            // 如果匹配, 则放行
            if (subUrl.indexOf(urlStr) > -1) {
            	log.info("subUrl.indexOf(urlStr):" + subUrl.indexOf(urlStr) + "__放行");
                filterChain.doFilter(request, response);
                return;
            }
        }
	

        // 获得session
        HttpSession session = request.getSession();
        // 从session中获取SessionKey对应值,若值不存在,则重定向到redirectUrl
        Object user = session.getAttribute(SystemConfig.SESSION_KEY.LOGIN_SESSION_KEY);
        log.info("user:" + user);
        if (user != null) {
            filterChain.doFilter(request, response);
        } else {
            response.sendRedirect(ctxPath + "/" + redirectUrl);
        }
	}

}
