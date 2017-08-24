package com.android.srx.github.smartbulter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.android.srx.github.smartbulter.R;
import com.android.srx.github.smartbulter.entity.GirlData;
import com.android.srx.github.smartbulter.utils.PicassoUtils;

import java.util.List;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: PictureAdapter
 * Created by sunrongxin on 2017/8/24 下午3:58.
 * Description: Picture RecyclerView Adapter
 */

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.PicViewHoder> {

	private Context mContext;
	private List<GirlData> mList;
	private LayoutInflater inflater;
	private GirlData data;
	private WindowManager wm;
	private int width;

	private OnItemClickListener mListener;

	public PictureAdapter(Context mContext, List<GirlData> mList){
		this.mContext = mContext;
		this.mList = mList;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();
	}

	@Override
	public PicViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.girl_item,parent,false);
		return new PicViewHoder(view);
	}

	@Override
	public void onBindViewHolder(final PicViewHoder holder, int position) {
		data = mList.get(position);
		//解析图片
		String url = data.getImgUrl();
		PicassoUtils.loadImageViewSize(mContext,url,width/3, (int) (400+Math.random()*300),holder.mImageView);
		if(mListener!=null){
			holder.mImageView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					int pos = holder.getAdapterPosition();
					mListener.onItemClick(v, pos);
				}
			});
		}
	}

	@Override
	public int getItemCount() {
		return mList.size();
	}


	public void setListener(OnItemClickListener listener){
		this.mListener = listener;
	}

	class PicViewHoder extends RecyclerView.ViewHolder{
		ImageView mImageView;
		PicViewHoder(View itemView) {
			super(itemView);
			mImageView = (ImageView) itemView.findViewById(R.id.imageview);
		}
	}
}
