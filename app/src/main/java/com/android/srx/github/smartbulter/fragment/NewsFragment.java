package com.android.srx.github.smartbulter.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.adapter.NewsAdapter;
import com.android.srx.github.smartbulter.adapter.OnItemClickListener;
import com.android.srx.github.smartbulter.entity.WeChatData;
import com.android.srx.github.smartbulter.ui.WebViewActivity;
import com.android.srx.github.smartbulter.utils.L;
import com.android.srx.github.smartbulter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends android.support.v4.app.Fragment {
	private RecyclerView mRecyclerView;

	private List<WeChatData> mList = new ArrayList<>();
	//标题
	private List<String> mListTitle = new ArrayList<>();
	//地址
	private List<String> mListUrl = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_news,null);
		findView(view);
		return view;
	}

	private void findView(View view) {
		mRecyclerView = (RecyclerView) view.findViewById(R.id.rec_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		mRecyclerView.setHasFixedSize(true);

		//解析接口
		String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY + "&ps=20";
		RxVolley.get(url, new HttpCallback() {
			@Override
			public void onSuccess(String t) {
				super.onSuccess(t);
				//Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT).show();
				L.i("wechat json:" + t);
				parsingJson(t);
			}
		});

	}
	//解析Json
	private void parsingJson(String t) {
		try {
			JSONObject jsonObject = new JSONObject(t);
			JSONObject jsonresult = jsonObject.getJSONObject("result");
			JSONArray jsonList = jsonresult.getJSONArray("list");
			for (int i = 0; i < jsonList.length(); i++) {
				JSONObject json = (JSONObject) jsonList.get(i);
				WeChatData data = new WeChatData();

				String title = json.getString("title");
				String url = json.getString("url");

				data.setTitle(title);
				data.setSource(json.getString("source"));
				data.setImgUrl(json.getString("firstImg"));

				mList.add(data);

				mListTitle.add(title);
				mListUrl.add(url);
			}
			NewsAdapter adapter = new NewsAdapter(getActivity(), mList);

			//点击事件
			adapter.setListener(new OnItemClickListener() {
				@Override
				public void onItemClick(View view, int position) {
					L.i("position:" + position);
					Intent intent = new Intent(getActivity(), WebViewActivity.class);
					intent.putExtra("title", mListTitle.get(position));
					intent.putExtra("url", mListUrl.get(position));
					startActivity(intent);
				}

				@Override
				public void onItemLongClick(View v, int position) {
					onItemClick(v,position);
				}
			});
			mRecyclerView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
