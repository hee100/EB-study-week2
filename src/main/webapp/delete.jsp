<%@ page import="com.study.DAO.BoardDAO" %>
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
<form action="board" method="post">
    <input type="hidden" name="commandStr" value="boardDelete"/>
    <input type="hidden" name="boardId" value="<%=boardID%>"/>
    <table border="1">
        <tr>
            <th>비밀번호</th>
            <th><input type="password" name="pwd" placeholder="비밀번호" required /></th>
        </tr>
    </table>
    <input type="button" value="취소" onclick="location.href='board?commandStr=boardList'"/>
    <input type="submit" value="삭제" />
</form>

</body>
</html>
