package com.android.srx.github.smartbulter.ui;


import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.srx.github.smartbulter.R;

import java.util.ArrayList;
import java.util.List;

///引导页
public class GuideActivity extends AppCompatActivity {

	private ViewPager mViewPager;

	private List<View> mViewList= new ArrayList<>();

	private ImageView point1,point2,point3;

	private ImageView imgBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		initView();
	}

	private void initView() {
		//初始化ImageView
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);

		mViewList.add(View.inflate(this,R.layout.pager_item_one,null));
		mViewList.add(View.inflate(this,R.layout.pager_item_two,null));
		mViewList.add(View.inflate(this,R.layout.pager_item_three,null));

		imgBack = (ImageView) findViewById(R.id.image_back);

		//适配器
		mViewPager.setAdapter(new GuideAdapter(mViewList));
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				setPoint(position+1);
				if(position==mViewList.size()-1)
					imgBack.setVisibility(View.GONE);
				else {
					imgBack.setVisibility(View.VISIBLE);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

		//初始化小圆点
		point1 = (ImageView) findViewById(R.id.point1);
		point2 = (ImageView) findViewById(R.id.point2);
		point3 = (ImageView) findViewById(R.id.point3);
		//设置默认的图片
		setPoint(1);
	}

	private void setPoint(int index){
		point1.setBackgroundResource(R.drawable.point_off);
		point2.setBackgroundResource(R.drawable.point_off);
		point3.setBackgroundResource(R.drawable.point_off);
		switch (index){
			case 1 :
				point1.setBackgroundResource(R.drawable.point_on);
				break;
			case 2 :
				point2.setBackgroundResource(R.drawable.point_on);
				break;
			case 3 :
				point3.setBackgroundResource(R.drawable.point_on);
				break;
		}
	}

	public void gotoMainActivity(View view) {
		startActivity(new Intent(this, RegisteredActivity.class));
		finish();
	}

	private class GuideAdapter extends PagerAdapter {
		List<View> mViewList;

		public GuideAdapter(List<View> list){
			this.mViewList = list;
		}

		@Override
		public int getCount() {
			return mViewList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view==object;
		}

		//实例化一个成员
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViewList.get(position));
			return mViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mViewList.get(position));
			//super.destroyItem(container, position, object);
		}
	}
}
