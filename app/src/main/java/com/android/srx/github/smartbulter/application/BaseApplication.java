package com.android.srx.github.smartbulter.application;

import android.app.Application;
import android.content.Context;

import com.android.srx.github.smartbulter.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;


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

		//初始化Bmob
		Bmob.initialize(this, StaticClass.BMOB_APPID);

		// 将“12345678”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
		SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=" + StaticClass.VOICE_KEY);

		//初始化Bugly
		CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APPID, true);
	}
}
