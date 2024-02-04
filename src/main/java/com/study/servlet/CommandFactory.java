package com.study.servlet;

import com.study.servlet.command.*;

public class CommandFactory {
    private static CommandFactory instance = new CommandFactory();

    public static CommandFactory getInstance() {
        return instance;
    }

    /**
     * commandStr에 따른 command 객체를 반환하는 메서드.
     * @param commandStr
     * @return command 객체
     */
    public Command getCommand(String commandStr) {
        Command command = null;
        System.out.println("commandFactory = " + commandStr);
        if (commandStr.equals("boardList")) {
            command = new BoardListCommand();
        } else if (commandStr.equals("boardInsert")) {
            command = new BoardInsertCommand();
        } else if (commandStr.equals("boardView")) {
            command = new BoardViewCommand();
        } else if (commandStr.equals("boardUpdateLoad")) {
            command = new BoardUpdateLoadCommand();
        } else if (commandStr.equals("boardUpdate")) {
            command = new BoardUpdateCommand();
        } else if (commandStr.equals("boardDelete")) {
            command = new BoardDeleteCommand();
        } else if (commandStr.equals("commentInsert")) {
            command = new CommentInsertCommand();
        } else if (commandStr.equals("download")) {
            command = new DownloadCommand();
        }
        // TODO : else로 예외처리
        return command;
    }
}
