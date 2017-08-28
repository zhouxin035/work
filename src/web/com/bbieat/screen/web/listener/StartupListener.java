package com.bbieat.screen.web.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoaderListener;

import com.holley.eemas.util.Constants;
import com.holley.eemas.util.FileUtil;

/**
 * 系统启动监听
 */
public class StartupListener extends ContextLoaderListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void init() throws Exception {
		Constants.LOGGER  = "/home/files/" + "loggers" + File.separator;
		Constants.TEMPFILE =  "/home/files/" + "files" + File.separator ;
		FileUtil.createPath(Constants.LOGGER, true);
		FileUtil.createPath(Constants.TEMPFILE, true);
	}

}
