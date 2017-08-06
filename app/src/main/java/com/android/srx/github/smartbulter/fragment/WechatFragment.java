package com.android.srx.github.smartbulter.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.srx.github.smartbulter.R;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.fragment
 * File: WechatFragment
 * Created by sunrongxin on 2017/7/17 下午10:47.
 * Description: wechat news
 */

public class WechatFragment extends Fragment{

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wechat,null);
		return view;
	}
}
