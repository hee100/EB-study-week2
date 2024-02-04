<%@ page import="com.study.VO.BoardVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.study.VO.CategoryVO" %>
<%@ page import="com.study.VO.FileVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
  BoardVO boardVO = (BoardVO) request.getAttribute("boardVO");
  List<CategoryVO> categoryList = (List<CategoryVO>) request.getAttribute("categoryList");
  List<FileVO> fileVOs = (List<FileVO>) request.getAttribute("fileVOs");
%>

<html>
<head>
  <meta charset="UTF-8">
  <title>글 상세</title>
  <script src="jquery.js"></script>
</head>
<body>
<h1><%= "게시판 - 수정" %></h1>
<form action="board" method="post" enctype="multipart/form-data">
  <input type="hidden" name="commandStr" value="boardUpdate">
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
  <% for (FileVO fileVO: fileVOs) { %>
    <tr class="deleted-file">
    <th>파일 첨부 : </th>
      <th class="file-link">
        <a href="board?commandStr=download&realName=<%=fileVO.getRealName()%>&saveName=<%=fileVO.getSaveName()%>"><%=fileVO.getRealName()%></a>
      </th>
      <th>
        <button class="delete-file" value="<%=fileVO.getFileId()%>">X</button>
        <input type="hidden" name="fileId" value="<%=fileVO.getFileId()%>">
      </th>
<%--      <th><button class="delete-file" value=<%=fileVO.getFileId()%>>X</button></th>--%>
    </tr>
  <% } %>

    <% for (int i = 0; i < 3 - fileVOs.size(); i++) { %>
    <tr>
      <th><label for="file2">파일 첨부 : </label></th>
      <th><input type="file" name="file" id="file2" ></th>
    </tr>
    <% } %>
  </table>
  <input type="button" value="취소" onclick="location.href='board?commandStr=boardList'"/>
  <input type="submit" value="저장" />
</form>

<script>
  $(document).ready(function() {
    $('table').on('click', '.delete-file', function() {
      let row = $(this).closest('tr');
      let fileId = row.find('[name="fileId"]').val();
      row.find('.file-link').hide();

      if (row.hasClass('deleted-file')) {
        row.find('.file-link').hide();

        // let inputForm = '<input type="file" name="file" id="file2">';
        let inputForm = '<input type="hidden" name="deleteFile" value=' + fileId + '>';
        row.find('.file-link').after(inputForm);
      } else {
        row.find('.file-link').hide();
      }
    });
  });
</script>
</body>
</html>
