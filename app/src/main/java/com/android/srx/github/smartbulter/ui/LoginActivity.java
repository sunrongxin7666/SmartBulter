package com.android.srx.github.smartbulter.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.srx.github.smartbulter.Models.LoginModel;
import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.databinding.ActivityLoginBinding;
import com.android.srx.github.smartbulter.entity.MyUser;
import com.android.srx.github.smartbulter.utils.SharedUtils;
import com.android.srx.github.smartbulter.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

	private CustomDialog dialog;

	private ActivityLoginBinding mBinding;
	private LoginModel mModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
		mModel = new LoginModel();
		initView();
	}

	private void initView() {

		mModel.isKeep.set(SharedUtils.getBoolen(this, "keeppass", false));

		dialog = new CustomDialog(this, 130, 130, R.layout.dialog_loding, R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
		dialog.setCancelable(false);

		if(mModel.isKeep.get()){
			mModel.userName.set(SharedUtils.getString(this, "name", ""));
			mModel.password.set(SharedUtils.getString(this, "password", ""));
		}

		mBinding.setModel(mModel);
	}

	public void doRegister(View view) {
		startActivity(new Intent(this,RegisteredActivity.class));
	}

	public void doLogin(View view) {
		//1.获取输入框的值 依靠DataBingd
		//2.判断是否为空
		if (!TextUtils.isEmpty(mModel.userName.get()) & !TextUtils.isEmpty(mModel.password.get())) {
			dialog.show();
			//登录
			final MyUser user = new MyUser();
			user.setUsername(mModel.userName.get());
			user.setPassword(mModel.password.get());
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
		SharedUtils.putBoolean(this, "keeppass", mModel.isKeep.get());

		//是否记住密码
		if (mModel.isKeep.get()) {
			//记住用户名和密码
			SharedUtils.putString(this, "name", mModel.userName.get());
			SharedUtils.putString(this, "password", mModel.password.get());
		} else {
			SharedUtils.deleteSharedValue(this, "name");
			SharedUtils.deleteSharedValue(this, "password");
		}
	}

	public void doReset(View view) {
		startActivity(new Intent(this,ForgetPasswordActivity.class));
	}
}
