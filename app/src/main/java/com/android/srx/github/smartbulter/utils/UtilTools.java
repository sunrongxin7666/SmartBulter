package com.android.srx.github.smartbulter.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

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

	//保存图片到shareutils
	public static void putImageToShare(Context mContext, ImageView imageView) {
		BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
		Bitmap bitmap = drawable.getBitmap();
		//第一步：将Bitmap压缩成字节数组输出流
		ByteArrayOutputStream byStream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 80, byStream);
		//第二步：利用Base64将我们的字节数组输出流转换成String
		byte[] byteArray = byStream.toByteArray();
		String imgString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
		//第三步：将String保存shareUtils
		SharedUtils.putString(mContext, "image_title", imgString);
	}
}
