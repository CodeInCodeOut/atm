package com.dayuanit.atm.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dayuanit.utils.QRCodeUtil;


@Controller
@RequestMapping("/qr")
public class QRCodeController {

	@RequestMapping("/qrcode.do")
	public void createQrCode(HttpServletResponse resp) {
		OutputStream os = null;
		try {
			os = resp.getOutputStream();
			QRCodeUtil.encode("http://ape.dayuanit.com/cv/baimoxiansheng", os);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
