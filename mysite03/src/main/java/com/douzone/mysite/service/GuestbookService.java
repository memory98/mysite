package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;

@Service
public class GuestbookService {

	@Autowired
	private GuestbookRepository guestbookRepository;
	
}
