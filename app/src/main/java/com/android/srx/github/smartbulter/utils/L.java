package com.android.srx.github.smartbulter.utils;

import android.util.Log;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.utils
 * File: L
 * Created by sunrongxin on 2017/7/19 上午10:39.
 * Description: Log Util : DIWE
 */

public class L {
	//开关
	private static final  boolean DEBUG = true;

	//TAG
	private static final String TAG = "SmartButler";

	static public void d(String text){
		if(DEBUG){
			Log.d(TAG,text);
		}
	}

	static public void i(String text){
		if(DEBUG){
			Log.i(TAG,text);
		}
	}

	static public void w(String text){
		if(DEBUG){
			Log.w(TAG,text);
		}
	}

	static public void e(String text){
		if(DEBUG){
			Log.e(TAG,text);
		}
	}
}
