package com.study.servlet.command;

import com.study.DAO.BoardDAO;
import com.study.DAO.FileDAO;
import com.study.VO.BoardVO;
import com.study.VO.FileVO;
import com.study.connection.ConnectionJdbc;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.List;
import java.util.UUID;


public class BoardInsertCommand implements Command{
    /**
     * 게시글 작성해서 DB에 업로드하는 메서드.
     * @param request 클라이언트에서 입력된 board 데이터.
     * @param response
     * @throws SecurityException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        Long boardId = boardDAO.insertBoard(boardVO);

        // TODO : utils 클래스 안에 넣을것. (리팩토링)
        List<Part> fileParts = (List<Part>) request.getParts();
        OutputStream outputStream = null;
        InputStream fileContent = null;
        try {
            for (Part filePart : fileParts) {
                if (!filePart.getName().equals("file")) {
                    continue;
                }
                if (filePart.getSubmittedFileName().equals("")) {
                    continue;
                }

                String saveDirectory = "/Users/gonghuibae/EB_study/files";
                File dir = new File(saveDirectory);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                fileContent = filePart.getInputStream();
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + filePart.getSubmittedFileName();

                File file = new File(saveDirectory, fileName);
                outputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileContent.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                }

                FileDAO fileDAO = new FileDAO();
                FileVO fileVO = FileVO.builder()
                        .boardId(boardId)
                        .realName(filePart.getSubmittedFileName())
                        .saveName(fileName)
                        .size((int) filePart.getSize())
                        .build();

                fileDAO.insertFile(fileVO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // TODO: for문 끝나고 닫지말것. 파일 한개 완료시 바로 받을것. 요청이 한번에 많이오면 서버가 죽는다.
            if (outputStream != null) {
                outputStream.flush(); //
                outputStream.close();
            }
            if (fileContent != null) {
                fileContent.close();
            }
            ConnectionJdbc.releaseResources();
            response.sendRedirect("board?commandStr=boardList");
        }
    }
}
