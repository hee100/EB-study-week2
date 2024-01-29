package com.study.DAO;

import com.study.VO.CategoryVO;
import com.study.connection.ConnectionJdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private Connection conn;
    private PreparedStatement psmt;
    private ResultSet rs;

    public CategoryDAO() {
        conn = new ConnectionJdbc().getConnection();
    }

    /**
     * 한개의 카테고리 이름을 반환.
     * @param categoryId
     * @return categoryId에 매칭되는 categoryName을 반환
     * <p>실패시 공백 반환</p>
     */
    public String getCategory(int categoryId) {
        String categoryName = "";
        try {
            String SQL = "SELECT category_name FROM category WHERE category_id = ?";

            psmt = conn.prepareStatement(SQL);
            psmt.setInt(1, categoryId);
            rs = psmt.executeQuery();
            if (rs.next()) {
                categoryName = rs.getString("category_name");
            }
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }

        return categoryName;
    }


    /**
     * 모든 카테고리 이름을 반환.
     * @return cateogry table안의 모든 내용을 ArrayList<String> 으로 반환함.
     * @See category table안의 컬럼이 name하나라 VO 객체를 쓰지않고 String 사용.
     */
    public List<CategoryVO> getCategoryList () {
        List<CategoryVO> categoryList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM category";

            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            while (rs.next()) {
                String categoryName = rs.getString("category_name");
                CategoryVO categoryVO = CategoryVO.builder()
                        .categoryName(categoryName)
                        .build();
                categoryList.add(categoryVO);
            }
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        }

        return categoryList;
    }

    /**
     * CategoryDAO 클래스 내부에서 연결되는 모든 자원을 해제함.
     * @See categoryDAO 내부의 메서드 마지막 구문마다 releaseReources를 추가할지,
     * <p>.jsp내부 에서 사용할지 생각하다가 자원 해제 시기를 직접 정하는게 좋을것 같아 .jsp 내부에서 사용하기로 결정.</p>
     */
    public void releaseResources() {
        try {
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
