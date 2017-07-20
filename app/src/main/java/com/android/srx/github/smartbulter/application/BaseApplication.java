package com.android.srx.github.smartbulter.application;

import android.app.Application;
import android.content.Context;

import com.android.srx.github.smartbulter.utils.StaticClass;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;


public class BaseApplication extends Application {
	public static RefWatcher getRefWatcher(Context context) {
		BaseApplication application = (BaseApplication) context.getApplicationContext();
		return application.refWatcher;
	}

	private RefWatcher refWatcher;
	@Override
	public void onCreate() {
		super.onCreate();
		refWatcher = LeakCanary.install(this);

		//初始化Bugly
		CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APPID, true);
	}
}
