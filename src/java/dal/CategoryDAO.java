package dal;

import Model.Category;
import Controller.DBConnect;
import java.sql.*;
import java.util.*;

public class CategoryDAO {

    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();

        String sql = "SELECT * FROM categories";

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}