/*
*
* made by Sunku, Lee
* since - 2016.07.12
* email - devleesk@daumsoft.com
* 
* 
* 참조 사이트 : https://developers.naver.com/docs/login/web
* 1. 랜덥으로 state 키를 생성합니다. - home()
* 2. 생성된 state 키와 clientId를 네이버 api에 요청하여, 네이버 로그인 시도합니다. - naverLogin()
* 3.
* 
* 
*/
package com.leesk.socialLogin;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
public class MainController {
	//clientId 와 clientSecret 는 네이버 개발자 센터에서 직접 등록해야합니다.
	private static final String naverClientId = "{naverClientId}";
	private static final String naverClientSecret = "{naverClientSecret}";
	// 로그인 후 콜백 받을 url입니다. 역시, 네이버 개발자 센터에 등록해주어야 합니다.
	private static final String naverCallbackUrl = "{naverCallbackUrl}";
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpSession session) {
		// state 생성해서 session에 저장
		session.setAttribute("state", generateState());
		return "index";
	}
	// 로그인 요청 controller
	@RequestMapping(value = "naverLogin", method = RequestMethod.GET)
	public String naverLogin(HttpSession session){
		return "redirect:https://nid.naver.com/oauth2.0/authorize?client_id=" + naverClientId + "&response_type=code&redirect_uri=" + naverCallbackUrl + "&state=" + session.getAttribute("state");
	}
	// {naverCallbackUrl} 로 로그인 결과를 반환받습니다. state, code 를 받게됩니다.
	@RequestMapping(value = "naverCallback", method = RequestMethod.GET)
	public String naverCallback(String state, String code, Model model,HttpSession session) throws Exception{
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("client_id", naverClientId);
		params.put("client_secret", naverClientSecret);
		params.put("grant_type", "authorization_code");
		params.put("state", state);
		params.put("code", code);
		return null;
	}
	// randem state generate
	private String generateState(){
	    return new BigInteger(130, new SecureRandom()).toString(32);
	}
}
