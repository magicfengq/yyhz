package com.yyhz.utils.stream.util;

import java.util.Map;

public class StreamVO {
	
	private String urlPath;
	
	private int fwidth;
	
	private int fheight;

	private long size;
	
	private String ftype;
	
	private String suffix;
	
	private String name;
	
	private String params;
	
	private String msg;
	
	private Map<String,Object> map;

	public String getUrlPath() {
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public int getFwidth() {
		return fwidth;
	}

	public void setFwidth(int fwidth) {
		this.fwidth = fwidth;
	}

	public int getFheight() {
		return fheight;
	}

	public void setFheight(int fheight) {
		this.fheight = fheight;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
