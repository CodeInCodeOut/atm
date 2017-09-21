package com.dayuanit.utils;

import java.util.List;

public class PageUtils<T> {
		
		public static final Integer detailSize = 2;
		
		private Integer lastPage;
		private Integer PageNum;
		private List<T> data;
		
		public List<T> getData() {
			return data;
		}

		public void setData(List<T> data) {
			this.data = data;
		}

		public Integer getLastPage() {
			return lastPage;
		}

		public Integer getPageNum() {
			return PageNum;
		}

		public PageUtils(Integer PageNum, Integer cardDetailTotal) {
			this.PageNum = PageNum;
			this.lastPage = getTotalPageNum(cardDetailTotal);
		}
		
		private static Integer getTotalPageNum(int cardDetailTotal) {
			return (cardDetailTotal % detailSize) == 0 ? cardDetailTotal / detailSize : (cardDetailTotal / detailSize) + 1;
		}
		
		public Integer getStartNum() {
			return (PageNum - 1) * detailSize;
		}

	}



