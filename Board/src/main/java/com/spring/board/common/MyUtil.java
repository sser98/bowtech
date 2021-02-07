package com.spring.board.common;

import javax.servlet.http.HttpServletRequest;


public class MyUtil {

	// *** ? 다음의 데이터까지 포함한 현재 URL 주소를 알려주는 메소드를 생성 *** //
	public static String getCurrentURL(HttpServletRequest request) {
		
		String currentURL = request.getRequestURL().toString();
		// http://localhost:9090/MyMVC/member/memberList.up
		
		String queryString = request.getQueryString();
		// currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=홍승의 
		
		if(queryString != null) {
			currentURL += "?" + queryString;
		 // http://localhost:9090/MyMVC/member/memberList.up?currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=홍승의 
		}
		
		
		String ctxPath = request.getContextPath();
		//     /MyMVC
		
		int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
		//   27        =           21                +        6
		
		currentURL = currentURL.substring(beginIndex + 1);
		//           member/memberList.up?currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=홍승의 
		
		return currentURL;
	}
	
	
	// **** 모든 html 특수문자 html 태그 제거 데이터 처리 **** // 
	public static String removeTag(String html) throws Exception {
		
		
		html=html.replaceAll("<(/)?([a-zA-Z]*)(\\\\s[a-zA-Z]*=[^>]*)?(\\\\s)*(/)?>", html);
		
		
		html=html.replaceAll("<", "&lt;"); 
		html=html.replaceAll(">", "&gt;");
		html=html.replaceAll("\r\n", "<br>");
		
		return html; 
		
	}
	
	public static String restoreTag(String html) throws Exception {
		
		html=html.replaceAll("&lt;","<");
		html=html.replaceAll("&gt;",">");
		html=html.replaceAll("<br>","\r\n");
		html=html.replaceAll("&amp;","[&]");
		html=html.replaceAll("&quot;","[\"]");
		html=html.replaceAll("&#39;","[\\]");
		html=html.replaceAll("&nbsp;"," ");
		html=html.replaceAll("&quot;","'");
		html=html.replaceAll("     ", "\t");
		
		return html; 
		
	}
	
	
	
	
	
	
	
	
}