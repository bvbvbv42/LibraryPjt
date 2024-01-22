package com.goodee.library.book;

import lombok.Getter;
import lombok.Setter;


// lombok 라이브러리사용할때 쓰는 어노테이션
@Getter
@Setter
public class BookVo {

	private int b_no;
	private String b_name;
	private String b_author;
	private String b_publisher;
	private String b_publish_year;
	private String b_thumbnail;
	private String b_reg_date;
	private String b_mod_date;
	
}