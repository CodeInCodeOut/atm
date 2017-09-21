package com.dayuanit.utils;

public class PageUtil {
	
	public static final Integer cardSize = 2;
	
	private static Integer pageNum;
	
	private Integer lastPage;
	
	public Integer getPageNum() {
		return pageNum;
	}

	public Integer getLastPage() {
		return lastPage;
	}

	public PageUtil(Integer pageNum, Integer cardTotal) {
	
		this.pageNum = pageNum;
		this.lastPage = getLastPage(cardTotal);
	}
	
	public static Integer getLastPage(Integer cardTotal) {
		return ((cardTotal%cardSize)==0)? ( cardTotal/cardSize) : (cardTotal/cardSize + 1);
	}
	
	public static Integer getStartNum() {
		
		return (pageNum-1)*cardSize;
	}
	


}
