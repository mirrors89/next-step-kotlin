<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <jsp:include page="../layout/header.jsp"></jsp:include>
</head>
<body>
<jsp:include page="../layout/navigation.jsp"></jsp:include>

<div class="container" id="main">
    <div class="col-md-10 col-md-offset-1">
        <div class="panel panel-default">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>#</th>
                    <th>사용자 아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th></th>
                </tr>
                </thead>


                <tbody>
                <c:forEach items="${users}" var="user" varStatus="status">
                <tr>
                    <th scope="row">${status.count}</th>
                    <td>${user.userId}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>
                        <c:if test="${not empty sessionScope.user && sessionScope.user.userId eq user.userId}">
                        <a href="/user/updateForm?userId=${user.userId}" class="btn btn-success" role="button">수정</a>
                        </c:if>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="../layout/footer.jsp" %>
</body>
</html>