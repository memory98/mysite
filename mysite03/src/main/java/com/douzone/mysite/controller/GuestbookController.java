package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	@RequestMapping(value = "/list")
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		model.addAttribute("list", list);
		return "/guestbook/list";
	}
	
	@RequestMapping(value = "/spa")
	public String listSPA() {
		System.out.println("listSPA method....");
		return "/guestbook/index-spa";
	}
	
	@RequestMapping(value = "add")
	public String add(GuestbookVo vo) {
		System.out.println(vo);
		guestbookService.addMessage(vo);
		System.out.println(vo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value = "/spa/add")
	public String addSPA() {
		System.out.println("addSPA method");
		return "redirect:/guestbook/spa";
	}
	
	@RequestMapping(value = "delete/{no}",method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no,Model model) {
		model.addAttribute("no", no);
		return "/guestbook/delete";
	}
	@RequestMapping(value = "delete/{no}",method = RequestMethod.POST)
	public String delete(@PathVariable("no") Long no, @RequestParam(value = "password", required=true, defaultValue="" ) String password) {
		guestbookService.deleteMessage(no,password);
		return "redirect:/guestbook/list";
	}
}