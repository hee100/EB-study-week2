package com.study.servlet.command;

import com.study.DAO.BoardDAO;
import com.study.DAO.CategoryDAO;
import com.study.VO.BoardVO;
import com.study.VO.CategoryVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class BoardUpdateCommand implements Command{
    /**
     * 작성된 board를 수정하는 메서드
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardDAO boardDAO = new BoardDAO();
        Long boardId = Long.parseLong(request.getParameter("boardId"));
        BoardVO boardVO = boardDAO.getBoard(boardId);

        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryVO> categoryList = categoryDAO.getCategoryList();

        request.setAttribute("boardVO", boardVO);
        request.setAttribute("categoryList", categoryList);

//        response.sendRedirect("board?commandStr=boardList");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update.jsp");
        requestDispatcher.forward(request, response);
    }
}
