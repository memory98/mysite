package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web2.mvc.Action;
import com.douzone.web2.util.MvcUtil;

public class UpdateAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");

		UserVo vo = new UserVo();
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);
		HttpSession session = request.getSession(false);
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		UserVo updateUser =null;
		if (name.equals(authUser.getName()) && password.equals("")) {
			System.out.println(1);
			updateUser = new UserDao().update(1,vo,authUser.getNo());

		} else if (name.equals(authUser.getName()) &&!password.equals("")) {
			System.out.println(2);
			updateUser = new UserDao().update(2,vo,authUser.getNo());

		} else if (!name.equals(authUser.getName()) && password.equals("")) {
			System.out.println(3);
			updateUser = new UserDao().update(3,vo,authUser.getNo());

		} else if (!name.equals(authUser.getName()) && !password.equals("")) {
			System.out.println(4);
			updateUser = new UserDao().update(4,vo,authUser.getNo());

		}
		
		authUser.setName(updateUser.getName());
//		session.setAttribute("authUser", updateUser);
		
		MvcUtil.redirect(request.getContextPath(), request, response);

	}
}
