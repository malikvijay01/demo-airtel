package com.airtel.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value="/home", method = RequestMethod.GET)
	
	public ModelAndView showHomepage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("distance-view");
		String str = "Hello World!";
		mav.addObject("message", str);
		return mav;
		}

}