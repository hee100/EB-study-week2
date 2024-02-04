package com.study.DAO;

import com.study.VO.CommentVO;
import com.study.connection.ConnectionJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public CommentDAO() {
        conn = ConnectionJdbc.getConnection();
    }

    /**
     * 코멘트 신규 등록
     * @param commentVO
     */
    public void insertComment(CommentVO commentVO) {

        try {
            String sql = "INSERT INTO comment(board_id, content, created_date) VALUES (?, ?, NOW())";

            psmt = conn.prepareStatement(sql);
            int idx = 1;
            psmt.setLong(idx++, commentVO.getBoardId());
            psmt.setString(idx++, commentVO.getContent());
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionJdbc.releaseResources();
        }
    }

    /**
     * DB에서 댓글 리스트를 반환하는 메서드.
     * @param boardId 댓글리스트가 있는 boardId.
     * @return name, content, created_date 정보가 담겨있는 CommentVO 리스트를 반환한다.
     * 실패시 null 반환.
     */
    public List<CommentVO> getComments(Long boardId) {
        List<CommentVO> commentVOs = new ArrayList<>();

        try {
            String sql = "SELECT content, created_date FROM comment WHERE board_id = ?";

            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, boardId);
            rs = psmt.executeQuery();
            while (rs.next()) {
                CommentVO commentVO = CommentVO.builder()
                        .content(rs.getString("content"))
                        .createdDate(rs.getTimestamp("created_date").toLocalDateTime())
                        .build();
                commentVOs.add(commentVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionJdbc.releaseResources();
        }

        return commentVOs;
    }
}
