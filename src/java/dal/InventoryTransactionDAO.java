package dal;

import Controller.DBConnect;
import java.sql.*;
import java.util.*;
import Model.*;

public class InventoryTransactionDAO {

	//1.  Lấy danh sách Lịch sử giao dịch (Kết hợp Lấy tất cả & Lọc theo SKU / Loại giao dịch)
	public static List<InventoryTransaction> getAllTransactions(String sku, String type) {
		List<InventoryTransaction> list = new ArrayList<>();

		String sql = "SELECT t.*, p.name AS product_name, p.sku AS product_sku, u.full_name AS user_name "
				+ "FROM inventory_transactions t "
				+ "INNER JOIN products p ON t.product_id = p.id "
				+ "LEFT JOIN users u ON t.user_id = u.id "
				+ "WHERE 1=1";

		// A. LỌC THEO MÃ SKU 
		if (sku != null && !sku.trim().isEmpty()) {
			sql += " AND p.sku LIKE ?";
		}

		// B. LỌC THEO LOẠI GIAO DỊCH (Nhập kho / Xuất kho/ Xuất bán)
		if (type != null && !type.trim().isEmpty()) {
			sql += " AND t.transaction_type = ?";
		}

		// C. LUÔN SẮP XẾP MỚI NHẤT LÊN ĐẦU
		sql += " ORDER BY t.created_at DESC";

		try (Connection conn = DBConnect.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			int paramIndex = 1;

			// Gán tham số động theo đúng thứ tự đã nối chuỗi
			if (sku != null && !sku.trim().isEmpty()) {
				String skuParam = "%" + sku.trim() + "%";
				ps.setString(paramIndex++, skuParam);
			}

			if (type != null && !type.trim().isEmpty()) {
				ps.setString(paramIndex++, type);
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					InventoryTransaction tx = new InventoryTransaction();
					tx.setId(rs.getInt("id"));
					tx.setTransactionType(rs.getString("transaction_type"));
					tx.setQuantityChanged(rs.getInt("quantity_changed"));
					tx.setNote(rs.getString("note"));
					tx.setCreatedAt(rs.getTimestamp("created_at"));

					Product product = new Product();
					product.setId(rs.getInt("product_id"));
					product.setName(rs.getString("product_name"));
					product.setSku(rs.getString("product_sku"));
					tx.setProduct(product);

					User user = new User();
					int userId = rs.getInt("user_id");
					if (!rs.wasNull()) {
						user.setId(userId);
						user.setFullName(rs.getString("user_name"));
					}
					tx.setUser(user);

					list.add(tx);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 2. Phương thức thêm Phiếu Nhập Kho (Bao gồm ghi Log + Cộng số lượng)
	public static boolean addImportTransaction(InventoryTransaction tx) {
		Connection conn = null;
		PreparedStatement psInsertLog = null;
		PreparedStatement psUpdateStock = null;
		boolean isSuccess = false;

		String insertLogSQL = "INSERT INTO inventory_transactions (product_id, user_id, transaction_type, quantity_changed, note) VALUES (?, ?, ?, ?, ?)";
		String updateStockSQL = "UPDATE products SET stock = stock + ? WHERE id = ?";

		try {
			conn = DBConnect.getConnection();

			// TẮT AUTO COMMIT ĐỂ BẮT ĐẦU TRANSACTION
			conn.setAutoCommit(false);

			// Bước 1: Thêm dòng lịch sử vào bảng inventory_transactions
			psInsertLog = conn.prepareStatement(insertLogSQL);
			psInsertLog.setInt(1, tx.getProduct().getId());

			if (tx.getUser() != null && tx.getUser().getId() > 0) {
				psInsertLog.setInt(2, tx.getUser().getId());
			} else {
				psInsertLog.setNull(2, java.sql.Types.INTEGER);
			}

			// ĐÃ SỬA: Lấy loại giao dịch trực tiếp từ object tx thay vì fix cứng
			psInsertLog.setString(3, tx.getTransactionType());

			psInsertLog.setInt(4, tx.getQuantityChanged());
			psInsertLog.setString(5, tx.getNote());
			psInsertLog.executeUpdate();

			// Bước 2: Cập nhật cộng thêm số lượng vào bảng products
			psUpdateStock = conn.prepareStatement(updateStockSQL);
			psUpdateStock.setInt(1, tx.getQuantityChanged());
			psUpdateStock.setInt(2, tx.getProduct().getId());
			psUpdateStock.executeUpdate();

			// LƯU LẠI TOÀN BỘ THAY ĐỔI
			conn.commit();
			isSuccess = true;

		} catch (Exception e) {
			e.printStackTrace();
			// NẾU CÓ LỖI XẢY RA, HỦY BỎ TOÀN BỘ (KHÔNG GHI LOG, KHÔNG CỘNG KHO)
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} finally {
			// Đóng các resources
			try {
				if (psInsertLog != null) {
					psInsertLog.close();
				}
				if (psUpdateStock != null) {
					psUpdateStock.close();
				}
				if (conn != null) {
					conn.setAutoCommit(true); // Trả lại trạng thái mặc định
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return isSuccess;
	}

	// 3. Phương thức thêm Phiếu Xuất Kho (Ghi Log)
	public static boolean addExportTransaction(InventoryTransaction tx) {
		boolean isSuccess = false;
		String insertLogSQL = "INSERT INTO inventory_transactions (product_id, user_id, transaction_type, quantity_changed, note) VALUES (?, ?, ?, ?, ?)";
		
		try (Connection conn = DBConnect.getConnection(); PreparedStatement psInsertLog = conn.prepareStatement(insertLogSQL)) {

			psInsertLog.setInt(1, tx.getProduct().getId());

			if (tx.getUser() != null && tx.getUser().getId() > 0) {
				psInsertLog.setInt(2, tx.getUser().getId());
			} else {
				psInsertLog.setNull(2, java.sql.Types.INTEGER);
			}

			psInsertLog.setString(3, tx.getTransactionType());
			psInsertLog.setInt(4, tx.getQuantityChanged());
			psInsertLog.setString(5, tx.getNote());

			int rowsAffected = psInsertLog.executeUpdate();

			if (rowsAffected > 0) {
				isSuccess = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return isSuccess;
	}
}
