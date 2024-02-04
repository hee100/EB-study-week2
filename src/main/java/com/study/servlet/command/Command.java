package com.study.servlet.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Command {
    /**
     * Command 패턴 구현을 위해 사용되는 인터페이스
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @See 커맨드 패턴 사용 이유 :
     * <p>
     *     - 객체마다 기능을 빼두어서 유지보수에 용이해진다.
     * </p>
     * <p>
     *     - 큰 기능들을 서블릿 단위로 나누어 볼 수 있다. (ex : boardServlet, memberServlet 등 ...)
     * </p>
     */
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
