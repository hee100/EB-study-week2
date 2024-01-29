<%@ page import="com.study.DAO.CommentDAO" %>
<%@ page import="com.study.VO.CommentVO" %><%--
  Created by IntelliJ IDEA.
  User: gonghuibae
  Date: 2024/01/27
  Time: 11:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Long boardId = Long.parseLong(request.getParameter("boardId"));
  String content = request.getParameter("content");
  CommentDAO commentDAO = new CommentDAO();
  CommentVO commentVO = CommentVO.builder()
          .boardId(boardId)
          .content(content)
          .build();

  commentDAO.insertComment(commentVO);
  commentDAO.releaseResources();
  response.sendRedirect("view.jsp?boardId=" + String.valueOf(boardId));
%>

<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
