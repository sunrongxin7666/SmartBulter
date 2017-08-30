package com.android.srx.github.smartbulter.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.srx.github.smartbulter.BR;
import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.CourierData;

import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: CourierAdapter
 * Created by sunrongxin on 2017/7/27 上午12:01.
 * Description: 快递路由配置器
 */

public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.BindingViewHolder> {
	private List<CourierData> mList;
	//布局加载器
	private LayoutInflater inflater;

	public CourierAdapter(Context mContext, List<CourierData> mList) {
		this.mList = mList;
		//获取系统服务
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		//注意这里使用的是ViewDatatBinding
		ViewDataBinding  binding = DataBindingUtil.inflate(inflater, R.layout.layout_courier_item, parent,false);
		return new BindingViewHolder<>(binding);
	}

	@Override
	public void onBindViewHolder(BindingViewHolder holder, int position) {
		CourierData data = mList.get(position);
		holder.getBinding().setVariable(BR.data, data);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	public class BindingViewHolder<T extends ViewDataBinding>
			extends RecyclerView.ViewHolder {

		private T mBinding;

		public BindingViewHolder(T binding) {
			//获得layout标签下，那个真正的view
			super(binding.getRoot());
			mBinding = binding;
		}

		public T getBinding() {
			return mBinding;
		}
	}
}

