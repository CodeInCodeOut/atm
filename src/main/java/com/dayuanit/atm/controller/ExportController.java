package com.dayuanit.atm.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dayuanit.atm.Exception.AtmException;
import com.dayuanit.atm.service.DetailService;

@Controller()
@RequestMapping("/export")
public class ExportController extends BaseController{
	
	@Autowired
	private DetailService detailService;
	

	
	@RequestMapping("/exportCardDetail")
	public void exportCardDetail(HttpServletRequest req, HttpServletResponse resp) {
		
		String cardNum = req.getParameter("cardNum");
		if ("".equals(cardNum) || null == cardNum) {
			throw new AtmException("查询卡号为空");
		}
		
		String path = req.getServletContext().getRealPath("/") + "/WEB-INF/exportCache";
		
		InputStreamReader isr = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			
			String realPath = detailService.getCardAllDetailList(getUserId(req), "".equals(cardNum)? null : cardNum, path);
			isr = new InputStreamReader(new FileInputStream(new File(realPath)));
			br = new BufferedReader(isr);
			
			resp.setContentType("octets/stream"); 
			resp.addHeader("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + ".txt");
			bw = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream()));
			String msg ="";
			while (null !=(msg = br.readLine())) {
				bw.write(msg);
				bw.newLine();
				bw.flush();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new AtmException("导出文件异常 请联系客服");
		} finally {
			
			try {
				if (null != bw) {
					bw.close();
				}
				
				if (null != br) {
					br.close();
				}
				
				if (null != isr) {
					isr.close();
				}
				
			} catch (IOException ioe) {
				ioe.printStackTrace();
				throw new AtmException("导出文件异常 请联系客服");
			}		
		}
		
	}

}
