package com.android.srx.github.smartbulter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.MyUser;
import com.android.srx.github.smartbulter.utils.SharedUtils;
import com.android.srx.github.smartbulter.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

	private EditText et_name;
	private EditText et_password;
	private CheckBox keep_password;

	private CustomDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {

		et_name = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_password);
		keep_password = (CheckBox) findViewById(R.id.keep_password);

		boolean isKeep = SharedUtils.getBoolen(this, "keeppass", false);
		keep_password.setChecked(isKeep);

		dialog = new CustomDialog(this, 130, 130, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
		dialog.setCancelable(false);

		if(isKeep){
			String name = SharedUtils.getString(this, "name", "");
			String password = SharedUtils.getString(this, "password", "");
			et_name.setText(name);
			et_password.setText(password);
		}
	}

	public void doRegister(View view) {
		startActivity(new Intent(this,RegisteredActivity.class));
	}

	public void doLogin(View view) {
		//1.获取输入框的值
		String name = et_name.getText().toString().trim();
		String password = et_password.getText().toString().trim();
		//2.判断是否为空
		if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
			dialog.show();
			//登录
			final MyUser user = new MyUser();
			user.setUsername(name);
			user.setPassword(password);
			user.login(new SaveListener<MyUser>() {
				@Override
				public void done(MyUser myUser, BmobException e) {
					dialog.dismiss();
					//判断结果
					if (e == null) {
						//判断邮箱是否验证
						if (user.getEmailVerified()) {
							//跳转
							startActivity(new Intent(LoginActivity.this, MainActivity.class));
							finish();
						} else {
							Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(LoginActivity.this, "登录失败：" + e.toString(), Toast.LENGTH_SHORT).show();
					}
				}
			});
		} else {
			Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	//假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
	@Override
	protected void onDestroy() {
		super.onDestroy();

		//保存状态
		SharedUtils.putBoolean(this, "keeppass", keep_password.isChecked());

		//是否记住密码
		if (keep_password.isChecked()) {
			//记住用户名和密码
			SharedUtils.putString(this, "name", et_name.getText().toString().trim());
			SharedUtils.putString(this, "password", et_password.getText().toString().trim());
		} else {
			SharedUtils.deleteSharedValue(this, "name");
			SharedUtils.deleteSharedValue(this, "password");
		}
	}

	public void doReset(View view) {
		startActivity(new Intent(this,ForgetPasswordActivity.class));
	}
}
