package com.goodee.library.book;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

	@Autowired
	SqlSession sqlSession;

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookDao.class);
	
	
	private final String namespace = "com.goodee.library.book.BookDao.";
	
	public int insertBook(BookVo vo) {
		LOGGER.info("[BookDao] insertBook();");
		
		// return bookDao.insertBook(vo); <<여기에 밑에있는 수행결과가 들어간다
		
		// int result = sqlSession.insert(namespace + "insertBook", vo);
		// return result; 밑에꺼와 같다
		
		
		return sqlSession.insert(namespace + "insertBook", vo);
	}
	
	public List<BookVo> selectBookList(BookVo vo){
		LOGGER.info("[BookDao] selectBookList;");
		List<BookVo> bookVos = sqlSession.selectList(namespace + "selectBookList", vo);
		return bookVos;
	}
	
	public int selectBookCount(String b_name) {
		LOGGER.info("[BookDao] selectBookCount();");
		int totalCount = sqlSession.selectOne(namespace + "selectBookCount", b_name);
		return totalCount;
	}
	
	public BookVo selectBookOne(int b_no) {
		LOGGER.info("[BookDao] selectBookOne();");
		BookVo vo = sqlSession.selectOne(namespace + "selectBookOne", b_no);
		return vo;
	}
	
	public int updateBook(BookVo vo) {
		LOGGER.info("[BookDao] updateBook();");
		return sqlSession.update(namespace + "updateBook", vo);
	}
	
	public int deleteBook(int b_no) {
		LOGGER.info("[BookDa] deleteBook();");
		return sqlSession.delete(namespace + "deleteBook", b_no); 
	}
}
