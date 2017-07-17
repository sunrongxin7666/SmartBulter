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
 * File: ButlerFragment
 * Created by sunrongxin on 2017/7/17 下午10:38.
 * Description: TODO
 */

public class ButlerFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_butler,null);
		return view;
	}
}
