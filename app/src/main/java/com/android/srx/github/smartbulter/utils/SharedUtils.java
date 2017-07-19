package com.android.srx.github.smartbulter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.utils
 * File: SharedUtils
 * Created by sunrongxin on 2017/7/19 上午10:51.
 * Description: SharedPreferences 封装
 */

public class SharedUtils {
	private static final String fileName = "config";

	private void test(Context context){
		//传入文件名和模式（默认就是PRIVATE=0）
		SharedPreferences sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		editor.putString("key","value");
		editor.apply();
	}

	public static void putString(Context context, String key, String value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key,value);
		editor.apply();
	}

	public static String getString(Context context, String key, String defaultValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
		return sharedPreferences.getString(key,defaultValue);
	}

	public static void putInt(Context context, String key, int value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(key,value);
		editor.apply();
	}

	public static int getInt(Context context, String key, int defaultValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
		return sharedPreferences.getInt(key,defaultValue);
	}

	public static void putBoolean(Context context, String key, Boolean value){
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(key,value);
		editor.apply();
	}

	public static Boolean getBoolen(Context context, String key, Boolean defaultValue){
		SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, 0);
		return sharedPreferences.getBoolean(key,defaultValue);
	}

	//delete
	public static void deleteSharedValue(Context context, String key){
		SharedPreferences sp = context.getSharedPreferences(fileName, 0);
		sp.edit().remove(key).apply();
	}
	public static void deleteAllShared(Context context){
		SharedPreferences sp = context.getSharedPreferences(fileName, 0);
		sp.edit().clear().apply();
	}
}
