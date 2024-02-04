package com.study.servlet.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

public class DownloadCommand implements Command {
    /**
     * 파일 클릭시 파일이 다운로드 되는 메서드.
     * @param request realName : 원본 파일명, saveName : 암호화된 파일명
     * @param response 파일 다운로드
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String realName = request.getParameter("realName");
        String saveName = request.getParameter("saveName");
        String path = "/Users/gonghuibae/EB_study/files/";

        OutputStream out = response.getOutputStream();
        response.setContentType("text/html; charset=utf-8");
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Content-disposition", "attachment; fileName=" + realName);

        File f = new File(path + saveName);
        FileInputStream in = new FileInputStream(f);
        byte[] buffer = new byte[1024];
        while(true) {
            int count = in.read(buffer);
            if (count == -1) {
                break;
            }
            out.write(buffer,0,count);
        }
        in.close();
        out.close();
    }
}
