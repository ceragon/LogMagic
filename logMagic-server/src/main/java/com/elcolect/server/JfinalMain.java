package com.elcolect.server;

import com.jfinal.core.JFinal;

public class JfinalMain {
	public static void main(String[] args) {
		JFinal.start("src/main/webapp", 8080, "/", 1);
	}
}
