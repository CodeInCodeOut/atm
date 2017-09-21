package com.dayuanit.atm.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dayuanit.atm.Exception.AtmException;

@Controller
@RequestMapping("/read")
public class ReadController extends BaseController{
	
	@RequestMapping("/readFile")
	public String readFile(HttpServletRequest req, HttpServletResponse resp) {
		
		String realPath = req.getServletContext().getRealPath("/") + "/WEB-INF/uploadCache";
		String fileName = "UserId - " + getUserId(req); 
		System.out.println("filePath = " + realPath  + "    " + "fileName = " + fileName);
		File file = new File(realPath, fileName);
		
		System.out.println(file.getPath());
		if (!file.exists()) {
			throw new AtmException("文件不存在");
		}
		
		OutputStream os = null;
		BufferedInputStream bis = null;
		try {
			os = resp.getOutputStream();
			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] buff = new byte[1024];
			int length = -1;
			while (-1 != (length = bis.read(buff))) {
				os.write(buff, 0, length);
				os.flush();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			// 可以加 resp.sendError(500);
			throw new AtmException("系统异常请联系客服");
				
		} finally {
			
				try {
					if (null != bis) {
						bis.close();
					}
					
					if (null != os) {
						os.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new AtmException("系统异常请联系客服");
				}
			}
	
		return null; //已经读取关闭不用继续提交数据  
	} 

}
