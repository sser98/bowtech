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
</script>



 <!-- container-->
  <div id="container">
    <div id="contents">
      <h2>메인내용</h2>
      <p><img src="<%=ctxPath%>/resources/images/sub/info/sub_vimg_01.jpg" alt="건강한 급식 행복한 학교" /></p>
      <ul class="lnb">
        <li><img src="<%=ctxPath%>/resources/images/sub/particiation/sub_title_01.gif" alt="알림마당" /></li>
        <li><a href="#"><img src="<%=ctxPath%>/resources/images/sub/particiation/sub_stitle_01Off.gif" alt="학교급식인력풀" /></a></li>
        <li><a href="#"><img src="<%=ctxPath%>/resources/images/sub/particiation/sub_stitle_02Off.gif" alt="영양(교)사이야기" /></a></li>
        <li><a href="#"><img src="<%=ctxPath%>/resources/images/sub/particiation/sub_stitle_03Off.gif" alt="조리(원)사이야기" /></a></li>
        <li><a href="#"><img src="<%=ctxPath%>/resources/images/sub/particiation/sub_stitle_04Off.gif" alt="자유게시판" /></a></li>
        <li><a href="#"><img src="<%=ctxPath%>/resources/images/sub/particiation/sub_stitle_05On.gif" alt="설문조사" /></a></li>
      </ul>
      <div class="right_box">
        <h3><img src="<%=ctxPath%>/resources/images/sub/particiation/title_04.gif" alt="급식기구관리전환" /></h3>
        <p class="history"><img src="<%=ctxPath%>/resources/images/sub/history_home.gif" alt="home" /> 알림마당 <img src="<%=ctxPath%>/resources/images/sub/history_arrow.gif" alt="다음" /> <strong>설문조사</strong></p>
        <p class="pt30"></p>
        
       
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
                <td colspan="5" class="tl"><strong>제목입니다.</strong></td>
                </tr>
              <tr>
                <th>시작일</th>
                <td class="tl">2013-12-12</td>
                <th>종료일</th>
                <td class="tl">2013-12-12</td>
                <th>결과확인</th>
                <td class="tl"><img src="<%=ctxPath%>/resources/images/sub/btn/btn_view.gif" alt="결과보기" /></td>
              </tr>
              <tr>
                <th>문항수</th>
                <td colspan="5" class="tl">16개</td>
                </tr>
              <tr>
               <td colspan="6" class="tl">
               	   <div class="research">
                       <p>1. 위생불량 납품단절 편함</p>
                        <ul>
                        <li>① 매우그렇다</li>
                        <li>② 조금그렇다</li>
                        <li>③ 그렇다</li>
                        <li>④ 조금 아니다</li>
                        <li>⑤ 매우 아니다</li>
                        <li>선택사유 <input type="text" id="aa" name="aa" class="inp" style="width:200px;" /> </li>
                        </ul>
					</div>
                    
                    <div class="research">
                       <p>1. 위생불량 납품단절 편함</p>
                        <ul>
                        <li>① 매우그렇다</li>
                        <li>② 조금그렇다</li>
                        <li>③ 그렇다</li>
                        <li>④ 조금 아니다</li>
                        <li>⑤ 매우 아니다</li>
                        <li>선택사유 <input type="text" id="aa" name="aa" class="inp" style="width:200px;" /> </li>
                        </ul>
					</div>
               </td>
              </tr>
              <tr>
               <th>첨부파일</th>
               <td colspan="5" class="tl">
               	내용입력 <img src="<%=ctxPath%>/resources/images/sub/btn/btn_pdf.gif" alt="pdf" />
               </td>
              </tr>
            </tbody>
          </table>
          
          <p class="pt40"></p>
          <!-- btn--> 
          <span class="bbs_btn"> 

          <span class="wte_l"><a href="#" class="wte_r">목록</a></span>
          <span class="wte_l"><a href="#" class="wte_r">수정</a></span>
          <span class="wte_l"><a href="#" class="wte_r">삭제</a></span>
          <span class="per_l"><a href="#" class="pre_r">결과보기</a></span>
          <span class="wte_l"><a href="#" class="wte_r">사유전체보기</a></span>
          
          

          </span> 
          <!-- //btn--> 
          
        </div>
      </div>
      
      <p class="bottom_bg"></p>
    </div>
  </div>
  <!-- //container-->