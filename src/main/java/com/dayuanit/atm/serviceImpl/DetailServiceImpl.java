package com.dayuanit.atm.serviceImpl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dayuanit.atm.Exception.AtmException;
import com.dayuanit.atm.domain.Detail;
import com.dayuanit.atm.dto.DetailDTO;
import com.dayuanit.atm.enumUtils.OptType;
import com.dayuanit.atm.enumUtils.StatusEnum;
import com.dayuanit.atm.mapper.DetailMapper;
import com.dayuanit.atm.service.DetailService;
import com.dayuanit.utils.PageUtils;


@Service
public class DetailServiceImpl implements DetailService{
	
	
	@Autowired
	private DetailMapper detailMapper;

	@Override
	public PageUtils<DetailDTO> listDetail(String cardNum, Integer pageNum) {
		// TODO Auto-generated method stub
		Integer detailTotal = detailMapper.getCardDetailTotal(cardNum);
		PageUtils<DetailDTO> pageUtils = new PageUtils<DetailDTO>(pageNum, detailTotal);
		
		List<Detail> listDetail = detailMapper.getDetailByCardNum(cardNum, pageUtils.getStartNum(), PageUtils.detailSize);
		if (null == listDetail) {
			throw new AtmException("找不到卡流水");	
		}
		List<DetailDTO> detailDTOList = new ArrayList<DetailDTO>(listDetail.size());
		
		for (Detail detail : listDetail) {
			DetailDTO ddto = new DetailDTO();
			ddto.setId(detail.getId());
			ddto.setAmount(detail.getAmount());
			ddto.setCardNum(detail.getCardNum());
			ddto.setFlowDesc(detail.getFlowDesc());
			ddto.setId(detail.getId());
			StatusEnum se = StatusEnum.getEnum(detail.getStatus());
			ddto.setStatus(se.getValue());
			OptType ot = OptType.getEnum(detail.getOptType());
			ddto.setOptType(ot.getV());
			ddto.setCreateTime(new SimpleDateFormat("yyyy-mm-dd HH-mm-ss").format(detail.getCreateTime()));
			detailDTOList.add(ddto);
		}
		pageUtils.setData(detailDTOList);
		
		return pageUtils;
	}

	@Override
	public String getCardAllDetailList( Integer userId, String cardNum, String path) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null;
		String realPath = null;
		
		try {
			
			List<Detail> cardDetailList = detailMapper.getAllDetailByCardNum(cardNum, userId);
			
			if (null == cardDetailList) {
				throw new AtmException("找不到流水");	
			}
			
			StringBuffer sb = new StringBuffer(path);
			sb.append("/").append("note-").append(userId).append(System.currentTimeMillis()).append(".txt");
			realPath = sb.toString();
			System.out.println(realPath);
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(realPath))));
			bw.write("ID\t卡号\t交易金额\t操作类型\t操作说明\t操作时间");
			bw.newLine();
			for (Detail detail : cardDetailList) {
				bw.write(detail.getId() + "\t" 
			    + detail.getCardNum() + "\t" 
				+ detail.getAmount()+ "\t" 
			    + detail.getOptType()+ "\t" 
				+ detail.getFlowDesc()+ "\t" 
			    + detail.getCreateTime() );
				
				bw.newLine();
				bw.flush();
			}
				
		} catch(IOException ioe) {
			ioe.printStackTrace();
			throw new AtmException("导出文件异常 请联系客服");
		} finally {
			if (null != bw){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return realPath;
	}

}
