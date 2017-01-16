package com.elcolect.server.jfinal;

import com.elcolect.server.jfinal.controller.ELogController;
import com.elcolect.server.jfinal.controller.IndexController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.render.ViewType;

public class MyJFinalConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		me.setDevMode(true);
		me.setViewType(ViewType.VELOCITY);
	}

	@Override
	public void configHandler(Handlers arg0) {
		
	}
	@Override
	public void configInterceptor(Interceptors arg0) {
		
	}

	@Override
	public void configPlugin(Plugins arg0) {
		
	}
	@Override
	public void configRoute(Routes me) {
		me.add("index", IndexController.class);
		me.add("elog", ELogController.class);
	}
	@Override
	public void afterJFinalStart() {
	}
	@Override
	public void beforeJFinalStop() {
	}
	

}
