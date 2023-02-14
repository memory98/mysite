package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	private static final int LIST_SIZE = 10; //리스팅되는 게시물의 수
	// private static final int PAGE_SIZE = 5; //페이지 리스트의 페이지 수
	
	public void addContents(BoardVo vo) {
		boardRepository.insert(vo);
	}
	
	public BoardVo getContents(Long no) {
		return boardRepository.findNo(no);
	}
	public void updateContents(BoardVo vo) {
		boardRepository.update(vo);
	}
	
	public void deleteContents(Long no,Long userNo) {
		boardRepository.deleteNo(no,userNo);
	}
	
	public List<BoardVo> getContentsList(String keyword) {
		return boardRepository.findAll(keyword);
	}

	public Long maxgNo() {
		return boardRepository.maxgNo();
	}

	public BoardVo findNo(Long no) {
		return boardRepository.findNo(no);
	}

	public void updateoNo(long getgNo, long getoNo) {
		boardRepository.updateoNo(getgNo, getoNo);
	}

	public List<BoardVo> split(List<BoardVo> list,String page) {
		int cnt = list.size();
		int integerPage = Integer.parseInt(page);
		int start = (integerPage - 1) * LIST_SIZE;
		int end = (LIST_SIZE * integerPage) > cnt - 1 ? cnt : (LIST_SIZE * integerPage);
		list = list.subList(start, end);
		
		return list;
	}
	
	public int getPageSize() {
		return LIST_SIZE;
	}
}