package com.android.srx.github.smartbulter.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.srx.github.smartbulter.R;

public class LoginActivity extends AppCompatActivity {

	private Button btnRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	private void initView() {
		btnRegister = (Button) findViewById(R.id.btn_register);
	}

	public void doRegister(View view) {
		startActivity(new Intent(this,RegisteredActivity.class));
	}
}
