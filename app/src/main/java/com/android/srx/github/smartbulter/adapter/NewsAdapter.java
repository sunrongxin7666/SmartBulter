package com.android.srx.github.smartbulter.adapter;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.WeChatData;
import com.android.srx.github.smartbulter.utils.L;
import com.android.srx.github.smartbulter.utils.PicassoUtils;

import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: NewsAdapter
 * Created by sunrongxin on 2017/8/23 下午6:28.
 * Description: WeChat RecyclerView Adapter
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

	private Context mContext;
	private List<WeChatData> mList;
	private WeChatData mData;
	private LayoutInflater mInflater;

	private int width,height;
	private WindowManager wm;

	private OnItemClickListener mListener;

	public NewsAdapter(Context context, List<WeChatData> list){
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

	public void setListener(OnItemClickListener listener) {
		mListener = listener;
	}

	@Override
	public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = mInflater.inflate(R.layout.wechat_item,parent,false);
		return new NewsViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final NewsViewHolder holder, int position) {
		mData = mList.get(position);
		holder.tx_title.setText(mData.getTitle());
		holder.tx_source.setText(mData.getSource());
		//Picasso.with(mContext).load(mData.getImgUrl()).into(viewHolder.iv_img);
		if(!TextUtils.isEmpty(mData.getImgUrl())){
			//加载图片
			PicassoUtils.loadImageViewSize(mContext, mData.getImgUrl(), width/3, 250, holder.iv_img);
		}
		if(mListener!=null) {
			View.OnClickListener listener = new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int pos = holder.getAdapterPosition();
					mListener.onItemClick(v, pos);
				}
			};
			holder.iv_img.setOnClickListener(listener);
			holder.tx_source.setOnClickListener(listener);
			holder.tx_title.setOnClickListener(listener);
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}

	class NewsViewHolder extends RecyclerView.ViewHolder {

		private ImageView iv_img;
		private TextView tx_title;
		private TextView tx_source;

		public NewsViewHolder(View itemView) {
			super(itemView);
			iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
			tx_title = (TextView) itemView.findViewById(R.id.tv_title);
			tx_source = (TextView) itemView.findViewById(R.id.tv_source);
		}
	}

}
