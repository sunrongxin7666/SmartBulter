package com.android.srx.github.smartbulter.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.srx.github.smartbulter.MainActivity;
import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.application.BaseApplication;
import com.android.srx.github.smartbulter.utils.SafeHandler;
import com.android.srx.github.smartbulter.utils.SharedUtils;
import com.android.srx.github.smartbulter.utils.StaticClass;
import com.android.srx.github.smartbulter.utils.UtilTools;
import com.squareup.leakcanary.RefWatcher;

/**
 * 1. 延时加载2000ms，然后自动跳转到下一页
 * 2. 判断是否第一次记载
 * 3. 自定义字体
 * 4. 全屏主题
 */
public class SplashActivity extends AppCompatActivity {

	private TextView mTextView;

	private Handler mHandler;

	//
	private boolean isFirst() {
		Boolean isFirst = SharedUtils.getBoolen(this, StaticClass.SHARE_IS_FIRST_RUN, true);
		if(isFirst){//是第一次运行
			//标记以后已经不是第一次运行了
			SharedUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST_RUN,false);
			return true;
		}
		else {//不是第一次运行
			return isFirst;
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initHandler();
		initView();

		UtilTools.setFront(this,mTextView);
	}

	private void initHandler() {
		mHandler = new MySafeHandler(this);
	}

	private void initView() {
		Message message = Message.obtain();
		message.what = StaticClass.HANDLER_SPLASH;
		message.arg1 = isFirst()?1:0;
		//延时2000
		mHandler.sendMessageDelayed(message,2000);
		mTextView = (TextView) findViewById(R.id.tv_splash);
	}

	@Override
	public void onBackPressed() {
		//禁止返回键
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
		refWatcher.watch(this);
	}

	/**
	 * 安全Handler的实现
	 */
	private static class MySafeHandler extends SafeHandler{
		MySafeHandler(Activity activity){
			super(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			Activity activity = mActivity.get();
			switch (msg.what){
				case StaticClass.HANDLER_SPLASH:
					//是否第一次运行
					if (msg.arg1 == 1) {//进入引导页
						activity.startActivity(new Intent(activity.getApplicationContext(), GuideActivity.class));
					} else if (msg.arg1 == 0) {//进入主页
						activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
					}
					activity.finish();
					break;
			}
		}
	}
}
