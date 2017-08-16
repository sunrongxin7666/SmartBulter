package com.android.srx.github.smartbulter.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.ui
 * File: DispatchLinearLayout
 * Created by sunrongxin on 2017/8/16 下午7:37.
 * Description: TODO
 */

public class DispatchLinearLayout extends LinearLayout {
	private DispatchKeyEventListener dispatchKeyEventListener;

	public DispatchLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DispatchLinearLayout(Context context) {
		super(context);
	}

	public DispatchLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DispatchKeyEventListener getDispatchKeyEventListener() {
		return dispatchKeyEventListener;
	}

	public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
		this.dispatchKeyEventListener = dispatchKeyEventListener;
	}

	//接口
	public static interface DispatchKeyEventListener {
		boolean dispatchKeyEvent(KeyEvent event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		//如果不为空 说明调用了 去获取事件
		if (dispatchKeyEventListener != null) {
			return dispatchKeyEventListener.dispatchKeyEvent(event);
		}
		return super.dispatchKeyEvent(event);
	}

}
