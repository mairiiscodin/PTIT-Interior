/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import Model.Product;
import Controller.DBConnect;
import java.sql.*;
import java.util.*;
/**
 *
 * @author Laptop
 */
public class ProductDAO {
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products";

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("url_image"),
                        rs.getInt("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public List<Product> filterProducts(String keyword, String sort, String category) {
        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE 1=1";

        if (keyword != null && !keyword.isEmpty()) {
            sql += " AND name LIKE ?";
        }

        if (category != null && !category.isEmpty()) {
            sql += " AND category_id = ?";
        }

        if ("asc".equals(sort)) {
            sql += " ORDER BY price ASC";
        } else if ("desc".equals(sort)) {
            sql += " ORDER BY price DESC";
        }

        try {
            Connection conn = DBConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            int index = 1;

            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(index++, "%" + keyword + "%");
            }

            if (category != null && !category.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(category));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id"),
                        rs.getInt("category_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("url_image"),
                        rs.getInt("status"),
                        rs.getTimestamp("created_at"),
                        rs.getTimestamp("updated_at")
                );

                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
