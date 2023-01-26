package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web2.mvc.Action;
import com.douzone.web2.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String userNoStr = request.getParameter("user_no");
		Long no = Long.parseLong(request.getParameter("no"));
		Long userNo = Long.parseLong(userNoStr);
		BoardVo vo = new BoardVo();
		
		if(!title.equals("")&&!content.equals("")) {
			if(no==-1) {
				vo.setoNo(1);
				vo.setgNo(new BoardDao().maxgNo()+1);
				vo.setDepth(0);
			} else {
				vo = new BoardDao().findNo(no);
				vo.setoNo(vo.getoNo()+1);
				new BoardDao().updateoNo(vo.getgNo(),vo.getoNo());
				vo.setDepth(vo.getDepth()+1);
			}
			vo.setTitle(title);
			vo.setContents(content);
			vo.setUserNo(userNo);
			
			new BoardDao().insert(vo);
		} 
		MvcUtil.redirect(request.getContextPath()+"/board", request, response);
	}
}