/*
*
* made by Sunku, Lee
* since - 2016.07.12
* email - devleesk@daumsoft.com
* 
* 
* 참조 사이트 : https://developers.naver.com/docs/login/web
*/
package com.leesk.socialLogin;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
public class MainController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session) {
		session.setAttribute("state", generateState());
		return "index";
	}
	
	// randem state generate
	private String generateState(){
	    return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
