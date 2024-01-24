package com.goodee.library.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookService.class);
	
	@Autowired
	BookDao bookDao;
	
	public int createBookConfirm(BookVo vo) {
		LOGGER.info("[BookService] createBookConfirm();");

		return bookDao.insertBook(vo);
	}
	
	public List<BookVo> selectBookList(BookVo vo){  // 매개변수란 일을시키기위해 전달하는것
		LOGGER.info("[BookService] selectBookList();");
		
		// List<bookVo> bookVos = bookDao.selectBookList(b_name);
		// return bookVos  이2줄은 밑에있는 코드와 같다
		return bookDao.selectBookList(vo);
	}
	
	public int selectBookCount(String b_name) {
		LOGGER.info("[BookService] selectBookCount();");
		int totalCount = bookDao.selectBookCount(b_name);
		return totalCount;
	}
	
	public BookVo bookDetail(int b_no) {
		LOGGER.info("[BookService] bookDetail();" );
		BookVo vo = bookDao.selectBookOne(b_no);
		return vo;
	}
	
	public int modifyConfirm(BookVo vo) {
		LOGGER.info("[BookService] modifyConfirm();");
		
		return bookDao.updateBook(vo);
	}
	
}
