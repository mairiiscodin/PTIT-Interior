/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

/**
 *
 * @author Laptop
 */

import Controller.DBConnect;
import java.sql.*;


public class CartDAO {
    // Lấy CartID của user, nếu chưa có thì tạo mới
    public int getCartIdByUserId(int userId) {
        int cartId = -1;
        try {
            Connection conn = DBConnect.getConnection();
            // Kiểm tra xem user đã có giỏ hàng chưa
            String selectSql = "SELECT id FROM Cart WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(selectSql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                cartId = rs.getInt("id");
            } else {
                // Nếu chưa có, tạo mới một giỏ hàng cho user
                String insertSql = "INSERT INTO Cart (user_id) VALUES (?)";
                PreparedStatement psInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                psInsert.setInt(1, userId);
                psInsert.executeUpdate();
                ResultSet rsKeys = psInsert.getGeneratedKeys();
                if (rsKeys.next()) {
                    cartId = rsKeys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartId;
    }

    // Thêm sản phẩm vào giỏ hoặc cập nhật số lượng nếu đã tồn tại
    public void addToCart(int cartId, int productId, int quantity) {
        try {
            Connection conn = DBConnect.getConnection();
            // Sử dụng ON DUPLICATE KEY UPDATE của MySQL để xử lý nhanh
            String sql = "INSERT INTO CartItem (cart_id, product_id, quantity) VALUES (?, ?, ?) "
                       + "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
