package com.android.srx.github.smartbulter.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.utils
 * File: SafeHandler
 * Created by sunrongxin on 2017/7/19 下午3:49.
 * Description: 使用若引用和静态类的方式避免内存泄漏
 */

public abstract class SafeHandler extends Handler {
	//弱引用<引用外部类>
	protected WeakReference<Activity> mActivity;

	protected SafeHandler(Activity activity) {
		//构造创建弱引用
		mActivity = new WeakReference<>(activity);
	}

	@Override
	abstract public void handleMessage(Message msg);
}
