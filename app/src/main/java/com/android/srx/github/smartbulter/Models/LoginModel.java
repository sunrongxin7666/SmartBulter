package com.android.srx.github.smartbulter.Models;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.entity
 * File: LoginModel
 * Created by sunrongxin on 2017/8/22 下午5:25.
 * Description: loginActivity 对应的数据Model
 */

public class LoginModel {
	public ObservableField<String> userName = new ObservableField<>();
	public ObservableField<String> password = new ObservableField<>();
	public ObservableBoolean isKeep = new ObservableBoolean();
}
