<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<%
	String ctxPath = request.getContextPath();
  //       /board 
%>

<style>
	p#my_title {
		font-size: 16pt;
		margin: 20px;
	}
	
	span#storename {
		color: red;
		font-weight: bold;
	}
</style>

<div id="container">
    <div id="main_contents">
      <p class="main_img"><img src="<%= ctxPath%>/resources/images/main/img_v.jpg" alt="건강한급식, 행복한학교,꿈과 끼를 키우는 서울행복교육" /></p>
      
      <!-- right-->
      <div class="main_right">
        <div class="notice"> 
          <!--tab object -->
          <ul class="main_tab">
            <li><a href="javascript:void(0)" onclick="doGoTab(this,'01');" class="main_tab01_on">급식소식</a></li>
            <li><a href="javascript:void(0)" onclick="doGoTab(this,'02');" class="main_tab02">연수·행사활동</a></li>
            <li><a href="<%= ctxPath%>/board/reserch.action" onclick="doGoTab(this,'03');" class="main_tab03">설문조사</a></li>
          </ul>
          <!--//tab object -->
          
          <div id="tab01" class="tabDivArea">
            <ul>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span></li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
            </ul>
            <span class="more"><img src="<%= ctxPath%>/resources/images/main/more.gif" alt="더보기" /></span> </div>
          <div id="tab02" class="tabDivArea" style="display:none;">
            <ul>
              <li><a href="#">연수활동.</a> <span class="main_data">11.02.28</span></li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
            </ul>
            <span class="more"><img src="<%= ctxPath%>/resources/images/main/more.gif" alt="더보기" /></span> </div>
          <div id="tab03" class="tabDivArea" style="display:none;">
            <ul>
              <li><a href="#">설문조사.</a> <span class="main_data">11.02.28</span></li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
              <li><a href="#">급식소식을 전해드립니다.</a> <span class="main_data">11.02.28</span> </li>
            </ul>
            <span class="more"><img src="<%= ctxPath%>/resources/images/main/more.gif" alt="더보기" /></span> </div>
        </div>
        <div class="m_search_box">
          <h3><img src="<%= ctxPath%>/resources/images/main/title_01.gif" alt="학교급식인력풀" /></h3>
          <fieldset>
            <legend>검색</legend>
            <p> 직종
              <select>
                <option>전체</option>
              </select>
            </p>
            <p> 지역
              <select>
                <option>구 선택</option>
              </select>
              <a href="#"><img src="<%= ctxPath%>/resources/images/main/btn_ser.gif" alt="검색" /></a> </p>
          </fieldset>
        </div>
        <div class="main_icon">
          <ul>
            <li><a href="#"><img src="<%= ctxPath%>/resources/images/main/img_btn_01.gif" alt="학교급식관리기구" style="width:100%;"></a></li>
            <li><a href="#"><img src="<%= ctxPath%>/resources/images/main/img_btn_02.gif" alt="영양(교)사이야기" /></a></li>
            <li><a href="#"><img src="<%= ctxPath%>/resources/images/main/img_btn_03.gif" /></a></li>
            <li><a href="#"><img src="<%= ctxPath%>/resources/images/main/img_btn_04.gif" alt="추천식단" /></a></li>
            <li><a href="#"><img src="<%= ctxPath%>/resources/images/main/img_btn_05.gif" alt="추천레시피" /></a></li>
            <li class="prn"><a href="#"><img src="<%= ctxPath%>/resources/images/main/img_btn_06.gif" alt="학교급식컨설팅" /></a></li>
          </ul>
        </div>
        <div class="main_left_box">
          <h3><img src="<%= ctxPath%>/resources/images/main/main_h3_01.gif" alt="학교급식특색활동" /><span class="more"><a href="#"><img src="<%= ctxPath%>/resources/images/main/more1.gif" alt="더보기" /></a></span></h3>
          <dl>
            <dt><img src="<%= ctxPath%>/resources/images/main/main_img.gif" alt="" /></dt>
            <dd><a href="#">학교급식정보센터 사이트를 오픈하였습니다. 사이트에 방문해주신 여러...</a></dd>
            <dd class="text"><a href="#">궁금한 사항은 문의게시판을 통해 질문...학교급식정보센터 사이트를 오픈하였습니다...</a></dd>
          </dl>
          <h3><img src="<%= ctxPath%>/resources/images/main/main_h3_02.gif" alt="연수·행사 활동" /> <span class="more"><a href="#"><img src="<%= ctxPath%>/resources/images/main/more1.gif" alt="더보기" /></a></span></h3>
          <dl>
            <dt><img src="<%= ctxPath%>/resources/images/main/main_img.gif" alt="" /></dt>
            <dd><a href="#">학교급식정보센터 사이트를 오픈하였습니다. 사이트에 방문해주신 여러...</a></dd>
            <dd class="text"><a href="#">궁금한 사항은 문의게시판을 통해 질문...학교급식정보센터 사이트를 오픈하였습니다...</a></dd>
          </dl>
        </div>
        <div class="s_banner">
          <ul>
            <li><a href="#"><img src="<%= ctxPath%>/resources/images/main/s_banner_01.gif" alt="식중독 발생시 대처요령" /></a></li>
            <li><a href="#"><img src="<%= ctxPath%>/resources/images/main/s_banner_02.gif" alt="안전사고 발생시 대처요령" /></a></li>
          </ul>
        </div>
        <div class="s_notice"> 
          <!--tab object -->
          <ul class="s_main_tab">
            <li><a href="javascript:void(0)" onclick="doGoTab(this,'01');" class="s_main_tab01_on">안내</a></li>
            <li><a href="javascript:void(0)" onclick="doGoTab(this,'02');" class="s_main_tab02">정보</a></li>
            <li><a href="javascript:void(0)" onclick="doGoTab(this,'03');" class="s_main_tab03">관련기관</a></li>
            <li><a href="javascript:void(0)" onclick="doGoTab(this,'04');" class="s_main_tab04">교육청</a></li>
          </ul>
          <!--//tab object -->
          
          <div id="s_tab01" class="tabDivArea">
            <ul>
              <li><a href="http://www.mfds.go.kr/jsp/page/food_zone_new.jsp" target="_blank">식중독지수</a></li>
              <li><a href="http://www.mfds.go.kr/fm/index.do" target="_blank">식중독예방</a></li>
              <li><a href="http://ews.foodnara.go.kr/jsp/src/session/loginForm.jsp" target="_blank">식중독조기경보시스템</a></li>
              <li><a href="http://www.haccphub.or.kr/welcome.do" target="_blank">HACCP통합검색</a></li>
              <li><a href="http://www.foodsafety.go.kr/fsafe/main.fs" target="_blank">농식품안전정보서비스</a></li>
              <li><a href="info/baseWay.html" target="_self">학교급식 기본방향</a> </li>
            </ul>
          </div>
          <div id="s_tab02" class="tabDivArea" style="display:none;">
            <ul>
              <li><a href="http://www.naqs.go.kr/index.jsp" target="_blank">농산물 품질</a></li>
              <li><a href="http://www.enviagro.go.kr/" target="_blank">인증</a></li>
              <li><a href="http://www.kamis.co.kr/customer/main/main.do" target="_blank">유통</a></li>
              <li><a href="http://www.ekape.or.kr/view/user/main/main.asp" target="_blank">축산물 품질</a></li>
              <li><a href="http://cattle.mtrace.go.kr/index.do" target="_blank">이력</a></li>
              <li><a href="http://www.ekapepia.com/home/homeIndex.do" target="_blank">유통</a></li>
              <li><a href="http://www.nfqs.go.kr/2013/index.asp" target="_blank">수산물 품질</a></li>
              <li><a href="http://www.fishtrace.go.kr/" target="_blank">이력</a></li>
              <li><a href="http://www.fips.go.kr/" target="_blank">유통</a></li>
              <li><a href="https://schoolhealth.kedi.re.kr/" target="_blank">학생건강정보</a></li>
              <li><a href="http://www.foodnara.go.kr/foodnara/index.do" target="_blank">식품나라</a></li>
              <li><a href="http://call.mfds.go.kr/kfda" target="_blank">식약처 FAQ</a></li>
              <li><a href="http://kostat.go.kr/portal/korea/kor_nw/2/1/index.board?bmode=list&bSeq=&aSeq=&pageNo=1&rowNum=10&navCount=10&currPg=&sTarget=title&sTxt=%EC%86%8C%EB%B9%84%EC%9E%90" target="_blank">물가동향</a></li>
            </ul>
          </div>
          <div id="s_tab03" class="tabDivArea" style="display:none;">
            <ul>
              <li><a href="http://www.moe.go.kr/" target="_blank">교육부</a></li>
              <li><a href="http://www.mfds.go.kr/" target="_blank">식품의약품안전처 </a></li>
              <li><a href="http://www.maf.go.kr/" target="_blank">농림축산식품부</a></li>
              <li><a href="http://www.mof.go.kr/" target="_blank">해양수산부</a></li>
              <li><a href="http://www.moel.go.kr/" target="_blank">고용노동부</a></li>
              <li><a href="http://www.kosha.or.kr/" target="_blank">안전보건공단 </a></li>
              <li><a href="http://www.mw.go.kr/" target="_blank">보건복지부 </a></li>
              <li><a href="http://www.cdc.go.kr/" target="_blank">질병관리본부 </a></li>
              <li><a href="http://www.g2b.go.kr/" target="_blank">g2b</a></li>
              <li><a href="http://www.eat.co.kr/" target="_blank">eaT</a></li>
              <li><a href="http://www.orbon.co.kr/" target="_blank">친환경유통센터</a></li>
            </ul>
          </div>
          <div id="s_tab04" class="tabDivArea" style="display:none;">
            <ul>
              <li><a href="http://www.seoul.go.kr/" target="_blank">서울특별시교육청</a></li>
              <li><a href="www.bogun.seoul.kr" target="_blank">서울특별시학교보건진흥원</a></li>
              <li><a href="#">동부</a></li>
              <li><a href="#">서부</a></li>
              <li><a href="#">남부</a></li>
              <li><a href="#">북부</a></li>
              <li><a href="#">중부</a></li>
              <li><a href="#">강동</a></li>
              <li><a href="#">강서</a></li>
              <li><a href="#">강남</a></li>
              <li><a href="#">동작</a></li>
              <li><a href="#">성동</a></li>
              <li><a href="#">성북</a></li>
            </ul>
          </div>
        </div>
      </div>
      <!-- //right-->
      
      <div class="banner">
        <p><img src="<%= ctxPath%>/resources/images/main/banner/img_01.gif" alt="관련사이트" /></p>
        <ul>
          <li><a href="http://www.moe.go.kr/" target="_blank"><img src="<%= ctxPath%>/resources/images/main/banner/banner_01.gif" alt="교육부" /></a></li>
          <li><a href="http://www.seoul.go.kr/" target="_blank"><img src="<%= ctxPath%>/resources/images/main/banner/banner_02.gif" alt="서울특별시교육청" /></a></li>
          <li><a href="http://www.mfds.go.kr/" target="_blank"><img src="<%= ctxPath%>/resources/images/main/banner/banner_03.gif" alt="식품의약품안전처" /></a></li>
          <li><a href="http://www.maf.go.kr/" target="_blank"><img src="<%= ctxPath%>/resources/images/main/banner/banner_04.gif" alt="농림축산식품부" /></a></li>
          <li><a href="http://www.mof.go.kr/" target="_blank"><img src="<%= ctxPath%>/resources/images/main/banner/banner_05.gif" alt="해양수산부" /></a></li>
          <li><a href="http://www.kosha.or.kr/" target="_blank"><img src="<%= ctxPath%>/resources/images/main/banner/banner_06.gif" alt="안전보건공단" /></a></li>
        </ul>
        <span class="btn"> <a href="#"><img src="<%= ctxPath%>/resources/images/main/banner/up.gif" alt="위로" /></a> <a href="#"><img src="<%= ctxPath%>/resources/images/main/banner/stop.gif" alt="정지" /></a> <a href="#"><img src="<%= ctxPath%>/resources/images/main/banner/down.gif" alt="아래로" /></a> </span> </div>
    </div>
  </div>
  <!-- //container-->    