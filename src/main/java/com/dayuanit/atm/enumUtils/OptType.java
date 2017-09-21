package com.dayuanit.atm.enumUtils;

import com.dayuanit.atm.Exception.AtmException;

public enum OptType {
	
	OPACCOUNT(0, "开户存款"), DEPOSIT(1,"存款"), 
	DRAW(2, "取款"), TRANSFEROUT(3, "转账支出"),TRANSFERIN(4, "转账收入"),
	BACK_MONEY(5, "转账失败回退");
	
	private Integer code;
	
	private String v;
	
	private OptType(Integer code, String v) {
		this.code = code;
		this.v = v;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getV() {
		return v;
	}
	
	
	public static OptType getEnum(Integer code) {
		for (OptType ot : OptType.values()) {
			if (code == ot.getCode()) {
				return ot;
			}
		}
		throw new AtmException("没有合适的类型");
	}
	
	

}
