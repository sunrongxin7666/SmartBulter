package com.android.srx.github.smartbulter.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.utils
 * File: UtilTools
 * Created by sunrongxin on 2017/7/17 下午9:35.
 * Description: util tools
 */

public class UtilTools {

	static public void setFront(Context context, View view){
		//加载对应路径下的字体库
		Typeface fromAsset = Typeface.createFromAsset(context.getAssets(), "fronts/FONT.TTF");
		//设置字体
		((TextView)view).setTypeface(fromAsset);
	}
}
