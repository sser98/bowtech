<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>    

<style type="text/css">
		
	th {
		font-family: "제주고딕";
		font-size: 15pt;
	}
	
	textarea, input[type=text] {
		font-family: "제주고딕";
		font-size: 10pt;
	} 
	
	table, th, td, input, textarea {border: solid gray 1px;}
	
	#table {border-collapse: collapse;
	 		width: 98%;
	 		}
	 		
	#table th, #table td{padding: 5px;}
	#table th{width: 10%; background-color: #DDDDDD; text-align: center;}
	#table td{width: 80%;}
	.long {width: 470px;}
	.short {width: 120px;}
	
	span#error {
   	display: none;
   	color: red;
   }
	
	input#subject {
		width: 100%;
	}
	
	textarea {
		width: 100%;
		resize: none;
	}
	
	div#btn {
		display: flex;
		align-items: center;
		justify-content : center;
	}
	
	button {
		margin-top: 20px;
		margin-right: 20px;
		margin-bottom: 20px;
		font-size: 10pt;
	}
	
	div#lost {
		display: flex;
		flex-direction: column;
	}
</style>

<script type="text/javascript">

	var n = 1; // alert 한번만 뜨도록 설정
	var a = 0; // 조건 변수

	var checkpwVal = 0; // 암효 맞는지 틀렸는지 여부
	
	$(document).ready(function(){
		
		textarea_analysis();
		
		$('textarea#content').bind({
			input : textarea_analysis,
			//change : textarea_analysis,
			paste : textarea_analysis,
			propertychange : textarea_analysis
		}); 
		
		
		$('input#subject').bind({
			input : subject_analysis,
			//change : subject_analysis,
			paste : subject_analysis,
			propertychange : subject_analysis
		});
			    
		
		// 완료버튼
		$("button#btnUpdate").click(function(){
						
				    // 글제목 유효성 검사
					var subjectVal = $("input#subject").val().trim();
					if(subjectVal == "") {
						alert("글 제목을 입력하세요!!");
						
						return;
					}
					
				    // 글내용 유효성 검사
					var contentVal = $("textarea#content").val().trim();
					if(contentVal == "") {
						alert("글 내용을 입력하세요!!");
						$("textarea#content").focus();
						return;
					}
				    
					
			        $("textarea#content").val(contentVal);
			        
					// 글암호 유효성 검사
					var pwVal = $("input#pw").val().trim();
					
					
					if(pwVal == "") {
						alert("글암호를 입력하세요!!");
						$("input#pw").focus();
						return;
					}
					
					else {
						
						var regExp = new RegExp(/^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).*$/g);
						var bool = regExp.test(pwVal);
						
						if(!bool) {
							
							// 암호가 정규표현식에 위배된 경우
							alert("암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.");
						    $(this).focus();
						   	$("span.error").show();
						    return;
						    
						}
					
					}
			
			
			
			checkpw();		
			
			return;
			
			// 폼(form)을 전송(submit)
<%-- 			var frm = document.editFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/editEnd.action";
			frm.submit(); --%>
			

			
		}); // end of updateBtn Function
		
		
		
		}); // end of $(document).ready(function(){
		
		
		

		function textarea_analysis(event) {
			 

			//alert("분석함수가 실행되었습니다.");
		    content = $("textarea#content").val().trim();
		    $("td#result").html("("+content.length+" / 최대 4500자)");
		    
		    if(content.length > 4500 ) {
		    	textarea_unbind();
		        alert_content(1);
				$("textarea#content").val(content.substring(0, 4500));
				content = $("textarea#content").val().trim();
				$("td#result").html("(4500 / 최대 4500자)");
				return;				
		    }
			return; 
	};
	
	function textarea_bind() {
		var content=$("textarea#content").val();
		$("textarea#content").val(content.substring(0, 4500));
		$('textarea#content').bind({
			//change : textarea_analysis,
			paste : textarea_analysis,
			input : textarea_analysis,
			propertychange : textarea_analysis
		});
		
	};
	
	function textarea_unbind() {

		//alert("unbind 함수가 실행되었습니다.");
		$("textarea#content").unbind("input");
		//$("textarea#content").unbind("change");
		$("textarea#content").unbind("paste");
		$("textarea#content").unbind("propertychange");
	};
	
	function subject_analysis(event) {

		subject = $("input#subject").val().trim();
		
		
		if (subject.length > 75) {
			subject = $(this).val().trim();
			subject_unbind();
			alert_content(2);
			$(this).val(subject.substring(0, 75));
			$("input#subject").focus();

		}

		return;
	};
	function subject_bind() {
		var subject=$("input#subject").val();
		$("input#subject").val(subject.substring(0, 75));
		
		$('input#subject').bind({
			paste : subject_analysis,
			input : subject_analysis,
			propertychange : subject_analysis
		});
		
	};
	
	function subject_unbind() {

		$("input#subject").unbind("input");
		$("input#subject").unbind("paste");
		$("input#subject").unbind("propertychange");
	};
	
	
	function alert_content(a) {

		// 함수 실행 깃발

		if (n == 1) {
			n = 0;
			textarea_bind();
			subject_bind();
			return;
		}

		// 내용
		if (a == 1) {
			
			alert("내용은 최대 4500자 까지 입력 가능합니다.");
			n = 1;
			  setTimeout(function() {
				  alert_content(1);
			}, 0.1);  

		}

		// 제목
		if (a == 2) {
			
			alert("제목은 최대 75자 까지 입력 가능합니다.");
			n = 1;
			setTimeout(function() {
				alert_content(2);		
				}, 0.1);
		}
 
		// 이름
		if (a == 3) {
			alert("이름은 최대 5자 까지 입력 가능합니다.");
			n = 1;
			setTimeout(function() {
				alert_content(3);		
				}, 0.1);
		}

		return;

	}; // end of alert 함수
	
function checkpw() {
		
		$.ajax({
    		url:"<%= ctxPath%>/checkpw.action",
    		data:{"seq":${boardvo.seq},
    			  "pw":$("input#pw").val()	
    			},  
    		type:"post",
    		dataType:"json",    
    		success:function(json){
				
    			var count = json.n; 
    				
    			if(count == 0) {
    				
       				alert("글 암호가 맞지 않습니다.");
    				$("input#pw").focus();
    				return;	
    				

    			}
    			
    			else {
    				
    			
    				var frm = document.editFrm;
    				frm.method = "POST";
    				frm.action = "<%= ctxPath%>/editEnd.action";
    				frm.submit();
    				
    				return;
    			}
    			

    			
 
    			
    		},
    		
    		error: function(request, status, error){
    			 alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
			}
    	});	
		
	}// end of function isExistEmailCheck()--------------------------
	
</script>

<div style="padding-left: 5%;">
	
	<c:if test="${empty boardvo}">
		<div id="lost">
				<h2>요청하신 페이지를 찾을 수 없습니다.</h2>
				<h5>입력한 주소가 잘못되었거나, 사용이 일시 중단되어 요청하신 페이지를 찾을 수 없습니다.</h5>
				<h5>서비스 이용에 불편을 드려 죄송합니다.</h5>
		</div>
		
		<div id="btn">
			<div>
				<button type="button" onclick="javascript:location.href='<%= request.getContextPath()%>/list.action'">게시판 메인으로</button>
			</div>
		</div>	
	</c:if>
	
	<c:if test="${not empty boardvo}">
	<h1>글수정</h1>

			<form name="editFrm">
				<table id="table">
					<tr>
						<th>작성자</th>
						<td><c:out value="${boardvo.name}"></c:out> 
						<input type="hidden" name="seq" value="${boardvo.seq}" /> 
						<input type="hidden" name="name" value="${boardvo.name}" />
						
						</td>
					</tr>
					
					<tr>
						<th>제목</th>
						<td><input type="text" name="subject" id="subject" class="long" value="<c:out value="${boardvo.subject}"></c:out>" maxlength='200' /></td>
					</tr>
					<tr>
						<th>내용</th>
						<td><textarea rows="10" cols="100" style="height: 612px;" name="content" id="content"><c:out value="${boardvo.content}"></c:out></textarea></td>
					</tr>
					<tr>
						<th>글자 수</th>
						<td id="result"></td>
					</tr>
					<tr>
						<th>글암호</th>
						<td><input type="password" name="pw" id="pw" class="short" />
							<span id="error" class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15글자로 입력하세요.</span></td>
					</tr>
					<%-- <input type="text" style="width: 100%;" name="gobackURL" value="${gobackURL}"/> --%>
				</table>
	
				<div id="btn">
					<div>
						<button type="button" id="btnUpdate">완료</button>
						<button type="button" onclick="javascript:history.back()">취소</button>
					</div>
				</div>
			</form>
	</c:if>		
	</div>
