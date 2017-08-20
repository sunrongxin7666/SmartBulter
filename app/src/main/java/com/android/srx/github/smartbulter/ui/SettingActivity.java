package com.android.srx.github.smartbulter.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.service.SmsService;
import com.android.srx.github.smartbulter.utils.L;
import com.android.srx.github.smartbulter.utils.SharedUtils;
import com.android.srx.github.smartbulter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.ui
 * File: SettingActivity
 * Created by sunrongxin on 2017/7/18 下午11:50.
 * Description: Setting Activity
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

	private String versionName;
	private int versionCode;
	private String url;

	private Switch sw_speak;
	private Switch sw_sms;

	private LinearLayout ll_update;
	private TextView tv_version;

	//扫一扫
	private LinearLayout ll_scan;
	//扫描的结果
	private TextView tv_scan_result;
	//生成二维码
	private LinearLayout ll_qr_code;
	//我的位置
	private LinearLayout ll_my_location;
	//关于软件
	private LinearLayout ll_about;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		sw_speak = (Switch) findViewById(R.id.sw_speak);
		sw_speak.setOnClickListener(this);

		sw_sms = (Switch) findViewById(R.id.sw_sms) ;
		sw_sms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SharedUtils.putBoolean(buttonView.getContext(),"isSms",isChecked);
				if(isChecked){
					startService(new Intent(getApplicationContext(),SmsService.class));
				}else {
					stopService(new Intent(getApplicationContext(), SmsService.class));
				}
			}
		});

		Boolean isSpeak = SharedUtils.getBoolen(this, "isSpeak", false);
		//不能使用setSelected
		sw_speak.setChecked(isSpeak);
		sw_sms.setChecked(SharedUtils.getBoolen(this,"isSms",false));

		ll_update = (LinearLayout) findViewById(R.id.ll_update);
		ll_update.setOnClickListener(this);
		tv_version = (TextView) findViewById(R.id.tv_version);

		try {
			getVersionNameCode();
			tv_version.setText(getString(R.string.text_test_version) + versionName);
		} catch (PackageManager.NameNotFoundException e) {
			tv_version.setText(getString(R.string.text_test_version) );
		}

		ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
		ll_scan.setOnClickListener(this);

		tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);

		ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
		ll_qr_code.setOnClickListener(this);

		ll_my_location = (LinearLayout) findViewById(R.id.ll_my_location);
		ll_my_location.setOnClickListener(this);

		ll_about = (LinearLayout) findViewById(R.id.ll_about);
		ll_about.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.sw_speak:
				//取反
				sw_speak.setSelected(!sw_speak.isSelected());
				//保存状态
				SharedUtils.putBoolean(this,"isSpeak",sw_speak.isChecked());
				break;
			case R.id.ll_update:
				L.i("ll_update");
				/**
				 * 步骤:
				 * 1.请求服务器的配置文件，拿到code
				 * 2.比较
				 * 3.dialog提示
				 * 4.跳转到更新界面，并且把url传递过去
				 */
				RxVolley.get(StaticClass.CHECK_UPDATE_URL, new HttpCallback() {
					@Override
					public void onSuccess(String t) {
						parsingJson(t);
					}
				});
				break;
			case R.id.ll_scan:
				L.i("ll_scan");
				//打开扫描界面扫描条形码或二维码
//                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
//                startActivityForResult(openCameraIntent, 0);
				break;
			case R.id.ll_qr_code:
				startActivity(new Intent(this, QrCodeActivity.class));
				break;
			case R.id.ll_my_location:
				startActivity(new Intent(this,LocationActivity.class));
				break;
			case R.id.ll_about:
				startActivity(new Intent(this,AboutActivity.class));
				break;
		}
	}

	private void parsingJson(String t) {
		try {
			JSONObject jsonObject = new JSONObject(t);
			int code = jsonObject.getInt("versionCode");
			url = jsonObject.getString("url");
			if(code > versionCode){
				showUpdateDialog(jsonObject.getString("content"));
			}else {
				Toast.makeText(this, "当前是最新版本", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	//弹出升级提示
	private void showUpdateDialog(String content) {
		new AlertDialog.Builder(this)
				.setTitle("有新版本啦！")
				//content
				.setMessage("修复多项Bug!")
				.setPositiveButton("更新", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(SettingActivity.this, UpdateActivity.class);
						intent.putExtra("url", url);
						startActivity(intent);
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//我什么都不做，也会执行dismiss方法
			}
		}).show();
	}

	//获取版本号/Code
	private void getVersionNameCode() throws PackageManager.NameNotFoundException {
		PackageManager pm = getPackageManager();
		PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
		versionName = info.versionName;
		versionCode = info.versionCode;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			tv_scan_result.setText(scanResult);
		}
	}
}
