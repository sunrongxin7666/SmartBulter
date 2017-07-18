package com.android.srx.github.smartbulter;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.srx.github.smartbulter.fragment.ButlerFragment;
import com.android.srx.github.smartbulter.fragment.GirlFragment;
import com.android.srx.github.smartbulter.fragment.UserFragment;
import com.android.srx.github.smartbulter.fragment.WehatFragment;
import com.android.srx.github.smartbulter.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	private FloatingActionButton mActionButton;

	private List<String> mTitles;
	private List<Fragment> mFragments;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initView();

		//Action的渐进阴影设为空，去掉阴影
		getSupportActionBar().setElevation(0);
	}

	private void initView() {
		mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		mActionButton = (FloatingActionButton) findViewById(R.id.fab_setting);

		//预加载
		mViewPager.setOffscreenPageLimit(mFragments.size());

		//设置配置器 ViewPager是ViewGroup
		mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public Fragment getItem(int position) {
				return mFragments.get(position);
			}

			@Override
			public int getCount() {
				return mFragments.size();
			}

			@Override
			public CharSequence getPageTitle(int position) {
				return mTitles.get(position);
			}
		});

		//绑定
		mTabLayout.setupWithViewPager(mViewPager);
	}

	private void initData() {
		mTitles = new ArrayList<>();
		mTitles.add("服务管家");
		mTitles.add("微信精选");
		mTitles.add("美女社区");
		mTitles.add("个人中心");

		mFragments = new ArrayList<>();
		mFragments.add(new ButlerFragment());
		mFragments.add(new WehatFragment());
		mFragments.add(new GirlFragment());
		mFragments.add(new UserFragment());
	}

	public void gotoSetting(View view) {
		this.startActivity(new Intent(this,SettingActivity.class));
	}
}
