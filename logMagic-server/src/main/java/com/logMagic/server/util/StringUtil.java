package com.logMagic.server.util;

public class StringUtil {
	public static boolean isEmpty(String str) {
		boolean mReturn=false;
		if (str==null||str.equals("")) {
			mReturn=true;
		}
		return mReturn;
	}
}
