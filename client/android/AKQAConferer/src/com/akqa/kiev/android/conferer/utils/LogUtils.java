package com.akqa.kiev.android.conferer.utils;

import android.util.Log;

public class LogUtils {

	private LogUtils() {
	}

	public static void logE(String tag, Exception e) {
		String msg = (e != null && e.getMessage() != null) ? e.getMessage()
				: "NullPointerException";
		Log.e(tag, msg);
	}
}
