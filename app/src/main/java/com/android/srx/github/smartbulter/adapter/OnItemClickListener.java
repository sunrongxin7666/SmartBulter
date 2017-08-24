package com.android.srx.github.smartbulter.adapter;

import android.view.View;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.adapter
 * File: OnItemClickListener
 * Created by sunrongxin on 2017/8/24 下午4:50.
 * Description: RecyclerView OnItemClickListener
 */
public interface OnItemClickListener {
	void onItemClick(View v, int position);

	void onItemLongClick(View v, int position);
}
