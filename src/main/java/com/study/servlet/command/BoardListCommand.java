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

public class BoardListCommand implements Command{
    /**
     * DB의 board들을 boardVO형태로 담아 보내는 메서드.
     * @param request page: 페이지네이션에 사용되는 현재 페이지 값.
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;

        // 값이 넘어오면 null이 아니고, 값을 선택한 적이 없으면 null → 여기 정해둔 대로 page값이 1.
        if(request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        BoardDAO boardDAO = new BoardDAO();
        List<BoardVO> boardVOs = boardDAO.getBoardList(page);
        CategoryDAO categoryDAO = new CategoryDAO();
        List<CategoryVO> categoryList = categoryDAO.getCategoryList();

        int listCount = boardDAO.getListCount();
        int maxPage = (listCount % 10) != 0 ? (listCount / 10) + 1 : (listCount / 10);
        int startPage = ((int) ((double) page / 10 + 0.9) - 1) * 10 + 1;
        int endPage = startPage + 10 - 1;

        if (endPage > maxPage) {
            endPage = maxPage;
        }
        request.setAttribute("maxPage", maxPage);
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("listCount", listCount);
        request.setAttribute("page", page);

        request.setAttribute("boardVOs", boardVOs);
        request.setAttribute("categoryList", categoryList);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/list.jsp");
        requestDispatcher.forward(request, response);
    }
}
