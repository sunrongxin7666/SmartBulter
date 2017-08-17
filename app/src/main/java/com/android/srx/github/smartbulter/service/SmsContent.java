package com.android.srx.github.smartbulter.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.ui.DispatchLinearLayout;
import com.android.srx.github.smartbulter.utils.L;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.service
 * File: SmsContent
 * Created by sunrongxin on 2017/8/17 下午6:20.
 * Description: 监听数据库来获取新短信
 */


public class SmsContent extends ContentObserver {

	private static final String TAG = SmsContent.class.getSimpleName();
	private static final String MARKER = "SMS";
	private Cursor cursor = null;
	private Activity mActivity;
	private WindowManager wm;
	private DispatchLinearLayout mView;

	public SmsContent(Handler handler, Activity activity) {
		super(handler);
		this.mActivity = activity;
	}

	/**
	 * This method is called when a content change occurs.
	 * <p>
	 * Subclasses should override this method to handle content changes.
	 * </p>
	 *
	 * @param selfChange True if this is a self-change notification.
	 */
	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		Log.d(TAG, "onChange(boolean selfChange). selfChange=" + selfChange);
		onChange(selfChange, null);
	}

	/**
	 * Notice: onChange will be triggered twice on some devices when a sms received,
	 * eg: samsung s7 edge(API.23) - twice
	 *     samsung note3(API.18) - once
	 * 06-15 11:45:48.706 D/SmsContent: onChange(boolean selfChange, Uri uri). selfChange=false, uri=content://sms/raw
	 * 06-15 11:45:49.466 D/SmsContent: onChange(boolean selfChange, Uri uri). selfChange=false, uri=content://sms/387
	 *
	 * Generally onChange will be triggered twice, first time is triggered by uri "content://sms/raw"(sms received,
	 * but have not written into inbox), second time is triggered by uri "content://sms/387"(number is sms id)
	 *
	 * Android official comments:
	 * This method is called when a content change occurs.
	 * Includes the changed content Uri when available.
	 * <p>
	 * Subclasses should override this method to handle content changes.
	 * To ensure correct operation on older versions of the framework that
	 * did not provide a Uri argument, applications should also implement
	 * the {@link #onChange(boolean)} overload of this method whenever they
	 * implement the {@link #onChange(boolean, Uri)} overload.
	 * </p><p>
	 * Example implementation:
	 * <pre><code>
	 * // Implement the onChange(boolean) method to delegate the change notification to
	 * // the onChange(boolean, Uri) method to ensure correct operation on older versions
	 * // of the framework that did not have the onChange(boolean, Uri) method.
	 * {@literal @Override}
	 * public void onChange(boolean selfChange) {
	 *     onChange(selfChange, null);
	 * }
	 *
	 * // Implement the onChange(boolean, Uri) method to take advantage of the new Uri argument.
	 * {@literal @Override}
	 * public void onChange(boolean selfChange, Uri uri) {
	 *     // Handle change.
	 * }
	 * </code></pre>
	 * </p>
	 *
	 * @param selfChange True if this is a self-change notification.
	 * @param uri The Uri of the changed content, or null if unknown.
	 */
	@Override
	public void onChange(boolean selfChange, Uri uri) {
		Log.d(TAG, "onChange(boolean selfChange, Uri uri). selfChange=" + selfChange + ", uri=" + uri);

		/**
		 * 适配某些较旧的设备，可能只会触发onChange(boolean selfChange)方法，没有传回uri参数，
		 * 此时只能通过"content://sms/inbox"来查询短信
		 */
		if (uri == null) {
			uri = Uri.parse("content://sms/inbox");
		}
		/**
		 * 06-15 11:45:48.706 D/SmsContent: onChange(boolean selfChange, Uri uri). selfChange=false, uri=content://sms/raw
		 * 06-15 11:45:49.466 D/SmsContent: onChange(boolean selfChange, Uri uri). selfChange=false, uri=content://sms/387
		 *
		 * Generally onChange will be triggered twice, first time is triggered by uri "content://sms/raw"(sms received,
		 * but have not written into inbox), second time is triggered by uri "content://sms/387"(number is sms id)
		 */
		if (uri.toString().equals("content://sms/raw")) {
			return;
		}
		testShowWindow();
		// TODO: 2017/8/17 获取短信内容权限不足
//		cursor = this.mActivity.getContentResolver().query(uri, null, null, null, null);
//		if (cursor != null) {
//			if (cursor.moveToFirst()) {
//				int id = cursor.getInt(cursor.getColumnIndex("_id"));
//				String body = cursor.getString(cursor.getColumnIndex("body"));
//				Log.d(TAG, "sms id: " + id + "\nsms body: " + body);
//				cursor.close();
//
//				// Already got sms body, do anything you want, for example: filter the verify code
//				getVerifyCode(body);
//			}
//		}
//		else {
//			Log.e(TAG, "error: cursor == null");
//		}
	}

	/**
	 * Register a monitor of changing of sms
	 */
	public void register() {
		Log.d(TAG, "Register sms monitor");
		this.mActivity.getContentResolver().registerContentObserver(
				Uri.parse("content://sms/"), true, this);
	}

	/**
	 * Unregister the monitor of changing of sms
	 */
	public void unRegister() {
		Log.d(TAG, "Unregister sms monitor");
		this.mActivity.getContentResolver().unregisterContentObserver(this);
	}

	/**
	 * Get verify code from sms body
	 * @param str
	 * @return
	 */
	public String getVerifyCode(String str) {
		String verifyCode = null;
		if (smsContentFilter(str)) {
			Log.d(TAG, "sms content matched, auto-fill verify code.");
			verifyCode = getDynamicPassword(str);
		}
		else {
			// Do nothing
			Log.d(TAG, "sms content did not match, do nothing.");
		}
		return verifyCode;
	}

	/**
	 * Check if str is verification-code-formatted
	 *
	 * @param str
	 * @return
	 */
	private boolean smsContentFilter(String str) {
		Log.d(TAG, "smsContentFilter. smsBody = " + str);
		boolean isMatched = false;
		if (!TextUtils.isEmpty(str)) {
			// Check if str contains keyword
			if (str.contains(MARKER)) {
				Log.d(TAG, "This sms contains \"" + MARKER + "\"");
				// Check if str contains continuous 6 numbers
				Pattern continuousNumberPattern = Pattern.compile("[0-9\\.]+");
				Matcher m = continuousNumberPattern.matcher(str);
				while(m.find()){
					if(m.group().length() == 6) {
						Log.d(TAG, "This sms contains continuous 6 numbers : " + m.group());
						isMatched = true;
					}
				}
			}
		}
		return isMatched;
	}

	/**
	 * Cut the continuous 6 numbers from str
	 *
	 * @param str sms content
	 * @return verification code
	 */
	private String getDynamicPassword(String str) {
		Log.d(TAG, "getDynamicPassword. smsBody = " + str);
		Pattern  continuousNumberPattern = Pattern.compile("[0-9\\.]+");
		Matcher m = continuousNumberPattern.matcher(str);
		String dynamicPassword = "";
		while(m.find()){
			if(m.group().length() == 6) {
				Log.d(TAG, m.group());
				dynamicPassword = m.group();
			}
		}

		Log.d(TAG, "Verification code: " + dynamicPassword);
		return dynamicPassword;
	}

	private void testShowWindow() {
		//获取系统服务
		wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		//获取布局参数
		WindowManager.LayoutParams layoutparams = new WindowManager.LayoutParams();
		//定义宽高
		layoutparams.width = WindowManager.LayoutParams.MATCH_PARENT;
		layoutparams.height = WindowManager.LayoutParams.MATCH_PARENT;
		//定义标记
		layoutparams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕亮
				                     | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH; //监听触屏
		//定义格式 透明
		layoutparams.format = PixelFormat.TRANSLUCENT;

		// TODO: 2017/8/17 必须设定 format和type 否者将报错
		// TYPE_PHONE 不允许添加 改为TYPE_TOAST
		//定义类型
		layoutparams.type = WindowManager.LayoutParams.TYPE_TOAST;

		//加载布局
		mView = (DispatchLinearLayout) View.inflate(getApplicationContext(), R.layout.sms_item, null);

		TextView tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
		TextView tv_content = (TextView) mView.findViewById(R.id.tv_content);
		Button btn_send_sms = (Button) mView.findViewById(R.id.btn_send_sms);
		btn_send_sms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.btn_send_sms:
						sendSms();
						//消失窗口
						if (mView.getParent() != null) {
							wm.removeView(mView);
						}
						break;
				}
			}
		});

		//设置数据
		tv_phone.setText("发件人:" + "Sun");
		L.i("短信内容：" + "testWindow");
		tv_content.setText("testWindow");

		//添加View到窗口
		wm.addView(mView, layoutparams);

		//mView.setDispatchKeyEventListener(mDispatchKeyEventListener);
	}

	private void sendSms() {
		//回复短信
		Uri uri = Uri.parse("smsto:" + "123456");
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		//设置启动模式
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("sms_body", "");
		getApplicationContext().startActivity(intent);
	}
}
