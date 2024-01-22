package com.goodee.library.book;

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
}
