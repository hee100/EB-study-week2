package com.study.servlet.command;

import com.study.DAO.BoardDAO;
import com.study.DAO.CategoryDAO;
import com.study.DAO.CommentDAO;
import com.study.DAO.FileDAO;
import com.study.VO.BoardVO;
import com.study.VO.CommentVO;
import com.study.VO.FileVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class BoardViewCommand implements Command{
    /**
     * board 한개의 데이터를 가져오는 메서드.
     * @param request boardId: 보기를 원하는 게시글의 boardId
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardDAO boardDAO = new BoardDAO();
        Long boardId = Long.parseLong(request.getParameter("boardId"));
        boardDAO.updateViewCount(boardId);
        BoardVO boardVO = boardDAO.getBoard(boardId);

        CategoryDAO categoryDAO = new CategoryDAO();
        String categoryName = categoryDAO.getCategory(boardVO.getCategoryId());

        CommentDAO commentDAO = new CommentDAO();
        List<CommentVO> commentVOs = commentDAO.getComments(boardId);

        FileDAO fileDAO = new FileDAO();
        List<FileVO> fileVOs = fileDAO.getFileList(boardId);

        System.out.println("boardView");
        request.setAttribute("boardVO", boardVO);
        request.setAttribute("categoryName", categoryName);
        request.setAttribute("commentVOs", commentVOs);
        request.setAttribute("boardId", boardId);
        request.setAttribute("fileVOs", fileVOs);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/view.jsp");
        requestDispatcher.forward(request, response);
    }
}
