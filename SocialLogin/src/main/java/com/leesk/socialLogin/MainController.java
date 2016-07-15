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
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.sf.json.JSONObject;
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
		JSONObject jsonObj = JSONObject.fromObject(getInputStream( naverApiRequest("https://nid.naver.com/oauth2.0/token", params, "GET")));
		session.setAttribute("accessToken", jsonObj.getString("access_token"));
		session.setAttribute("tokenType", jsonObj.getString("token_type"));
		session.setAttribute("refreshToken", jsonObj.getString("refresh_token"));

		return null;
	}
	// randem state generate
	private String generateState(){
	    return new BigInteger(130, new SecureRandom()).toString(32);
	}
	
	// url 요청 메소드 (url, params, type[post or get]);
	private InputStream naverApiRequest(String urlStr, Map<String, Object> params, String type) throws Exception{
		if (type.equals("GET")) urlStr = urlStr + "?" + strParamsByMapParams(params);
		URL url = new URL(urlStr);
		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod(type);
		OutputStreamWriter wr =  new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
		if (type.equals("POST")) wr.write(strParamsByMapParams(params));
		wr.flush();
		return  connection.getInputStream();
	}
	// map 타입 params 를 String 으로 변환
	private String strParamsByMapParams(Map<String, Object> params){
		String paramsStr = "";
		for (String key:params.keySet()){
			Object value = params.get(key);
			paramsStr += key + "=" + value + "&";
		}
		return paramsStr.substring(0, paramsStr.length()-1);
	}
	private String getInputStream(InputStream is) throws Exception{
		InputStreamReader isr = new InputStreamReader(is,"UTF-8");
		BufferedReader br = new BufferedReader(isr);
	    String result = "";
	    String f_line = "" ;
		while (( f_line = br.readLine()) != null) {
			result += f_line.toString(); 
		}
		return result;
	}
}
