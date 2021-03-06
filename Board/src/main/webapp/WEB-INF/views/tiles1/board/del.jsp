<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String ctxPath = request.getContextPath(); %>    

<style type="text/css">

	table, th, td, input, textarea {border: solid gray 1px;}
	
	#table {border-collapse: collapse;
	 		width: 900px;
	 		}
	#table th, #table td{padding: 5px;}
	#table th{width: 120px; background-color: #DDDDDD;}
	#table td{width: 860px;}
	.long {width: 470px;}
	.short {width: 120px;}

</style>

<script type="text/javascript">
	$(document).ready(function(){
		
		// 완료버튼
		$("button#btnDelete").click(function(){
			
			// 글암호 유효성 검사
			var pwVal = $("#pw").val().trim();
			if(pwVal == "") {
				alert("글암호를 입력하세요!!");
				return;
			}
			
			// 폼(form)을 전송(submit)
			var frm = document.delFrm;
			frm.method = "POST";
			frm.action = "<%= ctxPath%>/delEnd.action";
			frm.submit();
		});
				
	});// end of $(document).ready(function(){})----------------
	
</script>

<div style="padding-left: 10%;">
	<h1>글삭제</h1>

	<form name="delFrm">
		<table id="table">
			<tr>
				<th>글암호</th>
				<td>
					<input type="password" name="pw" id="pw" class="short" /> 
					<input type="hidden" name="seq" value="${seq}" />       
				</td>
			</tr>
		</table>
		
		<div style="margin: 20px;">
		
			<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">
  					삭제
			</button>
			
			<!-- <button type="button" id="btnDelete">완료</button> -->
			<button type="button" onclick="javascript:history.back()">취소</button>
		</div>
			
	</form>
	
	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">게시물 삭제</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					
				</div>
				<div class="modal-body">게시물을 정말 삭제하시겠습니까?</div>
				<div class="modal-footer">
					<button type="button" id="btnDelete" class="btn btn-primary">삭제하기</button>
					<button type="button" onclick="javascript:history.back()" class="btn btn-secondary"
						data-dismiss="modal">취소하기</button>
				</div>
			</div>
		</div>
	</div>

</div>
