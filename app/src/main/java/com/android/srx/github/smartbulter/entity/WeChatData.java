package com.android.srx.github.smartbulter.entity;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.entity
 * File: WeChatData
 * Created by sunrongxin on 2017/8/6 下午11:31.
 * Description: TODO
 */

public class WeChatData {
	//标题
	private String title;
	//出处
	private String source;
	//图片的url
	private String imgUrl;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
