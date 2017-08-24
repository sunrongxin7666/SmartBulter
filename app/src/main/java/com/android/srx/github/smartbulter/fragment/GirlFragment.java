package com.android.srx.github.smartbulter.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.adapter.GridAdapter;
import com.android.srx.github.smartbulter.adapter.OnItemClickListener;
import com.android.srx.github.smartbulter.adapter.PictureAdapter;
import com.android.srx.github.smartbulter.entity.GirlData;
import com.android.srx.github.smartbulter.utils.L;
import com.android.srx.github.smartbulter.utils.PicassoUtils;
import com.android.srx.github.smartbulter.view.CustomDialog;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.fragment
 * File: GirlFragment
 * Created by sunrongxin on 2017/7/17 下午10:42.
 * Description: 美女照片
 */

public class GirlFragment extends Fragment {

	//列表
	private RecyclerView mRecyclerView;
	//数据
	private List<GirlData> mList = new ArrayList<>();
	//适配器
	private PictureAdapter mAdapter;
	//提示框
	private CustomDialog dialog;
	//预览图片
	private ImageView iv_img;
	//图片地址的数据
	private List<String> mListUrl = new ArrayList<>();
	//PhotoView
	private PhotoViewAttacher mAttacher;

	/**
	 * 1.监听点击事件
	 * 2.提示框
	 * 3.加载图片
	 * 4.PhotoView
	 */

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_girl, null);

		findView(view);
		return view;
	}

	//初始化View
	private void findView(View view) {

		mRecyclerView = (RecyclerView) view.findViewById(R.id.mGridView);
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
		mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

		//初始化提示框
		dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
				                         LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
				                         R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
		iv_img = (ImageView) dialog.findViewById(R.id.iv_img);

		String welfare = null;
		try {
			//Gank升級 需要转码
			welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//解析
		RxVolley.get("http://gank.io/api/search/query/listview/category/"+welfare+"/count/50/page/1", new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				L.i("Girl Json:" + t);
				parsingJson(t);
			}
		});

		//监听点击事件

	}

	//解析Json
	private void parsingJson(String t) {
		try {
			JSONObject jsonObject = new JSONObject(t);
			JSONArray jsonArray = jsonObject.getJSONArray("results");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject json = (JSONObject) jsonArray.get(i);
				String url = json.getString("url");
				mListUrl.add(url);

				GirlData data = new GirlData();
				data.setImgUrl(url);
				mList.add(data);
			}
			setupAdapterToRecyclerView();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void setupAdapterToRecyclerView() {
		mAdapter = new PictureAdapter(getActivity(), mList);
		//设置适配器
		mRecyclerView.setAdapter(mAdapter);
		//设置监听事件
		mAdapter.setListener(new OnItemClickListener() {
			@Override
			public void onItemClick(View v, int position) {
				//解析图片
				PicassoUtils.loadImaheView(getActivity(), mListUrl.get(position), iv_img);
				//缩放
				mAttacher = new PhotoViewAttacher(iv_img);
				//刷新
				mAttacher.update();
				dialog.show();
			}

			@Override
			public void onItemLongClick(View v, int position) {
				onItemClick(v,position);
			}
		});
	}
}
