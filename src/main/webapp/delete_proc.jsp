<%@ page import="com.study.DAO.BoardDAO" %><%--
  Created by IntelliJ IDEA.
  User: gonghuibae
  Date: 2024/01/26
  Time: 6:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Long boardId = Long.parseLong(request.getParameter("boardId"));
  BoardDAO boardDAO = new BoardDAO();
  String DBpassWord = boardDAO.getPassWord(boardId);
  String boardPassWord = request.getParameter("pwd");
  if (!boardPassWord.equals(DBpassWord)){
    boardDAO.releaseResources();
    response.sendRedirect("delete.jsp?boardId=" + String.valueOf(boardId));
    // 나중에 alter로 변경
    return;
  }

  boardDAO.deleteBoard(boardId);
  boardDAO.releaseResources();
  response.sendRedirect("list.jsp");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
