package com.android.srx.github.smartbulter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.ChatListData;

import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: ChatListAdapter
 * Created by sunrongxin on 2017/8/3 下午11:16.
 * Description: 智能管家回话配置器
 */

public class ChatListAdapter extends BaseAdapter {

	//左边的type
	public static final int VALUE_LEFT_TEXT = 1;
	//右边的type
	public static final int VALUE_RIGHT_TEXT = 2;

	private Context mContext;
	private LayoutInflater inflater;
	private ChatListData data;
	private List<ChatListData> mList;

	public ChatListAdapter(Context mContext, List<ChatListData> mList) {
		this.mContext = mContext;
		this.mList = mList;
		//获取系统服务
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	/**
	 * @param position 位置
	 * @param convertView 要构建的view
	 * @param parent 父view
	 * @return 构件好的view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderLeftText viewHolderLeftText = null;
		ViewHolderRightText viewHolderRightText = null;
		//获取当前要显示的type 根据这个type来区分数据的加载
		int type = getItemViewType(position);
		if (convertView == null) {
			switch (type) {
				case VALUE_LEFT_TEXT:
					viewHolderLeftText = new ViewHolderLeftText();
					//使用点9d图片，不会拉长而变形；
					convertView = inflater.inflate(R.layout.left_item, null);
					viewHolderLeftText.tv_left_text = (TextView) convertView.findViewById(R.id.tv_left_text);
					convertView.setTag(viewHolderLeftText);
					break;
				case VALUE_RIGHT_TEXT:
					viewHolderRightText = new ViewHolderRightText();
					convertView = inflater.inflate(R.layout.right_item, null);
					viewHolderRightText.tv_right_text = (TextView) convertView.findViewById(R.id.tv_right_text);
					convertView.setTag(viewHolderRightText);
					break;
			}
		} else {
			switch (type) {
				case VALUE_LEFT_TEXT:
					viewHolderLeftText = (ViewHolderLeftText) convertView.getTag();
					break;
				case VALUE_RIGHT_TEXT:
					viewHolderRightText = (ViewHolderRightText) convertView.getTag();
					break;
			}
		}

		//赋值
		ChatListData data = mList.get(position);
		switch (type){
			case VALUE_LEFT_TEXT:
				viewHolderLeftText.tv_left_text.setText(data.getText());
				break;
			case VALUE_RIGHT_TEXT:
				viewHolderRightText.tv_right_text.setText(data.getText());
				break;
		}
		return convertView;
	}

	//根据数据源的positiion来返回要显示的item
	@Override
	public int getItemViewType(int position) {
		ChatListData data = mList.get(position);
		int type = data.getType();
		return type;
	}

	//返回所有的layout数据
	@Override
	public int getViewTypeCount() {
		return 3; //mlisy.size + 1
	}

	//左边的文本
	class ViewHolderLeftText {
		private TextView tv_left_text;
	}

	//右边的文本
	class ViewHolderRightText {
		private TextView tv_right_text;
	}
}
