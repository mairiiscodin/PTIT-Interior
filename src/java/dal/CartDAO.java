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
import static Controller.DBConnect.getConnection;
import Model.CartItem;
import Model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


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
    ProductDAO dao = new ProductDAO();

    // 1. Lấy ID giỏ hàng của User, nếu chưa có thì tự động tạo mới
    public int getOrCreateCartId(int userId) {
        String selectSQL = "SELECT id FROM Cart WHERE user_id = ?";
        String insertSQL = "INSERT INTO Cart (user_id) VALUES (?)";

        try (Connection conn = getConnection(); PreparedStatement psSelect = conn.prepareStatement(selectSQL)) {

            psSelect.setInt(1, userId);
            try (ResultSet rs = psSelect.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

            // Nếu chưa có giỏ hàng, tiến hành tạo mới
            try (PreparedStatement psInsert = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                psInsert.setInt(1, userId);
                psInsert.executeUpdate();
                try (ResultSet rsKey = psInsert.getGeneratedKeys()) {
                    if (rsKey.next()) {
                        return rsKey.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 2. Cập nhật hoặc Thêm mới sản phẩm vào giỏ (Xử lý cho AJAX)
    public void updateCartItem(int userId, int productId, int quantity) {
        int cartId = getOrCreateCartId(userId);
        if (cartId == -1) {
            return;
        }

        // Câu lệnh này sẽ thêm mới nếu chưa có, hoặc CỘNG DỒN nếu đã có
        String sql = "INSERT INTO CartItem (cart_id, product_id, quantity) VALUES (?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity); // Giá trị để INSERT mới
            ps.setInt(4, quantity); // Giá trị để CỘNG THÊM khi UPDATE
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. Lấy danh sách sản phẩm trong giỏ kèm thông tin Product
    public List<CartItem> getCartItems(int userId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, p.url_image, p.stock, ci.quantity "
                + "FROM CartItem ci "
                + "JOIN Cart c ON ci.cart_id = c.id "
                + "JOIN Products p ON ci.product_id = p.id "
                + "WHERE c.user_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));
                    p.setUrlImage(rs.getString("url_image"));
                    p.setStock(rs.getInt("stock"));
                    list.add(new CartItem(p, rs.getInt("quantity")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 4. Tính tổng tiền toàn bộ giỏ hàng
    public double calculateTotal(int userId) {
        String sql = "SELECT SUM(p.price * ci.quantity) as total "
                + "FROM CartItem ci "
                + "JOIN Cart c ON ci.cart_id = c.id "
                + "JOIN Products p ON ci.product_id = p.id "
                + "WHERE c.user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteCartItem(int userId, int productId) {
        int cartId = getOrCreateCartId(userId);
        String sql = "DELETE FROM CartItem WHERE cart_id = ? AND product_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setCartItemQuantity(int userId, int productId, int newQty) {
        int cartId = getOrCreateCartId(userId);
        // Câu lệnh này sẽ GHI ĐÈ (SET) số lượng thay vì cộng dồn
        String sql = "UPDATE CartItem SET quantity = ? WHERE cart_id = ? AND product_id = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newQty);
            ps.setInt(2, cartId);
            ps.setInt(3, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void clearCart(int userId) {
    // Bước 1: Lấy cartId của người dùng (giống các hàm khác bạn đã viết)
    int cartId = getOrCreateCartId(userId);
    
    if (cartId != -1) {
        // Bước 2: Xóa tất cả các dòng trong bảng CartItem có cart_id này
        String sql = "DELETE FROM CartItem WHERE cart_id = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, cartId);
            int rowsDeleted = ps.executeUpdate();
            
            System.out.println("Đã dọn dẹp giỏ hàng. Số sản phẩm đã xóa: " + rowsDeleted);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
}
