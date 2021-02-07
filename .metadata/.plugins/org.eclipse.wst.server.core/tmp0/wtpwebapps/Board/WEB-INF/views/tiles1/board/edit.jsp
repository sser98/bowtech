<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>    

<style type="text/css">

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
</style>

<script type="text/javascript">

	var n = 1; // alert 한번만 뜨도록 설정
	var a = 0; // 조건 변수
	
	
	$(document).ready(function(){
		
		$('textarea#content').bind({
			change: textarea_analysis,
			click: textarea_analysis,
			keydown: textarea_analysis,
			mousemove: textarea_analysis,
			onfocus : textarea_analysis
		});
		
		
		$('input#subject').bind({
			change: subject_analysis,
			click: subject_analysis,
			keydown: subject_analysis,
			mousemove: subject_analysis,
			onfocus : subject_analysis
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
						return;
					}
				    
					
			        $("textarea#content").val(contentVal);
			        
					// 글암호 유효성 검사
					var pwVal = $("input#pw").val().trim();
					
					
					if(pwVal == "") {
						alert("글암호를 입력하세요!!");
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
			
					
			// 폼(form)을 전송(submit)
			var frm = document.editFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/editEnd.action";
			frm.submit();
			
		}); // end of updateBtn Function
		
		}); // end of $(document).ready(function(){
		
		
		

		   $(function(){
			   
				$('#textarea#content').bind({
					change: textarea_analysis,
					click: textarea_analysis,
					keydown: textarea_analysis,
					hover: textarea_analysis,
					onfocus : textarea_analysis
				});
				
			});
		   
			function textarea_analysis(event) {
					   
				    content = $("textarea#content").val().trim();
				    $("td#result").html("("+content.length+" / 최대 4500자)");
				    
				    if(content.length > 4500 ) {
				    	
				    	alert_content(1);
				        
				        
				        subject = $(this).val().trim();
				        $(this).val(content.substring(0, 4500));
				        $("textarea#content").focus();
				        
				        return;
				        
				    }
			};
			
			function subject_analysis(event) {
						   
					subject = $("input#subject").val().trim();
						
				    if(subject.length > 80 ) {
					    	
				    	alert_content(2);
				        
				        subject = $(this).val().trim();
				        $(this).val(subject.substring(0, 80));
				        $("input#subject").focus();
				        return;    
					    }
			};
			
			
			function alert_content(a) {
				
				if(a == 1) {
					
					setTimeout(function() {
						alert("내용은 최대 4500자 까지 입력 가능합니다.");
						}, 1);
					return;
				}
				
				
				if(a == 2) {
					
					setTimeout(function() {
						alert("제목은 최대 80자 까지 입력 가능합니다.");
						}, 1);
					return;
				}
				
				
				
			};
				
	
	
</script>

<div style="padding-left: 5%;">
	<h1>글수정</h1>

	<form name="editFrm">
		<table id="table">
			<tr>
				<th>작성자</th>
				<td>
				    <input type="hidden" name="seq" value="${boardvo.seq}" />
				    
				    <c:out value="${boardvo.name}" />
				    <input type="hidden" name="name" value="${boardvo.name}" />
					
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="subject" id="subject" class="long" value="${boardvo.subject}" maxlength='100'/>       
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea rows="10" cols="100" style="height: 612px;" name="content" id="content">${boardvo.content}</textarea>       
				</td>
			</tr>
		<tr>
            <th>글자 수</th>
            <td id="result">
            </td>
         </tr>
			<tr>
				<th>글암호</th>
				<td>
					<input type="password" name="pw" id="pw" class="short" />
					<span id="error" class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>       
				</td>
			</tr>
		</table>
		<div id="btn">
			<div>
				<button type="button" id="btnUpdate">완료</button>
				<button type="button" onclick="javascript:history.back()">취소</button>
			</div>
		</div>
	</form>
	
</div>