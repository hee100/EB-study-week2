<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.study.DAO.BoardDAO" %>
<%@ page import="com.study.VO.BoardVO" %>
<%@ page import="com.study.DAO.CategoryDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.VO.CategoryVO" %>
<%
    BoardDAO boardDAO = new BoardDAO();
    List<BoardVO> boardVOs = boardDAO.getBoardList();

    // List 사용
    CategoryDAO categoryDAO = new CategoryDAO();
    List<CategoryVO> categoryList = categoryDAO.getCategoryList();

    boardDAO.releaseResources();
    categoryDAO.releaseResources();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>자유 게시판 - 목록</title>
</head>
<body>

    <h1><%= "자유 게시판 - 목록" %></h1>
    <br/>
    <table border="1">
        <tr>
            <td>카테고리</td>
            <td>제목</td>
            <td>작성자</td>
            <td>조회수</td>
            <td>등록 일시</td>
            <td>수정 일시</td>
        </tr>

    <% for (BoardVO boardVO : boardVOs) { %>
        <tr>
            <td><%=categoryList.get(boardVO.getCategoryId()-1).getCategoryName()%></td>
            <td>
                <a href="view.jsp?boardId=<%=boardVO.getBoardId()%>"><%=boardVO.getTitle()%></a>
            </td>
            <td><%=boardVO.getName()%></td>
            <td><%=boardVO.getViewCount()%></td>
            <td><%=boardVO.getCreatedData()%></td>
            <td><%=boardVO.getUpdateData()%></td>
        </tr>
    <%    } %>
    </table>
<a href="write.jsp">등록</a>
</body>
</html>