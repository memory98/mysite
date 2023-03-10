package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "")
	public String list() {
		return "redirect:/board/list";
	}

	@RequestMapping(value = "/list")
	public String index(Model model, @RequestParam(value = "page", required=false, defaultValue = "1") Long page,@RequestParam(value = "keyword", required=false, defaultValue = "") String keyword) {
		keyword=keyword==null?"":keyword;
		
		Long max = boardService.maxgNo();
		List<BoardVo> list = boardService.getContentsList(keyword);
		int cnt = list.size();
		list = boardService.split(list,String.valueOf(page));
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("boardCnt", cnt);
		model.addAttribute("maxgno", max);
		model.addAttribute("list", list);
		
		return "/board/list";
	}
	@Auth()
	@RequestMapping(value = "/write/{no}", method = RequestMethod.GET)
	public String write(Model model, @PathVariable("no") Long no) {
		model.addAttribute("number", no);
		System.out.println("write");
		return "/board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(
			Model model,
			@AuthUser UserVo authUser,
			Long number,BoardVo vo) {
		if (!vo.getTitle().equals("") && !vo.getContents().equals("")) {
			if (number == -1) {
				vo.setoNo(1);
				vo.setgNo(boardService.maxgNo() + 1);
				vo.setDepth(0);
			} else {
				BoardVo findBoard = boardService.findNo(number);
				vo.setgNo(findBoard.getgNo());
				vo.setoNo(findBoard.getoNo() + 1);
				boardService.updateoNo(vo.getgNo(), vo.getoNo());
				vo.setDepth(findBoard.getDepth() + 1);
			}
			vo.setUserNo(authUser.getNo());
			boardService.addContents(vo);
		}
		return "redirect:/board/list";
	}
	
	@RequestMapping(value="/view/no={no}")
	public String view(Model model,@PathVariable("no") Long no) {
		BoardVo vo = boardService.getContents(no);
		System.out.println("boardvo : "+vo);
		BoardVo hitVo = new BoardVo();
		hitVo.setHit(vo.getHit()+1);
		hitVo.setNo(vo.getNo());
		boardService.updateContents(hitVo);
		model.addAttribute("vo", vo);
		return "/board/view";
	}
	@Auth
	@RequestMapping(value="/modify/no={no}")
	public String modify(
			@AuthUser UserVo authUser,
			Model model,
			@PathVariable("no") Long no) {
		BoardVo vo = boardService.getContents(no);
		model.addAttribute("vo", vo);
		return "/board/modify";
	}
	
	@RequestMapping(value="/updateboard", method = RequestMethod.POST)
	public String updateboard(Model model,BoardVo vo) {
		boardService.updateContents(vo);
		return "redirect:/board/list";
	}
	
	@Auth
	@RequestMapping(value="/delete")
	public String delete(
			@RequestParam(value="no", required=true, defaultValue="") Long no,
			@RequestParam(value= "page",required=false, defaultValue="") Long page,
			@RequestParam(value = "keyword",required=false, defaultValue="") String keyword,
			@AuthUser UserVo authUser) {
		boardService.deleteContents(no, authUser.getNo());
		return "redirect:/board/list?page="+page+"&keyword="+keyword;
	}
}