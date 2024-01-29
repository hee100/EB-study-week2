package com.study.DAO;
import com.study.VO.BoardVO;
import com.study.connection.ConnectionJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;
    private ResultSet generatedKeys;

    public BoardDAO() {
        conn = new ConnectionJdbc().getConnection();
    }


    /**
     * 입력한 board에 대한 내용을 DB baord 테이블에 저장하는 메서드.
     * @param boardVO DB에 저장되는 boardVO 객체를 받는다.
     * @return psmt.getGeneratedKeys() 의 반환값을 return하며 비정상 구동시 -1을 반환한다.
     * 반환된 값은 file Table에 사용될 예정.
     * @See 유효성 검증 X, 파일첨부 X, 비밀번호 암호화 X
     */
    public Long insertBoard(BoardVO boardVO) {
        try {
            String sql = "INSERT INTO board(name, title, password," +
                    " content, view_count, category_id, created_date)" +
                    " VALUES (?, ?, ?, ?, ?, ?, NOW())";

            psmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            int idx = 1;
            psmt.setString(idx++, boardVO.getName());
            psmt.setString(idx++, boardVO.getTitle());
            psmt.setString(idx++, boardVO.getPassWord());
            psmt.setString(idx++, boardVO.getContent());
            psmt.setLong(idx++, boardVO.getViewCount());
            psmt.setInt(idx++, boardVO.getCategoryId());

            int rowCnts = psmt.executeUpdate();
            if (rowCnts > 0) {
                try {
                    generatedKeys = psmt.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                } catch (SQLException SQLe) {
                    SQLe.printStackTrace();
                }
            }
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }

        return -1L;
    }

    /**
     * DB에서 가져와 게시글 목록 출력에 필요한 BoardVO 리스트를 반환하는 메서드.
     * @return boardVO리스트를 반환한다.(boardVO : 카테고리, 제목, 작성자, 조회수, 등록 일시, 수정 일시)
     * @See 페이지네이션 미구현
     */
    public List<BoardVO> getBoardList() {
        List<BoardVO> boardVOs = new ArrayList<>();
        try {
            String sql = "SELECT board_id, category_id, name," +
                    " title, view_count, created_date," +
                    " update_date FROM board";

            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();

            while (rs.next()) {
                BoardVO boardVO = BoardVO.builder()
                        .boardId(rs.getLong("board_id"))
                        .categoryId(rs.getInt("category_id"))
                        .name(rs.getString("name"))
                        .title(rs.getString("title"))
                        .viewCount(rs.getLong("view_count"))
                        .createdData(rs.getTimestamp("created_date").toLocalDateTime())
                        .updateData(rs.getTimestamp("update_date"))
                        .build();
                boardVOs.add(boardVO);
            }
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }

        return boardVOs;
    }

    /**
     * DB에서 게시글 한개의 대한 내용을 가져와 boardVO 객체로 반환하는 메서드.
     * @param boardId 조회를 원하는 게시글의 boardId.
     * @return boardId로 조회된 boardVO 객체. 실패시 null.
     */
    public BoardVO getBoard(Long boardId) {
        try {
            String sql = "SELECT board_id, category_id, name," +
                    " title, content, view_count," +
                    " created_date, update_date FROM board" +
                    " WHERE board_id= ?";

            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, boardId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                BoardVO boardVO = BoardVO.builder()
                        .boardId(rs.getLong("board_id"))
                        .categoryId(rs.getInt("category_id"))
                        .name(rs.getString("name"))
                        .title(rs.getString("title"))
                        .content(rs.getString("content"))
                        .viewCount(rs.getLong("view_count"))
                        .createdData(rs.getTimestamp("created_date").toLocalDateTime())
                        .updateData(rs.getTimestamp("update_date"))
                        .build();

                return boardVO;
            }
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }

        return null;
    }

    /**
     * 글(board)의 조회수를 1 올려주는 메서드.
     * @param boardId 조회수를 올려야하는 게시글의 boardId
     */
    public void updateViewCount(Long boardId) {
        try {
            String sql = "UPDATE board SET view_count = view_count + 1" +
                    " WHERE board_id = ?";

            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, boardId);
            psmt.executeUpdate();
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }
    }

    /**
     * 글(board)의 내용을 수정하는 메서드.
     * @param boardVO
     * @See 유효성 검증 X, 파일첨부 X
     */
    public void updateBoard(BoardVO boardVO) {
        String sql = "UPDATE board SET title=?, " +
                "content=?, name=?, update_date=NOW()" +
                " WHERE board_id=?";
        try {
            psmt = conn.prepareStatement(sql);
            int idx = 1;
            psmt.setString(idx++, boardVO.getTitle());
            psmt.setString(idx++, boardVO.getContent());
            psmt.setString(idx++, boardVO.getName());
            psmt.setLong(idx++, boardVO.getBoardId());
            psmt.executeUpdate();
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }

    }

    /**
     * 글(board)을 삭제하는 메서드.
     * @param boardId 삭제를 원하는 게시판의 board_id
     * @See 비밀번호 암호화, 복호화 X /
     */
    public void deleteBoard(Long boardId) {
        String sql = "DELETE FROM board " +
                "WHERE board_id = ?";
        try {
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, boardId);
            psmt.execute();
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }
    }


    /**
     * 해당 글(board)의 패스워드를 반환하는 메서드.
     * @param boardId 원하는 패스워드가 있는 row의 board_id
     * @return 성공시 패스워드 반환. 실패시 빈 공백 반환.
     * @See 암호화,복호화 X, 보안 X
     */
    public String getPassWord(Long boardId) {
        String sql = "SELECT password FROM board WHERE board_id = ?";
        String passWord = "";

        try {
            psmt = conn.prepareStatement(sql);
            psmt.setLong(1, boardId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                passWord = rs.getString("password");
                return passWord;
            }

        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }
        return passWord;
    }



    /**
     * BoardDAO 클래스 내부에서 연결되는 모든 자원을 해제함.
     */
    public void releaseResources() {
        try {
            if (generatedKeys != null) {
                generatedKeys.close();
            }
            if (rs != null) {
                rs.close();
            }
            if (psmt != null) {
                psmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }
    }


}
