package com.packt.webstore.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class AuditingInterceptor extends HandlerInterceptorAdapter
{
	Logger logger = Logger.getLogger("auditLogger");
	private String user;
	private String productId;
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object handler) throws Exception {
		if(request.getRequestURI().endsWith("products/add") && request.getMethod().equals("POST")){
			user = request.getRemoteUser();
			productId = request.getParameterValues("productId")[0];
		}
		return true;
	}
	
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object handler, Exception arg3) throws Exception {
		if(request.getRequestURI().endsWith("products/add") && response.getStatus()==302) {
			logger.info(String.format("Nowy produkt [%s] dodany przez %s , data %s", productId, user, new Date()));
			}
	}
}