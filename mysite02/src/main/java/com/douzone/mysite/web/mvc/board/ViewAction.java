package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web2.mvc.Action;
import com.douzone.web2.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo vo = new BoardDao().findNo(no);
		BoardVo hitVo = new BoardVo();
		hitVo.setHit(vo.getHit()+1);
		hitVo.setNo(vo.getNo());
		new BoardDao().update(hitVo);
		request.setAttribute("vo", vo);
		MvcUtil.forward("board/view", request, response);
		
//		Long visitCount = vo.getHit();
//		Cookie[] cookies = request.getCookies();
//		for(Cookie cookie:cookies) System.out.println(cookie.getComment());
//		if(cookies!= null && cookies.length>0) {
//			System.out.println("cookie 생존 visitCount : "+visitCount);
//		} else {
//			hitVo.setHit(vo.getHit()+1);
//			
//			Cookie cookie = new Cookie("visitCount",String.valueOf(visitCount));
//			cookie.setPath(request.getContextPath());
//			cookie.setMaxAge(24*60*60);
//		}
	}
}