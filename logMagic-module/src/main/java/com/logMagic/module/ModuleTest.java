package com.logMagic.module;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleTest {
	private final static Logger log=LogManager.getLogger(ModuleTest.class);
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			log.error("测试输出内容");
		}
	}
}
