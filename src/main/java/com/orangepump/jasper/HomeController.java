package com.orangepump.jasper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}

	// 되는 소스 - 베열로 받기 
	@RequestMapping(value = "/print.do", method = RequestMethod.POST)
	public String print(@RequestBody List<Address> dto, 
			Model model) {
		logger.info(dto.toString());
//		model.addAttribute("list", Sheet1);
		return "print";
	}
	
	
	@RequestMapping(value = "/jasper.do",
			method = RequestMethod.POST)
	public String print2(@RequestBody AddressLabel dto, 
			Model model) {
		logger.info(dto.toString());
		return "print";
	}
}
