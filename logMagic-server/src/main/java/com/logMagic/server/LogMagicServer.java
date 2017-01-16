package com.logMagic.server;

import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.logMagic.server.elastic.ElasticSearchHandler;
import com.logMagic.server.handler.AbstractHandler;

public class LogMagicServer {
	private final static Logger log=LogManager.getLogger(LogMagicServer.class);
	private static AbstractHandler handler=null;
	public static void start(){
		try {
			String host=PropKit.get("ElasticSearchHost");
			handler=new ElasticSearchHandler(host);
			log.info("LogMagicServer start success!!!");
		} catch (Exception e) {
			log.error(e,e);
		}
	}
	private static void destroy(){
		JFinal.stop();
	}
	
	public static AbstractHandler getHandler() {
		return handler;
	}
	public static void main(String[] args) throws UnknownHostException {
		JFinal.start("src/main/webapp", 8080, "/", 1);
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run() {
				LogMagicServer.destroy();
			}
		});
	}
}
