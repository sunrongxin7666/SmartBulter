package com.android.srx.github.smartbulter.ui;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.fragment.ButlerFragment;
import com.android.srx.github.smartbulter.fragment.GirlFragment;
import com.android.srx.github.smartbulter.fragment.UserFragment;
import com.android.srx.github.smartbulter.fragment.WechatFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getSimpleName();
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

		//CrashReport.testJavaCrash();
	}

	private void initView() {
		mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		mActionButton = (FloatingActionButton) findViewById(R.id.fab_setting);

		//预加载
		mViewPager.setOffscreenPageLimit(mFragments.size());

		//ViewPager滑动监听
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				Log.e(TAG, "onPageSelected: "+position);
				if(position==0){
					mActionButton.setVisibility(View.GONE);
				} else {
					mActionButton.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

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

		//A去掉ActionBar的渐进阴影
		//noinspection ConstantConditions
		getSupportActionBar().setElevation(0);
	}

	private void initData() {
		mTitles = new ArrayList<>();
		mTitles.add("服务管家");
		mTitles.add("微信精选");
		mTitles.add("美女社区");
		mTitles.add("个人中心");

		mFragments = new ArrayList<>();
		mFragments.add(new ButlerFragment());
		mFragments.add(new WechatFragment());
		mFragments.add(new GirlFragment());
		mFragments.add(new UserFragment());
	}

	public void gotoSetting(View view) {
		this.startActivity(new Intent(this,SettingActivity.class));
	}
}
