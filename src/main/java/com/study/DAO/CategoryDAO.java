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
        conn = ConnectionJdbc.getConnection();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionJdbc.releaseResources();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionJdbc.releaseResources();
        }

        return categoryList;
    }

}
