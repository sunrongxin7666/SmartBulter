package com.android.srx.github.smartbulter.adapter;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.WeChatData;
import com.android.srx.github.smartbulter.utils.L;
import com.android.srx.github.smartbulter.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: WechatAdapter
 * Created by sunrongxin on 2017/8/6 下午11:30.
 * Description: 微信精选Adapter
 */

public class WechatAdapter extends BaseAdapter {
	private Context mContext;
	private List<WeChatData> mList;
	private WeChatData mData;
	private LayoutInflater mInflater;

	private int width,height;
	private WindowManager wm;

	public WechatAdapter(Context context, List<WeChatData> list){
		mContext = context;
		mList = list;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		L.i("Width:" + width + "Height:" + height);
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
		//Picasso.with(mContext).load(mData.getImgUrl()).into(viewHolder.iv_img);
		if(!TextUtils.isEmpty(mData.getImgUrl())){
			//加载图片
			PicassoUtils.loadImageViewSize(mContext, mData.getImgUrl(), width/3, 250, viewHolder.iv_img);
		}

		//PicassoUtils.loadImageViewSize(mContext,mData.getImgUrl(),300,100,viewHolder.iv_img);
		return convertView;
	}

	class ViewHolder{
		private ImageView iv_img;
		private TextView tx_title;
		private TextView tx_source;
	}
}
