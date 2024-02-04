package com.study.servlet;

import com.study.servlet.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * board와 관련된 커맨드들을 실행시키는 클래스.
 */
@WebServlet("/board")
@MultipartConfig
public class BoardServlet extends HttpServlet {
    /**
     * uri 받아서 실행시키는 get 메서드.
     * <p>
     *     파라미터로 commandStr을 받은후 commandStr에 따른 Command객체를 실행시킨다.
     * </p>
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String commandStr = request.getParameter("commandStr");
        System.out.println("commandStr : " + commandStr);
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(commandStr);

        if (command != null) {
            command.execute(request, response);
        }
    }

    /**
     * uri 받아서 실행시키는 post 메서드.
     * <p>
     *     파라미터로 commandStr을 받은후 commandStr에 따른 Command객체를 실행시킨다.
     * </p>
     * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String commandStr = request.getParameter("commandStr");
        System.out.println("commandStr : " + commandStr);
        CommandFactory commandFactory = CommandFactory.getInstance();
        Command command = commandFactory.getCommand(commandStr);

        if (command != null) {
            command.execute(request, response);
        }
    }
}
