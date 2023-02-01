package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;
@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private SqlSession sqlSession;
	public void insert(UserVo vo) {
		sqlSession.selectOne("user.insert",vo);
	}

	public UserVo findByEmailAndPassword(String email,String password) {
		Map<String,Object> map = new HashMap<>();
		map.put("e",email);
		map.put("p", password);
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}

	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo", no);
	}

	public UserVo update(int i, UserVo vo,Long no) {
		Map<String,Object> map = new HashMap<>();
		map.put("i", String.valueOf(i));
		map.put("no", String.valueOf(no));
		map.put("name", vo.getName());
		map.put("password", vo.getPassword());
		map.put("gender", vo.getGender());
		sqlSession.update("user.update",map);
		System.out.println("map : "+map);
		return findByNo(no);
	}
}