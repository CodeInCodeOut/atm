package com.dayuanit.atm.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dayuanit.atm.Exception.AtmException;
import com.dayuanit.atm.domain.Card;
import com.dayuanit.atm.dto.AjaxResultDTO;
import com.dayuanit.atm.dto.DetailDTO;
import com.dayuanit.atm.service.CardService;
import com.dayuanit.atm.service.DetailService;
import com.dayuanit.atm.vo.AsynTransferVo;
import com.dayuanit.utils.PageUtils;

@Controller
@RequestMapping("/card")
public class CardController extends BaseController {
	
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private DetailService detailService;
	
	@RequestMapping("/deleteCard")
	@ResponseBody
	public AjaxResultDTO deleteCard(HttpServletRequest req, String cardNum) {
		try {
			cardService.deleteCardVirtual(getUserId(req), cardNum);
		} catch(Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
		
		return AjaxResultDTO.success();
	}
	
	@RequestMapping(value="/toCardDetail",method = { RequestMethod.POST, RequestMethod.GET })  
	public String toCardDetail(ModelMap mm, @ModelAttribute("cardNum")String cardNum) {
		try {
			Card card = cardService.getCardByCardNum(cardNum);
			mm.addAttribute("card", card);
		} catch(Exception e) {
			return "redict:/error.jsp";
		}
		
		return "cardDetailList";
	}
	
	@RequestMapping("/toOpenAccount")
	public String toOpenAccount() {
		return "openAccount";
	}
	
	
	@RequestMapping(value="/toDeposit",method = { RequestMethod.POST, RequestMethod.GET })  
	public String toDeposit(ModelMap mm, @ModelAttribute("cardNum")String cardNum) {
		try {
			Card currentCard = cardService.getCardByCardNum(cardNum);
			if (null == currentCard) {
				throw new AtmException("系统异常 联系客服");
			}
			mm.addAttribute("card", currentCard);
		} catch (Exception e) {
			mm.addAttribute("error",  e.getMessage());
			return "redirect:/error.jsp";
		}
		return "deposit";
	}
	

	@RequestMapping(value="/toDraw",method = { RequestMethod.POST, RequestMethod.GET })  
	public String toDraw(ModelMap mm, @ModelAttribute("cardNum")String cardNum) {
		try {
			Card currentCard = cardService.getCardByCardNum(cardNum);
			if (null == currentCard) {
				throw new AtmException("系统异常 联系客服");
			}
			mm.addAttribute("card", currentCard);
		} catch (Exception e) {
			mm.addAttribute("error",  e.getMessage());
			return "redirect:/error.jsp";
		}
		
		return "draw";
	}
	
	
	@RequestMapping("/toTransfer")  
	public String toTransfer(HttpServletRequest req, String cardNum) {
		try {
			Card currentCard = cardService.getCardByCardNum(cardNum);
			if (null == currentCard) {
				throw new AtmException("系统异常 联系客服");
			}
			req.setAttribute("card", currentCard);
		} catch (Exception e) {
			req.setAttribute("error", e.getMessage());
			return "redirect:/error.jsp";
		}
		return "transfer";
	}
	
	@RequestMapping(value="/transfer",method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public AjaxResultDTO transfer(HttpServletRequest req, String outCardNum, Integer amount,  String inCardNum) {
	
		try {
			cardService.transfer(outCardNum, amount, inCardNum);
			
		} catch (Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
		return AjaxResultDTO.success();
		
	}
	
	@RequestMapping("/draw")
	@ResponseBody
	public AjaxResultDTO draw(HttpServletRequest req, String cardNum, Integer amount) {
	
		try {
			cardService.draw(getUserId(req), cardNum, amount);
		} catch (Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
		return AjaxResultDTO.success();
		
	}
	
	@RequestMapping("/deposit")
	@ResponseBody
	public AjaxResultDTO deposit(HttpServletRequest req, String cardNum, Integer amount) {
	
		try {
			cardService.deposit(getUserId(req), cardNum, amount);
		} catch (Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
		return AjaxResultDTO.success();
		
	}
	
	@RequestMapping("/cardDetailList")
	@ResponseBody
	public AjaxResultDTO cardDetailList(HttpServletRequest req, Integer pageNum, String cardNum) {
	
		try {
			PageUtils<DetailDTO> pageUtils = detailService.listDetail(cardNum, null == pageNum ? 1 : pageNum);
			
			return AjaxResultDTO.success(pageUtils);
		} catch (Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
	}
	
	@RequestMapping("/openAccount")
	@ResponseBody
	public AjaxResultDTO openAccount(HttpServletRequest req, Integer amount) {
	
		try {
			cardService.openAccount(getUserId(req), amount);
		} catch (Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
		return AjaxResultDTO.success();
		
	}
	
	
	@RequestMapping("/listCard")
	@ResponseBody
	public AjaxResultDTO listCard(HttpServletRequest req, Integer pageNum) {
	
		if (null == pageNum) {
			pageNum = 1;
		}
		
		try {
			Map<String, Object> map = cardService.cardList(getUserId(req),pageNum);
			return AjaxResultDTO.success(map);
		} catch (Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
		
	}
	
	@RequestMapping(value="/toAsynTransfer",method = { RequestMethod.POST, RequestMethod.GET })  
	public String toAsynTransfer(HttpServletRequest req, String cardNum) {
		try {
			Card currentCard = cardService.getCardByCardNum(cardNum);
			if (null == currentCard) {
				throw new AtmException("系统异常 联系客服");
			}
			req.setAttribute("card", currentCard);
		} catch (Exception e) {
			req.setAttribute("error", e.getMessage());
			return "redirect:/error.jsp";
		}
		return "asynTransfer";
	}
	
	@RequestMapping("/asynTransfer")
	@ResponseBody
	public AjaxResultDTO asynTransfer(AsynTransferVo asynTransferVo) {
	
		try {
			cardService.asynTransferOut(asynTransferVo.getOutCardNum(), asynTransferVo.getAmount(), asynTransferVo.getInCardNum());
			
		} catch (Exception e) {
			return AjaxResultDTO.failed(e.getMessage());
		}
		return AjaxResultDTO.success();
		
	}
	
	

}
