package com.goodee.library.book;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.goodee.library.util.UploadFileService;

@Controller
@RequestMapping("/book")
public class BookController {
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(BookController.class);
	
	
	@Autowired
	UploadFileService uploadFileService;
	
	@Autowired
	BookService bookService;
	
	// 도서 등록 화면 이동
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public String createBookForm() {
		LOGGER.info("[BookController] createBookForm();");
		return "book/create";
	}
	
	// 도서 등록 기능
	@RequestMapping(value="/create", method=RequestMethod.POST)
	
	// 파일은 vo로 안넘어올때도 있음 
	public String createBookConfirm(BookVo vo,
			@RequestParam("file") MultipartFile file) {
		LOGGER.info("[BookController] createBookConfirm();");
		int result = -1;
		// 1. 파일 파싱(UploadFileService)
		String savedFileName = uploadFileService.upload(file);//파일의 이름과 위치정보 
		// 2. 도서 등록
		if(savedFileName != null && "".equals(savedFileName) == false) {
			vo.setB_thumbnail(savedFileName);
			result = bookService.createBookConfirm(vo);
			
			// 1. BookService 빈 등록
			// 2. BookController에 BookService 의존성 주입
			// 3. BookService 도서 등록 메소드(BookDao 부탁)
			// 4. BookDao 데이터베이스에 도서 등록(Mapper한테 부탁)
			// 5. book_mapper.xml 생성
		    // 6. insert구문 작성
			// 7. 테이블 tbl_bok
			
			
		}
		// 3. 결과 화면 전환
		
		// webapp/resources/css/book -> create_result.css
		// webapp/WEB-INF/views/book -> craete_success.jsp
		// webapp/WEB-INF/views/book -> craete_fail.jsp
		
		if(result > 0) {
			return "/book/create_success";
		} else {
			return "/book/create_fail";
		}
	}
	
	
	// 도서 목록 조회 기능(검색)
	@RequestMapping(method=RequestMethod.GET)
	// required=false ==> 검색어 필수 아니다!!!
	public String selectBookList(Model model, BookVo vo) {
		LOGGER.info("[BookController] selectBookList();");
		// 1. 목록 정보 조회(DB)
		vo.setTotalCount(bookService.selectBookCount(vo.getB_name()));
		List<BookVo> bookVos = bookService.selectBookList(vo);
		// 2. 화면 전환 + 정보 전달
		
		// webapp/resources/css/book/listup.css
		// views/book/listup.jsp
		
		model.addAttribute("bookVos", bookVos);
		model.addAttribute("pagingVo", vo);
		return "book/listup";
	}

	
	// 도서 상세 이동
	@RequestMapping(value="/{b_no}",method=RequestMethod.GET)
	public String bookDetail(@PathVariable int b_no, Model model) {
		LOGGER.info("[BookController] bookDetail();");
		// 1. 도서 하나 정보 조회
		BookVo vo = bookService.bookDetail(b_no);
		// 2. 화면 전환 + 정보 전달
		model.addAttribute("bookVo", vo);
		
		// webapp//resources/css/book/detail.css
		// WEB-INF//views/book/detail.jsp
		
		return "/book/detail";
	}
	
	// 도서 수정 이동
	@RequestMapping(value="/modify/{b_no}", method=RequestMethod.GET)
	// url에서 {b_no}을 받아오려면 @PathVariable 사용해야함
	public String modifyBookForm(@PathVariable int b_no, Model model) {
		LOGGER.info("[BookController] modifyBookForm();");
		// 1. 기존 정보 조회
		BookVo vo = bookService.bookDetail(b_no);
		// 2. 화면 전환 + 정보 전달
		model.addAttribute("bookVo",vo);
		return "book/modify";
	}
	
	// 도서 수정 기능
	@RequestMapping(value="/modify/{b_no}", method=RequestMethod.POST)
	public String modifyBookConfirm(BookVo vo, 
			// 파일은 requestparam으로 받아와야됨
			// 파일은 input타입으로 수정화면에 가져올수없다 (원래그럼)
			@RequestParam("file") MultipartFile file) {
		LOGGER.info("[BookController] modifyBookConfirm();");
		// 1. 만약에 새로운 파일 o -> 파일 업로드
		// 파일이 있으면
		if(file.getOriginalFilename().equals("") == false) {
			String savedFileName = uploadFileService.upload(file);
			if(savedFileName != null) {
				vo.setB_thumbnail(savedFileName);
			}
		}
		// 2. 도서 정보 수정
		int result = bookService.modifyConfirm(vo);
		// (1) BookService에 modifyConfirm 메소드 생성
		// (2) BookDao에 updateBook 메소드를 생성
		// (3) BookService의 modifyConfim이 BookDao의 updateBook으로부터
		// in(update 수행결과)를 전달
		// (4) book_mapper에 updateBook 쿼리 생성
		// -> 파라미터가 BookVo
		// -> tbl_book을 UPDATE
		// -> b_name, b_author, b_publisher, b_publish_year, b_mod_date
		// -> 만약에 b_thumnail이 null이 아니면서 빈 스트링이 아니라면 b_thumbnail도 수정
		if(result <= 0) {
			return "book/modify_fail";
		} else {
			return "book/modify_success";
		}
	
		
		
		// 3. 결과 화면 이동
	}
}