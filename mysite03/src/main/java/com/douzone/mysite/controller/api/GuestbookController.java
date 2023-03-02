package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@RestController("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping("")
	public JsonResult index(
			@RequestParam(value = "sno", required=true, defaultValue="0") Long startNo) {
		//guestbookService.addMessage(vo);
		List<GuestbookVo> list = guestbookService.getMessageList();
		return JsonResult.success(list);
	}
	
	@PostMapping("/add")
	public JsonResult add(@RequestBody GuestbookVo vo) {

		System.out.println("vo : "+vo);
		if(vo.getName().equals("")) {
			System.out.println("getName null");
			return JsonResult.fail("fail");
		}
		
		if(vo.getPassword().equals("")) {
			System.out.println("getPassword null");
			return JsonResult.fail("fail");
		}
		
		if(vo.getMessage().equals("")) {
			System.out.println("getMessage null");
			return JsonResult.fail("fail");
		}
		guestbookService.addMessage(vo);
		return JsonResult.success(vo);
	}
	
	@DeleteMapping("/{no}")
	public JsonResult add(
			@PathVariable("no") Long no,@RequestParam(value="password", required=true, defaultValue="") String password) {
		if(guestbookService.deleteMessage(no, password)) {
			return JsonResult.success(no);
		} else {
			return JsonResult.fail("fail");
		}
	}
}