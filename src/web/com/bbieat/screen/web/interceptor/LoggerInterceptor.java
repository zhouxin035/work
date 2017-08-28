package com.bbieat.screen.web.interceptor;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoggerInterceptor extends AbstractInterceptor {

	static Logger log = Logger.getLogger(LoggerInterceptor.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 2600307978246800434L;

	
	public String intercept(ActionInvocation ai) throws Exception {
		ActionContext ac = ai.getInvocationContext();
		ActionProxy px = ai.getProxy();
		if (log.isDebugEnabled()) {
			StringBuffer msg = new StringBuffer();
			msg.append("\n	<nameSpace>").append(px.getNamespace()).append("</nameSpace>\n");
			msg.append("	<actionName>").append(px.getActionName()).append("</actionName>\n");
			msg.append("	<method>").append(px.getMethod()).append("</method>\n");
			Map<String, Object> mp = ac.getParameters();
			Iterator<String> it = mp.keySet().iterator();
			while(it.hasNext()) {
				String pname = it.next();
				if(mp.get(pname) instanceof String[]){
					String pvalue = new String(((String[])mp.get(pname))[0].getBytes("ISO-8859-1"), "utf-8");
					msg.append("	<param name=\"").append(pname).append("\">").append(pvalue).append("</param>\n");
				}else{
					msg.append("	<param name=\"").append(pname).append("\">").append((pname)).append("</param>\n");
				}
			}
			log.debug(msg);
		}
		return ai.invoke();
	}

}
