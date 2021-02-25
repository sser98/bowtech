<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
	
	input#name {
		width: 80px;
		
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
	
	var n = 0; // alert 한번만 뜨도록 설정
	var a = 0; // 조건 변수
	// var content ="";
	
   $(document).ready(function(){
	   
	
	   // on이랑 bind사용방법 똑같음
       /*$("textarea#content").on({
		   propertychange : textarea_analysis,
		   change : textarea_analysis,
		   keyup : textarea_analysis,
		   paste : textarea_analysis,
	   }); */
	   
	   
  		$('textarea#content').bind({
			input : textarea_analysis,
			paste : textarea_analysis,
			propertychange : textarea_analysis
		}); 
		
		
		$('input#subject').bind({
			input : subject_analysis,
			paste : subject_analysis,
			propertychange : subject_analysis
		});
		
		
		$('input#name').bind({
			input : name_analysis,
			paste : name_analysis,
			propertychange : name_analysis
		}); 
			    
		
		   // 쓰기버튼
		   $("button#btnWrite").click(function(){
				
			   
			   var nameVal = $("input#name").val(); 
			   
			   if(nameVal == "") {
				   
				   alert("작성자명을 입력하세요");
				   $("input#name").focus();
				   return;
			   }
			   
			    // 글제목 유효성 검사
				var subjectVal = $("input#subject").val().trim();
				if(subjectVal == "") {
					alert("글제목을 입력하세요!!");
					$("input#subject").focus();
					return;
				}
				
			    // 글내용 유효성 검사
				var contentVal = $("textarea#content").val().trim();
				if(contentVal == "") {
					alert("글내용을 입력하세요!!");
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
					   	$("span.error").show();
					   	$("input#pw").focus();					   	
					    return;
					}
				
					
				}
				
				
				// 폼(form) 을 전송(submit)
				var frm = document.addFrm;
				frm.method = "POST";
				frm.action = "<%= ctxPath%>/addEnd.action";
				frm.submit();  
				
				
		   });
		   
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
			paste : textarea_analysis,
			input : textarea_analysis,
			propertychange : textarea_analysis
		});
		
	};
	
	function textarea_unbind() {

		//alert("unbind 함수가 실행되었습니다.");
		$("textarea#content").unbind("input");
		$("textarea#content").unbind("paste");
		$("textarea#content").unbind("propertychange");
	};
	
	function subject_analysis(event) {

		var subject = $("input#subject").val()
		
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
		
		// alert("제목 bind 함수를 실행합니다.");
		var subject = $("input#subject").val();
		$("input#subject").val(subject.substring(0, 75));
		
		$('input#subject').bind({
			//change : subject_analysis,
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
	
	
	function name_analysis(event) {
	
		name = $("input#name").val().trim();

		if (name.length > 5) {

			name = $(this).val().trim();
			name_unbind();
			alert_content(3);
			$(this).val(name.substring(0, 5));
			$("input#name").focus();

		}
		return;
	};
	
	function name_bind() {
		var name = $("input#name").val();
		$("input#name").val(name.substring(0, 5));
		$('input#name').bind({
			paste : name_analysis,
			input : name_analysis,
			propertychange : name_analysis
		});
		
	};
	
	function name_unbind() {

		//alert("unbind 함수가 실행되었습니다.");
		$("input#name").unbind("input");
		//$("input#name").unbind("change");
		$("input#name").unbind("paste");
		$("input#name").unbind("propertychange");
	};

	function alert_content(a) {

		// 함수 실행 깃발

		if (n == 1) {
			n = 0;
			textarea_bind();
			subject_bind();
			name_bind();
			return;
		}

		// 내용
		if (a == 1) {
			
			alert("내용은 최대 4500자 까지 입력 가능합니다.");
			
			n = 1;
			setTimeout(function() {
			  alert_content(1);
			}, 0.01);  

		}

		// 제목
		if (a == 2) {
			
			alert("제목은 최대 75자 까지 입력 가능합니다.");
			n = 1;
			setTimeout(function() {
				alert_content(2);		
				}, 0.01);
		}
 
		// 이름
		if (a == 3) {
			alert("이름은 최대 5자 까지 입력 가능합니다.");
			n = 1;
			setTimeout(function() {
				alert_content(3);		
				}, 0.01);
		}

		return;

	}; // end of alert 함수
</script>

<div style="padding-left: 5%;">
 <h1>글쓰기</h1>

 <form name="addFrm" enctype="multipart/form-data"> 
      <table id="table">
         <tr>
            <th>작성자</th>
            <td>
                <input type="hidden" name="fk_userid" value="leess" />
                <input type="text" name="name" id="name" value="" class="name" maxlength='10'/>       
            </td>
         </tr>
         <tr>
            <th>제목</th>
            <td>
               <input type="text" name="subject" id="subject" class="long" maxlength='210'/>       
            </td>
         </tr>
         <tr>
            <th>내용</th>
            <td>
               <textarea rows="10" cols="100" style="height: 612px;" name="content" id="content"></textarea>
                <span id="result"></span>
            </td>
         </tr>
         <tr>
         
            <th>글자 수</th>
            <td id="result">
               
            </td>
         </tr>

         
         <%-- === #150. 파일첨부 타입 추가하기 === --%>
         <tr>
            <th>파일첨부</th>
            <td>
               <input type="file" name="attach" />       
            </td>
         </tr>
         
         <tr>
            <th>글암호</th>
            <td>
               <input type="password" name="pw" id="pw" class="short"/> 
               <span id="error" class="error">암호는 영문자,숫자,특수기호가 혼합된 8~15 글자로 입력하세요.</span>      
               
            </td>
            
         </tr>
      </table>
      
      
      <%-- === #143. 답변글쓰기가 추가된 경우 === --%>
      <input type="hidden" name="fk_seq"  value="${requestScope.fk_seq}" />
      <input type="hidden" name="groupno" value="${requestScope.groupno}" />
      <input type="hidden" name="depthno" value="${requestScope.depthno}" />
      <div id="btn">
	      <div>
	     	<button type="button" id="btnWrite">쓰기</button>
         	<button type="button" onclick="javascript:history.back()">취소</button> 
	      </div>
      </div>
   </form>
   
</div>    