package com.android.srx.github.smartbulter.entity;

/**
 * Project: SmartBulter
 * Packege: com.android.srx.github.smartbulter.entity
 * File: CourierData
 * Created by sunrongxin on 2017/7/26 下午11:59.
 * Description: 快递路由信息数据
 */

public class CourierData {
	//时间
	private String datetime;
	//状态
	private String remark;
	//城市
	private String zone;

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	@Override
	public String toString() {
		return "CourierData{" +
				       "datetime='" + datetime + '\'' +
				       ", remark='" + remark + '\'' +
				       ", zone='" + zone + '\'' +
				       '}';
	}
}
