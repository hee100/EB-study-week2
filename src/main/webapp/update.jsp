<%@ page import="com.study.DAO.BoardDAO" %>
<%@ page import="com.study.DAO.CategoryDAO" %>
<%@ page import="com.study.VO.BoardVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.VO.CategoryVO" %><%--
  Created by IntelliJ IDEA.
  User: gonghuibae
  Date: 2024/01/25
  Time: 3:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
  BoardDAO boardDAO = new BoardDAO();
  Long boardId = Long.parseLong(request.getParameter("boardId"));
  BoardVO boardVO = boardDAO.getBoard(boardId);

  CategoryDAO categoryDAO = new CategoryDAO();
  List<CategoryVO> categoryList = categoryDAO.getCategoryList();

  categoryDAO.releaseResources();
  boardDAO.releaseResources();
%>

<html>
<head>
  <meta charset="UTF-8">
  <title>글 상세</title>
</head>
<body>
<h1><%= "게시판 - 수정" %></h1>
<form action="update_proc.jsp" method="post">
  <input type="hidden" name="boardId" value="<%=boardVO.getBoardId() %>"/>
  <table border="1">
    <tr>
      <th>카테고리</th>
      <th><%=categoryList.get(boardVO.getCategoryId()-1).getCategoryName()%></th>
    </tr>
    <tr>
      <th>작성자</th>
      <th><input type="text" name="name" value="<%=boardVO.getName()%>" required/></th>
    </tr>
    <tr>
      <th>비밀번호</th>
      <th><input type="password" name="pwd" placeholder="비밀번호" required /></th>
    </tr>
    <tr>
      <th>제목</th>
      <th><input type="text" name="title" value="<%=boardVO.getTitle()%>" required /></th>
    </tr>
    <tr>
      <th>내용</th>
      <th><textarea cols="20" name="content" rows="10"><%=boardVO.getContent()%></textarea></th>
    </tr>
  </table>
  <input type="button" value="취소" onclick="location.href='list.jsp'"/>
  <input type="submit" value="저장" />
</form>

</body>
</html>
