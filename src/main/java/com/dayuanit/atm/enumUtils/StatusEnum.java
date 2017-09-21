package com.dayuanit.atm.enumUtils;

import com.dayuanit.atm.Exception.AtmException;

public enum StatusEnum {
	
	DELETE(0, "删除不可用"), USABLE(1, "可用"), FREEZE(2, "银行卡冻结");
	
	private Integer code;
	
	private String value;

	public Integer getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private StatusEnum(Integer code, String value) {
		this.code = code;
		this.value = value;
	}
	
	public static StatusEnum getEnum(Integer code) {
		for (StatusEnum se : StatusEnum.values()) {
			if (code == se.getCode()) {
				return se;
			}
		}
		throw new AtmException("没有合适的类型");
	}

	
	
	

}
