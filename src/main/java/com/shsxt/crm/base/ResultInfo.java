package com.shsxt.crm.base;

public class ResultInfo {
	private Integer resultCode;
	private String resultMesssage;
	private Object result;
	
	public ResultInfo() {
		super();
	}

	public ResultInfo(Integer resultCode, String resultMesssage, Object result) {
		super();
		this.resultCode = resultCode;
		this.resultMesssage = resultMesssage;
		this.result = result;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMesssage() {
		return resultMesssage;
	}

	public void setResultMesssage(String resultMesssage) {
		this.resultMesssage = resultMesssage;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
}
