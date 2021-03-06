package com.android.srx.github.smartbulter.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.adapter.CourierAdapter;
import com.android.srx.github.smartbulter.databinding.ActivityCourierBinding;
import com.android.srx.github.smartbulter.entity.CourierData;
import com.android.srx.github.smartbulter.utils.L;
import com.android.srx.github.smartbulter.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CourierActivity extends BaseActivity implements View.OnClickListener {

	private List<CourierData> mList = new ArrayList<>();

	private ActivityCourierBinding mBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this,R.layout.activity_courier);

		initView();
	}

	//初始化View
	private void initView() {
		mBinding.btnGetCourier.setOnClickListener(this);
		mBinding.mListView.setLayoutManager(new LinearLayoutManager(this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_get_courier:
				/**
				 * 1.获取输入框的内容
				 * 2.判断是否为空
				 * 3.拿到数据去请求数据（Json）
				 * 4.解析Json
				 * 5.listview适配器
				 * 6.实体类（item）
				 * 7.设置数据/显示效果
				 */

				//1.获取输入框的内容
				String name = mBinding.etName.getText().toString().trim();
				String number = mBinding.etNumber.getText().toString().trim();

				//拼接我们的url
				String url = "http://v.juhe.cn/exp/index?key=" + StaticClass.JUHE_COURIER_API_KEY
						             + "&com=" + name + "&no=" + number;

				//2.判断是否为空
				if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
					//3.拿到数据去请求数据（Json）
					RxVolley.get(url, new HttpCallback() {
						@Override
						public void onSuccess(String t) {
							//Toast.makeText(CourierActivity.this, t, Toast.LENGTH_SHORT).show();
							L.i("Courier:" + t);
							//4.解析Json
							parsingJson(t);
						}
					});
				} else {
					Toast.makeText(this, getString(R.string.text_tost_empty), Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	//解析数据
	private void parsingJson(String t) {
		try {
			JSONObject jsonObject = new JSONObject(t);
			if(!jsonObject.has("result")){
				throw new JSONException("no result mapping");
			}
			JSONObject jsonResult = jsonObject.getJSONObject("result");
			JSONArray jsonArray = jsonResult.getJSONArray("list");
			mList.clear();
			for (int i = 0; i < jsonArray.length(); i++) {
				//list中每一个Json都一个CourierData对象；
				JSONObject json = (JSONObject) jsonArray.get(i);

				CourierData data = new CourierData();
				data.setRemark(json.getString("remark"));
				data.setZone(json.getString("zone"));
				data.setDatetime(json.getString("datetime"));
				mList.add(data);
			}
			//倒序
			Collections.reverse(mList);
			CourierAdapter adapter = new CourierAdapter(this,mList);
			mBinding.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
