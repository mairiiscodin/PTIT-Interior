package dal;

import Controller.DBConnect;
import Model.*;
import java.sql.*;
import java.util.*;

public class OrderDAO {

    public static int createOrder(int userId, double total, String status, String method, String address, List<CartItem> items) {
        Connection conn = null;
        try {
            conn = DBConnect.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            // Bước 1: Insert vào bảng Orders
            String sqlOrder = "INSERT INTO Orders (user_id, total_amount, status, payment_method, shipping_address) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setDouble(2, total);
            ps.setString(3, status);
            if (method.equals("COD")) {
                method = "Thanh toán khi nhận hàng";
            } else {
                method = "Chuyển khoản";
            }
            ps.setString(4, method);
            ps.setString(5, address);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Bước 2: Insert từng sản phẩm vào bảng OrderDetail
            String sqlDetail = "INSERT INTO order_items (order_id, product_id, price, quantity) VALUES (?,?,?,?)";
            String sqlUpdateStock = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
            PreparedStatement psDetail = conn.prepareStatement(sqlDetail);
            PreparedStatement psUpdateStock = conn.prepareStatement(sqlUpdateStock);
            for (CartItem item : items) {
                psDetail.setInt(1, orderId);
                psDetail.setInt(2, item.getProduct().getId());
                psDetail.setInt(4, item.getQuantity());
                psDetail.setDouble(3, item.getProduct().getPrice());
                psDetail.addBatch();

                psUpdateStock.setInt(1, item.getQuantity());
                psUpdateStock.setInt(2, item.getProduct().getId());
                psUpdateStock.setInt(3, item.getQuantity()); // Đảm bảo tồn kho hiện tại đủ để trừ
                int updateCount = psUpdateStock.executeUpdate();

                if (updateCount == 0) {
                    // Nếu không trừ được kho (do có người khác vừa mua hết sạch chẳng hạn)
                    throw new Exception("Sản phẩm " + item.getProduct().getName() + " đã hết hàng!");
                }
            }
            psDetail.executeBatch();

            conn.commit(); // Hoàn tất nếu mọi thứ ổn
            return orderId;
        } catch (Exception e) {
            if (conn != null) try {
                conn.rollback();
            } catch (SQLException ex) {
            }
            e.printStackTrace();
        }
        return -1;
    }
    // Lấy danh sách đơn hàng của người dùng

    public static List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setPaymentMethod(rs.getString("payment_method"));
                o.setShippingAddress(rs.getString("shipping_address"));
                o.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ======================
    // Hàm map ResultSet -> Order
    // ======================
    private static Order mapOrder(ResultSet rs) throws Exception {
        User user = new User();
        user.setId(rs.getInt("u_id"));
        user.setFullName(rs.getString("u_full_name"));
        user.setEmail(rs.getString("u_email"));
        user.setPhone(rs.getString("u_phone"));
        user.setAddress(rs.getString("u_address"));
        user.setRole(rs.getString("u_role"));
        user.setStatus(rs.getInt("u_status"));
        user.setCreatedAt(rs.getTimestamp("u_created_at"));
        user.setUpdatedAt(rs.getTimestamp("u_updated_at"));

        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setUser(user);
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setStatus(rs.getString("status"));
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        order.setUpdatedAt(rs.getTimestamp("updated_at"));

        return order;
    }

    // ======================
    // 1. Lấy tất cả đơn hàng
    // ======================
    public static List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT o.*, "
                + "u.id AS u_id, u.full_name AS u_full_name, u.email AS u_email, "
                + "u.phone AS u_phone, u.address AS u_address, u.role AS u_role, "
                + "u.status AS u_status, u.created_at AS u_created_at, u.updated_at AS u_updated_at "
                + "FROM orders o "
                + "JOIN users u ON o.user_id = u.id "
                + "ORDER BY o.id ASC";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ======================
    // 2. Tìm kiếm + lọc trạng thái
    // ======================
    public static List<Order> searchOrders(String keyword, String status) {
        List<Order> list = new ArrayList<>();

        String sql = "SELECT o.*, "
                + "u.id AS u_id, u.full_name AS u_full_name, u.email AS u_email, "
                + "u.phone AS u_phone, u.address AS u_address, u.role AS u_role, "
                + "u.status AS u_status, u.created_at AS u_created_at, u.updated_at AS u_updated_at "
                + "FROM orders o "
                + "JOIN users u ON o.user_id = u.id "
                + "WHERE 1=1";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND (CAST(o.id AS CHAR) LIKE ? "
                    + "OR u.full_name LIKE ? "
                    + "OR u.phone LIKE ?)";
        }

        if (status != null && !status.trim().isEmpty()) {
            sql += " AND o.status = ?";
        }

        sql += " ORDER BY o.id ASC";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            int paramIndex = 1;

            if (keyword != null && !keyword.trim().isEmpty()) {
                String searchValue = "%" + keyword.trim() + "%";
                ps.setString(paramIndex++, searchValue);
                ps.setString(paramIndex++, searchValue);
                ps.setString(paramIndex++, searchValue);
            }

            if (status != null && !status.trim().isEmpty()) {
                ps.setString(paramIndex++, status.trim());
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapOrder(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ======================
    // 3. Tìm đơn theo ID
    // ======================
    public static Order getOrderById(int id) {
        String sql = "SELECT o.*, "
                + "u.id AS u_id, u.full_name AS u_full_name, u.email AS u_email, "
                + "u.phone AS u_phone, u.address AS u_address, u.role AS u_role, "
                + "u.status AS u_status, u.created_at AS u_created_at, u.updated_at AS u_updated_at "
                + "FROM orders o "
                + "JOIN users u ON o.user_id = u.id "
                + "WHERE o.id = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapOrder(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ======================
    // 4. Cập nhật trạng thái đơn hàng
    // ======================
    public static boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    // ======================
    // 5. Lấy chi tiết đơn hàng
    // ======================
    public List<OrderItem> getOrderDetails(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.url_image, oi.price, oi.quantity "
                + "FROM order_items oi JOIN products p ON oi.product_id = p.id "
                + "WHERE oi.order_id = ?";
        try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem oi = new OrderItem();
                
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setUrlImage(rs.getString("url_image"));

                oi.setProduct(p);
                oi.setPrice(rs.getDouble("price"));
                oi.setQuantity(rs.getInt("quantity"));
                list.add(oi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
