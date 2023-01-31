package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handlerException(Model model,Exception e) {
		//1. 로깅(Logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		System.out.println(errors.toString());
		
		//2. 사과페이지(정상종료)
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception",e);
		mav.setViewName("error/exception");
		return mav;
	}
}
