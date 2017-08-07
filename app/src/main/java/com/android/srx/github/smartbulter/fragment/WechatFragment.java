package com.android.srx.github.smartbulter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.adapter.WechatAdapter;
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
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.fragment
 * File: WechatFragment
 * Created by sunrongxin on 2017/7/17 下午10:47.
 * Description: wechat news
 */

public class WechatFragment extends Fragment{

	private ListView mListView;

	private List<WeChatData> mList = new ArrayList<>();
	//标题
	private List<String> mListTitle = new ArrayList<>();
	//地址
	private List<String> mListUrl = new ArrayList<>();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_wechat,null);
		findView(view);
		return view;
	}

	private void findView(View view) {
		mListView = (ListView) view.findViewById(R.id.mListView);

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

		//点击事件
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				L.i("position:" + position);
				Intent intent = new Intent(getActivity(), WebViewActivity.class);
				intent.putExtra("title", mListTitle.get(position));
				intent.putExtra("url", mListUrl.get(position));
				startActivity(intent);
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
			WechatAdapter adapter = new WechatAdapter(getActivity(), mList);
			mListView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
