package com.study.servlet.command;

import com.study.DAO.CommentDAO;
import com.study.VO.CommentVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CommentInsertCommand implements Command {
    /**
     * 댓글읍 입력하는 메서드.
     * @param request boardId: 댓글이 입력되는 게시글의 boardId, content: 댓글 내용
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long boardId = Long.parseLong(request.getParameter("boardId"));
        String content = request.getParameter("content");
        CommentDAO commentDAO = new CommentDAO();
        CommentVO commentVO = CommentVO.builder()
                .boardId(boardId)
                .content(content)
                .build();

        commentDAO.insertComment(commentVO);
        response.sendRedirect("board?commandStr=boardView&boardId=" + String.valueOf(boardId));
    }
}
