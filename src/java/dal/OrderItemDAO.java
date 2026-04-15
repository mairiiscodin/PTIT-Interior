package dal;

import Controller.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.*;

public class OrderItemDAO {

	public static List<OrderItem> getItemsByOrderId(int orderId) {
		List<OrderItem> list = new ArrayList<>();
		// Sử dụng JOIN để lấy luôn thông tin cơ bản của Product (ví dụ: tên sản phẩm)
		String sql = "SELECT oi.id, oi.order_id, oi.product_id, oi.price, oi.quantity, oi.subtotal, oi.created_at, "
				+ "p.name AS product_name " // Giả sử bảng products có cột 'name'
				+ "FROM order_items oi "
				+ "INNER JOIN products p ON oi.product_id = p.id "
				+ "WHERE oi.order_id = ?";

		try (Connection conn = DBConnect.getConnection(); // Đổi DBContext theo class của bạn
				 PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, orderId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					OrderItem item = new OrderItem();
					item.setId(rs.getInt("id"));
					item.setPrice(rs.getDouble("price"));
					item.setQuantity(rs.getInt("quantity"));
					item.setSubtotal(rs.getDouble("subtotal"));
					item.setCreatedAt(rs.getTimestamp("created_at"));

					// Khởi tạo và map dữ liệu cho Order object
					Order order = new Order();
					order.setId(rs.getInt("order_id"));
					item.setOrder(order); // Set Order vào OrderItem

					// Khởi tạo và map dữ liệu cho Product object
					Product product = new Product();
					product.setId(rs.getInt("product_id"));
					// Lấy tên sản phẩm từ câu lệnh JOIN
					product.setName(rs.getString("product_name"));
					item.setProduct(product); // Set Product vào OrderItem

					list.add(item);
				}
			}
		} catch (SQLException e) {
			System.err.println("Lỗi khi lấy OrderItem theo OrderId: " + e.getMessage());
			e.printStackTrace();
		}

		return list;
	}

	public static OrderItem getItemById(int id) {
		String sql = "SELECT oi.id, oi.order_id, oi.product_id, oi.price, oi.quantity, oi.subtotal, oi.created_at, "
				+ "p.name AS product_name "
				+ "FROM order_items oi "
				+ "INNER JOIN products p ON oi.product_id = p.id "
				+ "WHERE oi.id = ?";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					OrderItem item = new OrderItem();
					item.setId(rs.getInt("id"));
					item.setPrice(rs.getDouble("price"));
					item.setQuantity(rs.getInt("quantity"));
					item.setSubtotal(rs.getDouble("subtotal"));
					item.setCreatedAt(rs.getTimestamp("created_at"));

					Order order = new Order();
					order.setId(rs.getInt("order_id"));
					item.setOrder(order);

					Product product = new Product();
					product.setId(rs.getInt("product_id"));
					product.setName(rs.getString("product_name"));
					item.setProduct(product);

					return item;
				}
			}
		} catch (SQLException e) {
			System.err.println("Lỗi khi lấy OrderItem theo ID: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	}
}
