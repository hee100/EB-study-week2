package com.study.DAO;

import com.study.VO.FileVO;
import com.study.connection.ConnectionJdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public FileDAO() {
        conn = ConnectionJdbc.getConnection();
    }

    /**
     * 저장되는 파일과 관련된 데이터를 DB에 저장하는 메서드.
     * @param fileVO
     */
    public void insertFile(FileVO fileVO) {
        try {
            String sql = "INSERT INTO file(board_id, real_name, save_name, size) " +
                    "VALUES (?, ?, ?, ?)";

            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, fileVO.getBoardId());
            psmt.setString(2, fileVO.getRealName());
            psmt.setString(3, fileVO.getSaveName());
            psmt.setInt(4, fileVO.getSize());
            psmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionJdbc.releaseResources();
        }
    }

    /**
     * 게시글에 맞는 파일 리스트 반환하는 메서드.
     * @param boardId
     * @return FileVO 객체 리스트 반환.
     */
    public List<FileVO> getFileList(Long boardId) {
        List<FileVO> fileVOs = new ArrayList<>();
        try {
            String sql = "SELECT file_id, save_name, real_name FROM file " +
                    "WHERE board_id = ?";
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, boardId);
            rs = psmt.executeQuery();

            while (rs.next()) {
                FileVO fileVO = FileVO.builder()
                        .fileId(rs.getLong("file_id"))
                        .realName(rs.getString("real_name"))
                        .saveName(rs.getString("save_name"))
                        .build();
                fileVOs.add(fileVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionJdbc.releaseResources();
        }
        return fileVOs;
    }

    /**
     * 서버의 file과 DB의 file을 삭제하는 메서드.
     * @param fileId 삭제할 파일의 id
     */
    public void deleteFile(Long fileId) {
        try {
            // TODO : util 클랙스로 넘기기
            String fileDeleteSQL = "SELECT save_name FROM file" +
                    " WHERE file_id = ?";
            psmt = conn.prepareStatement(fileDeleteSQL);
            psmt.setLong(1, fileId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                String saveDirectory = "/Users/gonghuibae/EB_study/files/";
                String saveName = rs.getString("save_name");
                File file = new File(saveDirectory + saveName);
                if (file.exists()) {
                    file.delete();
                }
            }

            String dbDeleteSQL = "DELETE FROM file" +
                    " WHERE file_id = ?";
            psmt = conn.prepareStatement(dbDeleteSQL);
            psmt.setLong(1, fileId);
            psmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionJdbc.releaseResources();
        }
    }

}
