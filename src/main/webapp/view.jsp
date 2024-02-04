<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.study.VO.BoardVO" %>
<%@ page import="com.study.VO.CommentVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.VO.FileVO" %>

<%
    Long boardId = (Long) request.getAttribute("boardId");
    BoardVO boardVO = (BoardVO) request.getAttribute("boardVO");
    String categoryName = (String) request.getAttribute("categoryName");
    List<CommentVO> commentVOs = (List<CommentVO>) request.getAttribute("commentVOs");
    List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("fileVOs");
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
    <div>파일 첨부 : </div>
<% for (FileVO fileVO: fileVOs) { %>
    <p>
        <a href="board?commandStr=download&realName=<%=fileVO.getRealName()%>&saveName=<%=fileVO.getSaveName()%>"><%=fileVO.getRealName()%></a>
    </p>
<% } %>

    <br>
<% for (CommentVO commentVO: commentVOs) { %>
    <div><%=commentVO.getCreatedDate()%></div>
    <div><%=commentVO.getContent()%></div>
    <p></p>
<% } %>

    <form action="board" method="post">
        <input type="hidden" name="commandStr" value="commentInsert"/>
        <input type="hidden" name="boardId" value="<%=boardId%>"/>
        <textarea cols="20" name="content" rows="5"></textarea>
        <input type="submit" value="등록" />
    </form>


    <input type="button" value="목록" onclick="location.href='board?commandStr=boardList'"/> // TODO : 페이지 복귀
    <input type="button" value="수정" onclick="location.href='board?commandStr=boardUpdateLoad&boardId=<%=boardVO.getBoardId()%>'"/>
    <input type="button" value="삭제" onclick="location.href='delete.jsp?boardId=<%=boardVO.getBoardId()%>'"/>

</body>



</html>
