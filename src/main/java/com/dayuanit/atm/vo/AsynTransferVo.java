package com.dayuanit.atm.vo;

public class AsynTransferVo {
	
	private String outCardNum;
	
	private Integer outCardId;
	
	private Integer amount;
	
	private String inCardNum;
	
	private Integer inCardId;

	public String getOutCardNum() {
		return outCardNum;
	}

	public void setOutCardNum(String outCardNum) {
		this.outCardNum = outCardNum;
	}

	public Integer getOutCardId() {
		return outCardId;
	}

	public void setOutCardId(Integer outCardId) {
		this.outCardId = outCardId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getInCardNum() {
		return inCardNum;
	}

	public void setInCardNum(String inCardNum) {
		this.inCardNum = inCardNum;
	}

	public Integer getInCardId() {
		return inCardId;
	}

	public void setInCardId(Integer inCardId) {
		this.inCardId = inCardId;
	}
	
	

}
