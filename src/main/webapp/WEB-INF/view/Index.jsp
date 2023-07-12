<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	async function apiListView() {
		//JSON 가져오기
		const response = await fetch('http://localhost:8080/newsApi');
		const json = await response.json();
		
		// json 리스트 사용
	    for (let i = 0; i < json.length; ++i) {

					// 화면에 추가할 객체 생성
	        let node = document.createElement("div");
	        
	        let titleLink = document.createElement("a");
			titleLink.href = `${pageContext.request.contextPath}/news?pid=`+json[i].postId;
			titleLink.textContent = json[i].title;
			
			node.appendChild(titleLink);

			// 만들어진 요소 화면속 요소 내부에 추가.
	        document.getElementById("myList").appendChild(node);
	    }
	}
</script>
</head>
<body>
	<h1>시작페이지</h1>
	
	<form action="${pageContext.request.contextPath}/testApi">
		<input type="submit" value="JSON확인하기">
	</form>
	
	<c:forEach var="v" items="${exList}">
		${v.getCnt() }
	</c:forEach>
	
	
</body>
</html>