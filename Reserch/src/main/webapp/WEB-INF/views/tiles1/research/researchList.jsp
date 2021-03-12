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
        
       
        <div class="tbl_box">
         <table width="100%" border="0" cellspacing="0" cellpadding="0" class="tbl_type01" summary="설문조사">
            <caption>
            설문조사
            </caption>
            <colgroup>
            <col width="8%"/>
            <col width="*%"/>
            <col width="15%"/>
            <col width="15%"/>
            <col width="10%"/>
            <col width="8%"/>
            <col width="10%"/>
            </colgroup>
            <tbody>
              <tr>
                <th>NO</th>
                <th>제목</th>
                <th>시작일</th>
                <th>마감일</th>
                <th>완료여부</th>
                <th>조회수</th>
                <th>결과확인</th>
              </tr>
              <tr>
                <td>10</td>
                <td class="tl">내용입니다.</td>
                <td>2013-01-02</td>                
                <td>2013-01-02</td>
                <td>완료</td>
                <td>0</td>
                <td><a href="#"><img src="<%= ctxPath%>/resources/images/sub/btn/btn_view.gif" alt="결과보기" /></a></td>
              </tr> 
            </tbody>
          </table>
          
          <!-- paging-->
          <ul class="paging">
            <li><a href="#" title="맨 처음 페이지로 가기"><img src="<%= ctxPath%>/resources/images/sub/btn/pre_01.gif"  alt="맨 처음 페이지로 가기" /></a></li>
            <li><a href="#" title="이전 페이지로 가기"><img src="<%= ctxPath%>/resources/images/sub/btn/pre.gif" alt="이전 페이지로 가기" /></a></li>
            <li><span title="현재페이지"><a href="#" class="on">1</a></span></li>
            <li><a href="# " title="2페이지">2</a></li>
            <li><a href="#" title="3페이지">3</a></li>
            <li><a href="#" title="4페이지">4</a></li>
            <li><a href="# " title="5페이지">5</a></li>
            <li><a href="#" title="6페이지">6</a></li>
            <li><a href="#" title="7페이지">7</a></li>
            <li><a href="#" title="8페이지">8</a></li>
            <li><a href="#" title="9페이지">9</a></li>
            <li><a href="#" title="10페이지">10</a></li>
            <li><a href="#" title="다음 페이지로 가기" ><img src="<%= ctxPath%>/resources/images/sub/btn/next.gif" alt="다음 페이지" /></a></li>
            <li><a href="#" title="마지막 페이지로 가기"><img src="<%= ctxPath%>/resources/images/sub/btn/next_01.gif" alt="마지막 페이지" /></a></li>
          </ul>
          <!-- //paging--> 
          
          <!-- btn--> 
          <span class="bbs_btn"> 

          <span class="per_l"><a href="<%= ctxPath%>/research/researchCreate.action" class="pre_r">글쓰기</a></span>

          </span> 
          <!-- //btn--> 
          
        </div>
        <div class="search_box">
          <select>
            <option>제목</option>
          </select>
          <input type="text" id="serch" name="serch" />
          <a href="#"><img src="<%= ctxPath%>/resources/images/sub/btn/btn_serch.gif" alt="검색" /></a> </div>
      </div>
      
      <p class="bottom_bg"></p>
    </div>
  </div>
  <!-- //container-->