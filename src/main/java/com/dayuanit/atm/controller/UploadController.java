package com.dayuanit.atm.controller;



import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dayuanit.atm.Exception.AtmException;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {
	
	@RequestMapping("/toUpload")
	public String toUpload(){
		return "upLoad";
	}
	
	@RequestMapping("/upLoad")
	public String upLoad(HttpServletRequest req, HttpServletResponse resp){
		String realPath = req.getServletContext().getRealPath("/") + "/WEB-INF/uploadCache";
		String tempPath = req.getServletContext().getRealPath("/") + "/WEB-INF/uploadTemp";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024);
		factory.setRepository(new File(tempPath));
		
		ServletFileUpload sfu = new ServletFileUpload(factory);
		sfu.setSizeMax(1024*1024); // 单次上传  setFileSizeMax() 单个文件上传
		
		
		List<FileItem> items = null;
		try {
			items = sfu.parseRequest(req);
			System.out.println(items);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new AtmException("文件上传失败");
		}
		Iterator<FileItem> iterator = items.iterator();
		while (iterator.hasNext()) {
			FileItem item = iterator.next();
			if (item.isFormField()) {
				
				String name = item.getFieldName();
				String value = item.getString();
				System.out.println(name + "=" + value);
				
			} else {
				String filedName = item.getFieldName();
				String orgFileName = item.getName();
				String contentType = item.getContentType();
				boolean isInMemory = item.isInMemory();
				long size = item.getSize();
				System.out.println("filedName=" + filedName);
				System.out.println("orgFileName=" + orgFileName);
				System.out.println("contentType=" + contentType);
				System.out.println("isInMemory=" + isInMemory);
				System.out.println("size=" + size);
			
				String fileName = "UserId - " + getUserId(req); 
				req.getSession().setAttribute("fileName", fileName);
				
				try {
					item.write(new File(realPath, fileName));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "usercenter";
	}
	
}
		
		
	



