<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.study.DAO.BoardDAO" %>
<%@ page import="com.study.VO.BoardVO" %>
<%

  int category_id = Integer.parseInt(request.getParameter("category"));
  String name = request.getParameter("name");
  String password = request.getParameter("pwd");
  String title = request.getParameter("title");
  String content = request.getParameter("content");

  BoardVO boardVO = BoardVO.builder()
          .categoryId(category_id)
          .name(name)
          .passWord(password)
          .title(title)
          .content(content)
          .viewCount(0L)
          .build();

  BoardDAO boardDAO = new BoardDAO();
  boardDAO.insertBoard(boardVO);
  boardDAO.releaseResources();
  response.sendRedirect("list.jsp");
%>