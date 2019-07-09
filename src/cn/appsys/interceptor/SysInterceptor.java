package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class SysInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = Logger.getLogger(SysInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		// logger.debug("SysInterceptor preHandle! ");
		// HttpSession session=request.getSession();
		// User user=(User)session.getAttribute("userSession");
		// if(null == user){
		// response.sendRedirect(request.getContextPath()+"/user/401.html");
		// return false;
		// }
		return true;
	}
}
