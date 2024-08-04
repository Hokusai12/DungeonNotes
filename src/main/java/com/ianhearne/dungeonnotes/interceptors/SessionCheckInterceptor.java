package com.ianhearne.dungeonnotes.interceptors;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionCheckInterceptor implements HandlerInterceptor{
	@Override
	public boolean preHandle(
			HttpServletRequest request,
			HttpServletResponse response,
			Object handler) throws Exception {
		HttpSession session = request.getSession();
		String requestUrl = request.getRequestURL().toString();
		
		if(requestUrl.contains("/css/") 
				|| requestUrl.contains("/js/") 
				|| requestUrl.contains("/fonts/")
				|| requestUrl.contains("/icons/")) {
			return true;
		}
		
		List<StringBuffer> safeUrls = new ArrayList<StringBuffer>();
		safeUrls.add(new StringBuffer("http://localhost:8080/"));
		safeUrls.add(new StringBuffer("http://localhost:8080/login"));
		safeUrls.add(new StringBuffer("http://localhost:8080/logout"));
		safeUrls.add(new StringBuffer("http://localhost:8080/register"));
		
		for(StringBuffer safeUrl : safeUrls) {
			if(safeUrl.toString().equals(requestUrl)) {
				return true;
			}
		}
		
		if(session.getAttribute("userId") == null) {
			response.sendRedirect("/logout");
			return false;
		}
		
		return true;
	}
}
