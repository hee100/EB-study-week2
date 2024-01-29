<%@ page import="com.study.DAO.BoardDAO" %>
<%@ page import="com.study.VO.BoardVO" %><%--
  Created by IntelliJ IDEA.
  User: gonghuibae
  Date: 2024/01/25
  Time: 4:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    Long boardId = Long.parseLong(request.getParameter("boardId"));
    BoardDAO boardDAO = new BoardDAO();
    String boardPassWord = request.getParameter("pwd");
    String DBpassWord = boardDAO.getPassWord(boardId);
    if (!boardPassWord.equals(DBpassWord)){
        boardDAO.releaseResources();
        response.sendRedirect("update.jsp?boardId=" + String.valueOf(boardId));
        return;
    }


    String name = request.getParameter("name");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    BoardVO boardVO = BoardVO.builder()
            .boardId(boardId)
            .name(name)
            .title(title)
            .content(content)
            .build();

    boardDAO.updateBoard(boardVO);
    boardDAO.releaseResources();
    response.sendRedirect("list.jsp");
%>
<html>
<head>
    <title>게시판 업데이트</title>
</head>
<body>

</body>
</html>
