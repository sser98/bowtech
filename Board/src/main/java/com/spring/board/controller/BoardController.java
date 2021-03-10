package com.spring.board.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.ParseConversionEvent;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.board.common.FileManager;
import com.spring.board.common.MyUtil;
import com.spring.board.common.Sha256;
import com.spring.board.model.*;
import com.spring.board.service.*;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

/*
	사용자 웹브라우저 요청(View)  ==> DispatcherServlet ==> @Controller 클래스 <==>> Service단(핵심업무로직단, business logic단) <==>> Model단[Repository](DAO, DTO) <==>> myBatis <==>> DB(오라클)           
	(http://...  *.action)                                  |                                                                                                                              
	 ↑                                                View Resolver
	 |                                                      ↓
	 |                                                View단(.jsp 또는 Bean명)
	 -------------------------------------------------------| 
	
	사용자(클라이언트)가 웹브라우저에서 http://localhost:9090/board/test_insert.action 을 실행하면
	배치서술자인 web.xml 에 기술된 대로  org.springframework.web.servlet.DispatcherServlet 이 작동된다.
	DispatcherServlet 은 bean 으로 등록된 객체중 controller 빈을 찾아서  URL값이 "/test_insert.action" 으로
	매핑된 메소드를 실행시키게 된다.                                               
	Service(서비스)단 객체를 업무 로직단(비지니스 로직단)이라고 부른다.
	Service(서비스)단 객체가 하는 일은 Model단에서 작성된 데이터베이스 관련 여러 메소드들 중 관련있는것들만을 모아 모아서
	하나의 트랜잭션 처리 작업이 이루어지도록 만들어주는 객체이다.
	여기서 업무라는 것은 데이터베이스와 관련된 처리 업무를 말하는 것으로 Model 단에서 작성된 메소드를 말하는 것이다.
	이 서비스 객체는 @Controller 단에서 넘겨받은 어떤 값을 가지고 Model 단에서 작성된 여러 메소드를 호출하여 실행되어지도록 해주는 것이다.
	실행되어진 결과값을 @Controller 단으로 넘겨준다.
*/

//=== #30. 컨트롤러 선언 === 
@Component
/*
 * XML에서 빈을 만드는 대신에 클래스명 앞에 @Component 어노테이션을 적어주면 해당 클래스는 bean으로 자동 등록된다. 그리고
 * bean의 이름(첫글자는 소문자)은 해당 클래스명이 된다. 즉, 여기서 bean의 이름은 boardController 이 된다.
 * 여기서는 @Controller 를 사용하므로 @Component 기능이 이미 있으므로 @Component를 명기하지 않아도
 * BoardController 는 bean 으로 등록되어 스프링컨테이너가 자동적으로 관리해준다.
 */
@Controller
@Log4j
public class BoardController {

	// === #35. 의존객체 주입하기(DI: Dependency Injection) ===
	// ※ 의존객체주입(DI : Dependency Injection)
	// ==> 스프링 프레임워크는 객체를 관리해주는 컨테이너를 제공해주고 있다.
	// 스프링 컨테이너는 bean으로 등록되어진 BoardController 클래스 객체가 사용되어질때,
	// BoardController 클래스의 인스턴스 객체변수(의존객체)인 BoardService service 에
	// 자동적으로 bean 으로 등록되어 생성되어진 BoardService service 객체를
	// BoardController 클래스의 인스턴스 변수 객체로 사용되어지게끔 넣어주는 것을 의존객체주입(DI : Dependency
	// Injection)이라고 부른다.
	// 이것이 바로 IoC(Inversion of Control == 제어의 역전) 인 것이다.
	// 즉, 개발자가 인스턴스 변수 객체를 필요에 의해 생성해주던 것에서 탈피하여 스프링은 컨테이너에 객체를 담아 두고,
	// 필요할 때에 컨테이너로부터 객체를 가져와 사용할 수 있도록 하고 있다.
	// 스프링은 객체의 생성 및 생명주기를 관리할 수 있는 기능을 제공하고 있으므로, 더이상 개발자에 의해 객체를 생성 및 소멸하도록 하지 않고
	// 객체 생성 및 관리를 스프링 프레임워크가 가지고 있는 객체 관리기능을 사용하므로 Inversion of Control == 제어의 역전
	// 이라고 부른다.
	// 그래서 스프링 컨테이너를 IoC 컨테이너라고도 부른다.

	// IOC(Inversion of Control) 란 ?
	// ==> 스프링 프레임워크는 사용하고자 하는 객체를 빈형태로 이미 만들어 두고서 컨테이너(Container)에 넣어둔후
	// 필요한 객체사용시 컨테이너(Container)에서 꺼내어 사용하도록 되어있다.
	// 이와 같이 객체 생성 및 소멸에 대한 제어권을 개발자가 하는것이 아니라 스프링 Container 가 하게됨으로써
	// 객체에 대한 제어역할이 개발자에게서 스프링 Container로 넘어가게 됨을 뜻하는 의미가 제어의 역전
	// 즉, IOC(Inversion of Control) 이라고 부른다.

	// === 느슨한 결합 ===
	// 스프링 컨테이너가 BoardController 클래스 객체에서 BoardService 클래스 객체를 사용할 수 있도록
	// 만들어주는 것을 "느슨한 결합" 이라고 부른다.
	// 느스한 결합은 BoardController 객체가 메모리에서 삭제되더라도 BoardService service 객체는 메모리에서 동시에
	// 삭제되는 것이 아니라 남아 있다.

	// ===> 단단한 결합(개발자가 인스턴스 변수 객체를 필요에 의해서 생성해주던 것)
	// private InterBoardService service = new BoardService();
	// ===> BoardController 객체가 메모리에서 삭제 되어지면 BoardService service 객체는 멤버변수(필드)이므로
	// 메모리에서 자동적으로 삭제되어진다.

	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private InterBoardService service;

	// === #155. 파일업로드 및 다운로드를 해주는 FileManager 클래스 의존객체 주입하기(DI : Dependency
	// Injection) ===
	@Autowired // Type에 따라 알아서 Bean 을 주입해준다.
	private FileManager fileManager;

	// ============= ***** 기초시작 ***** ============= //
	@RequestMapping(value = "/test/test_insert.action")
	public String test_insert(HttpServletRequest request) {

		int n = service.test_insert();

		String message = "";

		if (n == 1) {
			message = "데이터 입력 성공!!";
		} else {
			message = "데이터 입력 실패!!";
		}

		request.setAttribute("message", message);
		request.setAttribute("n", n);

		return "sample/test_insert";
		// /WEB-INF/views/sample/test_insert.jsp 페이지를 만들어야 한다.
	}

	@RequestMapping(value = "/test/test_select.action")
	public String test_select(HttpServletRequest request) {

		List<TestVO> testvoList = service.test_select();

		request.setAttribute("testvoList", testvoList);

		return "sample/test_select";
		// /WEB-INF/views/sample/test_select.jsp 페이지를 만들어야 한다.
	}

//	@RequestMapping(value="/test/test_form.action", method= {RequestMethod.GET})  오로지 GET방식만 허락하는 것임.
//	@RequestMapping(value="/test/test_form.action", method= {RequestMethod.POST}) 오로지 POST방식만 허락하는 것임.
	@RequestMapping(value = "/test/test_form.action") // GET방식 및 POST방식 둘 모두 허락하는 것임.
	public String test_form(HttpServletRequest request) {

		String method = request.getMethod();

		if ("get".equalsIgnoreCase(method)) { // GET 방식이라면
			return "sample/test_form"; // view단 페이지를 띄워라
			// /WEB-INF/views/sample/test_form.jsp 페이지를 만들어야 한다.
		} else { // POST 방식이라면
			String no = request.getParameter("no");
			String name = request.getParameter("name");

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("no", no);
			paraMap.put("name", name);

			int n = service.test_insert(paraMap);

			if (n == 1) {
				return "redirect:/test/test_select.action";
				// /test/test_select.action 페이지로 redirect(페이지이동)해라는 말이다.
			} else {
				return "redirect:/test/test_form.action";
				// /test/test_form.action 페이지로 redirect(페이지이동)해라는 말이다.
			}
		}
	}

	@RequestMapping(value = "/test/test_form2.action") // GET방식 및 POST방식 둘 모두 허락하는 것임.
	public String test_form2(HttpServletRequest request, TestVO vo) {

		String method = request.getMethod();

		if ("get".equalsIgnoreCase(method)) { // GET 방식이라면
			return "sample/test_form2"; // view단 페이지를 띄워라
			// /WEB-INF/views/sample/test_form2.jsp 페이지를 만들어야 한다.
		} else { // POST 방식이라면

			int n = service.test_insert(vo);

			if (n == 1) {
				return "redirect:/test/test_select.action";
				// /test/test_select.action 페이지로 redirect(페이지이동)해라는 말이다.
			} else {
				return "redirect:/test/test_form.action";
				// /test/test_form.action 페이지로 redirect(페이지이동)해라는 말이다.
			}
		}
	}

	// === AJAX 연습 시작 === //
	@RequestMapping(value = "/test/test_form3.action", method = { RequestMethod.GET }) // 오로지 GET방식만 허락하는 것임.
	public String test_form3() {

		return "sample/test_form3"; // view단 페이지를 띄워라
	}

	/*
	 * @ResponseBody 란? 메소드에 @ResponseBody Annotation이 되어 있으면 return 되는 값은 View 단
	 * 페이지를 통해서 출력되는 것이 아니라 return 되어지는 값 그 자체를 웹브라우저에 바로 직접 쓰여지게 하는 것이다. 일반적으로 JSON
	 * 값을 Return 할때 많이 사용된다.
	 */
	@ResponseBody
	@RequestMapping(value = "/test/ajax_insert.action", method = { RequestMethod.POST }) // 오로지 POST방식만 허락하는 것임.
	public String ajax_insert(HttpServletRequest request) {

		String no = request.getParameter("no");
		String name = request.getParameter("name");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("no", no);
		paraMap.put("name", name);

		int n = service.test_insert(paraMap);

		JSONObject jsonObj = new JSONObject(); // {}
		jsonObj.put("n", n); // {"n":1}

		return jsonObj.toString();
	}

	/*
	 * @ResponseBody 란? 메소드에 @ResponseBody Annotation이 되어 있으면 return 되는 값은 View 단
	 * 페이지를 통해서 출력되는 것이 아니라 return 되어지는 값 그 자체를 웹브라우저에 바로 직접 쓰여지게 하는 것이다. 일반적으로 JSON
	 * 값을 Return 할때 많이 사용된다.
	 * 
	 * >>> 스프링에서 json 또는 gson을 사용한 ajax 구현시 데이터를 화면에 출력해 줄때 한글로 된 데이터가 '?'로 출력되어 한글이
	 * 깨지는 현상이 있다. 이것을 해결하는 방법은 @RequestMapping 어노테이션의 속성 중
	 * produces="text/plain;charset=UTF-8" 를 사용하면 응답 페이지에 대한 UTF-8 인코딩이 가능하여 한글 깨짐을
	 * 방지 할 수 있다. <<<
	 */
	@ResponseBody
	@RequestMapping(value = "/test/ajax_select.action", produces = "text/plain;charset=UTF-8")
	public String ajax_select() {

		List<TestVO> testvoList = service.test_select();

		JSONArray jsonArr = new JSONArray(); // []

		if (testvoList != null) {
			for (TestVO vo : testvoList) {
				JSONObject jsonObj = new JSONObject(); // {} {}
				jsonObj.put("no", vo.getNo()); // {"no":101} {"no":1004}
				jsonObj.put("name", vo.getName()); // {"no":101, "name":"이순신"} {"no":1004, "name":"신호연"}
				jsonObj.put("writeday", vo.getWriteday()); // {"no":101, "name":"이순신", "writeday":"2020-11-24 16:20:30"}
															// {"no":1004, "name":"신호연", "writeday":"2020-11-24
															// 16:21:40"}

				jsonArr.put(jsonObj); // [{"no":101, "name":"이순신", "writeday":"2020-11-24 16:20:30"} {"no":1004,
										// "name":"신호연", "writeday":"2020-11-24 16:21:40"}]
			}
		}

		return jsonArr.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/delComment.action", produces = "text/plain;charset=UTF-8")
	public String delComment(String pw, String seq, String parentseq) {

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("pw", pw);
		paraMap.put("seq", seq);
		paraMap.put("parentseq", parentseq);

		System.out.println(pw);
		System.out.println(seq);
		System.out.println(parentseq);

		int n = service.delComment(paraMap);

		JSONObject jsonObj = new JSONObject(); // []

		jsonObj.put("n", n);

		return jsonObj.toString();
	}

	// === return 타입을 String 대신에 ModelAndView 를 사용해 보겠다. === //
	@RequestMapping(value = "/test/modelAndview_insert.action")
	public ModelAndView modelAndview_insert(ModelAndView mav, HttpServletRequest request) {

		String method = request.getMethod();

		if ("GET".equalsIgnoreCase(method)) {
			mav.setViewName("sample/test_form4");
			// view단 페이지의 파일명 지정하기
		} else {
			String no = request.getParameter("no");
			String name = request.getParameter("name");

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("no", no);
			paraMap.put("name", name);

			int n = service.test_insert(paraMap);

			if (n == 1) {
				/*
				 * List<TestVO> testvoList = service.test_select();
				 * 
				 * mav.addObject("testvoList", testvoList); // 위의 말은 request 영역에 testvoList 객체를
				 * "testvoList" 키이름으로 저장시켜두는 것이다. // 즉, request.setAttribute("testvoList",
				 * testvoList); 와 똑같은 것이다.
				 * 
				 * mav.setViewName("sample/test_select");
				 */

				// === 또는 페이지의 이동을 한다. ===
				mav.setViewName("redirect:/test/test_select.action");
			}
		}

		return mav;
	}

	// == 데이터테이블즈(datatables) -- datatables 1.10.19 기반으로 작성 == //
	@RequestMapping(value = "/test/employees.action")
	public ModelAndView test_employees(ModelAndView mav) {

		List<Map<String, String>> empList = service.test_employees();
		mav.addObject("empList", empList);
		mav.setViewName("sample/employees");
		// /WEB-INF/views/sample/employees.jsp 파일을 생성한다.

		return mav;
	}

	@RequestMapping(value = "/test/employees_tiles1.action")
	public ModelAndView employees_tiles1(ModelAndView mav) {

		List<Map<String, String>> empList = service.test_employees();

		mav.addObject("empList", empList);
		mav.setViewName("sample/employees.tiles1");
		// /WEB-INF/views/tiles1/sample/employees.jsp 파일을 생성한다.

		return mav;
	}

	@RequestMapping(value = "/test/employees_tiles2.action")
	public ModelAndView employees_tiles2(ModelAndView mav) {

		List<Map<String, String>> empList = service.test_employees();

		mav.addObject("empList", empList);
		mav.setViewName("sample/employees.tiles2");
		// /WEB-INF/views/tiles2/sample/employees.jsp 파일을 생성한다.

		return mav;
	}

	// ============= ***** 기초끝 ***** ============= //

	/////////////////////////////////////////////////////////////

	// === #36. 메인 페이지 요청 === //
	@RequestMapping(value = "/index.action")
	public ModelAndView index(ModelAndView mav) {

		List<String> imgfilenameList = service.getImgfilenameList();

		mav.addObject("imgfilenameList", imgfilenameList);
		mav.setViewName("main/index.tiles1");
		// /WEB-INF/views/tiles1/main/index.jsp 파일을 생성한다.

		return mav;
	}

	// === #40. 로그인 폼 페이지 요청 === //
	@RequestMapping(value = "/login.action", method = { RequestMethod.GET })
	public ModelAndView login(ModelAndView mav) {

		mav.setViewName("login/loginform.tiles1");
		// /WEB-INF/views/tiles1/login/loginform.jsp 파일을 생성한다.

		return mav;
	}

	// === #41. 로그인 처리하기 === //
	@RequestMapping(value = "/loginEnd.action", method = { RequestMethod.POST })
	public ModelAndView loginEnd(ModelAndView mav, HttpServletRequest request) {

		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pwd");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("pwd", Sha256.encrypt(pwd));

		MemberVO loginuser = service.getLoginMember(paraMap);

		if (loginuser == null) { // 로그인 실패시
			String message = "아이디 또는 암호가 틀립니다.";
			String loc = "javascript:history.back()";

			mav.addObject("message", message);
			mav.addObject("loc", loc);

			mav.setViewName("msg");
			// /WEB-INF/views/msg.jsp 파일을 생성한다.
		}

		else { // 아이디와 암호가 존재하는 경우

			if (loginuser.getIdle() == 1) { // 로그인 한지 1년이 경과한 경우
				String message = "로그인을 한지 1년지 지나서 휴면상태로 되었습니다. 관리자가에게 문의 바랍니다.";
				String loc = request.getContextPath() + "/index.action";
				// 원래는 위와같이 index.action 이 아니라 휴면인 계정을 풀어주는 페이지로 잡아주어야 한다.

				mav.addObject("message", message);
				mav.addObject("loc", loc);
				mav.setViewName("msg");
			}

			else { // 로그인 한지 1년 이내인 경우

				// !!!! session(세션) 이라는 저장소에 로그인 되어진 loginuser 을 저장시켜두어야 한다.!!!! //
				// session(세션) 이란 ? WAS 컴퓨터의 메모리(RAM)의 일부분을 사용하는 것으로 접속한 클라이언트 컴퓨터에서 보내온 정보를
				// 저장하는 용도로 쓰인다.
				// 클라이언트 컴퓨터가 WAS 컴퓨터에 웹으로 접속을 하기만 하면 무조건 자동적으로 WAS 컴퓨터의 메모리(RAM)의 일부분에 session
				// 이 생성되어진다.
				// session 은 클라이언트 컴퓨터 웹브라우저당 1개씩 생성되어진다.
				// 예를 들면 클라이언트 컴퓨터가 크롬웹브라우저로 WAS 컴퓨터에 웹으로 연결하면 session이 하나 생성되어지고 ,
				// 또 이어서 동일한 클라이언트 컴퓨터가 엣지웹브라우저로 WAS 컴퓨터에 웹으로 연결하면 또 하나의 새로운 session이 생성되어진다.
				/*
				 * ------------- | 클라이언트 | --------------------- | A 웹브라우저 | ----------- | WAS
				 * 서버 | ------------- | | | RAM (A session) | -------------- | (B session) | |
				 * 클라이언트 | | | | B 웹브라우저 | ---------- | | --------------- --------------------
				 * 
				 * !!!! 세션(session)이라는 저장 영역에 loginuser 를 저장시켜두면 Command.properties 파일에 기술된 모든
				 * 클래스 및 모든 JSP 페이지(파일)에서 세션(session)에 저장되어진 loginuser 정보를 사용할 수 있게 된다. !!!!
				 * 그러므로 어떤 정보를 여러 클래스 또는 여러 jsp 페이지에서 공통적으로 사용하고자 한다라면 세션(session)에 저장해야 한다.!!!!
				 */

				HttpSession session = request.getSession();
				// 메모리에 생성되어져 있는 session을 불러오는 것이다.

				session.setAttribute("loginuser", loginuser);
				// session(세션)에 로그인 되어진 사용자 정보인 loginuser 을 키이름을 "loginuser" 으로 저장시켜두는 것이다.

				if (loginuser.isRequirePwdChange() == true) { // 암호를 마지막으로 변경한것이 3개월이 경과한 경우
					String message = "비밀번호를 변경하신지 3개월이 지났습니다. 암호를 변경하세요!!";
					String loc = request.getContextPath() + "/index.action";
					// 원래는 위와같이 index.action 이 아니라 사용자의 암호를 변경해주는 페이지로 잡아주어야 한다.

					mav.addObject("message", message);
					mav.addObject("loc", loc);
					mav.setViewName("msg");
				}

				else { // 암호를 마지막으로 변경한것이 3개월 이내인 경우

					// 막바로 페이지 이동을 시킨다.

					// 특정 제품상세 페이지를 보았을 경우 로그인을 하면 시작페이지로 가는 것이 아니라
					// 방금 보았던 특정 제품상세 페이지로 가기 위한 것이다.
					String goBackURL = (String) session.getAttribute("goBackURL");
					// shop/prodView.up?pnum=66
					// 또는 null

					if (goBackURL != null) {
						mav.setViewName("redirect:/" + goBackURL);
						session.removeAttribute("goBackURL"); // 세션에서 반드시 제거해주어야 한다.
					} else {
						mav.setViewName("redirect:/index.action");
					}

				}

			}

		}

		return mav;
	}

	// === #50. 로그아웃 처리하기 === //
	@RequestMapping(value = "/logout.action")
	public ModelAndView logout(ModelAndView mav, HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.invalidate();

		String message = "로그아웃 되었습니다.";
		String loc = request.getContextPath() + "/index.action";

		mav.addObject("message", message);
		mav.addObject("loc", loc);
		mav.setViewName("msg");

		return mav;
	}
	
	
	// === #51. 게시판 글쓰기 폼페이지 요청 === //
	@RequestMapping(value = "/add.action")
	public ModelAndView requiredLogin_add(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {
		
		// === #142. 답변글쓰기가 추가된 경우 === //
		String fk_seq = request.getParameter("fk_seq");
		String groupno = request.getParameter("groupno");
		String depthno = request.getParameter("depthno");

		mav.addObject("fk_seq", fk_seq);
		mav.addObject("groupno", groupno);
		mav.addObject("depthno", depthno);
		//////////////////////////////////////////////////
		mav.setViewName("board/add.tiles1");
		// /WEB-INF/views/tiles1/board/add.jsp 파일을 생성한다.
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/testFile.action")
	public String testFile(MultipartFile[] attach, MultipartHttpServletRequest mrequest) {

		MultipartFile file = (MultipartFile) mrequest.getFileNames();

		System.out.println(file);

		return "1";
	}
	
	
	
	// === #54. 게시판 글쓰기 완료 요청 === //
	@RequestMapping(value = "/addEnd.action", method = { RequestMethod.POST })
	// public String addEnd(BoardVO boardvo) { <== After Advice 를 사용하기전
	/*
	 * form 태그의 name 명과 BoardVO 의 필드명이 같다라면 request.getParameter("form 태그의 name명");
	 * 을 사용하지 않더라도 자동적으로 BoardVO boardvo 에 set 되어진다.
	 */
	
	// public String pointPlus_addEnd(Map<String,String> paraMap, BoardVO boardvo) {
	/*
	 * === #151. 파일첨부가 된 글쓰기 이므로 먼저 위의 public String
	 * pointPlus_addEnd(Map<String,String> paraMap, BoardVO boardvo) { 을 주석처리 한 이후에
	 * 아래와 같이 한다. MultipartHttpServletRequest mrequest 를 사용하기 위해서는 먼저
	 * /Board/src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml 에서 #21
	 * 파일업로드 및 파일다운로드에 필요한 의존객체 설정하기 를 해두어야 한다.
	 */
	
	
	// public String addEnd(Map<String,String> paraMap, BoardVO boardvo,
	// MultipartHttpServletRequest mrequest) { 기존것
	
	
	public String addEnd(Map<String, String> paraMap, BoardVO boardvo, MultipartHttpServletRequest mrequest,
			@RequestParam("attach") MultipartFile[] attach) {
		// @RequestParam("files") MultipartFile[] files
		
		// 파일즈라는 걸로 들어감
		/*
		 * 웹페이지에 요청 form이 enctype="multipart/form-data" 으로 되어있어서 Multipart 요청(파일처리 요청)이
		 * 들어올때 컨트롤러에서는 HttpServletRequest 대신 MultipartHttpServletRequest 인터페이스를 사용해야
		 * 한다. MultipartHttpServletRequest 인터페이스는 HttpServletRequest 인터페이스와
		 * MultipartRequest 인터페이스를 상속받고있다. 즉, 웹 요청 정보를 얻기 위한 getParameter()와 같은 메소드와
		 * Multipart(파일처리) 관련 메소드를 모두 사용가능하다.
		 */
		
		// === 사용자가 쓴 글에 파일이 첨부되어 있는 것인지, 아니면 파일첨부가 안된것인지 구분을 지어주어야 한다. === //
		// === #153. !!! 첨부파일이 있는 경우 작업 시작 !!! === //
		
		
		HttpSession session = mrequest.getSession();

		
		if (!attach[0].isEmpty()) {

			// attach(첨부파일)가 비어있지 않으면(즉, 첨부파일이 있는 경우라면)
			/*
			 * 1. 사용자가 보낸 첨부파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다. >>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기
			 * 우리는 WAS의 webapp/resources/files 라는 폴더로 지정해준다. 조심할 것은 Package Explorer 에서
			 * files 라는 폴더를 만드는 것이 아니다.
			 */
			// WAS의 webapp 의 절대경로를 알아와야 한다.

			// 날짜 별로 폴더 만들기
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String str = sdf.format(date);
			str = str.replace("-", File.separator);

			String root = session.getServletContext().getRealPath("/");
			System.out.println("~~~~ webapp 의 절대경로 => " + root);
			// ~~~~ webapp 의 절대경로 =>
			// C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\

			String path = root + "resources" + File.separator + str;
			
			/*
			 * File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다. 운영체제가 Windows 이라면 File.separator
			 * 는 "\" 이고, 운영체제가 UNIX, Linux 이라면 File.separator 는 "/" 이다.
			 */
			// path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다.
			System.out.println("~~~~ path => " + path);
			// ~~~~ path =>

			// C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\files

			// 한 폴더에 너무 많은 파일이 있는 경우에 속도의 저하와 개수의 제한 문제가 생기는 것을 방지해야함.

			// 첨부파일의 크기

			String fileS = "";

			String newfile = "";

			String orginfile = "";

			for (MultipartFile multipartFile : attach) {

				int i = 1;
				String newFileName = "";
				// WAS(톰캣)의 디스크에 저장될 파일명
				String orginfileName = "";
				long fileSize = 0;

				/*
				 * 2. 파일첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일올리기
				 */

				byte[] bytes = null;
				// 첨부파일의 내용물을 담는 것

				try {

					bytes = multipartFile.getBytes();
					// 첨부파일의 내용물을 읽어오는 것

					newFileName = fileManager.doFileUpload(bytes, multipartFile.getOriginalFilename(), path);
					// 첨부되어진 파일을 업로드 하도록 하는 것이다.
					// attach.getOriginalFilename() 은 첨부파일의 파일명(예: 강아지.png)이다.

					// System.out.println(">>> 확인용 newFileName => " + newFileName);
					// >>> 확인용 newFileName => 2020120910381893648363550700.png

					// 사용자에게 보여질 원래의 파일명하고
					// WAS 내 파일 명 컬럼 안에 각각 입력시켜주어야 함.

					fileSize = multipartFile.getSize(); // 첨부파일의 크기(단위는 byte임)
					
					// 마지막 값인경우 콤마를 넣을 필요가없음
					if (i == attach.length) {

						newfile += newFileName;
						orginfile += multipartFile.getOriginalFilename();
						fileS += String.valueOf(fileSize);
					} else {
						newfile += newFileName + ",";
						orginfile += multipartFile.getOriginalFilename() + ",";
						fileS += String.valueOf(fileSize) + ",";

					}

					/*
					 * boardvo.setFileName(newFileName); // WAS(톰캣)에 저장될
					 * 파일명(2020120809271535243254235235234.png;)
					 */

					/* boardvo.setOrgFilename(multipartFile.getOriginalFilename()); */
					// 게시판 페이지에서 첨부된 파일(강아지.png)을 보여줄 때 사용.
					// 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.

				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
			
			
			boardvo.setFileName(newfile);
			// WAS(톰캣)에 저장될 파일명(2020120809271535243254235235234.png,)

			boardvo.setOrgFilename(orginfile);
			// 게시판 페이지에서 첨부된 파일(강아지.png)을 보여줄 때 사용.
			// 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.

			boardvo.setFileSize(fileS);

			int n = 0;

			// 첨부파일이 있는 경우라면
			n = service.add_withFile(boardvo);

			if (n == 1) {
				return "redirect:/list.action";
				// list.action 페이지로 redirect(페이지이동)해라는 말이다.
			} else {
				return "redirect:/add.action";
				// add.action 페이지로 redirect(페이지이동)해라는 말이다.
			}

		} // end of if

		// 첨부파일이 없는경우
		else {

			int n = 0;
			n = service.add(boardvo);

			if (n == 1) {
				return "redirect:/list.action";
				// list.action 페이지로 redirect(페이지이동)해라는 말이다.
			} else {
				return "redirect:/add.action";
				// add.action 페이지로 redirect(페이지이동)해라는 말이다.
			}

		}

		// *--------------- 이전것-------------------------
		/*
		 * if( attach.length != 0) {
		 * 
		 * for (MultipartFile multipartFile : attach ) {
		 * 
		 * System.out.println("파일명 : "+multipartFile.getOriginalFilename());
		 * System.out.println("파일크기 : "+multipartFile.getSize();); } // attach(첨부파일)가
		 * 비어있지 않으면(즉, 첨부파일이 있는 경우라면) 1. 사용자가 보낸 첨부파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다. >>>
		 * 파일이 업로드 되어질 특정 경로(폴더)지정해주기 우리는 WAS의 webapp/resources/files 라는 폴더로 지정해준다. 조심할
		 * 것은 Package Explorer 에서 files 라는 폴더를 만드는 것이 아니다. // WAS의 webapp 의 절대경로를 알아와야
		 * 한다.
		 * 
		 * String root = session.getServletContext().getRealPath("/");
		 * 
		 * System.out.println("~~~~ webapp 의 절대경로 => " + root); // ~~~~ webapp 의 절대경로 =>
		 * C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\
		 * wtpwebapps\Board\
		 * 
		 * String path = root+"resources"+ File.separator +"files"; File.separator 는
		 * 운영체제에서 사용하는 폴더와 파일의 구분자이다. 운영체제가 Windows 이라면 File.separator 는 "\" 이고, 운영체제가
		 * UNIX, Linux 이라면 File.separator 는 "/" 이다.
		 * 
		 * 
		 * // path 가 첨부파일이 저장될 WAS(톰캣)의 폴더가 된다. System.out.println("~~~~ path => " +
		 * path); // ~~~~ path =>
		 * C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\
		 * wtpwebapps\Board\resources\files
		 * 
		 * 
		 * 2. 파일첨부를 위한 변수의 설정 및 값을 초기화 한 후 파일올리기
		 * 
		 * String newFileName = ""; // WAS(톰캣)의 디스크에 저장될 파일명
		 * 
		 * byte[] bytes = null; // 첨부파일의 내용물을 담는 것
		 * 
		 * long fileSize = 0; // 첨부파일의 크기
		 * 
		 * // 다중이면 반복되야 되고 컬럼의 수가 많아야함?
		 * 
		 * try { bytes = attach.getBytes(); // 첨부파일의 내용물을 읽어오는 것
		 * 
		 * newFileName = fileManager.doFileUpload(bytes, attach.getOriginalFilename(),
		 * path); // 첨부되어진 파일을 업로드 하도록 하는 것이다. // attach.getOriginalFilename() 은 첨부파일의
		 * 파일명(예: 강아지.png)이다.
		 * 
		 * System.out.println(">>> 확인용  newFileName => " + newFileName); // >>> 확인용
		 * newFileName => 2020120910381893648363550700.png
		 * 
		 * 
		 * 3. BoardVO boardvo 에 fileName 값과 orgFilename 값과 fileSize 값을 넣어주기
		 * 
		 * 
		 * boardvo.setFileName(newFileName); // WAS(톰캣)에 저장될
		 * 파일명(2020120809271535243254235235234.png)
		 * 
		 * boardvo.setOrgFilename(attach.getOriginalFilename()); // 게시판 페이지에서 첨부된
		 * 파일(강아지.png)을 보여줄 때 사용. // 또한 사용자가 파일을 다운로드 할때 사용되어지는 파일명으로 사용.
		 * 
		 * fileSize = attach.getSize(); // 첨부파일의 크기(단위는 byte임)
		 * boardvo.setFileSize(String.valueOf(fileSize));
		 * 
		 * } catch(Exception e) { e.printStackTrace(); }
		 * 
		 * }
		 * 
		 * int n = 0;
		 * 
		 * // 첨부파일이 없는 경우라면 if(attach.isEmpty()) { n = service.add(boardvo); }
		 * 
		 * else { // 첨부파일이 있는 경우라면 n = service.add_withFile(boardvo); }
		 * 
		 * if(n==1) { return "redirect:/list.action"; // list.action 페이지로
		 * redirect(페이지이동)해라는 말이다. } else { return "redirect:/add.action"; // add.action
		 * 페이지로 redirect(페이지이동)해라는 말이다. }
		 */

	}

	// === #58. 글목록 보기 페이지 요청 === //
	@RequestMapping(value = "/list.action")
	public ModelAndView list(HttpServletRequest request, ModelAndView mav) {
		
		
		List<BoardVO> boardList = null;
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");
		
		if (searchType == null) {
			searchType = "subject";
		}

		if (searchWord == null || searchWord.trim().isEmpty()) {
			searchWord = "";
		}
		
		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);

		// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.
		
		
		int totalCount = 0; // 총 게시물 건수
		int sizePerPage = 10; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0; // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
		int startRno = 0; // 시작 행번호
		int endRno = 0; // 끝 행번호
		int number = 0; // 전체 수
		
		
		// 총 게시물 건수(totalCount)
		totalCount = service.getTotalCount(paraMap);

		totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

		if (str_currentShowPageNo == null) {
			// 게시판에 보여지는 초기화면
			currentShowPageNo = 1;
		}

		else {
			
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if (currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}

		startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
		endRno = startRno + sizePerPage - 1;
		
		
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		paraMap.put("currentShowPageNo", String.valueOf(currentShowPageNo));

		boardList = service.boardListSearchWithPaging(paraMap);
		
		// 페이징 처리한 글목록 가져오기(검색이 있든지, 검색이 없든지 모두 다 포함한것)
		
		mav.addObject("paraMap", paraMap);
		
		if (!"".equals(searchWord)) {
			// mav.addObject("paraMap", paraMap);
		}

		// === #121. 페이지바 만들기 === //
		String pageBar = "<ul style='list-style: none;'>";

		int blockSize = 10;

		// blockSize 는 1개 블럭(토막)당 보여지는 페이지번호의 개수 이다.

		/*
		 * 1 2 3 4 5 6 7 8 9 10 다음 -- 1개블럭 이전 11 12 13 14 15 16 17 18 19 20 다음 -- 1개블럭
		 * 이전 21 22 23
		 */

		int loop = 1;

		/*
		 * loop는 1부터 증가하여 1개 블럭을 이루는 페이지번호의 개수[ 지금은 10개(== blockSize) ] 까지만 증가하는 용도이다.
		 */

		int pageNo = ((currentShowPageNo - 1) / blockSize) * blockSize + 1;

		// *** !! 공식이다. !! *** //

		/*
		 * 1 2 3 4 5 6 7 8 9 10 -- 첫번째 블럭의 페이지번호 시작값(pageNo)은 1 이다. 11 12 13 14 15 16 17
		 * 18 19 20 -- 두번째 블럭의 페이지번호 시작값(pageNo)은 11 이다. 21 22 23 24 25 26 27 28 29 30
		 * -- 세번째 블럭의 페이지번호 시작값(pageNo)은 21 이다.
		 * 
		 * 
		 * currentShowPageNo pageNo ---------------------------------- 1 1 = ((1 -
		 * 1)/10) * 10 + 1 2 1 = ((2 - 1)/10) * 10 + 1 3 1 = ((3 - 1)/10) * 10 + 1 4 1 5
		 * 1 6 1 7 1 8 1 9 1 10 1 = ((10 - 1)/10) * 10 + 1
		 * 
		 * 11 11 = ((11 - 1)/10) * 10 + 1 12 11 = ((12 - 1)/10) * 10 + 1 13 11 = ((13 -
		 * 1)/10) * 10 + 1 14 11 15 11 16 11 17 11 18 11 + 19 11 20 11 = ((20 - 1)/10) *
		 * 10 + 1
		 * 
		 * 21 21 = ((21 - 1)/10) * 10 + 1 22 21 = ((22 - 1)/10) * 10 + 1 23 21 = ((23 -
		 * 1)/10) * 10 + 1 .. .. 29 21 30 21 = ((30 - 1)/10) * 10 + 1
		 */

		String url = "list.action";
		
		
		// === [맨처음][이전] 만들기 ===
		if (pageNo != 1) {
			pageBar += "<li alt='맨처음으로 페이지 이동' title='맨처음으로 페이지 이동' style='display:inline-block; width:70px; font-size:12pt;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=1'>[맨처음]</a></li>";
			pageBar += "<li alt='이전 페이지로 이동' title='이전페이지로 이동' style='display:inline-block; width:50px; font-size:12pt;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + (pageNo - 1)
					+ "'>[이전]</a></li>";
		}

		while (!(loop > blockSize || pageNo > totalPage)) {

			if (pageNo == currentShowPageNo) {
				pageBar += "<li alt='현재 페이지' title='현재 페이지는 "+currentShowPageNo+" 페이지' style='display:inline-block; width:30px; font-size:12pt; border:solid 1px gray; color:red; padding:2px 4px;'>"
						+ pageNo + "</li>";
			}

			else {
				pageBar += "<li title='"+pageNo+" 페이지로 이동' style='display:inline-block; width:30px; font-size:12pt;'><a href='" + url
						+ "?searchType=" + searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo
						+ "'>" + pageNo + "</a></li>";
			}

			loop++;
			pageNo++;

		} // end of while------------------------------

		// === [다음][마지막] 만들기 ===
		if (!(pageNo > totalPage)) {
			
			pageBar += "<li title='"+pageNo+" 페이지로 이동' style='display:inline-block; width:50px; font-size:12pt;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + pageNo + "'>[다음]</a></li>";
			pageBar += "<li title='마지막 페이지로 이동' style='display:inline-block; width:70px; font-size:12pt;'><a href='" + url + "?searchType="
					+ searchType + "&searchWord=" + searchWord + "&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";
		}

		pageBar += "</ul>";

		mav.addObject("pageBar", pageBar);

		// === #123. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
		// 사용자가 목록보기 버튼을 클릭했을때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.

		String gobackURL = MyUtil.getCurrentURL(request);
		// System.out.println("~~~~ 확인용 gobackURL : " + gobackURL);
		// ~~~~ 확인용 gobackURL : list.action?searchType=&searchWord=&currentShowPageNo=2
		
		mav.addObject("gobackURL", gobackURL);

		//////////////////////////////////////////////////////
		// === #69. 글조회수(readCount)증가 (DML문 update)는
		// 반드시 목록보기에 와서 해당 글제목을 클릭했을 경우에만 증가되고,
		// 웹브라우저에서 새로고침(F5)을 했을 경우에는 증가가 되지 않도록 해야 한다.
		// 이것을 하기 위해서는 session 을 사용하여 처리하면 된다.

		HttpSession session = request.getSession();
		session.setAttribute("readCountPermission", "yes");
		
		/*
		 * session 에 "readCountPermission" 키값으로 저장된 value값은 "yes" 이다. session 에
		 * "readCountPermission" 키값에 해당하는 value값 "yes"를 얻으려면 반드시 웹브라우저에서 주소창에
		 * "/list.action" 이라고 입력해야만 얻어올 수 있다.
		 */
		///////////////////////////////////////////////////////////////

		// url 처리
		// currentShowPageNo=15&sizePerPage=5&searchType=name&searchWord=홍승의

		number = totalCount - (currentShowPageNo - 1) * 10;

		mav.addObject("boardList", boardList);
		mav.addObject("number", number);
		mav.setViewName("board/list.tiles1");

		return mav;
	}

	// === #62. 글 1개를 보여주는 페이지 요청 === //
	@RequestMapping(value = "/view.action")
	public ModelAndView view(HttpServletRequest request, ModelAndView mav) throws Exception {
		
		
		
		
		// 조회하고자 하는 글번호 받아오기
		String seq = request.getParameter("seq");

		// === #125. 페이징 처리되어진 후 특정 글제목을 클릭하여 상세내용을 본 이후
		// 사용자가 목록보기 버튼을 클릭했을때 돌아갈 페이지를 알려주기 위해
		// 현재 페이지 주소를 뷰단으로 넘겨준다.

		String gobackURL = request.getParameter("gobackURL");
		
		
		
		
		if (gobackURL != null) {
			gobackURL = gobackURL.replaceAll(" ", "&"); // 이전글, 다음글을 클릭해서 넘어온 것임.
			// 디코딩 해준상태에서 addObject로 넘겨주기
			mav.addObject("gobackURL", gobackURL);
		}

		try {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String login_userid = null;
			if (loginuser != null) {
				login_userid = loginuser.getUserid();
				// login_userid 는 로그인 되어진 사용자의 userid 이다.
			}
			
			// 웹브라우저에서 새로고침(F5)을 클릭한 경우이다.
			// 글조회수 증가는 없고 단순히 글1개 조회만을 해주는 것이다.
			// 기존에는 배열로 처리를 한다면?
			
			BoardVO boardvo = service.getViewWithNoAddCount(seq);
			
			String OriFileName = boardvo.getOrgFilename();
			String FileName = boardvo.getFileName();

			
			// 0의 비교가 안되는 이유는 뭘까 null 오류가 떨어지진 않지만 이번엔 값의 비교가 안됨
			if (!FileName.trim().equalsIgnoreCase("0")) {
				
				String orgFilename = boardvo.getOrgFilename(); 
				String[] orgFilenameArray = orgFilename.split(",");
				
				String fileSize = boardvo.getFileSize();
				String[] fileSizeArray = fileSize.split(",");

				List<String> orgFilenameList = new ArrayList<>();
				for (int i = 0; i < orgFilenameArray.length; i++) {
					String fileNameList = orgFilenameArray[i] + " - " + fileSizeArray[i] + " KB ";
					orgFilenameList.add(fileNameList);
				}

				mav.addObject("orgFilenameList", orgFilenameList);

			}
			
			else {
				mav.addObject("orgFilenameList", "");
			}

			mav.addObject("boardvo", boardvo);

		} catch (NumberFormatException e) {
			e.getStackTrace();
		}

		mav.setViewName("board/view.tiles1");
		mav.addObject("gobackURL",gobackURL);
		return mav;
	}

	// === #71. 글수정 페이지 요청 === //
	
	@RequestMapping(value = "/edit.action")
	public ModelAndView requiredLogin_edit(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		// 글 수정해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		
		// 글 수정해야할 글1개 내용 가져오기
		BoardVO boardvo = service.getViewWithNoAddCount(seq);
		// 글조회수(readCount) 증가 없이 단순히 글1개만 조회 해주는 것이다.
		
		String gobackURL = request.getParameter("gobackURL");
		
		System.out.println("gobackURL의 값 : " + gobackURL );
		
		
		/*
		 * try { boardvo.setSubject(MyUtil.restoreTag(boardvo.getSubject()));
		 * boardvo.setContent(MyUtil.restoreTag(boardvo.getContent())); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		//		if( !loginuser.getUserid().equals(boardvo.getFk_userid()) ) {
		//			String message = "다른 사용자의 글은 수정이 불가합니다.";
		//			String loc = "javascript:history.back()";
		//			
		//			mav.addObject("message", message);
		//			mav.addObject("loc", loc);
		//			mav.setViewName("msg");
		//		}
		// 가져온 1개글을 글수정할 폼이 있는 view 단으로 보내준다.
		/////////////////////////////////////////
		
		String fileName = boardvo.getFileName(); // 20201209142730107400829530700.jpg 이것이 바로 WAS(톰캣) 디스크에 저장된
		
				
		if (!fileName.trim().equalsIgnoreCase("0")) {
			
			String orgFilename = boardvo.getOrgFilename(); // berkelekle디스트리뷰트06.jpg 다운로드시 보여줄 파일명.
			String[] orgFilenameArray = orgFilename.split(",");
			
			String fileSize = boardvo.getFileSize();
			String[] fileSizeArray = fileSize.split(",");
			
			
			int sumsize =0;
			String filesSize="";
			List<String> orgFilenameList = new ArrayList<>();
			for (int i = 0; i < orgFilenameArray.length; i++) {
				String fileNameList = orgFilenameArray[i] + " - " + fileSizeArray[i] + " Byte ";
				orgFilenameList.add(fileNameList);
				
				
				mav.addObject("fileSize"+i, fileSizeArray[i]);
				
				if(i+1 == orgFilenameArray.length)
				{
					filesSize+=fileSizeArray[i];
				}
				else {
					filesSize+=fileSizeArray[i]+",";
				}
				
				
				sumsize+=Integer.parseInt(fileSizeArray[i]);
				
			}
			
			
			
			
			mav.addObject("orgFilenameList", orgFilenameList);
			mav.addObject("sumsize", sumsize);
			
			mav.addObject("filesSize", filesSize);
			mav.addObject("orgFilename", orgFilename);

		}
		
		/////////////////////////////////////////
		

		mav.addObject("boardvo", boardvo);
		mav.addObject("gobackURL", gobackURL);
		mav.setViewName("board/edit.tiles1");
		
		return mav;
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/checkpw.action", method = { RequestMethod.POST })

	public String checkPw(HttpServletRequest request) {

		String pw = request.getParameter("pw");
		String seq = request.getParameter("seq");

		System.out.println("값 넘어오는지 확인 값 seq : " + seq);
		System.out.println("값 넘어오는지 확인 값 pw : " + pw);

		Map<String, String> checkPara = new HashMap<>();

		checkPara.put("pw", pw);
		checkPara.put("seq", seq);

		int n = service.checkPw(checkPara);

		System.out.println("n" + n);

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n);

		return jsonObj.toString();
	}
	
	// === #72. 글수정 페이지 완료하기 === //
	@RequestMapping(value = "/editEnd.action")
	public ModelAndView editEnd(BoardVO boardvo, MultipartHttpServletRequest mrequest, ModelAndView mav,
			@RequestParam("attach") MultipartFile[] attach) {
		
		
		/*
		 * 글 수정을 하려면 원본글의 글암호와 수정시 입력해준 암호가 일치할때만 글 수정이 가능하도록 해야한다.
		 */
		
		
		String gobackURL = mrequest.getParameter("gobackURL");
		String indexString = mrequest.getParameter("indexString");
		
		//////////////////////////////////////////////////////////
		
		
		// 파일정보를 담는 변수 초기화
		String orgFileNameString="";
		String fileNameString="";
		String fileSizeString="";
		String delFileNameString="";
		
		
		// get 방식의 seq로 boardvo를 가져와서 작성날짜를 봐야함.
		BoardVO boardvo2 = service.getViewWithNoAddCount(boardvo.getSeq());
		
		
		// 기존의 파일이 존재하고 새로 추가한 파일이 있는 경우
		if (!boardvo2.getFileName().trim().equalsIgnoreCase("0") && !attach[0].getOriginalFilename().isEmpty() ) {
			
			// 삭제할 index 번호를 가지고 있는 것을 배열로 만들어줌
			
			System.out.println("기존파일이 존재하고 새로 추가한 파일이 있는 경우  1239");
			System.out.println(" attach[0]의 값 : "+attach[0]);
			
			// 기존파일 정보
			String orgFileName=boardvo2.getOrgFilename();
			String fileName=boardvo2.getFileName();
			String fileSize=boardvo2.getFileSize();
			
			
			String[] orgFileNameArray=orgFileName.split(",");
			String[] fileNameArray=fileName.split(",");
			String[] fileSizeArray=fileSize.split(",");
			
			
			// 원래 올려진 파일 갯수
			for(int i=0; i<orgFileNameArray.length; i++) {
				
				// 제거한 인덱스 번호인경우
				if(indexString.contains(String.valueOf(i))) {
					
					delFileNameString+=fileNameArray[i]+",";
					System.out.println("제거한 인덱스 번호는 : " + i);
					continue;
				}
				
				// 삭제하지 않은 인덱스인 경우
				else {
					
					// 마지막 인덱스일경우
					if(i == (orgFileNameArray.length - 1) ) {
						System.out.println("마지막 인덱스 번호는  : " + i);
						orgFileNameString+=orgFileNameArray[i]+",";
						fileNameString+=fileNameArray[i]+",";
						fileSizeString+=fileSizeArray[i]+",";
					}
						
					// 마지막 인덱스가 아닌경우
					else {
						System.out.println("연속된 인덱스 번호  : " + i);
						orgFileNameString+=orgFileNameArray[i]+",";
						fileNameString+=fileNameArray[i]+",";
						fileSizeString+=fileSizeArray[i]+",";
					}

				}	
				
			} // end of orgFileNameArray for

			
			// 기존파일 중 선택한 파일만 삭제하기
			String str = boardvo2.getRegDate().substring(0, 10).replace("-", File.separator);
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + str;
			
			String oldFileName = boardvo2.getFileName(); 
			// 20201209142730107400829530700.jpg 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.
			String[] delFileNameArray =delFileNameString.split(",");
			
			
			try {
				FileManager.doFileDelete(delFileNameArray, path);
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			} 
			
			for (MultipartFile multipartFile : attach) {

				int i = 1;

				byte[] bytes = null;
				// 첨부파일의 크기를

				
				try {
					bytes = multipartFile.getBytes();
					
					// 마지막 인덱스인 경우
					if (i == attach.length) {
						
						
						fileNameString += fileManager.doFileUpload(bytes, multipartFile.getOriginalFilename(), path);
						orgFileNameString += multipartFile.getOriginalFilename();
						fileSizeString += String.valueOf(multipartFile.getSize());
						System.out.println(i+"번째 파일 이름은 :" + fileNameString );
						
					}
					
					// 마지막 인덱스가 아닌 경우
					else {
						fileNameString += fileManager.doFileUpload(bytes, multipartFile.getOriginalFilename(), path)+",";
						orgFileNameString += multipartFile.getOriginalFilename()+",";
						fileSizeString += String.valueOf(multipartFile.getSize())+",";
					}
					i++;
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			boardvo.setOrgFilename(orgFileNameString);
			boardvo.setFileName(fileNameString);
			boardvo.setFileSize(fileSizeString);
			
		} // 원래 첨부파일이 있는 경우 
		
		
		
		
		// 기존파일도 없고 추가한 파일도 없는경우
		else if(boardvo2.getFileName().trim().equalsIgnoreCase("0") && attach[0].getOriginalFilename().isEmpty()) {
			
				// 삭제할 index 번호를 가지고 있는 것을 배열로 만들어줌
				// 기존파일 정보
				
				System.out.println("기존파일도 없고 추가한 파일도 없는 경우 실행 1352");
				boardvo.setOrgFilename(orgFileNameString);
				boardvo.setFileName(fileNameString);
				boardvo.setFileSize(fileSizeString);
			
				
			
		} // end of else if 
		
		
		// 기존파일은 없고 추가한 경우
		else if(boardvo2.getFileName().trim().equalsIgnoreCase("0") && !attach[0].getOriginalFilename().isEmpty()) {
			
			// 삭제할 index 번호를 가지고 있는 것을 배열로 만들어줌
			// 기존파일 정보
		
			System.out.println("기존파일도 없고 추가한 파일의 경우 실행 1369");
			System.out.println(" attach[0]의 값 : "+attach[0]);
			
			String str = boardvo2.getRegDate().substring(0, 10).replace("-", File.separator);
			HttpSession session = mrequest.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + str;
			
			
			for (MultipartFile multipartFile : attach) {
				
				int i = 1;

				byte[] bytes = null;
				// 첨부파일의 크기를
				
				
				try {
					bytes = multipartFile.getBytes();
	
					// 마지막 인덱스인 경우
					if (i == attach.length) {
						
						fileNameString += fileManager.doFileUpload(bytes, multipartFile.getOriginalFilename(), path);
						orgFileNameString += multipartFile.getOriginalFilename()+",";
						fileSizeString += String.valueOf(multipartFile.getSize())+",";
					}
					
					// 마지막 인덱스가 아닌 경우
					else {
						fileNameString += fileManager.doFileUpload(bytes, multipartFile.getOriginalFilename(), path)+",";
						orgFileNameString += multipartFile.getOriginalFilename()+",";
						fileSizeString += String.valueOf(multipartFile.getSize())+",";
					}
					i++;
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				
				
			} // end of for
			
			
			boardvo.setOrgFilename(orgFileNameString);
			boardvo.setFileName(fileNameString);
			boardvo.setFileSize(fileSizeString);
		
		
	} // end of else if
		
		
		
		// 기존파일이 있고 첨부파일이 없는 경우 임... 삭제만 할 경우
		
				if (!boardvo2.getFileName().trim().equalsIgnoreCase("0") && attach[0].getOriginalFilename().isEmpty() ) {
					
					// 삭제할 index 번호를 가지고 있는 것을 배열로 만들어줌
					
					System.out.println("기존파일이 존재하고 새로 추가한 파일이 있는 경우  1431");
					System.out.println(" attach[0]의 값 : "+ attach[0]);
					
					// 기존파일 정보
					String orgFileName=boardvo2.getOrgFilename();
					String fileName=boardvo2.getFileName();
					String fileSize=boardvo2.getFileSize();
					
					
					String[] orgFileNameArray=orgFileName.split(",");
					String[] fileNameArray=fileName.split(",");
					String[] fileSizeArray=fileSize.split(",");
					
					
					
					// 원래 올려진 파일 갯수
					for(int i=0; i<orgFileNameArray.length; i++) {
						
						// 제거한 인덱스 번호인경우
						if(indexString.contains(String.valueOf(i))) {
							
							delFileNameString+=fileNameArray[i]+",";
							System.out.println("제거한 인덱스 번호는 : " + i);
							continue;
						}
						
						// 삭제하지 않은 인덱스인 경우
						else {
							
							// 마지막 인덱스일경우
							if(i == (orgFileNameArray.length - 1) ) {
								System.out.println("마지막 인덱스 번호는  : " + i);
								orgFileNameString+=orgFileNameArray[i]+",";
								fileNameString+=fileNameArray[i]+",";
								fileSizeString+=fileSizeArray[i]+",";
							}
								
							// 마지막 인덱스가 아닌경우
							else {
								System.out.println("연속된 인덱스 번호  : " + i);
								orgFileNameString+=orgFileNameArray[i]+",";
								fileNameString+=fileNameArray[i]+",";
								fileSizeString+=fileSizeArray[i]+",";
							}

						}	
						
					} // end of orgFileNameArray for

					
					// 기존파일 중 선택한 파일만 삭제하기
					String str = boardvo2.getRegDate().substring(0, 10).replace("-", File.separator);
					HttpSession session = mrequest.getSession();
					String root = session.getServletContext().getRealPath("/");
					String path = root + "resources" + File.separator + str;
					
					String oldFileName = boardvo2.getFileName(); 
					// 20201209142730107400829530700.jpg 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.
					String[] delFileNameArray =delFileNameString.split(",");
					
					
					try {
						FileManager.doFileDelete(delFileNameArray, path);
					} 
					catch (Exception e1) {
						e1.printStackTrace();
					} 
					
					for (MultipartFile multipartFile : attach) {

						int i = 1;

						byte[] bytes = null;
						// 첨부파일의 크기를

						
						try {
							bytes = multipartFile.getBytes();
							
							// 마지막 인덱스인 경우
							if (i == attach.length) {
								
								
								fileNameString += fileManager.doFileUpload(bytes, multipartFile.getOriginalFilename(), path);
								orgFileNameString += multipartFile.getOriginalFilename();
								fileSizeString += String.valueOf(multipartFile.getSize());
								System.out.println(i+"번째 파일 이름은 :" + fileNameString );
								
							}
							
							// 마지막 인덱스가 아닌 경우
							else {
								fileNameString += fileManager.doFileUpload(bytes, multipartFile.getOriginalFilename(), path)+",";
								orgFileNameString += multipartFile.getOriginalFilename()+",";
								fileSizeString += String.valueOf(multipartFile.getSize())+",";
							}
							i++;
						} 
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					boardvo.setOrgFilename(orgFileNameString);
					boardvo.setFileName(fileNameString);
					boardvo.setFileSize(fileSizeString);
					
				} // 원래 파일을 지웠을 경우
		
		
		
		service.edit(boardvo);
		mav.addObject("message", "글수정 성공!!");
		mav.addObject("loc", mrequest.getContextPath() + "/view.action?seq=" + boardvo.getSeq() + "&gobackURL=" + gobackURL);
		mav.setViewName("msg");

		return mav;
	}
	

	// === #76. 글삭제 페이지 요청 === //
	@RequestMapping(value = "/del.action")
	public ModelAndView requiredLogin_del(HttpServletRequest request, HttpServletResponse response, ModelAndView mav) {

		// 삭제해야 할 글번호 가져오기
		String seq = request.getParameter("seq");
		// 삭제해야할 글1개 내용 가져와서 로그인한 사람이 쓴 글이라면 글삭제가 가능하지만
		// 다른 사람이 쓴 글은 삭제가 불가하도록 해야 한다.

		String type = request.getParameter("type");
		String word = request.getParameter("word");

		Map<String, String> boardPara = new HashMap<String, String>();
		boardPara.put("seq", seq);
		boardPara.put("word", word);
		boardPara.put("type", type);

		System.out.println(seq);
		BoardVO boardvo = service.getViewWithNoAddCount(seq);
		// 글조회수(readCount) 증가 없이 단순히 글1개만 조회 해주는 것이다.

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		mav.addObject("seq", seq);
		mav.setViewName("board/del.tiles1");
		return mav;
		
	}
	
	// === #77. 글삭제 페이지 완료하기 === //
	@RequestMapping(value = "/delEnd.action", method = { RequestMethod.POST })
	public ModelAndView delEnd(HttpServletRequest request, ModelAndView mav) {

		/*
		 * 글 삭제를 하려면 원본글의 글암호와 삭제시 입력해준 암호가 일치할때만 글 삭제가 가능하도록 해야한다.
		 */

		String seq = request.getParameter("seq");
		String pw = request.getParameter("pw");

		String type = request.getParameter("type");
		String word = request.getParameter("word");

		String gobackURL = request.getParameter("gobackURL");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("seq", seq);
		paraMap.put("pw", pw);

		// === #164. 파일첨부가 된 글이라면 글 삭제시 먼저 첨부파일을 삭제해주어야 한다. === //
		Map<String, String> boardPara = new HashMap<String, String>();
		boardPara.put("seq", seq);
		boardPara.put("word", word);
		boardPara.put("type", type);

		BoardVO boardvo = service.getViewWithNoAddCount(seq);

		//////////////////////////////////////////////////////////////////////

		if (!boardvo.getFileName().isEmpty()) {

			String str = boardvo.getRegDate().substring(0, 10).replace("-", File.separator);
			HttpSession session = request.getSession();
			String root = session.getServletContext().getRealPath("/");
			String path = root + "resources" + File.separator + str;

			// 다중 파일 배열에서 꺼내기
			String fileName = boardvo.getFileName(); // 20201209142730107400829530700.jpg 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.

			paraMap.put("fileName", fileName); // 삭제해야할 파일명
			paraMap.put("path", path); // 삭제해야할 파일이 저장된 경로

			/* FileManager.doFileDelete(fileNameArray, path); */

		}

		int n = service.del(paraMap);
		// n 이 1 이라면 정상적으로 삭제됨.
		// n 이 0 이라면 글삭제에 필요한 글암호가 틀린경우

		if (n == 0) {
			mav.addObject("message", "암호가 일치하지 않아 글 삭제가 불가합니다.");
			mav.addObject("loc", request.getContextPath() + "/view.action?seq=" + seq + "&gobackURL=" + gobackURL);
		}

		else {
			mav.addObject("message", "글삭제 성공!!");
			mav.addObject("loc", request.getContextPath() + "/list.action");

		}

		mav.setViewName("msg");

		return mav;

	}

	// === #84. 댓글쓰기(Ajax 로 처리) === //

	@ResponseBody
	@RequestMapping(value = "/addComment.action", method = {
			RequestMethod.POST }, produces = "text/plain;charset=UTF-8")
	public String addComment(CommentVO commentvo) {

		int n = 0;

		String a = "";
		String b = "";

		try {
			a = MyUtil.removeTag(commentvo.getContent());
			b = MyUtil.removeTag(commentvo.getName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		commentvo.setName(b);
		commentvo.setContent(a);

		try {
			n = service.addComment(commentvo);
		} catch (Throwable e) {

		}
		// 댓글쓰기(insert) 및 원게시물(tbl_board 테이블)에 댓글의 개수 증가(update 1씩 증가)하기
		// 이어서 회원의 포인트를 50점을 증가하도록 한다. (tbl_member 테이블에 point 컬럼의 값을 50 증가하도록 update
		// 한다.)

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n);
		jsonObj.put("name", commentvo.getName());

		return jsonObj.toString();

	}

	/*
	 * @ExceptionHandler 에 대해서..... ==> 어떤 컨트롤러내에서 발생하는 익셉션이 있을시 익셉션 처리를 해주려고 한다면
	 * 
	 * @ExceptionHandler 어노테이션을 적용한 메소드를 구현해주면 된다
	 * 
	 * 컨트롤러내에서 @ExceptionHandler 어노테이션을 적용한 메소드가 존재하면, 스프링은 익셉션
	 * 발생시 @ExceptionHandler 어노테이션을 적용한 메소드가 처리해준다. 따라서, 컨트롤러에 발생한 익셉션을 직접 처리하고
	 * 싶다면 @ExceptionHandler 어노테이션을 적용한 메소드를 구현해주면 된다.
	 */
	/*
	 * @ExceptionHandler(java.lang.Throwable.class) public String
	 * handleThrowable(Throwable e, HttpServletRequest request) {
	 * 
	 * System.out.println("~~~~~ 오류메시지 : " + e.getMessage() );
	 * 
	 * String message = "오류발생 => " + e.getMessage(); String loc =
	 * "javascript:history.back()";
	 * 
	 * request.setAttribute("message", message); request.setAttribute("loc", loc);
	 * 
	 * return "msg"; }
	 */

	// === #90. 원게시물에 딸린 댓글들을 조회해오기(Ajax 로 처리) === //
	@ResponseBody
	@RequestMapping(value = "/readComment.action", method = {
			RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	public String readComment(HttpServletRequest request) {

		String parentSeq = request.getParameter("parentSeq");

		List<CommentVO> commentList = service.getCommentList(parentSeq);

		JSONArray jsonArr = new JSONArray(); // []

		if (commentList != null) {
			for (CommentVO cmtvo : commentList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("content", cmtvo.getContent());
				System.out.println(cmtvo.getSeq());
				jsonObj.put("seq", cmtvo.getSeq());
				jsonObj.put("name", cmtvo.getName());
				jsonObj.put("regDate", cmtvo.getRegDate());
				jsonObj.put("pw", cmtvo.getComment_pw());
				jsonArr.put(jsonObj);
			}

		}

		return jsonArr.toString();
	}

	// === #108. 검색어 입력시 자동글 완성하기 3 === //
	@ResponseBody
	@RequestMapping(value = "/wordSearchShow.action", method = {
			RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	public String wordSearchShow(HttpServletRequest request) {

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);

		List<String> wordList = service.wordSearchShow(paraMap);

		JSONArray jsonArr = new JSONArray(); // []

		if (wordList != null) {
			for (String word : wordList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("word", word);

				jsonArr.put(jsonObj);
			}
		}

		return jsonArr.toString();
	}

	// === #128. 원게시물에 딸린 댓글들을 페이징처리해서 조회해오기(Ajax 로 처리) === //
	@ResponseBody
	@RequestMapping(value = "/commentList.action", method = {
			RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	public String commentList(HttpServletRequest request) {

		String parentSeq = request.getParameter("parentSeq");
		String currentShowPageNo = request.getParameter("currentShowPageNo");

		if (currentShowPageNo == null) {
			currentShowPageNo = "1";
		}

		int sizePerPage = 5; // 한 페이지당 5개의 댓글을 보여줄 것임.

		// **** 가져올 게시글의 범위를 구한다.(공식임!!!) ****
		/*
		 * currentShowPageNo startRno endRno
		 * -------------------------------------------- 1 page ===> 1 5 2 page ===> 6 10
		 * 3 page ===> 11 15 4 page ===> 16 20 ...... ... ...
		 */

		int startRno = ((Integer.parseInt(currentShowPageNo) - 1) * sizePerPage) + 1;
		int endRno = startRno + sizePerPage - 1;

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("parentSeq", parentSeq);
		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));

		List<CommentVO> commentList = service.getCommentListPaging(paraMap);

		JSONArray jsonArr = new JSONArray(); // []

		if (commentList != null) {
			for (CommentVO cmtvo : commentList) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("content", cmtvo.getContent());
				jsonObj.put("seq", cmtvo.getSeq());
				jsonObj.put("name", cmtvo.getName());
				jsonObj.put("regDate", cmtvo.getRegDate());

				jsonArr.put(jsonObj);
			}
		}

		return jsonArr.toString();
	}

	// === #132. 원게시물에 딸린 댓글 totalPage 알아오기 (Ajax 로 처리) === //
	@ResponseBody
	@RequestMapping(value = "/getCommentTotalPage.action", method = {
			RequestMethod.GET }, produces = "text/plain;charset=UTF-8")
	public String getCommentTotalPage(HttpServletRequest request) {

		String parentSeq = request.getParameter("parentSeq");
		String sizePerPage = request.getParameter("sizePerPage");

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("parentSeq", parentSeq);

		// 원글 글번호(parentSeq)에 해당하는 댓글의 총개수를 알아오기
		int totalCount = service.getCommentTotalCount(paraMap);

		// === 총페이지수(totalPage)구하기 ===
		// 만약에 총 게시물 건수(totalCount)가 14개 이라면
		// 총 페이지수(totalPage)는 3개가 되어야 한다.
		int totalPage = (int) Math.ceil((double) totalCount / Integer.parseInt(sizePerPage));
		// (double)14/5 ==> 2.8 ==> Math.ceil(2.8) ==> 3.0 ==> (int)3.0 ==> 3

		JSONObject jsonObj = new JSONObject();
		jsonObj.put("totalPage", totalPage); // {"totalPage":3}

		return jsonObj.toString();
	}

	// === #163. 첨부파일 다운로드 받기 === //
	@RequestMapping(value = "/download.action")
	public void requiredLogin_download(HttpServletRequest request, HttpServletResponse response) {

		String seq = request.getParameter("seq");
		String index = request.getParameter("index");

		int i = Integer.parseInt(index);

		// 첨부파일이 있는 글번호

		// 전체 다 받는 것이 아니라 일부만 받는다면??

		/*
		 * 첨부파일이 있는 글번호에서 20201209142730107400829530700.jpg 처럼 이러한 fileName 값을 DB에서 가져와야
		 * 한다. 또한 orgFilename 값도 DB에서 가져와야 한다.
		 */

		String type = request.getParameter("type");
		String word = request.getParameter("word");

		// 파일 작성 날짜를 가져와야하는데?? 파일이 생성된 날짜를 가져올수 있다면?

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = null;

		try {

			Integer.parseInt(seq);
			Map<String, String> boardPara = new HashMap<String, String>();
			boardPara.put(type, "type");
			boardPara.put(word, "word");
			boardPara.put(seq, "seq");

			BoardVO boardvo = service.getViewWithNoAddCount(seq);

			// get 방식의 seq로 boardvo를 가져와서 작성날짜를 봐야함.
			String str = boardvo.getRegDate().substring(0, 10).replace("-", File.separator);

			System.out.println("날짜 루트는? " + str);
			
			// 첨부파일이 저장되어 있는 WAS(톰캣)의 디스크 경로명을 알아와야만 다운로드를 해줄수 있다.
			// 이 경로는 우리가 파일첨부를 위해서 /addEnd.action 에서 설정해두었던 경로와 똑같아야 한다.
			// WAS 의 webapp 의 절대경로를 알아와야 한다.
			
			HttpSession session = request.getSession();
			
			String root = session.getServletContext().getRealPath("/");

			System.out.println("~~~~ webapp 의 절대경로 => " + root);
			// ~~~~ webapp 의 절대경로 =>
			// C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\

			String path = root + "resources" + File.separator + str;

			/*
			 * File.separator 는 운영체제에서 사용하는 폴더와 파일의 구분자이다. 운영체제가 Windows 이라면 File.separator
			 * 는 "\" 이고, 운영체제가 UNIX, Linux 이라면 File.separator 는 "/" 이다.
			 */

			// 다중 파일 배열에서 꺼내기
			String fileName = boardvo.getFileName(); // 20201209142730107400829530700.jpg 이것이 바로 WAS(톰캣) 디스크에 저장된 파일명이다.
			String[] fileNameArray = fileName.split(",");

			String orgFilename = boardvo.getOrgFilename(); // berkelekle디스트리뷰트06.jpg 다운로드시 보여줄 파일명.
			String[] orgFilenameArray = orgFilename.split(",");

			/*
			 * List<String> fileNameList = new ArrayList<>(); for (int i = 0; i <
			 * fileNameArray.length; i++) { fileNameList.add(fileNameArray[i]); }
			 */

			/*
			 * List<String> orgFilenameList = new ArrayList<>(); for (int i = 0; i <
			 * orgFilenameArray.length; i++) { orgFilenameList.add(orgFilenameArray[i]); }
			 */

			// **** file 다운로드 하기 **** //
			boolean flag = false; // file 다운로드의 성공,실패를 알려주는 용도

			// 전체를 다 다운 받는경우
			/*
			 * for (int i=0; i<orgFilenameArray.length; i++) {
			 * fileManager.doFileDownload(fileNameArray[i], orgFilenameArray[i], path,
			 * response); // file 다운로드 성공시 flag 는 true, // file 다운로드 실패시 flag 는 false 를 가진다.
			 * }
			 */

			// 일부만 클릭해서 다운로드 받는 경우
			flag = fileManager.doFileDownload(fileNameArray[i], orgFilenameArray[i], path, response);

			if (!flag) {
				// 다운로드가 실패할 경우 메시지를 띄워준다.
				try {
					writer = response.getWriter();
					// 웹브라우저상에 메시지를 쓰기 위한 객체생성.

					writer.println(
							"<script type='text/javascript'>alert('파일 다운로드가 불가합니다!!'); history.back();</script>");
				} catch (IOException e) {

				}
			}

		} catch (NumberFormatException e) {
			try {
				writer = response.getWriter();
				// 웹브라우저상에 메시지를 쓰기 위한 객체생성.

				writer.println("<script type='text/javascript'>alert('파일 다운로드가 불가합니다!!'); history.back();</script>");
			} catch (IOException e1) {

			}
		}

	}

	// >>> Excel 파일로 다운받기 <<< //
	@RequestMapping(value = "/downloadExcelFile.action", method = { RequestMethod.POST })
	public String downloadExcelFile(HttpServletRequest request, Model model) {

		// 엑셀로 만든다는 의미는 검색한 결과를 다 Excel에 담아주면 될것.

		List<BoardVO> boardList = null;

		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String downLoadType = request.getParameter("downLoadType");
		String str_currentShowPageNo = request.getParameter("currentShowPageNo");

		// 분명히 함수가 다른것이 실행될텐데 왜 값은 로직이 처리가 되지 ??
		System.out.println("downLoadType값은 ? " + downLoadType);

		if (searchType == null) {
			searchType = "subject";
		}

		if (searchWord == null || searchWord.trim().isEmpty()) {
			searchWord = "";
		}

		if (downLoadType == null || downLoadType.trim().isEmpty()) {
			downLoadType = "N";
		}

		Map<String, String> paraMap = new HashMap<>();

		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);

		// 먼저 총 게시물 건수(totalCount)를 구해와야 한다.
		// 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.

		int totalCount = 0; // 총 게시물 건수
		int sizePerPage = 10; // 한 페이지당 보여줄 게시물 건수
		int currentShowPageNo = 0; // 현재 보여주는 페이지 번호로서, 초기치로는 1페이지로 설정함.
		int totalPage = 0; // 총 페이지수(웹브라우저상에서 보여줄 총 페이지 개수, 페이지바)
		int startRno = 0; // 시작 행번호
		int endRno = 0; // 끝 행번호
		int number = 0; // 전체 수

		// 총 게시물 건수(totalCount)

		totalCount = service.getTotalCount(paraMap);
		totalPage = (int) Math.ceil((double) totalCount / sizePerPage);

		if (str_currentShowPageNo == null) {
			// 게시판에 보여지는 초기화면
			currentShowPageNo = 1;
		}

		else {
			try {
				currentShowPageNo = Integer.parseInt(str_currentShowPageNo);
				if (currentShowPageNo < 1 || currentShowPageNo > totalPage) {
					currentShowPageNo = 1;
				}
			} catch (NumberFormatException e) {
				currentShowPageNo = 1;
			}
		}

		startRno = ((currentShowPageNo - 1) * sizePerPage) + 1;
		endRno = startRno + sizePerPage - 1;

		paraMap.put("startRno", String.valueOf(startRno));
		paraMap.put("endRno", String.valueOf(endRno));
		paraMap.put("currentShowPageNo", String.valueOf(currentShowPageNo));

		// 총 게시물 건수(totalCount)는 검색조건이 있을때와 없을때로 나뉘어진다.

		// 검색어 +페이징처리 한 결과를 엑셀로 출력한 것
		if (downLoadType.equalsIgnoreCase("Y")) {
			boardList = service.boardListSearchWithPaging(paraMap);
		}

		// 검색어를 포함한 페이지와 관계없이 전체를 엑셀로 출력한 것
		else {
			boardList = service.boardListSearchNoPaging(paraMap);
		}

		///////////////////// 검색어 처리한 List////////////////////////////////

		// === 조회결과물인 boardList 를 가지고 엑셀 시트 생성하기 ===
		// 시트를 생성하고, 행을 생성하고, 셀을 생성하고, 셀안에 내용을 넣어주면 된다.

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		// 시트생성
		SXSSFSheet sheet = workbook.createSheet("게시판목록");

		// 시트 열 너비 설정
		sheet.setColumnWidth(0, 2000);
		sheet.setColumnWidth(1, 4000);
		sheet.setColumnWidth(2, 3000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 2000);
		
		// 행의 위치를 나타내는 변수
		int rowLocation = 0;

		////////////////////////////////////////////////////////////////////////////////////////
		// CellStyle 정렬하기(Alignment)
		// CellStyle 객체를 생성하여 Alignment 세팅하는 메소드를 호출해서 인자값을 넣어준다.
		// 아래는 HorizontalAlignment(가로)와 VerticalAlignment(세로)를 모두 가운데 정렬 시켰다.

		CellStyle mergeRowStyle = workbook.createCellStyle();
		mergeRowStyle.setAlignment(HorizontalAlignment.CENTER);
		mergeRowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// import org.apache.poi.ss.usermodel.VerticalAlignment 으로 해야함.

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		CellStyle bodyStyle = workbook.createCellStyle();
		bodyStyle.setAlignment(HorizontalAlignment.CENTER);
		bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		
		// CellStyle 배경색(ForegroundColor)만들기
		// setFillForegroundColor 메소드에 IndexedColors Enum인자를 사용한다.
		// setFillPattern은 해당 색을 어떤 패턴으로 입힐지를 정한다.
		mergeRowStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex()); // IndexedColors.DARK_BLUE.getIndex()
																					// 는 색상(남색)의 인덱스값을 리턴시켜준다.
		mergeRowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); // IndexedColors.LIGHT_YELLOW.getIndex()
																					// 는 연한노랑의 인덱스값을 리턴시켜준다.
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		// Cell 폰트(Font) 설정하기
		// 폰트 적용을 위해 POI 라이브러리의 Font 객체를 생성해준다.
		// 해당 객체의 세터를 사용해 폰트를 설정해준다. 대표적으로 글씨체, 크기, 색상, 굵기만 설정한다.
		// 이후 CellStyle의 setFont 메소드를 사용해 인자로 폰트를 넣어준다.

		Font mergeRowFont = workbook.createFont(); // import org.apache.poi.ss.usermodel.Font; 으로 한다.
		mergeRowFont.setFontName("나눔고딕");
		mergeRowFont.setFontHeight((short) 500);
		mergeRowFont.setColor(IndexedColors.WHITE.getIndex());
		mergeRowFont.setBold(true);
		mergeRowStyle.setFont(mergeRowFont);

		// CellStyle 테두리 Border
		// 테두리는 각 셀마다 상하좌우 모두 설정해준다.
		// setBorderTop, Bottom, Left, Right 메소드와 인자로 POI라이브러리의 BorderStyle 인자를 넣어서
		// 적용한다.
		headerStyle.setBorderTop(BorderStyle.THICK);
		headerStyle.setBorderBottom(BorderStyle.THICK);
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setBorderRight(BorderStyle.THIN);
		
		
		// Cell Merge 셀 병합시키기
		/*
		 * 셀병합은 시트의 addMergeRegion 메소드에 CellRangeAddress 객체를 인자로 하여 병합시킨다.
		 * CellRangeAddress 생성자의 인자로(시작 행, 끝 행, 시작 열, 끝 열) 순서대로 넣어서 병합시킬 범위를 정한다. 배열처럼
		 * 시작은 0부터이다.
		 */

		// 병합할 행 만들기
		Row mergeRow = sheet.createRow(rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.

		// 병합할 행에 "게시판 정보" 로 셀을 만들어 셀에 스타일을 주기
		for (int i = 0; i < 5; i++) {
			Cell cell = mergeRow.createCell(i);
			cell.setCellStyle(mergeRowStyle);
			cell.setCellValue("자유게시판 목록");
		}

		// 셀 병합하기
		sheet.addMergedRegion(new CellRangeAddress(rowLocation, rowLocation, 0, 4)); // 시작 행, 끝 행, 시작 열, 끝 열

		// CellStyle 천단위 쉼표, 금액
		// CellStyle moneyStyle = workbook.createCellStyle();
		// moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
		////////////////////////////////////////////////////////////////////////////////////////////////

		// 헤더 행 생성
		Row headerRow = sheet.createRow(++rowLocation); // 엑셀에서 행의 시작은 0 부터 시작한다.
														// ++rowLocation는 전위연산자임.

		// 해당 행의 첫 번째 열 셀 생성
		Cell headerCell = headerRow.createCell(0); // 엑셀에서 열의 시작은 0 부터 시작한다.
		headerCell.setCellValue("글번호");
		headerCell.setCellStyle(headerStyle);
	
		
		// 해당 행의 두 번째 열 셀 생성
		headerCell = headerRow.createCell(1);
		headerCell.setCellValue("제목");
		headerCell.setCellStyle(headerStyle);

		// 해당 행의 세 번째 열 셀 생성
		headerCell = headerRow.createCell(2);
		headerCell.setCellValue("작성자");
		headerCell.setCellStyle(headerStyle);

		// 해당 행의 네 번째 열 셀 생성
		headerCell = headerRow.createCell(3);
		headerCell.setCellValue("작성일");
		headerCell.setCellStyle(headerStyle);

		// 해당 행의 다섯 번째 열 셀 생성
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("조회수");
		headerCell.setCellStyle(headerStyle);

		// 게시판 내용에 해당하는 행 및 셀 생성하기
		Row bodyRow = null;
		Cell bodyCell = null;

		for (int i = 0; i < boardList.size(); i++) {

			BoardVO boardvo = boardList.get(i);
			
			
			// 삭제한 글인경우 행을 생성하지 않게 하려고 로직 추가
			if(boardvo.getSubject().equalsIgnoreCase("0")) {
				
				continue;
			}
			
			
			// 행생성
			bodyRow = sheet.createRow(i + (rowLocation + 1));
		
			// 글번호 표시
			bodyCell = bodyRow.createCell(0);
			bodyCell.setCellValue(totalCount - i);
			bodyCell.setCellStyle(bodyStyle); 

			// 글 제목
			bodyCell = bodyRow.createCell(1);
			bodyCell.setCellValue(boardvo.getSubject());
			bodyCell.setCellStyle(bodyStyle);

			// 작성자 표시
			bodyCell = bodyRow.createCell(2);
			bodyCell.setCellValue(boardvo.getName());
			bodyCell.setCellStyle(bodyStyle);
			
			// 작성일 표시
			bodyCell = bodyRow.createCell(3);
			bodyCell.setCellValue(boardvo.getRegDate());
			bodyCell.setCellStyle(bodyStyle);
			
			// 조회수 표시
			bodyCell = bodyRow.createCell(4);
			bodyCell.setCellValue(boardvo.getReadCount());
			bodyCell.setCellStyle(bodyStyle);
			
		} // end of for----------------------------------------

		model.addAttribute("locale", Locale.KOREA);
		model.addAttribute("workbook", workbook);
		model.addAttribute("workbookName", "자유게시판");

		return "excelDownloadView";
	} // end of public String downloadExcelFile(HttpServletRequest request, Model
		// model)-------------

	// ==== #168. 스마트에디터. 드래그앤드롭을 사용한 다중사진 파일업로드 ====
	@RequestMapping(value = "/image/multiplePhotoUpload.action", method = { RequestMethod.POST })
	public void multiplePhotoUpload(HttpServletRequest req, HttpServletResponse res) {

		/*
		 * 1. 사용자가 보낸 파일을 WAS(톰캣)의 특정 폴더에 저장해주어야 한다. >>>> 파일이 업로드 되어질 특정 경로(폴더)지정해주기 우리는
		 * WAS 의 webapp/resources/photo_upload 라는 폴더로 지정해준다.
		 */

		// WAS 의 webapp 의 절대경로를 알아와야 한다.
		HttpSession session = req.getSession();
		String root = session.getServletContext().getRealPath("/");
		String path = root + "resources" + File.separator + "photo_upload";
		// path 가 첨부파일들을 저장할 WAS(톰캣)의 폴더가 된다.

		// System.out.println(">>>> 확인용 path ==> " + path);
		// >>>> 확인용 path ==>
		// C:\NCS\workspace(spring)\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Board\resources\photo_upload

		File dir = new File(path);
		if (!dir.exists())
			dir.mkdirs();

		String strURL = "";

		try {
			if (!"OPTIONS".equals(req.getMethod().toUpperCase())) {
				String filename = req.getHeader("file-name"); // 파일명을 받는다 - 일반 원본파일명

				// System.out.println(">>>> 확인용 filename ==> " + filename);
				// >>>> 확인용 filename ==> berkelekle%ED%8A%B8%EB%9E%9C%EB%94%9405.jpg

				InputStream is = req.getInputStream();
				/*
				 * 요청 헤더의 content-type이 application/json 이거나 multipart/form-data 형식일 때, 혹은 이름 없이
				 * 값만 전달될 때 이 값은 요청 헤더가 아닌 바디를 통해 전달된다. 이러한 형태의 값을 'payload body'라고 하는데 요청 바디에
				 * 직접 쓰여진다 하여 'request body post data'라고도 한다.
				 * 
				 * 서블릿에서 payload body는 Request.getParameter()가 아니라 Request.getInputStream() 혹은
				 * Request.getReader()를 통해 body를 직접 읽는 방식으로 가져온다.
				 */
				String newFilename = fileManager.doFileUpload(is, filename, path);

				int width = fileManager.getImageWidth(path + File.separator + newFilename);

				if (width > 600)
					width = 600;

				// System.out.println(">>>> 확인용 width ==> " + width);
				// >>>> 확인용 width ==> 600
				// >>>> 확인용 width ==> 121

				String CP = req.getContextPath(); // board

				strURL += "&bNewLine=true&sFileName=";
				strURL += newFilename;
				strURL += "&sWidth=" + width;
				strURL += "&sFileURL=" + CP + "/resources/photo_upload/" + newFilename;
			}

			/// 웹브라우저상에 사진 이미지를 쓰기 ///
			PrintWriter out = res.getWriter();
			out.print(strURL);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// === #173. (웹채팅관련4) ===
	@RequestMapping(value = "/chatting/multichat.action", method = { RequestMethod.GET })
	public String requiredLogin_multichat(HttpServletRequest request, HttpServletResponse response) {

		return "chatting/multichat.tiles1";
	}

}
