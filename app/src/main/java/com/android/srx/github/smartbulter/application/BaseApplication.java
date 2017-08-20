package com.android.srx.github.smartbulter.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.android.srx.github.smartbulter.service.SmsContent;
import com.android.srx.github.smartbulter.service.SmsService;
import com.android.srx.github.smartbulter.utils.SharedUtils;
import com.android.srx.github.smartbulter.utils.StaticClass;
import com.baidu.mapapi.SDKInitializer;
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

		Boolean isSms = SharedUtils.getBoolen(getApplicationContext(), "isSms", false);
		if(isSms){
			startService(new Intent(getApplicationContext(),SmsService.class));
		}

		//在使用SDK各组件之前初始化context信息，传入ApplicationContext
		//注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
	}
}
