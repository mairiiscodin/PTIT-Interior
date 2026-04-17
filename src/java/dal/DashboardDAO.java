package dal;

import Controller.DBConnect;
import java.sql.*;
import Model.*;
import java.util.*;

public class DashboardDAO {

	// 1. Đếm tổng số lượng (Sản phẩm, Đơn hàng, Người dùng)
	public static int getCount(String tableName) {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM " + tableName;
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// Đếm đơn chờ xác nhận
	public static int getPendingOrders() {
		int count = 0;
		String sql = "SELECT COUNT(*) FROM orders WHERE status = 'Chờ xác nhận'";
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// 3. Tính tổng tồn kho
	public static int getTotalStock() {
		int total = 0;
		String sql = "SELECT SUM(stock) FROM products";
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	// 4. Tính tổng doanh thu (đơn hàng đã hoàn thành)
	public static double getTotalRevenue() {
		double total = 0;
		String sql = "SELECT SUM(total_amount) FROM orders WHERE status = 'Hoàn thành'";
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				total = rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	// 5. Lấy Top 5 sản phẩm bán chạy nhất
	public static List<Product> getTopBestSellers(int limit) {
		List<Product> list = new ArrayList<>();
		String sql = "SELECT p.*, SUM(oi.quantity) AS total_sold "
				+ "FROM order_items oi "
				+ "JOIN products p ON oi.product_id = p.id "
				+ "GROUP BY p.id "
				+ "ORDER BY total_sold DESC LIMIT ?";
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setPrice(rs.getDouble("price"));
				// Bạn có thể dùng field 'stock' của Product để tạm lưu 'total_sold' hiển thị ra View
				p.setStock(rs.getInt("total_sold"));
                                p.setUrlImage(rs.getString("url_image"));
				list.add(p);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 5. Lấy Top 5 khách hàng chi tiêu nhiều nhất (Khách hàng thân thiết)
	public static List<User> getTopCustomers(int limit) {
		List<User> list = new ArrayList<>();
		String sql = "SELECT u.*, SUM(o.total_amount) AS total_spent "
				+ "FROM orders o "
				+ "JOIN users u ON o.user_id = u.id "
				+ "WHERE o.status = 'Hoàn thành' "
				+ "GROUP BY u.id "
				+ "ORDER BY total_spent DESC LIMIT ?";
		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, limit);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setFullName(rs.getString("full_name"));
				u.setEmail(rs.getString("email"));
				// Tạm thời dùng field không quan trọng để lưu total_spent hiển thị lên JSP
				u.setAddress(rs.getString("total_spent"));
				list.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
