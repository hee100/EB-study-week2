<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.study.VO.BoardVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.VO.CategoryVO" %>
<%
    // TODO : getAttribute가 아나리 파라미터 형식으로 받을것. 11페이지 URL을 공유할 경우, 받은사람은 1페이지로 가진다.
    int nowPage = (int) request.getAttribute("page");
    int maxPage = (int) request.getAttribute("maxPage");
    int startPage = (int) request.getAttribute("startPage");
    int endPage = (int) request.getAttribute("endPage");


    List<BoardVO> boardVOs = (List<BoardVO>) request.getAttribute("boardVOs");
    List<CategoryVO> categoryList = (List<CategoryVO>) request.getAttribute("categoryList");
    System.out.println("startPage : " + startPage); // TODO : sout -> log 사용
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
                <a href="board?commandStr=boardView&boardId=<%=boardVO.getBoardId()%>"><%=boardVO.getTitle()%></a>
            </td>
            <td><%=boardVO.getName()%></td>
            <td><%=boardVO.getViewCount()%></td>
            <td><%=boardVO.getCreatedData()%></td>
            <td><%=boardVO.getUpdateData()%></td>
        </tr>
    <%    } %>

        <tr align="center" height="20">
            <td colspan="5">

           <% if(nowPage <= 1) { %>
                <
            <% } else { %>
                <a href="board?commandStr=boardList&page=1"><<</a>
                <a href="board?commandStr=boardList&page=<%= nowPage - 1 %>"><</a>
            <% } %>

            <%
                for (int i = startPage; i <= endPage; i++) {
                    if (i == nowPage) { %>
                        <%=i%>
                    <% } else { %>
                        <a href="board?commandStr=boardList&page=<%= i %>"><%=i%></a>
                    <% } %>
                <% } %>

            <% if(nowPage >= maxPage){ %>
                    >
            <% } else { %>
                <a href="board?commandStr=boardList&page=<%= nowPage + 1 %>">></a>
                <a href="board?commandStr=boardList&page=<%= maxPage %>">>></a>
            <% } %>
            </td>
        </tr>
    </table>

<a href="write.jsp">등록</a>
</body>
</html>