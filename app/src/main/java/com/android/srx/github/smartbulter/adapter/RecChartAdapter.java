package com.android.srx.github.smartbulter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.ChatListData;

import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: RecChartAdapter
 * Created by sunrongxin on 2017/8/28 下午8:07.
 * Description: 聊天记录的RecyclerView Adapter
 */

public class RecChartAdapter extends RecyclerView.Adapter<RecChartAdapter.ChartViewHolder>{
	//左边的type
	public static final int VALUE_LEFT_TEXT = 1;
	//右边的type
	public static final int VALUE_RIGHT_TEXT = 2;

	private LayoutInflater inflater;
	private ChatListData data;
	private List<ChatListData> mList;


	public RecChartAdapter(Context mContext, List<ChatListData> mList){
		this.mList = mList;
		//获取系统服务
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public ChartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if(VALUE_LEFT_TEXT==viewType){
			View view = inflater.inflate(R.layout.left_item,null);
			return new LeftVH(view);
		} else {
			View view = inflater.inflate(R.layout.right_item,null);
			return new RightVH(view);
		}
	}

	@Override
	public void onBindViewHolder(ChartViewHolder holder, int position) {
		data = mList.get(position);
		holder.mTextView.setText(data.getText());
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	@Override
	public int getItemViewType(int position) {
		ChatListData data = mList.get(position);
		return data.getType();
	}



	public class ChartViewHolder  extends RecyclerView.ViewHolder{
		protected TextView mTextView;
		public ChartViewHolder(View itemView) {
			super(itemView);
		}
	}

	public class LeftVH extends ChartViewHolder{
		public LeftVH(View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.tv_left_text);
		}
	}

	public class RightVH extends ChartViewHolder{
		public RightVH(View itemView) {
			super(itemView);
			mTextView = (TextView) itemView.findViewById(R.id.tv_right_text);
		}
	}
}
