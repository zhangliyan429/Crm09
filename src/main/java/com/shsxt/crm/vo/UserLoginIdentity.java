package com.shsxt.crm.vo;

import java.io.Serializable;

/**
 * Created by Tony on 2016/8/22.
 */
@SuppressWarnings("serial")
public class UserLoginIdentity  implements Serializable{

	private String  userIdString; // 将userId加密
	private String userName;
	private String trueName;
	
	public String getUserIdString() {
		return userIdString;
	}
	public void setUserIdString(String userIdString) {
		this.userIdString = userIdString;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
}
