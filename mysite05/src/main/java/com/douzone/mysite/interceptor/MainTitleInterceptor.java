package com.douzone.mysite.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

public class MainTitleInterceptor implements HandlerInterceptor {

	@Autowired
	private ServletContext servletContext;
	@Autowired
	private SiteService siteService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		SiteVo siteVo = (SiteVo) servletContext.getAttribute("siteVo");
		if(siteVo==null) {
			SiteVo vo = siteService.getSite();
			servletContext.setAttribute("siteVo", vo);
//			request.getServletContext().setAttribute("siteVo", vo);
		}
		return true;
	}
}