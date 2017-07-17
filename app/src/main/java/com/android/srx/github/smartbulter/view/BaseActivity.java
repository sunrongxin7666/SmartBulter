package com.android.srx.github.smartbulter.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.view
 * File: BaseActivity
 * Created by sunrongxin on 2017/7/17 下午7:07.
 * Description: Activity Based Class
 */

/**
 * 主要的功能：
 * 1. 统一的接口
 * 2. 统一的接口
 * 3. 统一的方法
 */
public class BaseActivity extends AppCompatActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//显示返回键
		getSupportActionBar().setDisplayShowCustomEnabled(true);
	}

	//菜单栏显示
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.support.v7.appcompat.R.id.home:
				finish();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
