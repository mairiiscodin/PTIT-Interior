package DAO;

import Controller.DBConnect;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // Phương thức kiểm tra đăng nhập
    public User checkLogin(String fullName, String password) {
        String sql = "SELECT * FROM users WHERE full_name=? AND password=?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fullName);
            ps.setString(2, password);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setFullName(rs.getString("full_name"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setPhone(rs.getString("phone"));
                    user.setAddress(rs.getString("address"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu sai thông tin hoặc có lỗi
    }

    // Phương thức đăng ký
    public boolean register(String fullName, String email, String password, String phone, String address) {
        String sql = "INSERT INTO users(full_name,email,password,phone,address) VALUES(?,?,?,?,?)";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, phone);
            ps.setString(5, address);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu insert thành công
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}