<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.study.DAO.CategoryDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.VO.CategoryVO" %>
<!DOCTYPE html>
<html>
<head>
    <title>게시판 - 등록</title>
</head>
<%
    CategoryDAO categoryDAO = new CategoryDAO();
    List<CategoryVO> categoryList =  categoryDAO.getCategoryList();
    categoryDAO.releaseResources();
%>

<body>
    <h1><%= "게시판 - 등록" %></h1>
    <form action="write_proc.jsp" method="post">
        <table border="1">
            <tr>
                <th>카테고리</th>
                <th>
                <select name="category">
                    <option>카테고리 선택</option>
                    <% for (int i = 1; i <= categoryList.size(); i++) { %>
<%--                     i 대신 pk--%>
                        <option value=<%=i%>> <%=categoryList.get(i-1).getCategoryName()%></option>
                     <% } %>
                </select>
                </th>
            </tr>
            <tr>
                <th>작성자</th>
                <th><input type="text" name="name" required/></th>
            </tr>
            <tr>
                 <th>비밀번호</th>
                <th><input type="password" name="pwd" placeholder="비밀번호" required /></th>
                <th><input type="password" name="pwd_check" placeholder="비밀번호 확인" required /></th>
            </tr>
            <tr>
                <th>제목</th>
                <th><input type="text" name="title" required /></th>
            </tr>
            <tr>
                <th>내용</th>
                <th><textarea cols="20" name="content" rows="10"></textarea></th>
            </tr>
        </table>
        <input type="button" value="취소" onclick="location.href='list.jsp'"/>
        <input type="submit" value="저장" />
    </form>
</body>

</html>
