package com.douzone.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	
	@RequestMapping("/list")
	public String list() {
		return "guestbook/list";
	}
}
