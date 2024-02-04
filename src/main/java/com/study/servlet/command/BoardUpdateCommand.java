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

public class BoardUpdateCommand implements Command {
    /**
     * board 한개의 내용을 DB에 업데이트 시키는 메서드.
     * @param request boardId: 수정을 원하는 게시글의 boardId, pwd: 클라이언트에서 입력된 패스워드 String
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long boardId = Long.parseLong(request.getParameter("boardId"));
        BoardDAO boardDAO = new BoardDAO();
        String boardPassWord = request.getParameter("pwd");
        String DBpassWord = boardDAO.getPassWord(boardId);
        if (!boardPassWord.equals(DBpassWord)){
            response.sendRedirect("board?commandStr=boardUpdateLoad&boardId=" + String.valueOf(boardId));
            return;
        }

        String[] deleteFileIds = request.getParameterValues("deleteFile");
        if (deleteFileIds != null) {
            for (String deleteFileId : deleteFileIds) {
                Long fileId = Long.parseLong(deleteFileId);
                FileDAO fileDAO = new FileDAO();
                fileDAO.deleteFile(fileId);
            }
        }

        String name = request.getParameter("name");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        BoardVO boardVO = BoardVO.builder()
                .boardId(boardId)
                .name(name)
                .title(title)
                .content(content)
                .build();

        boardDAO.updateBoard(boardVO);

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
                    System.out.println(" -- ");
                    continue;
                }
                System.out.println(filePart.getSubmittedFileName());
                fileContent = filePart.getInputStream();
                UUID uuid = UUID.randomUUID();
                String fileName = uuid + "_" + filePart.getSubmittedFileName();
                String saveDirectory = "/Users/gonghuibae/EB_study/files";
                File dir = new File(saveDirectory);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

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
            if (outputStream != null) {
                outputStream.flush();
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
