package com.bbieat.screen.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AmrFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse rep,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest requset = (HttpServletRequest)req;
		HttpServletResponse reponse = (HttpServletResponse)rep;
		requset.setCharacterEncoding("utf-8");
		String method = requset.getMethod();
		if("DELETE".equals(method) || "PUT".equals(method) || "OPTIONS".equals(method)) {
			reponse.sendError(404, "会话访问出错");
		} else {
			reponse.setHeader("Pragma", "public");
			reponse.setHeader("Cache-Control", "public");
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response =(HttpServletResponse)rep;
 			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
