package com.douzone.mysite.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SqlSession sqlSession;
	public void insert(BoardVo vo) {
		sqlSession.insert("board.insert",vo);
	}

	public List<BoardVo> findAll(String search) {
		List<BoardVo> result = sqlSession.selectList("board.findAll", search);
		return result;
	}

	public BoardVo findNo(Long no) {
		BoardVo result = sqlSession.selectOne("findNo",no);
		return result;
	}
	
	// 글 수정 기능
	public void update(BoardVo vo) {
		sqlSession.selectOne("board.update",vo);
	}
	
	// 삭제 기능
	public void deleteNo(Long no,Long userNo) {
		Map<String, Object> map = Map.of("no",no,"userNo",userNo);
		sqlSession.selectOne("board.deleteNo",map);
	}
	
	public Long maxgNo() {
		return sqlSession.selectOne("board.maxgNo");
	}

	//ono 한 칸씩 뒤로 update
	public void updateoNo(Long inputgNo,Long inputoNo) {
		Map<String, Object> map = Map.of("inputgNo",inputgNo,"inputoNo",inputoNo);
		List<BoardVo> list = sqlSession.selectList("board.findoNo",map);
		
		for(BoardVo boardvo : list) {
			boardvo.setoNo(boardvo.getoNo()+1);
			sqlSession.selectOne("board.updateoNo",boardvo);
		}
	}
}