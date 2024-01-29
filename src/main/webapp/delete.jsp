<%@ page import="com.study.DAO.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: gonghuibae
  Date: 2024/01/26
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Long boardID = Long.parseLong(request.getParameter("boardId"));
%>
<html>
<head>
    <title>비밀번호 확인</title>
</head>
<body>
<h1><%= "비밀번호 확인" %></h1>
<form action="delete_proc.jsp" method="post">
    <input type="hidden" name="boardId" value="<%=boardID%>"/>
    <table border="1">
        <tr>
            <th>비밀번호</th>
            <th><input type="password" name="pwd" placeholder="비밀번호" required /></th>
        </tr>
    </table>
    <input type="button" value="취소" onclick="location.href='list.jsp'"/>
    <input type="submit" value="삭제" />
</form>

</body>
</html>
