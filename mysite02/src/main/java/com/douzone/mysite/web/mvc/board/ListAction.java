package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web2.mvc.Action;
import com.douzone.web2.util.MvcUtil;

public class ListAction implements Action {
	private static final int pageSize = 10;
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String search = request.getParameter("kwd")==null?"":request.getParameter("kwd");
		String page = request.getParameter("page")!=null?request.getParameter("page"):"1";
		request.setAttribute("keyword", search);
		request.setAttribute("page", page);
		List<BoardVo> list = null;
		int cnt;
		Long max = new BoardDao().maxgNo();
		list = new BoardDao().findAll2(search);
//		if (search == null || search.equals("")) {
//			list = new BoardDao().findAll();
//		} else {
//			list = new BoardDao().findTitle(search);
//		}
		cnt = list.size();
		int integerPage = Integer.parseInt(page);
		int start = (integerPage -1)*pageSize;
		int end = (pageSize * integerPage)>cnt-1?cnt:(pageSize * integerPage);
		list = list.subList(start,end);
		request.setAttribute("list", list);
		request.setAttribute("boardCnt", cnt);
		request.setAttribute("maxgno", max);
		MvcUtil.forward("board/list", request, response);
	}
}