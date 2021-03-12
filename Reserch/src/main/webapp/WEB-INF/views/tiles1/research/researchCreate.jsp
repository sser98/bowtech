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

<script type="text/javascript" >


	var flag1=true;
	var flag2=true;
	
	$(document).ready(function(){
		$(".mainMenu").each(function(index, item){
			$(item).click(function(){
				flag1=false;
			});
		});
		
		$(".subMenu").each(function(index, item){
			$(item).click(function(){
				flag1=true;
				flag2=false;
			});
		});
		
		$("#datepicker1").datepicker({
            dateFormat: 'yy-mm-dd' //Input Display Format 변경
            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
            ,changeYear: true //콤보박스에서 년 선택 가능
            ,changeMonth: true //콤보박스에서 월 선택 가능                
            ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
            ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
            ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
            ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
            ,minDate: "0D" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            ,maxDate: "+6M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                
        });                    
		
		$("#datepicker2").datepicker({
            dateFormat: 'yy-mm-dd' //Input Display Format 변경
            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
            ,changeYear: true //콤보박스에서 년 선택 가능
            ,changeMonth: true //콤보박스에서 월 선택 가능                
            ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시  
            ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
            ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
            ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
            ,minDate: "0D" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            ,maxDate: "+1Y" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                
        });     
		
		
		
		

		
		
	});

   function getElementsByClass(searchClass, node, tag) {
     var classElements = new Array();
     if ( node == null ) node = document;
     if ( tag == null ) tag = '*';
     var els = node.getElementsByTagName(tag);
     var elsLen = els.length;
     var pattern = new RegExp("(^|\\s)"+searchClass+"(\\s|$)");
     for (i = 0, j = 0; i < elsLen; i++) {
      if ( pattern.test(els[i].className) ) {
        classElements[j] = els[i];
        j++;
      }
    }
    return classElements;
  }

  function menuHidden(menu, sub) {
    menu.src = menu.src.replace("On", "Off");
    sub.style.display = "none";
  }

  function setEvtGnb() {
    var mainMenu = getElementsByClass("mainMenu");
    var prevMenu1, prevSub1, isHid1, prevMenu2, isHid2;
	
	var subMenu = getElementsByClass("subMenu");
	
    for (var i=0; i<mainMenu.length; i++) {
      (function (pos){
        mainMenu[pos].getElementsByTagName("img")[0].onmouseover = function(){
          if(prevMenu1) menuHidden(prevMenu1, prevSub1);
          prevMenu1 = this;
          this.src = this.src.replace("Off", "On");
          prevSub1 = document.getElementById("sub"+("0"+(pos+1)).match(/..$/));
          prevSub1.style.display = "block";
        };
    
        mainMenu[pos].onmouseout = function(e){
          var bool, e= e || event;
          (function (obj, tobj) {
            var childs = obj.childNodes;
            for (var x=0; x<childs.length; x++) {
              if(childs[x] == tobj) bool = true;
              else arguments.callee(childs[x], tobj);
            }
          })(this, document.elementFromPoint(e.clientX, e.clientY));
          if(flag1){
	          if(bool) return false;
	          menuHidden(prevMenu1, prevSub1);
          }
        };
      })(i);
    }
	
	for (var j=0; j<subMenu.length; j++) {
      (function (pos){
        subMenu[pos].getElementsByTagName("img")[0].onmouseover = function(){
          prevMenu2 = this;
          this.src = this.src.replace("Off", "On");
          prevSub2 = document.getElementById("sub"+("0"+(pos+1)).match(/..$/));
       	  flag2=true;
        };
    
        subMenu[pos].onmouseout = function(e){
          var bool, e= e || event;
          (function (obj, tobj) {
            var childs = obj.childNodes;
            for (var x=0; x<childs.length; x++) {
              if(childs[x] == tobj) bool = true;
              else arguments.callee(childs[x], tobj);
            }
          })(this, document.elementFromPoint(e.clientX, e.clientY));
          if(flag2){
	          if(bool) return false;
	          menuHidden(prevMenu2, prevSub2);
          }
        };
      })(j);
    }
  }
 
  window.onload = function() {
    setEvtGnb();
  }

</SCRIPT>

<script type="text/javascript">
initPage = function() {
	
};

doGoTab = function(thisObject, tab) {
	$(".business_tab").find(">li>a").each(function(index, el) {
		$(el).removeClass("business_tab0"+(index+1)+"_on");
		$(el).addClass("business_tab0"+(index+1));
	});
	$(thisObject).addClass("business_tab"+tab+"_on");
	if("01"==tab){
		$("#tab02").hide();
		$("#tab01").show();
	}else{
		$("#tab01").hide();
		$("#tab02").show();
	}
	
};

function go(){
	
	// 폼(form) 을 전송(submit)
	var frm = document.addFrm;
	frm.method = "POST";
	frm.action = "<%= ctxPath%>/research/researchCreateEnd.action";
	frm.submit();  
	
};

</script>



 <!-- container-->
  <div id="container">
    <div id="contents">
      <h2>메인내용</h2>
      <p><img src="<%= ctxPath%>/resources/images/sub/info/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
      <ul class="lnb">
        <li><img src="<%= ctxPath%>/resources/images/sub/particiation/sub_title_01.gif" alt="알림마당" /></li>
        <li><a href="#"><img src="<%= ctxPath%>/resources/images/sub/particiation/sub_stitle_01Off.gif" alt="학교급식인력풀" /></a></li>
        <li><a href="#"><img src="<%= ctxPath%>/resources/images/sub/particiation/sub_stitle_02Off.gif" alt="영양(교)사이야기" /></a></li>
        <li><a href="#"><img src="<%= ctxPath%>/resources/images/sub/particiation/sub_stitle_03Off.gif" alt="조리(원)사이야기" /></a></li>
        <li><a href="#"><img src="<%= ctxPath%>/resources/images/sub/particiation/sub_stitle_04Off.gif" alt="자유게시판" /></a></li>
        <li><a href="#"><img src="<%= ctxPath%>/resources/images/sub/particiation/sub_stitle_05On.gif" alt="설문조사" /></a></li>
      </ul>
      <div class="right_box">
        <h3><img src="<%= ctxPath%>/resources/images/sub/particiation/title_04.gif" alt="급식기구관리전환" /></h3>
        <p class="history"><img src="<%= ctxPath%>/resources/images/sub/history_home.gif" alt="home" /> 알림마당 <img src="<%= ctxPath%>/resources/images/sub/history_arrow.gif" alt="다음" /> <strong>설문조사</strong></p>
        <p class="pt30"></p>
        
       <form name="addFrm" enctype="multipart/form-data">
        <div class="tbl_box">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="설문조사">
            <caption>
            설문조사
            </caption>
            <colgroup>
            <col width="15%"/>
            <col width="20%"/>
            <col width="15%"/>
            <col width="20%"/>
            <col width="15%"/>
            <col width="%"/>
            </colgroup>
            <tbody>
              <tr>
                <th>제목</th>
                <td colspan="5" class="tl"><input type="text" name="sur_title" class="inp" /></td>
                </tr>
              <tr>
                <th>시작일</th>
                <td class="tl"><input type="text" id="datepicker1" name="sur_sat_date" class="inp" style="width:100px;" /> <a href="#"><img  src="<%= ctxPath%>/resources/images/sub/btn/ico_data.gif" alt="달력" /></a></td>
                <th>종료일</th>
                <td class="tl"><input type="text" id="datepicker2" name="sur_end_date" class="inp" style="width:100px;" /> <a href="#"><img src="<%= ctxPath%>/resources/images/sub/btn/ico_data.gif" alt="달력" /></a></td>
                <th>결과확인</th>
                <td class="tl"><img src="<%= ctxPath%>/resources/images/sub/btn/btn_view.gif" alt="결과보기" /></td>
              </tr>
              <tr>
                <th>문항수</th>
                <td colspan="5" class="tl">
                <select id="aa" name="que_cnt">
                	<option>1</option>
                    <option>2</option>
                    <option>3</option>
                    <option>4</option>
                    <option>5</option>
                    <option>6</option>
                    <option>7</option>
                    <option>8</option>
                    <option>9</option>
                    <option>10</option>
                </select>
                </td>
                </tr>
              <tr>
              	<th>내용</th>
               <td colspan="5" class="tl">
               	   <div class="research">
                       <p><input type="text"  name="list[0].surq_title" class="inp"  title="1. 위생불량 납품단절 편함" /></p>
                        <ul>
                        <li><input type="text" name="list[0].suri_title1" class="inp"  title="매우그렇다" /></li>
                        <li><input type="text" name="list[0].suri_title2" class="inp"  title="매우그렇다" /></li>
                        <li><input type="text" name="list[0].suri_title3" class="inp"  title="매우그렇다" /></li>
                        <li><input type="text" name="list[0].suri_title4" class="inp"  title="매우그렇다" /></li>
                        <li><input type="text" name="list[0].suri_title5" class="inp"  title="매우그렇다" /></li>
                        </ul>
					</div>									
               </td>
              </tr>
              <tr>
               <th>첨부파일</th>
               <td colspan="5" class="tl">
               		<input type="file" id="aa" name="aa" />
               </td>
              </tr>
            </tbody>
          </table>
          </form>
          
          <p class="pt40"></p>
          <!-- btn--> 
          <span class="bbs_btn"> 

          <span class="wte_l"><a href="<%= ctxPath%>/research/researchList.action" class="wte_r">목록</a></span>
          <span class="per_l"><a onclick="go();" class="pre_r">등록</a></span>
          <span class="wte_l"><a href="#" class="wte_r">취소</a></span>
          
          

          </span> 
          <!-- //btn--> 
          
        </div>
      </div>
      
      <p class="bottom_bg"></p>
    </div>
  </div>
  <!-- //container-->