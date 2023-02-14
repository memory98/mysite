package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public void join(UserVo vo) {
			userRepository.insert(vo);
	}

	public UserVo getUser(Long no) {
		
		return userRepository.findByNo(no);
	}
	
	public UserVo getUser(UserVo vo) {
		
		return userRepository.findByEmailAndPassword(vo.getEmail(),vo.getPassword());
	}

	public UserVo update(UserVo vo, UserVo authUser) {
		UserVo updateUser = new UserVo();
		if (vo.getName().equals(authUser.getName()) && vo.getPassword().equals("")) {
			System.out.println(1);
			updateUser = userRepository.update(1,vo,authUser.getNo());

		} else if (vo.getName().equals(authUser.getName()) &&!vo.getPassword().equals("")) {
			System.out.println(2);
			updateUser = userRepository.update(2,vo,authUser.getNo());

		} else if (!vo.getName().equals(authUser.getName()) && vo.getPassword().equals("")) {
			System.out.println(3);
			updateUser = userRepository.update(3,vo,authUser.getNo());

		} else if (!vo.getName().equals(authUser.getName()) && !vo.getPassword().equals("")) {
			System.out.println(4);
			updateUser = userRepository.update(4,vo,authUser.getNo());

		}
		
		return updateUser;
	}

	public UserVo findNo(Long no) {
		UserVo findUser = userRepository.findByNo(no);
		return findUser;
	}
}