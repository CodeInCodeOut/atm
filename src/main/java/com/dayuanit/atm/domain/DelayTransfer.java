package com.dayuanit.atm.domain;

import java.util.Date;

public class DelayTransfer {
	
	private Integer id;
	
	private String outCardNum;
	
	private Integer outCardId;
	
	private Integer amount;
	
	private String inCardNum;
	
	private Integer inCardId;
	
	private Date createTime;
	
	private Date modifyTime;
	
	private Integer transactionStatus;
	

	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Integer getTransactionStatus() {
		return transactionStatus;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	

}
