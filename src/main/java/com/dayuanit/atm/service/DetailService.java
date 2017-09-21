package com.dayuanit.atm.service;

import com.dayuanit.atm.dto.DetailDTO;
import com.dayuanit.utils.PageUtils;

public interface DetailService {
	
	PageUtils<DetailDTO> listDetail(String cardNum, Integer pageNum);

	String getCardAllDetailList(Integer userId, String cardNum, String path);
}
