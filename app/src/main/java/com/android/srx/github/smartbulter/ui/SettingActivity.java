package com.android.srx.github.smartbulter.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.service.SmsService;
import com.android.srx.github.smartbulter.utils.L;
import com.android.srx.github.smartbulter.utils.SharedUtils;

import static com.android.srx.github.smartbulter.R.id.btn_send_sms;
import static com.android.srx.github.smartbulter.R.id.tv_phone;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.ui
 * File: SettingActivity
 * Created by sunrongxin on 2017/7/18 下午11:50.
 * Description: Setting Activity
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

	private Switch sw_speak;
	private Switch sw_sms;

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
		}
	}
}
