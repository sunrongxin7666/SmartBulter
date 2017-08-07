package com.android.srx.github.smartbulter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.WeChatData;

import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: WechatAdapter
 * Created by sunrongxin on 2017/8/6 下午11:30.
 * Description: TODO
 */

public class WechatAdapter extends BaseAdapter {
	private Context mContext;
	private List<WeChatData> mList;
	private WeChatData mData;
	private LayoutInflater mInflater;

	public WechatAdapter(Context context, List<WeChatData> list){
		mContext = context;
		mList = list;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.wechat_item,null);
			viewHolder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
			viewHolder.tx_source = (TextView) convertView.findViewById(R.id.tv_source);
			viewHolder.tx_title = (TextView) convertView.findViewById(R.id.tv_title);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		mData = mList.get(position);
		viewHolder.tx_title.setText(mData.getTitle());
		viewHolder.tx_source.setText(mData.getSource());
		return convertView;
	}

	class ViewHolder{
		private ImageView iv_img;
		private TextView tx_title;
		private TextView tx_source;
	}
}
