package com.dayuanit.atm.enumUtils;

import com.dayuanit.atm.Exception.AtmException;

public enum AsnyTransStatusEnum {
	
	UN_TRANS(1,"待转账"), TRANSFERED(2,"已转账"), QUITTRANSFER(3, "取消转账");
	
	private Integer code;
	
	private String value;

	public Integer getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private AsnyTransStatusEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}
	
	
	public static AsnyTransStatusEnum getEnum(Integer code) {
		for (AsnyTransStatusEnum as : AsnyTransStatusEnum.values()) {
			if (code == as.getCode()) {
				return as;
			}
		}
		throw new AtmException("没有合适的类型");
	}
	
}
