package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {

	@Autowired
	private SqlSession sqlSession;
	public List<GalleryVo> findAll() {
		return sqlSession.selectList("gallery.findAll");
	}
	
	public void delete(Long no) {
		sqlSession.selectOne("gallery.delete",no);
	}
	
	public void insert(GalleryVo vo) {
		sqlSession.selectOne("gallery.insert",vo);
	}
}