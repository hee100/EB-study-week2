<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.study.VO.BoardVO" %>
<%@ page import="com.study.DAO.BoardDAO" %>
<%@ page import="com.study.DAO.CategoryDAO" %>
<%@ page import="com.study.DAO.CommentDAO" %>
<%@ page import="com.study.VO.CommentVO" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: gonghuibae
  Date: 2024/01/24
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>

<%
    BoardDAO boardDAO = new BoardDAO();
    Long boardId = Long.parseLong(request.getParameter("boardId"));
    boardDAO.updateViewCount(boardId);
    BoardVO boardVO = boardDAO.getBoard(boardId);

    CategoryDAO categoryDAO = new CategoryDAO();
    String categoryName = categoryDAO.getCategory(boardVO.getCategoryId());

    CommentDAO commentDAO = new CommentDAO();
    List<CommentVO> commentVOs = commentDAO.getComments(boardId);

    categoryDAO.releaseResources();
    boardDAO.releaseResources();
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>게시판 - 보기</title>
</head>
<body>
    <h1><%="게시판 - 보기"%></h1>
    <div>작성자: <%=boardVO.getName()%></div>
    <div>조회수: <%=boardVO.getViewCount()%></div>
    <div>카테고리: <%=categoryName%></div>
    <div>제목: <%=boardVO.getTitle()%></div>
    <div>내용: <%=boardVO.getContent()%></div>
    <div>작성일: <%=boardVO.getCreatedData()%></div>
    <div>수정일: <%=boardVO.getUpdateData()%></div>
    <p></p>
    <p></p>
<% for (CommentVO commentVO: commentVOs) { %>
    <div><%=commentVO.getCreatedDate()%></div>
    <div><%=commentVO.getContent()%></div>
    <p></p>
<% } %>



    <form action="comment_proc.jsp" method="post">
        <input type="hidden" name="boardId" value="<%=boardId%>"/>
        <textarea cols="20" name="content" rows="5"></textarea>
        <input type="submit" value="등록" />
    </form>


    <input type="button" value="목록" onclick="location.href='list.jsp'"/>
    <input type="button" value="수정" onclick="location.href='update.jsp?boardId=<%=boardVO.getBoardId()%>'"/>
    <input type="button" value="삭제" onclick="location.href='delete.jsp?boardId=<%=boardVO.getBoardId()%>'"/>
</body>
</html>
