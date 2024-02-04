package com.study.servlet.command;

import com.study.DAO.BoardDAO;
import com.study.VO.BoardVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BoardWriteCommand implements Command{
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
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
        response.sendRedirect("list.jsp");

    }
}
