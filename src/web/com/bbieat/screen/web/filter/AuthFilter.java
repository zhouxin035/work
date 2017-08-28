package com.bbieat.screen.web.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Filter implementation class AuthFilter
 */
public class AuthFilter implements Filter {

	static Logger log = Logger.getLogger(AuthFilter.class);

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		chain.doFilter(request, response);
		return;
	}

	/**
	 * 正则表达式匹配
	 * 
	 * @param s
	 *            处理字符
	 * @param format
	 *            匹配表达式
	 * @return
	 */
	public static boolean match(String s, String format) {
		return s.indexOf(format) > -1;
	}

	/**
	 * 响应客户端
	 * 
	 * @param response
	 * @param resultJs
	 * @throws IOException
	 */
	public void pringMsg(HttpServletResponse response, String resultJs) throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script type='text/javascript'>" + resultJs + "</script>");
		out.flush();
		out.close();
	}

	/**
	 * Default constructor.
	 */
	public AuthFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
