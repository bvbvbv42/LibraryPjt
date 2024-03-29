package com.goodee.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	private static final Logger LOGGER = 
			             LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value= {"","/"}, method=RequestMethod.GET) //localhost8080이거나 localhost8080/ 일때 이 메소드를 진행하겠다
	public String home() {
		LOGGER.info("[HomeController] home();");
		return "home";
	}
}
