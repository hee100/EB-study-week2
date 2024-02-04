package com.study.servlet.command;

import com.study.DAO.BoardDAO;
import com.study.DAO.CategoryDAO;
import com.study.DAO.FileDAO;
import com.study.VO.BoardVO;
import com.study.VO.CategoryVO;
import com.study.VO.FileVO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class BoardUpdateLoadCommand implements Command{
    /**
     * 작성된 board를 수정하기전에, 수정할려는 board의 데이터를 가져오는 메서드.
     * @param request boardId: 수정을 원하는 게시글의 boardId
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long boardId = Long.parseLong(request.getParameter("boardId"));

        BoardDAO boardDAO = new BoardDAO();
        BoardVO boardVO = boardDAO.getBoard(boardId);

        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryVO> categoryList = categoryDAO.getCategoryList();

        FileDAO fileDAO = new FileDAO();
        List<FileVO> fileVOs = fileDAO.getFileList(boardId);

        request.setAttribute("boardVO", boardVO);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("fileVOs", fileVOs);

//        response.sendRedirect("board?commandStr=boardList");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/update.jsp");
        requestDispatcher.forward(request, response);
    }
}
