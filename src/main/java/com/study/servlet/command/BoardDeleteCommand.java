package com.study.servlet.command;

import com.study.DAO.BoardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BoardDeleteCommand implements Command{
    /**
     * board 한개를 삭제하는 메서드.
     * @param request boardId : 삭제를 원하는 게시글의 boardId, pwd: 클라이언트에서 입력된 패스워드 String
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long boardId = Long.parseLong(request.getParameter("boardId"));
        BoardDAO boardDAO = new BoardDAO();
        String DBpassWord = boardDAO.getPassWord(boardId);
        String boardPassWord = request.getParameter("pwd");
        if (!boardPassWord.equals(DBpassWord)){
            response.sendRedirect("delete.jsp?boardId=" + String.valueOf(boardId));
            // 나중에 alter로 변경
            return;
        }

        boardDAO.deleteBoard(boardId);
        response.sendRedirect("board?commandStr=boardList");
    }
}
