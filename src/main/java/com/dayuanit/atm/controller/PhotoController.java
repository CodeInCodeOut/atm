package com.dayuanit.atm.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/photo")
public class PhotoController extends BaseController{
	

	@RequestMapping("/toUpPhoto")
	public String toUpPhoto(){
		return "upPhoto";
	}
	
	
	@RequestMapping("/upPhoto")
	public String upPhoto(@RequestParam("desc") String desc, @RequestParam("file1") MultipartFile file, HttpServletRequest req){
		System.out.println("desc = " + desc);
		String realPath = req.getServletContext().getRealPath("/") + "/WEB-INF/photo";
		String fileName = "UserId - " + getUserId(req); 
		
		String filePath = realPath + "/" + fileName;
		
		File targetFile = new File(filePath);
		
		try {
			file.transferTo(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "cardDetailList";
	}
	
}
			



