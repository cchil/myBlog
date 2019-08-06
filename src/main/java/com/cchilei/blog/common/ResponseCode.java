package com.cchilei.blog.common;

/**
 * Created With IDEA
 * Author:Chen·ZD
 * Date:2018/4/14
 * Time:11:18
 **/
public enum ResponseCode {
	SUCCESS(0,"SUCCESS"),
	ERROR(1,"ERROR"),
	NEED_LOGIN(10,"NEED_LOGIN"),
	ILLEGAL_ARGUMENT(20,"ILLEGAL_ARGUMENT");

	private final int code;
	private final String desc;

	ResponseCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
}
