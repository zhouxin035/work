package com.bbieat.screen.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @Description SpringContextUtil
 * @author ycl
 * @time Wed Apr 23 17:20:42 CST 2014
 */
public class SpringContextUtil implements ApplicationContextAware {

	/** Spring 应用上下文单例 */
	private static ApplicationContext ctx;

	/**
	 * 从应用上下文中取得 Bean 实例
	 * 
	 * @param beanName
	 *            Bean 实例名
	 * @return Bean 实例
	 */
	public static Object getBean(String beanName) {
		if (ctx == null) {
			return null;
		}

		return ctx.getBean(beanName);
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		ctx = context;
	}
}